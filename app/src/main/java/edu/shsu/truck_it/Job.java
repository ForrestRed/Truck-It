package edu.shsu.truck_it;

/**
 * Created by Forrest Red on 11/24/2016.
 */

public class Job {
    String _origin;
    String _destination;
    String _time;
    String _date;
    String _details;

    public Job(String origin, String destination, String time, String date, String details)
    {
        this._origin = origin;
        this._destination = destination;
        this._time = time;
        this._date = date;
        this._details = details;
    }
}
