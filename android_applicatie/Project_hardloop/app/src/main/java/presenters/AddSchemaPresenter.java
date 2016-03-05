package presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import Entity.TrainingsSchema;
import controllers.DatabaseHandler;
import interfaces.TrainingsSchemaToevoegenPresenter;
import services.DataSync;

public class AddSchemaPresenter {

    private TrainingsSchemaToevoegenPresenter view;
    private DatabaseHandler databaseHandler;
    private SharedPreferences sharedPreferences;

    public AddSchemaPresenter(TrainingsSchemaToevoegenPresenter view, DatabaseHandler databaseHandler, Context context){
        this.view = view;
        this.databaseHandler = databaseHandler;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /*Voegt training schema toe aan de sql lite database*/
    public void addTrainingSchema(int lengte, String naam, String omschrijving, String soort, String lengteSoort){
        TrainingsSchema trainingsSchema = new TrainingsSchema(lengte,naam,omschrijving,soort,lengteSoort);
        databaseHandler.addTrainingsSchema(trainingsSchema);
        SimpleDateFormat format = new SimpleDateFormat(DataSync.DATAFORMAT);
        sharedPreferences.edit().putString(DataSync.EDITDATESP, format.format(new Date()) ).apply();
        view.goToHome();
        view = null;
    }
}
