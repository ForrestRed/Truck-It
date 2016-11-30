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

        payDriver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(UserPaysDriverActivity.this, "Payment complete. Thank you for using TruckIt!", Toast.LENGTH_LONG).show();
                ////////////////////////////////////////////////////////////////////////////////
                //These lines of code test email function///////////////////////////////////////


                Log.i("SendMailActivity", "Send Button Clicked.");

                String fromEmail = "teamharambeshsu@gmail.com";
                String fromPassword = "Burris4317";
                String toEmails = "stovalljr@gmail.com , teamharmbeshsu@gmail.com";

                List toEmailList = Arrays.asList(toEmails
                        .split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList);

                String emailSubject = "donotreply harambe job confirmation";
                String emailBody = "Thank you for using TruckIt!";
                new SendMailTask(UserPaysDriverActivity.this).execute(fromEmail,
                        fromPassword, toEmailList, emailSubject, emailBody);

                Intent i = new Intent(getApplicationContext(), LogoutActivity.class);
                startActivity(i);
                ////////////////////////////////////////////////////////////////////////////////
            }
        });
    }
}
