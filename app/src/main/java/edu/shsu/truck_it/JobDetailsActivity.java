package edu.shsu.truck_it;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JobDetailsActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private Button submit;
    private EditText pickupLocation;
    private EditText dropOffLocation;
    private EditText date;
    private EditText time;
    private EditText details;
    String passedVar;
    String passedTruckVar;
    int driverTruck;
    private int userID;

    private String pickupStr = null, dropOffStr = null, dateStr = null, timeStr = null, detailsStr = null, test =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //pull all values from input
        pickupLocation = (EditText) findViewById(R.id.pLEditText);
        dropOffLocation = (EditText) findViewById(R.id.dOEditText);
        date = (EditText) findViewById(R.id.dateEditText);
        time = (EditText) findViewById(R.id.timeEditText);
        details = (EditText) findViewById(R.id.detailsEditText);
        submit = (Button) findViewById(R.id.button5);
        passedVar = getIntent().getStringExtra(UserSelectionActivity.User_ID_Final_EXTRA);
        passedTruckVar = getIntent().getStringExtra(UserSelectionActivity.User_Truck_EXTRA);
        driverTruck = Integer.parseInt(passedTruckVar);
        userID = Integer.parseInt(passedVar);


        //calls the internal database
        myDb = new DatabaseHelper(this);

        //making toast
        Context con = getApplicationContext();
        CharSequence toastText = "Complete input fields.";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(con, toastText, duration);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickupStr = pickupLocation.getText().toString();
                dropOffStr = dropOffLocation.getText().toString();
                dateStr = date.getText().toString();
                timeStr = time.getText().toString();
                detailsStr = details.getText().toString();

                //Make sure there are no blank entries
                if(pickupStr.equals("") || dropOffStr.equals("") || dateStr.equals("") || timeStr.equals("") || detailsStr.equals(""))
                    toast.show();
                else
                    AddData();
                    Intent intent = new Intent(getApplicationContext(), UserSelectionActivity.class);
                    startActivity(intent);
            }
        });
    }


    public void AddData() {
        boolean isInserted = myDb.insertData(userID, 0, pickupLocation.getText().toString(),
                dropOffLocation.getText().toString(),
                date.getText().toString(),
                time.getText().toString(),
                details.getText().toString(), 0, 0, driverTruck);

        if(isInserted = true)
            Toast.makeText(JobDetailsActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(JobDetailsActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }



}
