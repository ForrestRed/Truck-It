package edu.shsu.truck_it;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DriverStartJobActivity extends AppCompatActivity {

    //still ghetto, being worked on
    private Button Submit_button;
    private Button To_Maps_Start_button;
    private Button To_Maps_End_button;
    String passedVar;
    DatabaseHelper myDb;
    private TextView pickup, dropoff, details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_start_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new DatabaseHelper(this);
        pickup = (TextView) findViewById(R.id.startJobPickup);
        dropoff = (TextView) findViewById(R.id.startJobDropOff);
        details = (TextView) findViewById(R.id.startJobDetails);

        passedVar = getIntent().getStringExtra(DriverReadyJobsActivity.Trip_ID_EXTRA);
        Job job = myDb.getJob(Integer.parseInt(passedVar));

        pickup.setText(job._origin);
        dropoff.setText(job._destination);
        details.setText(job._details);
    }

}
