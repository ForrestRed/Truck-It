package edu.shsu.truck_it;

/**
 * Created by Jude on 11/26/2016.
 */

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import edu.shsu.truck_it.Route;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
