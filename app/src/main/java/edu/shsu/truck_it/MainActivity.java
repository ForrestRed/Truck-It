package edu.shsu.truck_it;

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

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView creatAccount;
    private Switch mySwitch;
    private EditText userText, passText;
    private String user = null, pass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.button);
        mySwitch = (Switch) findViewById(R.id.switch2);
        userText = (EditText) findViewById(R.id.editText);
        passText = (EditText) findViewById(R.id.editText2);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = userText.getText().toString();
                pass = passText.getText().toString();

                if(mySwitch.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), DriverPicksClientActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), UserSelectionActivity.class);
                    startActivity(intent);
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
