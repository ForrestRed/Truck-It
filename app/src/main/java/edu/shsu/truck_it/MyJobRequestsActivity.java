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

public class MyJobRequestsActivity extends AppCompatActivity {
    public final static String Trip_ID_EXTRA = "edu.shsu.truck_it._ID3";
    private ListView myJobsListView;
    String passedVar;
    DatabaseHelper myDb;
    private int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job_requests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myJobsListView = (ListView) findViewById(R.id.myJobsListView);
        passedVar = getIntent().getStringExtra(UserSelectionActivity.User_ID_Final_EXTRA);
        userID = Integer.parseInt(passedVar);
        myDb = new DatabaseHelper(this);
        populateListView();

        myJobsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AcceptOrDenyDriverActivity.class);
                intent.putExtra(Trip_ID_EXTRA, String.valueOf(id));
                startActivity(intent);
            }
        });

    }

    private void populateListView(){
        Cursor cursor = myDb.getUserJobs(userID);
        String[] fromFieldNames = new String[] {DatabaseHelper.ORIGIN, DatabaseHelper.DESTINATION, DatabaseHelper.DETAILS};
        int[] toViewIDs = new int[] {R.id.origin, R.id.destination, R.id.details};
        SimpleCursorAdapter myCursorAdaptor;
        myCursorAdaptor = new SimpleCursorAdapter(getBaseContext(), R.layout.job_listing, cursor, fromFieldNames, toViewIDs, 0);
        myJobsListView.setAdapter(myCursorAdaptor);
    }

}
