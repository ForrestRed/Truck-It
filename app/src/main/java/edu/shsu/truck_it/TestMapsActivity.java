package edu.shsu.truck_it;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import edu.shsu.truck_it.DirectionFinder;
import edu.shsu.truck_it.DirectionFinderListener;
import edu.shsu.truck_it.Route;

import java.util.ArrayList;
import java.util.List;

public class TestMapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    DatabaseHelper myDB;

    //this tripID needs to be passed from the activity preceding it
    int tripID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        myDB = new DatabaseHelper(this);
        sendRequest();



    }
    public void sendRequest(){
        //here to get trip object which contains origin and destination data
        Trip t = myDB.getTrip(tripID);
        //String testOrigin = "11th St Huntsville, TX 77320";
        //String testDestination = "Sam Houston Ave Huntsville, TX 77320";
        String testOrigin = t._origin;
        String testDestination = t._destination;


        try{
            new DirectionFinder(this, testOrigin, testDestination).execute();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //String testOrigin = "11th St Huntsville, TX 77320";
        //String testDestination = "Sam Houston Ave Huntsville, TX 77320";
        Trip t = myDB.getTrip(tripID);
        String testOrigin = t._origin;
        String testDestination = t._destination;


        LatLng origin = getLocationFromAddress(testOrigin);
        LatLng destination = getLocationFromAddress(testDestination);

        originMarkers.add(mMap.addMarker(new MarkerOptions().title(testOrigin).position(origin)));

        //mMap.addMarker(new MarkerOptions().position(origin).title("origin"));
        //mMap.addMarker(new MarkerOptions().position(destination).title("destination"));
        //mMap.addPolyline(new PolylineOptions().add(origin, new LatLng(30.724142, -95.547996), new LatLng(30.724096, -95.549389), destination).width(10).color(Color.BLUE).visible(true));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 16));



    }
    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding directions.", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            //distance with route.distance.text same with duration
            //Toast.makeText(TestMapsActivity.this, "distance: " + route.distance.text, Toast.LENGTH_LONG);

            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()

                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()

                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));

        }
    }

    //gets LatLngs from this method to use in marker locations
    public LatLng getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        LatLng p1;


        try{
            List<android.location.Address> address = coder.getFromLocationName(strAddress, 1);
            if(address == null){
                return null;
            }
            android.location.Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch(Exception ex){
            ex.printStackTrace();
            p1 = null;
        }
        return p1;

    }

}
