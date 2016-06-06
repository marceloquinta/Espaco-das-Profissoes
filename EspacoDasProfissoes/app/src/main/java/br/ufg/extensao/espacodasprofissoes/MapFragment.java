package br.ufg.extensao.espacodasprofissoes;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.NoSubscriberEvent;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {


    SupportMapFragment mapFragment;
    GoogleMap map;
<<<<<<< HEAD
    private Map<Marker,Place> markers;
    private List<Place> places;
=======
    private Map<Marker, String> markers;
>>>>>>> origin/master

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        markers = new HashMap<>();
        readPlaces();
        return view;
    }

    @NonNull
    private void readPlaces() {
        places = new ArrayList<>();
        Place inf = new Place();
        inf.setName("INF");
        inf.setSnippet("Instituto de Informática");
        inf.setLatitude(-16.6036908);
        inf.setLongitude(-49.266449);
        places.add(inf);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            this.map.setMyLocationEnabled(true);
            this.map.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }

//        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.nav_bg))
//                .position(inf, 300f, 300f);
// Add an overlay to the map, retaining a handle to the GroundOverlay object.
//        GroundOverlay imageOverlay = map.addGroundOverlay(newarkMap);

        map.getUiSettings().setScrollGesturesEnabled(true);
//        map.getUiSettings().setMapToolbarEnabled(false);
        map.clear();
        centerMap();
        addMarkers();
        addPaths();
        map.setOnInfoWindowClickListener(this);

    }

    private void centerMap() {
        LatLng point = new LatLng(-16.606231, -49.2622261);
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 17));
    }

    private void addMarkers() {
        for(Place place : places){
            addMarker(place);
        }
    }

    private void addMarker(Place place){

<<<<<<< HEAD
        double latitude = place.getLatitude();
        double longitude = place.getLongitude();
        Marker marker = null;

        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
        marker = map.addMarker(
                new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(place.getName())
                        .snippet(place.getSnippet())
                        .icon(icon)
        );
        markers.put(marker, place);

    }

    private void addPaths() {
        LatLng inf = new LatLng(-16.6036908, -49.266449);
        Polyline line = map.addPolyline(new PolylineOptions()
                .add(new LatLng(-16.6068122, -49.261486), inf)
                .width(5)
                .color(Color.RED));

=======
// Add an overlay to the map, retaining a handle to the GroundOverlay object.
        GroundOverlay imageOverlay = map.addGroundOverlay(newarkMap);
>>>>>>> origin/master
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            this.map.setMyLocationEnabled(true);
            this.map.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @Subscribe
    public void onEvent(Marker marker){
        Place place = markers.get(marker);

    }

    public GoogleMap getMap() {
        return map;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
            EventBus.getDefault().post(marker);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
