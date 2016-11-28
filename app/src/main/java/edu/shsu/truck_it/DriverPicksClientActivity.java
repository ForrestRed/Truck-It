package edu.shsu.truck_it;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DriverPicksClientActivity extends AppCompatActivity {

    private ListView listJobs;
    DatabaseHelper myDb;
    public final static String ID_EXTRA = "edu.shsu.truck_it._ID";
    public final static String Driver_ID_EXTRA = "edu.shsu.truck_it._ID2";
    String passedVar = null;
    private Button acceptedJobsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_picks_client);

        listJobs = (ListView) findViewById(R.id.listJob);
        acceptedJobsButton = (Button) findViewById(R.id.acceptedJobsBtn);
        //calls the internal database
        myDb = new DatabaseHelper(this);
        populateListView();
        passedVar = getIntent().getStringExtra(MainActivity.Driver_ID_EXTRA);
        String[] tests = new String[]{
                "test 1",
                "TEST 2",
                "TeSt3"
        };
        List<String> tests_list = new ArrayList<String>(Arrays.asList(tests));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, tests_list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;

            }

        };
        //listJobs.setAdapter(arrayAdapter);

        //Specifies what happens when an item in listView is clicked.
        listJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Driver_Requests_Job_Activity.class);
                intent.putExtra(ID_EXTRA, String.valueOf(id));
                intent.putExtra(Driver_ID_EXTRA, passedVar);
                startActivity(intent);

            }
        });

        acceptedJobsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DriverReadyJobsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void populateListView(){
        Cursor cursor = myDb.getAllRows();
        String[] fromFieldNames = new String[] {DatabaseHelper.ORIGIN, DatabaseHelper.DESTINATION, DatabaseHelper.DETAILS};
        int[] toViewIDs = new int[] {R.id.origin, R.id.destination, R.id.details};
        SimpleCursorAdapter myCursorAdaptor;
        myCursorAdaptor = new SimpleCursorAdapter(getBaseContext(), R.layout.job_listing, cursor, fromFieldNames, toViewIDs, 0);
        listJobs.setAdapter(myCursorAdaptor);
    }
}
