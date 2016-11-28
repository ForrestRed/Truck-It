package edu.shsu.truck_it;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private EditText newEmail;
    private EditText newPassword;
    private EditText newName;
    private EditText newPhone;
    private Button createAccount;
    private String email = null, password = null, name = null, phone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccount = (Button) findViewById(R.id.button2);
        newEmail = (EditText) findViewById(R.id.newEmailEditText);
        newPassword = (EditText) findViewById(R.id.newPasswordEditText);
        newName = (EditText) findViewById(R.id.newNameEditText);
        newPhone = (EditText) findViewById(R.id.newPhoneEditText);

        //calls the internal database
        myDb = new DatabaseHelper(this);

        TextView loginText = (TextView) findViewById(R.id.textView);

        //making toast
        Context con = getApplicationContext();
        CharSequence toastText = "Complete registration fields.";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(con, toastText, duration);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = newEmail.getText().toString();
                password = newPassword.getText().toString();
                name = newName.getText().toString();
                phone = newPhone.getText().toString();

                //make sure there are no null values and that a truck has been selected
                if(email.equals("") || password.equals("") || name.equals("") || phone.equals("")){
                    toast.show();

                }
                else{
                    AddAccount();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddAccount() {
        //convert editText to Integer
        //Phonenumbers will typically go out of range of an int which causes an error, we need to fix this by changing its type to string in the database
        String value = newPhone.getText().toString();
        //int textToInt = Integer.parseInt(newPhone.getText().toString());<--Do not reinstate
        boolean isInserted = myDb.insertAccount(newEmail.getText().toString(), newPassword.getText().toString(),
                newName.getText().toString(), newPhone.getText().toString());

        if (isInserted = true)
            Toast.makeText(RegisterActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(RegisterActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }
}
