package edu.shsu.truck_it;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {

    String passedVar;
    DatabaseHelper myDb;
    private int passedTripID;
    private double jobFinalCharge;
    private TextView driverName, vehicleType, distanceTraveled, totalCharge;
    private Button chargeUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDb = new DatabaseHelper(this);
        passedVar = getIntent().getStringExtra(DriverStartJobActivity.tripIDExtra);
        passedTripID = Integer.parseInt(passedVar);

        CompletedJob finishedJob = myDb.getCompletedJob(passedTripID);
        Driver finishedDriver = myDb.getDriver(finishedJob._driverID);

        jobFinalCharge = Payment(finishedJob._distance, finishedJob._vehicleType);

        driverName = (TextView) findViewById(R.id.payDriverName);
        vehicleType = (TextView) findViewById(R.id.payVehicle);
        distanceTraveled = (TextView) findViewById(R.id.payDistance);
        totalCharge = (TextView) findViewById(R.id.payCharge);
        chargeUser = (Button) findViewById(R.id.button7);

        driverName.setText(finishedDriver._name);
        vehicleType.setText(Integer.toString(finishedJob._vehicleType));
        distanceTraveled.setText(String.valueOf(finishedJob._distance));
        totalCharge.setText(String.valueOf(jobFinalCharge));

        chargeUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myDb.updateCompletedStatus(passedTripID);
                Toast.makeText(PaymentActivity.this, "Customer is being charged.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public double Payment(double distance, int truckType){
        switch (truckType) {
            case (1):
                double stc = 0.65;              //small truck charge
                double charge1 = 5+ (stc * distance);
                return charge1;
            case (2):
                double ltc = .79;              // large truck charge
                double charge2 = 5 +(ltc * distance);
                return charge2;
            case (3):
                double cvc = 0.85;          //cargo van charge
                double charge3 = 6 + (cvc * distance);
                return charge3;
        }
        double oranges = 0;                  // so the code works... completely arbitrary and not needed.
        return oranges;                             // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    }

}
