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
    private double latitude;
    private double longitude;

    /*Constrcutor voor sqllite db*/
    public TrainingsSchema(int id, int lengte, String naam, String omschrijving, String soort, String lengteSoort, double latitude, double longitude) {
        this.id = id;
        this.lengte = lengte;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.soort = soort;
        this.lengteSoort = lengteSoort;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TrainingsSchema(int lengte, String naam, String omschrijving, String soort, String lengteSoort, double latitude, double longitude) {
        this.lengte = lengte;
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.soort = soort;
        this.lengteSoort = lengteSoort;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected TrainingsSchema(Parcel in) {
        this.id = in.readInt();
        this.lengte = in.readInt();
        this.soort = in.readString();
        this.lengteSoort = in.readString();
        this.naam = in.readString();
        this.omschrijving = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
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
