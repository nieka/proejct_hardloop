package Entity;

/**
 * Created by niek on 12-11-2015.
 * model voor workout
 */
public class TrainingsSchema {
    private int id;
    private int lengte;
    private String soort;
    private String lengteSoort;
    private String naam;
    private String omschrijving;

    /*Constrcutor voor sqllite db*/
    public TrainingsSchema(int id, int lengte, String naam, String omschrijving, String soort, String lengteSoort) {
        this.id = id;
        this.lengte = lengte;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.soort = soort;
        this.lengteSoort = lengteSoort;
    }

    public TrainingsSchema(int lengte, String naam, String omschrijving, String soort, String lengteSoort) {
        this.lengte = lengte;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.soort = soort;
        this.lengteSoort = lengteSoort;
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

    public String getSoort() {
        return soort;
    }

    public String getLengteSoort() {
        return lengteSoort;
    }
}
