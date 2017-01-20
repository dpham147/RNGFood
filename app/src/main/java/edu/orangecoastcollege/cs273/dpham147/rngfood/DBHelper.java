package edu.orangecoastcollege.cs273.dpham147.rngfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    private static final String DATABASE_NAME = "RNGFood";
    static final String DATABASE_TABLE = "Locations";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_LOCATION = "name";

    public DBHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_LOCATION + " TEXT" + ")";
        database.execSQL (table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    // Create a method to add a brand new task to DB
    public void addLoc(Location newLoc) {
        // Step 1) Create a reference to the DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 2) Make a key-value pair for each value you want to insert
        ContentValues values = new ContentValues();
        // values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_LOCATION, newLoc.getLocation());

        // Step 3) Insert values into db
        db.insert(DATABASE_TABLE, null, values);

        // Step 4) Close the DB
        db.close();
    }

    // Create a method to get all the tasks
    public ArrayList<Location> getAllLocations() {
        // Step 1) Create reference to DB
        SQLiteDatabase db = this.getReadableDatabase();

        // Step 2) Create new empty ArrayList
        ArrayList<Location> allLocations = new ArrayList<>();

        // Step 3) Query the DB for all records (all rows) and all fields (all col)
        // The return type of a query is a Cursor
        Cursor results = db.query(DATABASE_TABLE, null, null, null, null, null, null);

        // Step 4) Loop through results and create Task objects to add to ArrayList
        if (results.moveToFirst()) {
            do {
                int id = results.getInt(0);
                String location = results.getString(1);

                allLocations.add(new Location(id, location));
            } while (results.moveToNext());
        }

        db.close();
        return allLocations;
    }

    public void deleteAllLocations()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }

}
