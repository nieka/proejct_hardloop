package controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import model.TrainingsSchema;

/**
 * Created by niek on 13-11-2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //stattic variable
    private final static String DATABASE_NAME = "project_Hardloop";

    private final static int DATABASE_VERSION = 1;

    //database table naams
    private final static String TABLE_TRAININGSSCHEMA = "trainingsSchema";
    private final static String TABLE_TRAINING = "training";
    private final static String TABLE_GPSPOINT = "GPSPoint";

    //trainingsschema table collum names
    private final static String TS_KEY_ID = "trainingsSchema_id";
    private final static String TS_KEY_LENGTH= "trainingsSchema_afstand";
    private final static String TS_KEY_NAAM= "trainingsSchema_naam";
    private final static String TS_KEY_OMSCHRIJVING= "trainingsSchema_omschrijving";
    //Trainings table collum names
    private final static String T_KEY_ID = "trainings_id";
    private final static String T_KEY_TIJD = "trainings_tijd";
    private final static String T_KEY_DATUM = "trainings_datum";
    //gpspoint table collum names
    private final static String P_KEY_ID = "gpsPoint_id";
    private final static String P_KEY_TRAININGID = "gpsPoint_trainingid";
    private final static String P_KEY_LONGITUDE = "gpsPoint_longitude";
    private final static String P_KEY_LATIDUTE = "gpsPoint_latidute";
    private final static String P_KEY_TIJD = "gpsPoint_tijd";
    private final static String P_KEY_HEADING = "gpsPoint_heading";

    //table create statments
    //trainingsschema create statment
    private final static String CREATE_TRAININGSSCHEMA = "CREATE TABLE " + TABLE_TRAININGSSCHEMA
            +"(" + TS_KEY_ID + " INTEGER PRIMARY KEY," + TS_KEY_LENGTH + " INTEGER,"
            + TS_KEY_NAAM + " TEXT," + TS_KEY_OMSCHRIJVING  + " TEXT" + ")";
    //training create statment
    private final static String CREATE_TRAINING = "CREATE TABLE" + TABLE_TRAINING
            +" (" + T_KEY_ID + " INTEGER PRIMARY KEY," + T_KEY_DATUM + " DATETIME,"
            + T_KEY_TIJD + " REAL" + ")";
    //gpspoint create statment
    private final static  String CREATE_GPSPOINT = "CREATE TABLE" + TABLE_GPSPOINT
            +" (" + P_KEY_ID + " INTEGER PRIMARY KEY," + P_KEY_TRAININGID + " INTEGER,"
            + P_KEY_LATIDUTE + " REAL," + P_KEY_LONGITUDE + " REAL,"
            + P_KEY_HEADING + " REAL," + P_KEY_TIJD + " REAL" + ")";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables
        db.execSQL(CREATE_TRAININGSSCHEMA);
        db.execSQL(CREATE_TRAINING);
        db.execSQL(CREATE_GPSPOINT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TRAININGSSCHEMA);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TRAINING);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_GPSPOINT);

        // create new tables
        onCreate(db);
    }

    //crud methodes
    public void createTrainingsSchema(TrainingsSchema trainingsSchema){

    }
}
