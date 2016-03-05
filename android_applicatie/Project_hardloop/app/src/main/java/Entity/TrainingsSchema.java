package Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by niek on 12-11-2015.
 * Model voor de trainingschema informatie uit de sql lite database te halen en in een class te zetten en de informatie te kunnen gebruiken.
 * Waarbij de class Parcelable is om hem door te kunnen geven als de orientatie van de telefoon verandert
 */
public class TrainingsSchema implements Parcelable {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLengte() {
        return lengte;
    }

    public String getNaam() {
        return naam;
    }

    public String getSoort() {
        return soort;
    }

    public String getLengteSoort() {
        return lengteSoort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.lengte);
        dest.writeString(this.soort);
        dest.writeString(this.lengteSoort);
        dest.writeString(this.naam);
        dest.writeString(this.omschrijving);
    }

    protected TrainingsSchema(Parcel in) {
        this.id = in.readInt();
        this.lengte = in.readInt();
        this.soort = in.readString();
        this.lengteSoort = in.readString();
        this.naam = in.readString();
        this.omschrijving = in.readString();
    }

    public static final Creator<TrainingsSchema> CREATOR = new Creator<TrainingsSchema>() {
        public TrainingsSchema createFromParcel(Parcel source) {
            return new TrainingsSchema(source);
        }

        public TrainingsSchema[] newArray(int size) {
            return new TrainingsSchema[size];
        }
    };
}
