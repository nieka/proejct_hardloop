package presenters;

import controllers.DatabaseHandler;
import interfaces.HomeView;

/**
 * Created by niek on 16-2-2016.
 */
public class HomePresenter {

    private DatabaseHandler databaseHandler;
    private HomeView view;

    public HomePresenter(DatabaseHandler databaseHandler, HomeView view){
        this.databaseHandler = databaseHandler;
        this.view = view;
    }

    public void loadTrainingsSchemas(){
        view.loadTrainingData(databaseHandler.getTrainingSchemas("SELECT  * FROM " + DatabaseHandler.TABLE_TRAININGSSCHEMA));
    }

    public void uitloggen(){
        databaseHandler.removeUser();
        view.toLogin();
    }
}
