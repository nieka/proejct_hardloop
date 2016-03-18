package presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import Entity.TrainingsSchema;
import controllers.DatabaseHandler;
import interfaces.EditView;
import services.DataSync;

/**
 * Created by niek on 18-3-2016.
 */
public class EditSchemaPresenter {

    private EditView view;
    private DatabaseHandler databaseHandler;
    private SharedPreferences sharedPreferences;

    public EditSchemaPresenter(EditView editView, Context context){
        this.view = editView;
        this.databaseHandler = new DatabaseHandler(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void editTraining(int id,int lengte, String naam, String omschrijving, String soort, String lengteSoort, double latitude, double longitude){
        TrainingsSchema trainingsSchema = new TrainingsSchema(lengte,naam,omschrijving,soort,lengteSoort, latitude, longitude);
        databaseHandler.removeTrainingSchema(id);
        databaseHandler.addTrainingsSchema(trainingsSchema);
        SimpleDateFormat format = new SimpleDateFormat(DataSync.DATAFORMAT);
        sharedPreferences.edit().putString(DataSync.EDITDATESP, format.format(new Date()) ).apply();
        view.goToHome();
        view = null;
    }
}
