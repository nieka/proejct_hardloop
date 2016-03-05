package controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Entity.TrainingsSchema;
import Entity.User;
public class DatabaseHandler extends SQLiteOpenHelper {

    //stattic variable
    private final static String DATABASE_NAME = "project_Hardloop";
    private final static int DATABASE_VERSION = 1;

    //database table naams
    public final static String TABLE_TRAININGSSCHEMA = "trainingsSchema";
    public final static String TABLE_USER = "user";

    //trainingsschema table collum names
    private final static String TS_KEY_ID = "trainingsSchema_id";
    private final static String TS_KEY_LENGTH= "trainingsSchema_lengte";
    private final static String TS_KEY_NAAM= "trainingsSchema_naam";
    private final static String TS_KEY_OMSCHRIJVING= "trainingsSchema_omschrijving";
    private final static String TS_KEY_SOORT= "trainingsSchema_soort";
    private final static String TS_KEY_LENGTESOORT= "trainingsSchema_lengte_soort";
    //user table collum names
    private final static String U_KEY_USERID = "userid";
    private final static String U_KEY_EMAIL = "email";
    private final static String U_KEY_displayname = "displayname";
    private final static String U_KEY_photourl = "photourl";


    //table create statments
    //trainingsschema create statment
    private final static String CREATE_TRAININGSSCHEMA = "CREATE TABLE " + TABLE_TRAININGSSCHEMA
            +"(" + TS_KEY_ID + " INTEGER PRIMARY KEY," + TS_KEY_LENGTH + " INTEGER,"
            + TS_KEY_NAAM + " TEXT," + TS_KEY_OMSCHRIJVING  + " TEXT," +TS_KEY_SOORT + " TEXT," + TS_KEY_LENGTESOORT + " REAL" + ")";
    //user create statment
    private final static String CREATE_USER = "CREATE TABLE " + TABLE_USER
            +" (" + U_KEY_USERID + " TEXT PRIMARY KEY," + U_KEY_EMAIL + " TEXT,"
            + U_KEY_displayname + " TEXT," + U_KEY_photourl + " TEXT" + ")";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables
        db.execSQL(CREATE_TRAININGSSCHEMA);
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAININGSSCHEMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // create new tables
        onCreate(db);
    }

    /*----------------------CRUD METHODES----------------------*/

    //crud methodes uers
    public void setUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(U_KEY_USERID, user.getId());
        values.put(U_KEY_displayname, user.getDisplayName());
        values.put(U_KEY_EMAIL, user.getEmail());
        values.put(U_KEY_photourl, user.getPhotoUrl());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    public int getUserCount(){
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int aantal = cursor.getCount();
        cursor.close();

        // return count
        return aantal;
    }

    public User getUser(){
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //colum ids
        int idcolum = cursor.getColumnIndex(U_KEY_USERID);
        int emailcolum = cursor.getColumnIndex(U_KEY_EMAIL);
        int naamcolum = cursor.getColumnIndex(U_KEY_displayname);
        int photocolum = cursor.getColumnIndex(U_KEY_photourl);
        cursor.moveToFirst();
        User user = new User(cursor.getString(idcolum),cursor.getString(emailcolum),cursor.getString(naamcolum),cursor.getString(photocolum));
        cursor.close();
        return user;
    }

    public void removeUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_USER, "", null);
    }

    //crud methodes trainingsschema
    public void removeAllTrainingschemas(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_TRAININGSSCHEMA, "", null);
    }

    public void addTrainingsSchema(TrainingsSchema trainingsSchema){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        if(trainingsSchema.getId() != 0){
            values.put(TS_KEY_ID, trainingsSchema.getId());
        }
        values.put(TS_KEY_NAAM, trainingsSchema.getNaam());
        values.put(TS_KEY_OMSCHRIJVING, trainingsSchema.getOmschrijving());
        values.put(TS_KEY_SOORT, trainingsSchema.getSoort());
        values.put(TS_KEY_LENGTH, trainingsSchema.getLengte());
        values.put(TS_KEY_LENGTESOORT, trainingsSchema.getLengteSoort());
        // Inserting Row
        db.insert(TABLE_TRAININGSSCHEMA, null, values);
        db.close(); // Closing database connection
    }

     public List<TrainingsSchema> getTrainingSchemas(String query){
         List<TrainingsSchema> list = new ArrayList<>();
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery(query, null);
         int colomIndexId = cursor.getColumnIndex(TS_KEY_ID);
         int colomIndexNaam = cursor.getColumnIndex(TS_KEY_NAAM);
         int colomIndexOmschrijving = cursor.getColumnIndex(TS_KEY_OMSCHRIJVING);
         int colomIndexSoort = cursor.getColumnIndex(TS_KEY_SOORT);
         int colomIndexLength = cursor.getColumnIndex(TS_KEY_LENGTH);
         int colomIndexLegnthSoort = cursor.getColumnIndex(TS_KEY_LENGTESOORT);

         cursor.moveToFirst();
         for(int i=0; i< cursor.getCount(); i++){
             TrainingsSchema traingingSchema = new TrainingsSchema(cursor.getInt(colomIndexId),cursor.getInt(colomIndexLength)
                     ,cursor.getString(colomIndexNaam),cursor.getString(colomIndexOmschrijving),
                     cursor.getString(colomIndexSoort), cursor.getString(colomIndexLegnthSoort));
             list.add(traingingSchema);
             cursor.moveToNext();
         }
         cursor.close();

         return list;
    }

    public void removeTrainingSchema(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_TRAININGSSCHEMA, TS_KEY_ID + "=" + id, null );
    }
}
