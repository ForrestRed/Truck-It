package edu.shsu.truck_it;

/**
 * Created by Forrest Red on 11/29/2016.
 */

public class CompletedJob {
    String _origin;
    String _destination;
    String _time;
    String _date;
    String _details;
    int _vehicleType;
    double _distance;
    int _userID;
    int _driverID;

    public CompletedJob(String origin, String destination, String time, String date, String details, int vehicleType, double distance, int userID, int driverID){
        this._origin = origin;
        this._destination = destination;
        this._time = time;
        this._date = date;
        this._details = details;
        this._vehicleType = vehicleType;
        this._distance = distance;
        this._userID = userID;
        this._driverID = driverID;
    }
}
