package edu.shsu.truck_it;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class AcceptOrDenyDriverActivity extends AppCompatActivity {

    String passedVar = null;
    int foundDriverID;
    DatabaseHelper myDb;
    private TextView driverName, driverVehicle, driverPhone;
    private RatingBar driverRating;


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

        passedVar = getIntent().getStringExtra(MyJobRequestsActivity.Trip_ID_EXTRA);
        myDb = new DatabaseHelper(this);
        foundDriverID = myDb.getDriverIdTTable(Integer.parseInt(passedVar));

        Driver driver = myDb.getDriver(foundDriverID);

        driverName.setText(driver._name);
        driverVehicle.setText(String.valueOf(driver._vehicleType));
        driverPhone.setText(String.valueOf(driver._phone));
        driverRating.setRating(driver._rating);
    }

}
