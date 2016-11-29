package edu.shsu.truck_it;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class UserSelectionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static String User_ID_Final_EXTRA = "edu.shsu.truck_it._ID";
    public final static String User_Truck_EXTRA = "edu.shsu.truck_it._ID5";

    private RadioButton smallTruck;
    private RadioButton largeTruck;
    private RadioButton cargoVan;
    private int truckType = 0; // 1 for small, 2 for large, 3 for cargo
    String passedVar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);

        final RadioButton smallTruck = (RadioButton) findViewById(R.id.sTRadioButton);
        final RadioButton largeTruck = (RadioButton) findViewById(R.id.lTRadioButton);
        final RadioButton cargoVan = (RadioButton) findViewById(R.id.cVRadioButton);

        passedVar = getIntent().getStringExtra(MainActivity.User_ID_EXTRA);


        //making toast
        Context con = getApplicationContext();
        CharSequence toastText = "Please select a truck.";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(con, toastText, duration);

        Button nextButton = (Button) findViewById(R.id.button3);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(smallTruck.isChecked())
                    truckType = 1;
                else if(largeTruck.isChecked())
                    truckType = 2;
                else if(cargoVan.isChecked())
                    truckType = 3;

                if(truckType == 0){
                    toast.show();
                }
                else{
                    Intent intent1 = new Intent(getApplicationContext(), JobDetailsActivity.class);
                    intent1.putExtra(User_ID_Final_EXTRA, passedVar);
                    intent1.putExtra(User_Truck_EXTRA, Integer.toString(truckType));
                    startActivity(intent1);
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Intent i = null;

        int id = item.getItemId();

        if (id == R.id.nav_jobs) {
            // Handle the myJobs action
            drawer.closeDrawer(GravityCompat.START);
            i = new Intent(this, MyJobRequestsActivity.class);
            i.putExtra(User_ID_Final_EXTRA, passedVar);
            startActivity(i);

        }
        else if (id == R.id.nav_completed){
            //goes to completed jobs list
            drawer.closeDrawer(GravityCompat.START);
            i = new Intent(this, UsersCompletedJobListActivity.class);
            i.putExtra(User_ID_Final_EXTRA, passedVar);
            startActivity(i);
        }
        else if (id == R.id.nav_logout) {
            //Handles the logout action
            drawer.closeDrawer(GravityCompat.START);
            i = new Intent(this, LogoutActivity.class);
            startActivity(i);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
