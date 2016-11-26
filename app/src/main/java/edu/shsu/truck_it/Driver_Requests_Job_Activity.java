package edu.shsu.truck_it;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Driver_Requests_Job_Activity extends AppCompatActivity {

    private Button requestJob;
    String passedVar = null, passedVar2 = null;
    private int passedID;
    private TextView pickupView, dropoffView, dateView, timeView, detailsView;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__requests__job_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDb = new DatabaseHelper(this);
        //get passed id of job from driver listView
        passedVar = getIntent().getStringExtra(DriverPicksClientActivity.ID_EXTRA);
        passedVar2 = getIntent().getStringExtra(DriverPicksClientActivity.Driver_ID_EXTRA);

        //assign to textView
        //passedView.setText(passedVar);

        pickupView = (TextView) findViewById(R.id.pickupTextView);
        dropoffView = (TextView) findViewById(R.id.dropoffTextView);
        dateView = (TextView) findViewById(R.id.dateTextView);
        timeView = (TextView) findViewById(R.id.timeTextView);
        detailsView = (TextView) findViewById(R.id.detailsTextView);

        passedID = Integer.parseInt(passedVar);

        Job job = myDb.getJob(passedID);

        pickupView.setText(job._origin);
        dropoffView.setText(job._destination);
        dateView.setText(job._date);
        timeView.setText(job._time);
        detailsView.setText(job._details);

        requestJob = (Button) findViewById(R.id.requestJob);
        requestJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyForJob();
                Intent intent = new Intent(getApplicationContext(), DriverPicksClientActivity.class);
                startActivity(intent);
            }
        });
    }

    public void ApplyForJob() {
        boolean isInserted = myDb.applyForJob(Integer.parseInt(passedVar2), passedVar);

        if(isInserted = true)
            Toast.makeText(Driver_Requests_Job_Activity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(Driver_Requests_Job_Activity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

}
