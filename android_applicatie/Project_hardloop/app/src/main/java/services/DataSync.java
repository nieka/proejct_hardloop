package services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;

import com.android.volley.VolleyError;
import com.example.niek.project_hardloop.HomeActivity;
import com.example.niek.project_hardloop.LoginActivity;
import com.example.niek.project_hardloop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Entity.TrainingsSchema;
import Entity.User;
import controllers.ApiConnector;
import controllers.DatabaseHandler;
import interfaces.NetworkReqeust;
/*
* service die kijkt of de gebruiker een internet connectie heeft en zoja kijkt of er gesynced met de server moet worden en zoja het doet.
* */
public class DataSync extends Service implements NetworkReqeust{
    private static final int DELAY = 20*1000; // 1 minut
    private static final int UNID = 1;

    public static final String DATAFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String EDITDATESP = "editdate";

    /*network reqeust tags*/
    private static final String TAG_EditDate = "editDate";
    private static final String TAG_getTrainingen = "tetTrainingen";
    private static final String TAG_POSTTRAINING = "postTrainingen";

    /*boradcast reqeust tags*/
    public static final String DATASYNC_RESULT = "datasync";
    public static final String DATASYNC_MESSAGE = "datasync_Message";
    public static final String DATASYNC_restart = "datasync_restart";


    private ApiConnector apiConnector;
    private DatabaseHandler databaseHandler;
    private SharedPreferences sharedPreferences;
    private NotificationManager mNotificationManager;
    private LocalBroadcastManager broadcaster;
    private String s_localEditDate;
    private Date serverEditDate;
    private Date localEditDate;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        apiConnector = new ApiConnector(this,this);
        databaseHandler = new DatabaseHandler(this);
        broadcaster = LocalBroadcastManager.getInstance(this);

        getEditDate();
        SimpleDateFormat format = new SimpleDateFormat(DATAFORMAT);

        try {
            s_localEditDate = sharedPreferences.getString(EDITDATESP, "1990-01-01 00:00:00");
            localEditDate = format.parse(sharedPreferences.getString(EDITDATESP,"1990-01-01 00:00:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*Krijgt de datum van de server dat die voor het laatst bijgewerkt is*/
    private void getEditDate(){
        setNotificationIcon();
        apiConnector.getReqeust("getLastEditdate.php", TAG_EditDate);
    }

    /*Synced de records met de server*/
    private void updateRecords(){
        setNotificationIcon();
        if(localEditDate.after(serverEditDate)){
            System.out.println("update to server");
            //de locale trainingen zijn de nieuwste en moeten op de server gezet worden.
            List<TrainingsSchema> trainingsSchemaList = databaseHandler.getTrainingSchemas("SELECT  * FROM " + DatabaseHandler.TABLE_TRAININGSSCHEMA);
            User user = databaseHandler.getUser();
            String json = "[";
            for(int i =0; i< trainingsSchemaList.size(); i++){
                JSONObject jsonObject = new JSONObject();
                TrainingsSchema schema = trainingsSchemaList.get(i);
                try {
                    jsonObject.put("id", schema.getId());
                    jsonObject.put("lengte", schema.getLengte());
                    jsonObject.put("soort", schema.getSoort());
                    jsonObject.put("lengteSoort", schema.getLengteSoort());
                    jsonObject.put("naam", schema.getNaam());
                    jsonObject.put("omschrijving", schema.getOmschrijving());
                    jsonObject.put("editDate", s_localEditDate);
                    jsonObject.put("userid", user.getId());
                    json = json + jsonObject.toString();

                    if(i < trainingsSchemaList.size() -1){
                        json += ",";
                    } else {
                        json += "]";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            apiConnector.postReqeust("posttraining.php",json,TAG_POSTTRAINING);

        } else {
            //De trainingen op de server zijn het nieuwst en moeten in de sqllite data base gezet worden
            System.out.println("get trainingen from server");
            User user = databaseHandler.getUser();
            String url = "getTrainingen.php?ID=" + user.getId();
            System.out.println(url);
            apiConnector.getReqeust(url,TAG_getTrainingen);
        }
    }

    /*Send een broadcast naar de homeactivity dat de traininglijst is aangepast en dus geupdate moet worden, kan ook gebruikt worden voor andere reqeust in de toekomst.*/
    private void sendResult(String message) {
        Intent intent = new Intent(DATASYNC_RESULT);
        if(message != null)
            intent.putExtra(DATASYNC_MESSAGE, message);
        broadcaster.sendBroadcast(intent);
    }

    private void setNotificationIcon(){
        //Voeg notificatie icon doe aan bar als teken dat er foto's genomen worden
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_cloud_upload_white_24dp)
                        .setContentTitle(getResources().getString(R.string.uploading))
                        .setContentText(getResources().getString(R.string.upload_long));
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, LoginActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(HomeActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager =
                (NotificationManager) getSystemService(DataSync.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(UNID, mBuilder.build());
    }

    /*Zet de trainingslijst in de locale sql lite database*/
    private void setTraininglist(JSONArray jsonArray){
        databaseHandler.removeAllTrainingschemas();
        for(int i=0; i< jsonArray.length(); i++){
            try {
                JSONObject element = jsonArray.getJSONObject(i);
                TrainingsSchema trainingsSchema = new TrainingsSchema(element.getInt("id"), element.getInt("lengte"),element.getString("naam"),element.getString("omschrijving"),
                        element.getString("soort"),element.getString("lengteSoort"));
                sharedPreferences.edit().putString(EDITDATESP, element.getString("editDate")).apply();
                databaseHandler.addTrainingsSchema(trainingsSchema);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        sendResult(DATASYNC_restart);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*Word aangeroepen als er een error is met een netwerk reqeust*/
    @Override
    public void onError(VolleyError response, String tag) {
        mNotificationManager.cancel(UNID);
        response.printStackTrace();
    }

    /*Wordt aangeroepen als een netwerk reqeust goed ging*/
    @Override
    public void onSucces(String response, String tag) {
        mNotificationManager.cancel(UNID);
        switch (tag){
            case TAG_EditDate:
                SimpleDateFormat format = new SimpleDateFormat(DATAFORMAT);
                try {
                    if(!response.equals("")){
                        serverEditDate = format.parse(response);
                    } else {
                        serverEditDate = format.parse("1990-01-01 00:00:00");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    public void run() {
                        System.out.println("local edit date= " + localEditDate.toString() + " server edit date= " + serverEditDate.toString());
                        if(!localEditDate.equals(serverEditDate)){
                            if(ApiConnector.isNetworkAvailable(DataSync.this) && sharedPreferences.getBoolean("internetUse",false)){
                                // De gebruiker heeft een internet connectie en heeft in de instelling gezet dat er naast wifi ook 3g gebruikt mag worden voor de network taken
                                 updateRecords();
                            } else if(ApiConnector.hasWifiConnection(DataSync.this) && !sharedPreferences.getBoolean("internetUse",false)){
                                // De gebruiker heeft wifi en heeft gezecht alleen wifi te gebruiken.
                                 updateRecords();
                            }
                        }
                        h.postDelayed(this, DELAY);
                    }
                }, DELAY);
                break;
            case TAG_getTrainingen:
                try {
                    setTraininglist(new JSONArray(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case TAG_POSTTRAINING:
                serverEditDate = localEditDate;
        }
    }
}
