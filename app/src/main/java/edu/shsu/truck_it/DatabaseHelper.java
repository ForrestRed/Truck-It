package edu.shsu.truck_it;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public static final String INSURANCE = "driversIns";
    public static final String DRIVERLICENSE = "driverLicense";
    public static final String VEHICLE_TYPE = "vehicleType";
    public static final String TRIPID = "tripID";
    public static final String ORIGIN = "origin";
    public static final String DESTINATION = "destination";
    public static final String DISTANCE = "miles";
    public static final String CHARGE = "amount";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String DETAILS = "details";
    public static final String JOBPENDING = "jobPending";
    public static final String STATUS = "status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ TABLE_NAME1 + " (userID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT, userPassword TEXT, userPhone INTEGER UNIQUE, userEmail TEXT UNIQUE)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME2 + " (driverID INTEGER PRIMARY KEY AUTOINCREMENT, driverName TEXT, driverPassword TEXT, driverPhone INTEGER UNIQUE, driverEmail TEXT UNIQUE, driversIns TEXT, driverLicense TEXT UNIQUE, vehicleType INTEGER)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME3 + " (tripID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, driverID INTEGER, origin TEXT, destination TEXT, miles DOUBLE, amount DOUBLE, date TEXT, time TEXT, details TEXT, jobPending INTEGER, status INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        //if(newVersion > oldVersion)
            //db.execSQL("ALTER TABLE trips ADD COLUMN details TEXT");
        //if(newVersion > oldVersion)
            //db.execSQL("ALTER TABLE trips ADD COLUMN jobPending INTEGER");
        //if(newVersion > oldVersion)
            //db.execSQL("ALTER TABLE trips ADD COLUMN status INTEGER");
        onCreate(db);
    }

    public boolean insertData(Integer userID, Integer driverID, String origin, String destination, String date, String time, String details, int jobPending, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERID, userID);
        contentValues.put(DRIVERID, driverID);
        contentValues.put(ORIGIN, origin);
        contentValues.put(DESTINATION, destination);
        contentValues.put(DATE, date);
        contentValues.put(TIME, time);
        contentValues.put(DETAILS, details);
        contentValues.put(JOBPENDING, jobPending);
        contentValues.put(STATUS, status);
        long result = db.insert(TABLE_NAME3, null, contentValues);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertAccount(String email, String password, String name, Integer phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, name);
        contentValues.put(USERPASS, password);
        contentValues.put(USEREMAIL, email);
        contentValues.put(USERPHONE, phoneNumber);
        long result = db.insert(TABLE_NAME1, null, contentValues);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }


    public boolean insertDriverAccount(String name, String password, Integer phone, String email, String insurance, String license, Integer vehicle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DRIVEREMAIL, email);
        contentValues.put(DRIVERPASS, password);
        contentValues.put(DRIVERNAME, name);
        contentValues.put(DRIVERPHONE, phone);
        contentValues.put(DRIVERLICENSE, license);
        contentValues.put(INSURANCE, insurance);
        contentValues.put(VEHICLE_TYPE, vehicle);
        long result = db.insert(TABLE_NAME2, null, contentValues);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean applyForJob(Integer dID, String tripID){
        SQLiteDatabase db = this.getWritableDatabase();
        int waiting = 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DRIVERID, dID);
        contentValues.put(JOBPENDING, waiting);
        long result = db.update(TABLE_NAME3, contentValues, "tripID = " +tripID, null);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public String searchPass(String uname){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select userEmail, userPassword from "+TABLE_NAME1;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(0);

                if(a.equals(uname)){
                    b = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return b;
    }

    public String searchDriverPass(String uname){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select driverEmail, driverPassword from "+TABLE_NAME2;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(0);

                if(a.equals(uname)){
                    b = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return b;
    }

    public Cursor getAllRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select tripID as _id, origin, destination, details from "+TABLE_NAME3;
        String where = null;
        String[] tableColumns = new String[] {"_id", "origin", "destination", "details"};
        //Cursor c = db.query(true, TABLE_NAME3, tableColumns, where, null, null, null, null, null);
        Cursor c = db.rawQuery(query, null);

        if(c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getUserJobs(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select tripID as _id, origin, destination, details from "+TABLE_NAME3+ " where " +USERID+ " = " +id;
        String where = null;
        String[] tableColumns = new String[] {"_id", "origin", "destination", "details"};
        //Cursor c = db.query(true, TABLE_NAME3, tableColumns, where, null, null, null, null, null);
        Cursor c = db.rawQuery(query, null);

        if(c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public int getUserId(String emailAddress){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select userEmail, userID from "+TABLE_NAME1;
        Cursor c2 = db.rawQuery(query, null);

        String a;
        int b = -1;
        if(c2.moveToFirst()){
            do{
                a = c2.getString(0);

                if(a.equals(emailAddress)){
                    b = c2.getInt(1);
                    break;
                }
            }
            while(c2.moveToNext());
        }
        return b;
    }

    public int getDriverId(String emailAddress){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select driverEmail, driverID from "+TABLE_NAME2;
        Cursor c2 = db.rawQuery(query, null);

        String a;
        int b = -1;
        if(c2.moveToFirst()){
            do{
                a = c2.getString(0);

                if(a.equals(emailAddress)){
                    b = c2.getInt(1);
                    break;
                }
            }
            while(c2.moveToNext());
        }
        return b;
    }

    public int getDriverIdTTable(int tID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select tripID, driverID from "+TABLE_NAME3;
        Cursor c2 = db.rawQuery(query, null);

        int a;
        int b = -1;
        if(c2.moveToFirst()){
            do{
                a = c2.getInt(0);

                if(a == tID){
                    b = c2.getInt(1);
                    break;
                }
            }
            while(c2.moveToNext());
        }
        return b;
    }

    public Job getJob(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select tripID as _id, origin, destination, date, time, details from "+TABLE_NAME3+ " where " +TRIPID+ " = " +id;
        Cursor c2 = db.rawQuery(query, null);

        if(c2 != null) {
            c2.moveToFirst();
        }
        Job job = new Job(c2.getString(1), c2.getString(2), c2.getString(3), c2.getString(4), c2.getString(5));
        return job;
    }

    public Driver getDriver (int dID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select driverID as _id, driverName, vehicleType, driverPhone from "+TABLE_NAME2+ " where " +DRIVERID+ " = " +dID;
        Cursor c3 = db.rawQuery(query, null);

        if(c3 != null) {
            c3.moveToFirst();
        }
        Driver driver = new Driver(c3.getString(1), c3.getInt(2), c3.getInt(3), 4);
        return driver;
    }

    public Trip getTrip (int tID){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select tripID as _id, origin, destination from "+TABLE_NAME3+ " where " +TRIPID+ " = " +tID;
        Cursor c4 = db.rawQuery(query, null);


        if(c4 != null) {
            c4.moveToFirst();
        }
        Trip trip = new Trip(c4.getString(1), c4.getString(2));
        return trip;
    }
  }
