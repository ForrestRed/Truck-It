package edu.shsu.truck_it;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class UsersCompletedJobListActivity extends AppCompatActivity {

    private ListView completedList;
    public final static String Trip_ID_EXTRA = "edu.shsu.truck_it._ID3";
    DatabaseHelper myDB;
    String passedVar;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_completed_job_list);

        completedList = (ListView) findViewById(R.id.completedLV);
        passedVar = getIntent().getStringExtra(UserSelectionActivity.User_ID_Final_EXTRA);
        userID = Integer.parseInt(passedVar);
        myDB = new DatabaseHelper(this);

        populateListView();
        completedList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent i = new Intent(getApplicationContext(), UserPaysDriverActivity.class);
                i.putExtra(Trip_ID_EXTRA, String.valueOf(id));
                startActivity(i);
            }
        });
    }

    private void populateListView(){
        Cursor c = myDB.getUserCompletedJobs(userID);
        String[] fromFieldNames = new String[] {DatabaseHelper.ORIGIN, DatabaseHelper.DESTINATION, DatabaseHelper.DETAILS};
        int[] toViewIDs = new int[] {R.id.origin2, R.id.destination2, R.id.details2};
        SimpleCursorAdapter myCursorAdaptor;
        myCursorAdaptor = new SimpleCursorAdapter(getBaseContext(), R.layout.job_complete, c, fromFieldNames, toViewIDs, 0);
        completedList.setAdapter(myCursorAdaptor);
    }
}
