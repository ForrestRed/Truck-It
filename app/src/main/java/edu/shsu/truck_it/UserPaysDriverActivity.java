package edu.shsu.truck_it;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.Arrays;
import android.util.Log;
import android.content.Intent;

public class UserPaysDriverActivity extends AppCompatActivity {
    String passedVar, userEmail, driverEmail;
    DatabaseHelper myDB;
    private int passedTripID, userID, driverID;
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

        //get userEmail and driverEmail with passedTripID by getting userID and driverID then query for emails
        driverID = myDB.getDriverIdTTable(passedTripID);
        userID = myDB.getUserIdTTable(passedTripID);
        driverEmail = myDB.getDriverEmail(driverID);
        userEmail = myDB.getUserEmail(userID);


        CompletedJob finishedJob = myDB.getCompletedJob(passedTripID);
        Driver finishedDriver = myDB.getDriver(finishedJob._driverID);
        jobFinalCharge = myDB.getCharge(passedTripID);

        driverName = (TextView) findViewById(R.id.payDriverName2);
        vehicleType = (TextView) findViewById(R.id.payVehicle2);
        distanceTraveled = (TextView) findViewById(R.id.payDistance2);
        totalCharge = (TextView) findViewById(R.id.payCharge2);
        payDriver = (Button) findViewById(R.id.payButton);

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

        payDriver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(UserPaysDriverActivity.this, "Payment complete. Thank you for using TruckIt!", Toast.LENGTH_LONG).show();
                ////////////////////////////////////////////////////////////////////////////////
                //These lines of code test email function///////////////////////////////////////


                Log.i("SendMailActivity", "Send Button Clicked.");

                String fromEmail = "teamharambeshsu@gmail.com";
                String fromPassword = "Burris4317";
                String toEmails = userEmail;

                List toEmailList = Arrays.asList(toEmails
                        .split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList);

                String emailSubject = "donotreply harambe job confirmation";
                String emailBody = "Thank you for using TruckIt! You have been charged $" + jobFinalCharge + ".";
                new SendMailTask(UserPaysDriverActivity.this).execute(fromEmail,
                        fromPassword, toEmailList, emailSubject, emailBody);

                Log.i("SendMailActivity", "Send Button Clicked.");

                String fromEmail2 = "teamharambeshsu@gmail.com";
                String fromPassword2 = "Burris4317";
                String toEmails2 = driverEmail;

                List toEmailList2 = Arrays.asList(toEmails2
                        .split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList2);

                String emailSubject2 = "donotreply harambe job confirmation";
                String emailBody2 = "Thank you for using TruckIt! You have completed your job and $" + jobFinalCharge + " will be transferred to your account momentarily.";
                new SendMailTask(UserPaysDriverActivity.this).execute(fromEmail2,
                        fromPassword2, toEmailList2, emailSubject2, emailBody2);


                Intent i = new Intent(getApplicationContext(), LogoutActivity.class);
                startActivity(i);
                ////////////////////////////////////////////////////////////////////////////////
            }
        });
    }
}
