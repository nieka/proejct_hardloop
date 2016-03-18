package com.example.niek.project_hardloop;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import controllers.DatabaseHandler;
import interfaces.TrainingsSchemaToevoegenPresenter;
import presenters.AddSchemaPresenter;

public class TrainingsSchema_toevoegen extends AppCompatActivity implements TrainingsSchemaToevoegenPresenter, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private AddSchemaPresenter addSchemaPresenter;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location location;
    private Marker locationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings_schema_toevoegen);
        if (mGoogleApiClient == null) {
            System.out.println("--------set google client-------------------");
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.addSchemaPresenter = new AddSchemaPresenter(this,new DatabaseHandler(this),this);
    }

    /*Training toevoegen*/
    public void trainingToevoegen(){
        EditText editTextNaam = (EditText) findViewById(R.id.E_naam);
        EditText editTextLengte = (EditText) findViewById(R.id.E_length);
        EditText editTextOmschrijving = (EditText) findViewById(R.id.E_omschrijving);
        Spinner spinnerSoort = (Spinner) findViewById(R.id.S_TrainingsSchemaSoort);
        Spinner spinnerLengtheSoort = (Spinner) findViewById(R.id.S_LengthSoort);
        addSchemaPresenter.addTrainingSchema(Integer.valueOf(editTextLengte.getText().toString()),
                editTextNaam.getText().toString(),editTextOmschrijving.getText().toString(),
                spinnerSoort.getSelectedItem().toString(),spinnerLengtheSoort.getSelectedItem().toString(),location.getLatitude(), location.getLongitude() );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_trainings_schema_toevoegen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.trainingsschemaOpslaan:
                trainingToevoegen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void goToHome() {
        Toast.makeText(this, R.string.training_toegevoegd, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        System.out.println("-----------------map ready-----------------");

        if(location != null){
            // Add a marker in Sydney and move the camera
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            locationMarker = mMap.addMarker(new MarkerOptions().position(loc).title("Jouw locatie"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("--------------------connected---------------------");
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mMap != null & location != null){
            // Add a marker in Sydney and move the camera
            System.out.println("set location");
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            locationMarker = mMap.addMarker(new MarkerOptions()
                    .position(loc)
                    .title("Mijn training locatie"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16.0f));
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if(locationMarker != null){
                    locationMarker.remove();
                }
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                locationMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Mijn training locatie"));

            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("----------------------suspended van location---------------------");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("----------------------error van location---------------------");
        System.out.println(connectionResult.getErrorMessage());
    }
}
