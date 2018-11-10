package com.doki_feeling.gtdonationtracking;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.doki_feeling.gtdonationtracking.model.Location;
import com.doki_feeling.gtdonationtracking.model.LocationList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener {

    public static final String TAG = "MapsActivity";

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        ArrayList<Location> locationList = LocationList.getInstance();
        for (Location location : locationList) {
            LatLng loc = new LatLng(location.getCord().getLatitude(), location.getCord().getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title(location.getSiteName()).snippet(location.getPhone()));
        }
        Location location = locationList.get(0);
        LatLng loc = new LatLng(location.getCord().getLatitude(), location.getCord().getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 10));

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
//        Intent callIntent = new Intent(Intent.ACTION_CALL);
//        callIntent.setData(Uri.parse("tel:"+marker.getSnippet()));
//        Log.d(TAG,marker.getSnippet());
//        startActivity(callIntent);
    }

//    /**
//     * This class implements a custom layout for the pin
//     */
//    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
//
//        private final View myContentsView;
//
//        /**
//         * Make the adapter
//         */
//        CustomInfoWindowAdapter(){
//            // hook up the custom layout view in res/custom_map_pin_layout.xml
//            myContentsView = getLayoutInflater().inflate(R.layout.map_pin_layout, null);
//        }
//
//        @Override
//        public View getInfoContents(Marker marker) {
//
//            TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.map_location_title));
//            tvTitle.setText(marker.getTitle());
//            TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.map_location_snippet));
//            tvSnippet.setText(marker.getSnippet());
//
//            return myContentsView;
//        }
//
//        @Override
//        public View getInfoWindow(Marker marker) {
//
//        }
//
//    }
}
