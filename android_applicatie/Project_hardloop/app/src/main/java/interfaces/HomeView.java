package interfaces;


import java.util.List;

import Entity.TrainingsSchema;

public interface HomeView {

    void toLogin();

    void loadTrainingData(List<TrainingsSchema> list);
}
