package edu.shsu.truck_it;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeremey on 11/19/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "truckit.db";
    public static final String TABLE_NAME1 = "users";
    public static final String TABLE_NAME2 = "drivers";
    public static final String TABLE_NAME3 = "trips";
    public static final String USERID = "userID";
    public static final String USERNAME = "userName";
    public static final String USERPASS = "userPassword";
    public static final String USERPHONE = "userPhone";
    public static final String USEREMAIL = "userEmail";
    public static final String DRIVERID = "driverID";
    public static final String DRIVERNAME = "driverName";
    public static final String DRIVERPASS = "driverPassword";
    public static final String DRIVERPHONE = "driverPhone";
    public static final String DRIVEREMAIL = "driverEmail";
    public static final String INSURANCE = "driverIns";
    public static final String DRIVERLICENSE = "driverLicense";
    public static final String VEHICLE_TYPE = "vehicleType";
    public static final String TRIPID = "tripID";
    public static final String ORIGIN = "origin";
    public static final String DESTINATION = "destination";
    public static final String DISTANCE = "miles";
    public static final String CHARGE = "amount";
    public static final String DATE = "date";
    public static final String TIME = "time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ TABLE_NAME1 + " (userID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT, userPassword TEXT, userPhone INTEGER UNIQUE, userEmail TEXT UNIQUE)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME2 + " (driverID INTEGER PRIMARY KEY AUTOINCREMENT, driverName TEXT, driverPassword TEXT, driverPhone INTEGER UNIQUE, driverEmail TEXT UNIQUE, driversIns TEXT, driverLicense TEXT UNIQUE, vehicleType INTEGER)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME3 + " (tripID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, driverID INTEGER, origin TEXT, destination TEXT, miles DOUBLE, amount DOUBLE, date TEXT, time TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME1);
        onCreate(db);
    }
  }
