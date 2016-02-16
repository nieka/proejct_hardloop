package controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Entity.TrainingsSchema;
import Entity.User;

public class DatabaseHandler extends SQLiteOpenHelper {

    //stattic variable
    private final static String DATABASE_NAME = "project_Hardloop";

    private final static int DATABASE_VERSION = 1;

    //database table naams
    public final static String TABLE_TRAININGSSCHEMA = "trainingsSchema";
    public final static String TABLE_TRAINING = "training";
    public final static String TABLE_GPSPOINT = "GPSPoint";
    public final static String TABLE_USER = "user";

    //trainingsschema table collum names
    private final static String TS_KEY_ID = "trainingsSchema_id";
    private final static String TS_KEY_LENGTH= "trainingsSchema_lengte";
    private final static String TS_KEY_NAAM= "trainingsSchema_naam";
    private final static String TS_KEY_OMSCHRIJVING= "trainingsSchema_omschrijving";
    private final static String TS_KEY_SOORT= "trainingsSchema_soort";
    private final static String TS_KEY_LENGTESOORT= "trainingsSchema_lengte_soort";
    //Trainings table collum names
    private final static String T_KEY_ID = "trainings_id";
    private final static String T_KEY_TIJD = "trainings_tijd";
    private final static String T_KEY_DATUM = "trainings_datum";
    //gpspoint table collum names
    private final static String P_KEY_ID = "gpsPoint_id";
    private final static String P_KEY_TRAININGID = "gpsPoint_trainingid";
    private final static String P_KEY_LONGITUDE = "gpsPoint_longitude";
    private final static String P_KEY_LATIDUTE = "gpsPoint_latidute";
    private final static String P_KEY_TIJD = "gpsPoint_tijd";
    private final static String P_KEY_HEADING = "gpsPoint_heading";
    //user table collum names
    private final static String U_KEY_USERID = "userid";
    private final static String U_KEY_EMAIL = "email";
    private final static String U_KEY_displayname = "displayname";
    private final static String U_KEY_photourl = "photourl";


    //table create statments
    //trainingsschema create statment
    //int lengte, String naam, String omschrijving, String soort, String lengteSoort
    private final static String CREATE_TRAININGSSCHEMA = "CREATE TABLE " + TABLE_TRAININGSSCHEMA
            +"(" + TS_KEY_ID + " INTEGER PRIMARY KEY," + TS_KEY_LENGTH + " INTEGER,"
            + TS_KEY_NAAM + " TEXT," + TS_KEY_OMSCHRIJVING  + " TEXT," +TS_KEY_SOORT + " TEXT," + TS_KEY_LENGTESOORT + " REAL" + ")";
    //training create statment
    private final static String CREATE_TRAINING = "CREATE TABLE " + TABLE_TRAINING
            +" (" + T_KEY_ID + " INTEGER PRIMARY KEY," + T_KEY_DATUM + " DATETIME,"
            + T_KEY_TIJD + " REAL" + ")";
    //gpspoint create statment
    private final static  String CREATE_GPSPOINT = "CREATE TABLE " + TABLE_GPSPOINT
            +" (" + P_KEY_ID + " INTEGER PRIMARY KEY," + P_KEY_TRAININGID + " INTEGER,"
            + P_KEY_LATIDUTE + " REAL," + P_KEY_LONGITUDE + " REAL,"
            + P_KEY_HEADING + " REAL," + P_KEY_TIJD + " REAL" + ")";
    //user create statment
    private final static String CREATE_USER = "CREATE TABLE " + TABLE_USER
            +" (" + U_KEY_USERID + " TEXT PRIMARY KEY," + U_KEY_EMAIL + " TEXT,"
            + U_KEY_displayname + " TEXT," + U_KEY_photourl + " TEXT" + ")";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables
        // TODO: 21-1-2016 Zorgen dat de create statments kloppen bij de inhoud 
        db.execSQL(CREATE_TRAININGSSCHEMA);
       // db.execSQL(CREATE_TRAINING);
       // db.execSQL(CREATE_GPSPOINT);
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAININGSSCHEMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GPSPOINT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // create new tables
        onCreate(db);
    }

    /*----------------------CRUD METHODES----------------------*/

    //crud methodes uers
    public void setUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(U_KEY_USERID, user.getId());
        values.put(U_KEY_displayname, user.getDisplayName());
        values.put(U_KEY_EMAIL, user.getEmail());
        values.put(U_KEY_photourl, user.getPhotoUrl());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    public int getUserCount(){
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int aantal = cursor.getCount();
        cursor.close();

        // return count
        return aantal;
    }

    //crud methodes trainingsschema
    public void addTrainingsSchema(TrainingsSchema trainingsSchema){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //id wordt niet gezet zodat voor nu er automatish een uniek id ana owrd gegeven.
        //Dit zorgt er wel voor dat het meer CPU, io en overheat veroorzaakt waardoor er een ander oplossing voor gevonden moet worden
        // TODO: 9-2-2016 Een oplossing vinden voor de id trainingschema
       // values.put(TS_KEY_ID, trainingsSchema.getId());
        values.put(TS_KEY_NAAM, trainingsSchema.getNaam());
        values.put(TS_KEY_OMSCHRIJVING, trainingsSchema.getOmschrijving());
        values.put(TS_KEY_SOORT, trainingsSchema.getSoort());
        values.put(TS_KEY_LENGTH, trainingsSchema.getLengte());
        values.put(TS_KEY_LENGTESOORT, trainingsSchema.getLengteSoort());
        // Inserting Row
        db.insert(TABLE_TRAININGSSCHEMA, null, values);
        db.close(); // Closing database connection
    }

     public List<TrainingsSchema> getTrainingSchemas(String query){
         List<TrainingsSchema> list = new ArrayList<>();
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery(query, null);
         int colomIndexId = cursor.getColumnIndex(TS_KEY_ID);
         int colomIndexNaam = cursor.getColumnIndex(TS_KEY_NAAM);
         int colomIndexOmschrijving = cursor.getColumnIndex(TS_KEY_OMSCHRIJVING);
         int colomIndexSoort = cursor.getColumnIndex(TS_KEY_SOORT);
         int colomIndexLength = cursor.getColumnIndex(TS_KEY_LENGTH);
         int colomIndexLegnthSoort = cursor.getColumnIndex(TS_KEY_LENGTESOORT);

         cursor.moveToFirst();
         for(int i=0; i< cursor.getCount(); i++){
             TrainingsSchema traingingSchema = new TrainingsSchema(cursor.getInt(colomIndexId),cursor.getInt(colomIndexLength)
                     ,cursor.getString(colomIndexNaam),cursor.getString(colomIndexOmschrijving),
                     cursor.getString(colomIndexSoort), cursor.getString(colomIndexLegnthSoort));
             list.add(traingingSchema);
             cursor.moveToNext();
         }
         cursor.close();

         return list;
     }

    public int getTrainingsSchemaCount(){
        String countQuery = "SELECT  * FROM " + TABLE_TRAININGSSCHEMA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int aantal = cursor.getCount();
        cursor.close();

        // return count
        return aantal;
    }
}
