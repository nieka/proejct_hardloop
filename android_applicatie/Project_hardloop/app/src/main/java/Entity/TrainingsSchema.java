package Entity;

/**
 * Created by niek on 12-11-2015.
 * model voor workout
 */
public class TrainingsSchema {
    private int id;
    private int lengte;
    private String naam;
    private String omschrijving;

    public TrainingsSchema(int id, int lengte, String naam, String omschrijving) {
        this.id = id;
        this.lengte = lengte;
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLengte() {
        return lengte;
    }

    public void setLengte(int lengte) {
        this.lengte = lengte;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }


}
