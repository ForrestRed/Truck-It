package edu.shsu.truck_it;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.content.Intent;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

public class DriverRegisterActivity extends AppCompatActivity {
    private Button createAccount;
    private TextView alreadyHaveAccount;
    private EditText emailText, passText, nameText, phoneText, dlText, insuranceText;
    private RadioButton smallTruck, largeTruck, cargoVan;
    private String email = null, password = null, name = null, phone = null, dlNum = null, insurance = null;
    private int truckType = 0; // 1 for small, 2 for large, 3 for cargo


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register);

        createAccount = (Button) findViewById(R.id.button9);
        alreadyHaveAccount = (TextView) findViewById(R.id.textView3);
        emailText = (EditText) findViewById(R.id.editText11);
        passText = (EditText) findViewById(R.id.editText12);
        nameText = (EditText) findViewById(R.id.editText13);
        phoneText = (EditText) findViewById(R.id.editText14);
        dlText = (EditText) findViewById(R.id.editText15);
        insuranceText = (EditText) findViewById(R.id.editText16);

        smallTruck = (RadioButton) findViewById(R.id.radioButton8);
        largeTruck = (RadioButton) findViewById(R.id.radioButton9);
        cargoVan = (RadioButton) findViewById(R.id.radioButton7);

        //making toast
        Context con = getApplicationContext();
        CharSequence toastText = "Complete registration fields.";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(con, toastText, duration);

        createAccount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //pull all values from input
                email = emailText.getText().toString();
                password = passText.getText().toString();
                name = nameText.getText().toString();
                phone = phoneText.getText().toString();
                dlNum = dlText.getText().toString();
                insurance = insuranceText.getText().toString();
                //check truck type
                if(smallTruck.isChecked())
                    truckType = 1;
                else if(largeTruck.isChecked())
                    truckType = 2;
                else if(cargoVan.isChecked())
                    truckType = 3;

                //make sure there are no null values and that a truck has been selected
                if(email.equals("") || password.equals("") || name.equals("") || phone.equals("") || dlNum.equals("") || insurance.equals("") || truckType == 0){
                    toast.show();

                }
                else{
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
