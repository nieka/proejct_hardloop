package Entity;

/**
 * Created by niek on 12-11-2015.
 * model class Training
 */
public class Training {
    private TrainingsSchema trainingsSchema;
    private int id;
    private float tijd;
    private String datum;

    public Training(int id,TrainingsSchema trainingsSchema, float tijd, String datum) {
        this.id = id;
        this.trainingsSchema = trainingsSchema;
        this.tijd = tijd;
        this.datum = datum;
    }

    public Training(float tijd, String datum) {
        this.tijd = tijd;
        this.datum = datum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public TrainingsSchema getTrainingsSchema() {
        return trainingsSchema;
    }

    public void setTrainingsSchema(TrainingsSchema trainingsSchema) {
        this.trainingsSchema = trainingsSchema;
    }

    public float getTijd() {
        return tijd;
    }

    public void setTijd(float tijd) {
        this.tijd = tijd;
    }


}
