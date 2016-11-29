package edu.shsu.truck_it;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PaymentActivity extends AppCompatActivity {

    String passedVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        passedVar = getIntent().getStringExtra(DriverStartJobActivity.tripIDExtra);
    }

    public float Payment(float distance, int truckType){
        switch (truckType) {
            case (1):
                float stc = (float) 0.65;              //small truck charge
                float charge1 = 5+ (stc * distance);
                return charge1;
            case (2):
                float ltc = (float) .79;              // large truck charge
                float charge2 = 5 +(ltc * distance);
                return charge2;
            case (3):
                float cvc = (float) 0.85;          //cargo van charge
                float charge3 = 6 + (cvc * distance);
                return charge3;
        }
        float oranges = (float) 0;                  // so the code works... completely arbitrary and not needed.
        return oranges;                             // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    }

}
