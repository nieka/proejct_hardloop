package presenters;

import Entity.TrainingsSchema;
import controllers.DatabaseHandler;
import interfaces.TrainingsSchemaToevoegenPresenter;

/**
 * Created by niek on 9-2-2016.
 */
public class AddSchemaPresenter {

    private TrainingsSchemaToevoegenPresenter view;
    private DatabaseHandler databaseHandler;

    public AddSchemaPresenter(TrainingsSchemaToevoegenPresenter view, DatabaseHandler databaseHandler ){
        this.view = view;
        this.databaseHandler = databaseHandler;
    }
    //int lengte, String naam, String omschrijving, String soort, String lengteSoort
    public void addTrainingSchema(int lengte, String naam, String omschrijving, String soort, String lengteSoort){
        TrainingsSchema trainingsSchema = new TrainingsSchema(lengte,naam,omschrijving,soort,lengteSoort);
        databaseHandler.addTrainingsSchema(trainingsSchema);
        view.goToHome();
        view = null;
    }
}
