package interfaces;


import java.util.List;

import Entity.TrainingsSchema;

public interface HomeView {

    void showTrainingsSchema();

    void toLogin();

    void loadTrainingData(List<TrainingsSchema> list);
}
