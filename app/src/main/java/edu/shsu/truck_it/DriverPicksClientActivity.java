package edu.shsu.truck_it;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DriverPicksClientActivity extends AppCompatActivity {

    private ListView listJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_picks_client);

        listJobs = (ListView) findViewById(R.id.listJob);
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
        listJobs.setAdapter(arrayAdapter);

    }
}
