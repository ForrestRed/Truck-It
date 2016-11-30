package edu.shsu.truck_it;

import android.content.Intent;
import android.os.Bundle;
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
    private Button chargeUser, logout;


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
        myDb.updateCharge(passedTripID, jobFinalCharge);

        driverName = (TextView) findViewById(R.id.payDriverName2);
        vehicleType = (TextView) findViewById(R.id.payVehicle2);
        distanceTraveled = (TextView) findViewById(R.id.payDistance2);
        totalCharge = (TextView) findViewById(R.id.payCharge);
        chargeUser = (Button) findViewById(R.id.button7);
        logout = (Button) findViewById(R.id.logoutButton2);

        driverName.setText(finishedDriver._name);
        if(finishedJob._vehicleType == 1)
            vehicleType.setText("Small Truck");
        else if(finishedJob._vehicleType == 2)
            vehicleType.setText("Large Truck");
        else if(finishedJob._vehicleType == 3)
            vehicleType.setText("Cargo Van");
        //vehicleType.setText(Integer.toString(finishedJob._vehicleType));
        distanceTraveled.setText(String.valueOf(finishedJob._distance));
        totalCharge.setText(String.valueOf(jobFinalCharge));

        chargeUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myDb.updateCompletedStatus(passedTripID);
                Toast.makeText(PaymentActivity.this, "Customer is being charged.", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), LogoutActivity.class);
                startActivity(intent1);
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
