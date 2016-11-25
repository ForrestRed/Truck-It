package edu.shsu.truck_it;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb; //<--This line creates the database, can comment out but dont delete
    public final static String User_ID_EXTRA = "edu.shsu.truck_it._ID";
    private Button loginButton;
    private TextView creatAccount;
    private Switch mySwitch;
    private EditText userText, passText;
    private String user = null, pass = null, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);//<---calls the internal db

        loginButton = (Button) findViewById(R.id.button);
        mySwitch = (Switch) findViewById(R.id.switch2);
        userText = (EditText) findViewById(R.id.editText);
        passText = (EditText) findViewById(R.id.editText2);

        //making toast
        Context con = getApplicationContext();
        CharSequence toastText = "Complete input fields.";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(con, toastText, duration);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pull all values from input
                user = userText.getText().toString();
                pass = passText.getText().toString();

                if(mySwitch.isChecked()){
                    //Make sure there are no blank entries
                    if(user.equals("") || pass.equals("")){
                        toast.show();
                    }
                    else{
                        //Test for driver login authentication
                        String dPassword = myDb.searchDriverPass(user);
                        if(pass.equals(dPassword))
                        {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), DriverPicksClientActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                else{
                    //Make sure there are no blank entries
                    if(user.equals("") || pass.equals("")){
                        toast.show();
                    }
                    else{
                        //Test for user login authentication
                        String password = myDb.searchPass(user);
                        if(pass.equals(password))
                        {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), UserSelectionActivity.class);
                            userId = Integer.toString(myDb.getUserId(user));
                            intent.putExtra(User_ID_EXTRA, userId);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });

        creatAccount = (TextView) findViewById(R.id.textView2);

        creatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mySwitch.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), DriverRegisterActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


}
