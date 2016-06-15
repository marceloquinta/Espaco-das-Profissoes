package br.ufg.extensao.espacodasprofissoes;


import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufg.extensao.espacodasprofissoes.model.Place;
import br.ufg.extensao.espacodasprofissoes.model.Route;
import br.ufg.extensao.espacodasprofissoes.util.AssetReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnPolylineClickListener {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Map<Marker, Place> markers;
    private Map<Polyline, Route> polylines;
    private List<Place> places;
    private List<Place> filteredPlaces;
    private List<Route> routes;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = getView();
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_maps, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        markers = new HashMap<>();
        polylines = new HashMap<>();
        readPlaces();
        readRoutes();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MainActivity activity = (MainActivity) getActivity();
        inflater.inflate(R.menu.menu_maps, menu);


        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String cs) {
                if (TextUtils.isEmpty(cs)) {
                    List<Place> originalPlaces = places;
                }
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                ((MainActivity) getActivity()).hideKeyboard();
                filteredPlaces = new ArrayList<>();
                for (Place place : places) {
                    if (place.getName().toLowerCase().contains(query.trim().toLowerCase())) {
                        filteredPlaces.add(place);
                    }
                }
                if (filteredPlaces.size() == 1) {
                    if (map != null) {
                        Place place = filteredPlaces.get(0);
                        centerMap(new LatLng(place.getLatitude(), place.getLongitude()));
                    }
                }
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    private void readPlaces() {
        places = new ArrayList<>();
        String file = AssetReader.loadStringFromAsset(getActivity(), "places.json");
        try {
            JSONArray placesAsJSON = new JSONObject(file).getJSONArray("places");
            for (int i = 0; i < placesAsJSON.length(); i++) {
                JSONObject placeAsJSON = placesAsJSON.getJSONObject(i);
                Place place = new Place();
                place.setName(placeAsJSON.getString("name"));
                place.setSnippet(placeAsJSON.getString("snippet"));
                place.setLatitude(placeAsJSON.getDouble("latitude"));
                place.setLongitude(placeAsJSON.getDouble("longitude"));
                places.add(place);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @NonNull
    private void readRoutes() {
        routes = new ArrayList<>();
        String file = AssetReader.loadStringFromAsset(getActivity(), "routes.json");
        try {
            JSONArray routesAsJSON = new JSONObject(file).getJSONArray("routes");
            for (int i = 0; i < routesAsJSON.length(); i++) {
                JSONObject routeAsJSONObject = routesAsJSON.getJSONObject(i);
                Route route = new Route();
                route.setName(routeAsJSONObject.getString("name"));
                route.setColor(routeAsJSONObject.getString("color"));
                ArrayList<String> edges = new ArrayList<>();
                JSONArray edgesAsJSON = routeAsJSONObject.getJSONArray("path");
                for (int j = 0; j < edgesAsJSON.length(); j++) {
                    String edge = edgesAsJSON.getString(j);
                    edges.add(edge);
                }
                route.setPoints(edges);
                routes.add(route);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        map.clear();
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
                    42);
        }

//        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.nav_bg))
//                .position(inf, 300f, 300f);
// Add an overlay to the map, retaining a handle to the GroundOverlay object.
//        GroundOverlay imageOverlay = map.addGroundOverlay(newarkMap);
        LatLng centerUFG = new LatLng(-16.606231, -49.2622261);
        centerMap(centerUFG);
        addMarkers();
        addPaths();
        map.setOnInfoWindowClickListener(this);
        map.setOnPolylineClickListener(this);

    }

    private void centerMap(LatLng point) {
        this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
    }

    private void addMarkers() {
        for (Place place : places) {
            addMarker(place);
        }
    }

    private void addMarker(Place place) {
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
        for (Route route : routes) {
            addRoute(route);
        }
    }

    private void addRoute(Route route) {
        List<LatLng> edges = new ArrayList<>();
        for (String edge : route.getPoints()) {
            String[] value = edge.split(",");
            LatLng point = new LatLng(Double.parseDouble(value[0]), Double.parseDouble(value[1]));
            edges.add(point);
        }
        Polyline line = map.addPolyline(new PolylineOptions()
                .addAll(edges)
                .width(5)
                .color(Color.parseColor(route.getColor())));
        line.setClickable(true);
        polylines.put(line, route);
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


    @Override
    public void onPolylineClick(Polyline polyline) {
        Route route = polylines.get(polyline);
        Toast.makeText(getActivity(),route.getName(),Toast.LENGTH_SHORT).show();
    }
}
