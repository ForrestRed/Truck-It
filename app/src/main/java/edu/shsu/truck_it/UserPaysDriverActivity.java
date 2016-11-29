package edu.shsu.truck_it;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class UserPaysDriverActivity extends AppCompatActivity {
    String passedVar;
    DatabaseHelper myDB;
    private int passedTripID;
    private TextView driverName, vehicleType, distanceTraveled, totalCharge;
    private Button payDriver;
    private double jobFinalCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pays_driver);

        myDB = new DatabaseHelper(this);
        passedVar = getIntent().getStringExtra(UsersCompletedJobListActivity.Trip_ID_EXTRA);
        passedTripID = Integer.parseInt(passedVar);

        CompletedJob finishedJob = myDB.getCompletedJob(passedTripID);
        Driver finishedDriver = myDB.getDriver(finishedJob._driverID);
        jobFinalCharge = myDB.getCharge(passedTripID);

        driverName = (TextView) findViewById(R.id.payDriverName2);
        vehicleType = (TextView) findViewById(R.id.payVehicle2);
        distanceTraveled = (TextView) findViewById(R.id.payDistance2);
        totalCharge = (TextView) findViewById(R.id.payCharge2);
        payDriver = (Button) findViewById(R.id.payButton);

        driverName.setText(finishedDriver._name);
        vehicleType.setText(Integer.toString(finishedJob._vehicleType));
        distanceTraveled.setText(String.valueOf(finishedJob._distance));
        totalCharge.setText(String.valueOf(jobFinalCharge));
    }
}
