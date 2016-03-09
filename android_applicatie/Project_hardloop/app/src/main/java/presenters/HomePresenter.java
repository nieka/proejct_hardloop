package presenters;

import java.util.List;

import Entity.TrainingsSchema;
import controllers.DatabaseHandler;
import interfaces.HomeView;
public class HomePresenter {

    private DatabaseHandler databaseHandler;
    private HomeView view;
    private List<TrainingsSchema> trainingen;
    private TrainingsSchema selectedTraining;

    public HomePresenter(DatabaseHandler databaseHandler, HomeView view){
        this.databaseHandler = databaseHandler;
        this.view = view;
    }

    /*Haalt de training schema's van de sql lite database op*/
    public void loadTrainingsSchemas(){
        trainingen = databaseHandler.getTrainingSchemas("SELECT  * FROM " + DatabaseHandler.TABLE_TRAININGSSCHEMA);
        view.loadTrainingData(trainingen);
    }

    public void deleteTraining(){
        if(selectedTraining != null){
            databaseHandler.removeTrainingSchema(selectedTraining.getId());
        }
    }

    public void deleteTraining(int position){
        databaseHandler.removeTrainingSchema(trainingen.get(position).getId());
    }

    public Boolean trainingSelected(){
        return selectedTraining != null;
    }

    public TrainingsSchema getSelectedTraining(){
        return selectedTraining;
    }

    public void setSelectedTraining(TrainingsSchema training){
        this.selectedTraining = training;
    }

    public TrainingsSchema getTrainingSchemaAtPosition(int position){
        selectedTraining = trainingen.get(position);
        return selectedTraining;
    }

    public void uitloggen(){
        databaseHandler.removeUser();
        view.toLogin();
    }
}
