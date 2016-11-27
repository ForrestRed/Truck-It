package edu.shsu.truck_it;

/**
 * Created by Jude on 11/26/2016.
 */

import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
