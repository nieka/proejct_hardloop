package interfaces;

import java.util.List;

import Entity.TrainingsSchema;
/**
 * Created by niek on 25-12-2015.
 * Interface met de callbacks die gebruikt worden door de home presenter
 */
public interface HomeView {

    void toLogin();

    void loadTrainingData(List<TrainingsSchema> list);
}
