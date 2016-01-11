package Entity;

/**
 * Created by niek on 12-11-2015.
 * model class to keep track of a gps point in the sqlite database
 */
public class gpsPoint {
    private int id;
    private int trainingid;
    private float longitude;
    private float latidute;
    private float heading;
    private String tijd;

    public gpsPoint(int id, int trainingid, float longitude, float latidute, String tijd, float heading) {
        this.id = id;
        this.trainingid = trainingid;
        this.longitude = longitude;
        this.latidute = latidute;
        this.tijd = tijd;
        this.heading = heading;
    }




    public float getHeading() {
        return heading;
    }

    public void setHeading(float heading) {
        this.heading = heading;
    }

    public float getLatidute() {
        return latidute;
    }

    public void setLatidute(float latidute) {
        this.latidute = latidute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainingid() {
        return trainingid;
    }

    public void setTrainingid(int trainingid) {
        this.trainingid = trainingid;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getTijd() {
        return tijd;
    }

    public void setTijd(String tijd) {
        this.tijd = tijd;
    }


}
