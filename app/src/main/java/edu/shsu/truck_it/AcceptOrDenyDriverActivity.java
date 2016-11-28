package edu.shsu.truck_it;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class AcceptOrDenyDriverActivity extends AppCompatActivity {

    String passedVar = null;
    int foundDriverID;
    DatabaseHelper myDb;
    private TextView driverName, driverVehicle, driverPhone;
    private RatingBar driverRating;
    private Button acceptButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_or_deny_driver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        driverName = (TextView) findViewById(R.id.dName);
        driverVehicle = (TextView) findViewById(R.id.dVehicle);
        driverPhone = (TextView) findViewById(R.id.dPhone);
        driverRating = (RatingBar) findViewById(R.id.ratingBar2);
        acceptButton = (Button) findViewById(R.id.acceptBtn);

        passedVar = getIntent().getStringExtra(MyJobRequestsActivity.Trip_ID_EXTRA);
        myDb = new DatabaseHelper(this);
        foundDriverID = myDb.getDriverIdTTable(Integer.parseInt(passedVar));

        final Driver driver = myDb.getDriver(foundDriverID);

        driverName.setText(driver._name);
        driverVehicle.setText(String.valueOf(driver._vehicleType));
        driverPhone.setText(String.valueOf(driver._phone));
        driverRating.setRating(driver._rating);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(driver._name.equals("No Driver Requests")){
                    Toast.makeText(AcceptOrDenyDriverActivity.this, "No Driver to Accept", Toast.LENGTH_LONG).show();
                }
                else{
                    ApplyForJob();
                }
            }
        });
    }

    public void ApplyForJob() {
        boolean isInserted = myDb.acceptDriver(passedVar);

        if(isInserted = true)
            Toast.makeText(AcceptOrDenyDriverActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(AcceptOrDenyDriverActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }


}
