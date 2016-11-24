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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ TABLE_NAME1 + " (userID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT, userPassword TEXT, userPhone INTEGER UNIQUE, userEmail TEXT UNIQUE)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME2 + " (driverID INTEGER PRIMARY KEY AUTOINCREMENT, driverName TEXT, driverPassword TEXT, driverPhone INTEGER UNIQUE, driverEmail TEXT UNIQUE, driversIns TEXT, driverLicense TEXT UNIQUE, vehicleType INTEGER)");
        db.execSQL("CREATE TABLE "+ TABLE_NAME3 + " (tripID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, driverID INTEGER, origin TEXT, destination TEXT, miles DOUBLE, amount DOUBLE, date TEXT, time TEXT, details TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        //if(newVersion > oldVersion)
            //db.execSQL("ALTER TABLE trips ADD COLUMN details TEXT");
        onCreate(db);
    }

    public boolean insertData(Integer userID, Integer driverID, String origin, String destination, String date, String time, String details){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERID, userID);
        contentValues.put(DRIVERID, driverID);
        contentValues.put(ORIGIN, origin);
        contentValues.put(DESTINATION, destination);
        contentValues.put(DATE, date);
        contentValues.put(TIME, time);
        contentValues.put(DETAILS, details);
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
  }
