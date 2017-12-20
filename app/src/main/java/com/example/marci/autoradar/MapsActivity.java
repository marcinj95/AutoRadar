package com.example.marci.autoradar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapters.AutoListAdapter;
import entities.Auto;
import models.AutoRestClient;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, GoogleMap.OnMarkerClickListener
{

    @Nullable
    private Auto mAuto;
    private GoogleMap mMap;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = getIntent();
        mAuto = (Auto)i.getSerializableExtra("AutoS");

        if(mAuto!=null){
            new MapsActivity.HttpRequestAskOne().execute();
        }else {
            new MapsActivity.HttpRequestAsk().execute();
        }



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Tu jesteś");
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcar));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(17.0f);

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }

    private class HttpRequestAsk extends AsyncTask<Void, Void, List<Auto>> {

        @Override
        protected List<Auto> doInBackground(Void... voids) {
            AutoRestClient autoRestClient = new AutoRestClient();
            // Auto auto = autoRestClient.find(12L);
            return autoRestClient.finAll();

        }

        @Override
        protected void onPostExecute(List<Auto> autos) {
            //setLista(autos);
            addMarkers(autos);

        }
    }

    private class HttpRequestAskOne extends AsyncTask<Void, Void, Auto> {

        @Override
        protected Auto doInBackground(Void... voids) {
            AutoRestClient autoRestClient = new AutoRestClient();
            // Auto auto = autoRestClient.find(12L);
            return autoRestClient.find(mAuto.getIdAuto());

        }

        @Override
        protected void onPostExecute(Auto auto) {
            //setLista(autos);
            addOneMarker(auto);

        }
    }

    private void addMarkers(List<Auto> autos){

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
       // List<Marker> markers = new ArrayList<>();
        List<Address> addresses ;
        for (Auto auto:autos) {

            if(auto.getAdres()!=null && auto.getAdres()!=""){
                try {
                    addresses = geoCoder.getFromLocationName(auto.getAdres(), 1);
                    if(!addresses.isEmpty()){
                        LatLng p = new LatLng(
                                addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude());

                        Marker marker = mMap.addMarker(new MarkerOptions().position(p).title(""+ auto.getCarBrand() + " " + auto.getModel() )); //...
                        marker.setTag(auto);
                        marker.showInfoWindow();

                       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p, 14.0f));


                    }



                }catch (IOException e){
                    e.printStackTrace();
                }


            }

        }




        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                Auto auto =(Auto) marker.getTag();

        if (auto != null) {

            Intent intent = new Intent(MapsActivity.this, AutoDetailActivity.class);
            intent.putExtra("AutoS", auto);


            startActivity((intent));
        }

            }
        });

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        //Integer clickCount = (Integer) marker.getTag();
       // Auto auto =(Auto) marker.getTag();



//        // Check if a click count was set, then display the click count.
//        if (auto != null) {
//            //clickCount = clickCount + 1;
//           // marker.setTag(clickCount);
////            Toast.makeText(this,
////                    marker.getTitle() +
////                            " has been clicked  times.",
////                    Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(this, AutoDetailActivity.class);
//            intent.putExtra("AutoS", auto);
//
//
//            startActivity((intent));
//        }
//



        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    private void addOneMarker(Auto auto){


        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses ;
        if(auto.getAdres()!=null && auto.getAdres()!=""){
            try {
                addresses = geoCoder.getFromLocationName(auto.getAdres(), 1);
                if(!addresses.isEmpty()){
                    LatLng p = new LatLng(
                            addresses.get(0).getLatitude(),
                            addresses.get(0).getLongitude());

                    Marker marker = mMap.addMarker(new MarkerOptions().position(p).title(""+ auto.getCarBrand() + " " + auto.getModel() )); //...
                    marker.setTag(auto);
                    marker.showInfoWindow();

                     mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p, 12.0f));


                }



            }catch (IOException e){
                e.printStackTrace();
            }


        }

    }




}
