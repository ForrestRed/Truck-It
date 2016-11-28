package edu.shsu.truck_it;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DriverReadyJobsActivity extends AppCompatActivity {
    public final static String Trip_ID_EXTRA = "edu.shsu.truck_it._ID3";
    DatabaseHelper myDb;
    ListView driverReadyListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_ready_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDb = new DatabaseHelper(this);
        driverReadyListView = (ListView) findViewById(R.id.dReadyJobslv);
        populateListView();

        driverReadyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DriverStartJobActivity.class);
                intent.putExtra(Trip_ID_EXTRA, String.valueOf(id));
                startActivity(intent);
            }
        });



    }

    private void populateListView(){
        Cursor cursor = myDb.getDriverReadyJobs(1);
        String[] fromFieldNames = new String[] {DatabaseHelper.ORIGIN, DatabaseHelper.DESTINATION, DatabaseHelper.DETAILS};
        int[] toViewIDs = new int[] {R.id.origin, R.id.destination, R.id.details};
        SimpleCursorAdapter myCursorAdaptor;
        myCursorAdaptor = new SimpleCursorAdapter(getBaseContext(), R.layout.job_listing, cursor, fromFieldNames, toViewIDs, 0);
        driverReadyListView.setAdapter(myCursorAdaptor);
    }

}
