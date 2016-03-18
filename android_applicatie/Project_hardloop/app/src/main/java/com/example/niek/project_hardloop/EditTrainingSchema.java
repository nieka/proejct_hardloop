package com.example.niek.project_hardloop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import Entity.TrainingsSchema;
import interfaces.EditView;
import presenters.EditSchemaPresenter;

public class EditTrainingSchema extends AppCompatActivity implements OnMapReadyCallback, EditView {

    private TrainingsSchema trainingsSchema;

    //ui objecten
    private EditText naam;
    private EditText afstand;
    private Spinner afstandSoort;
    private Spinner soort;
    private EditText omschrijving;
    private GoogleMap map;

    private Marker locationMarker;
    private EditSchemaPresenter editSchemaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_training_schema);
        editSchemaPresenter = new EditSchemaPresenter(this, this);

        trainingsSchema = getIntent().getParcelableExtra("trainig");
        if(trainingsSchema != null){

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            naam = (EditText) findViewById(R.id.E_naam);
            naam.setText(trainingsSchema.getNaam());
            afstand = (EditText) findViewById(R.id.E_length);
            afstand.setText(String.valueOf(trainingsSchema.getLengte()));
            omschrijving = (EditText) findViewById(R.id.E_omschrijving);
            omschrijving.setText(trainingsSchema.getOmschrijving());
            soort = (Spinner) findViewById(R.id.S_TrainingsSchemaSoort);
            String[] soorten = getResources().getStringArray(R.array.soort);
            soort.setSelection(indexOfString(trainingsSchema.getSoort(),soorten));
            afstandSoort = (Spinner) findViewById(R.id.S_LengthSoort);
            String[] lengtesSoort = getResources().getStringArray(R.array.lengthe);
            soort.setSelection(indexOfString(trainingsSchema.getLengteSoort(),lengtesSoort));
        }


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
                trainingAanpassen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*Training toevoegen*/
    private void trainingAanpassen(){
        editSchemaPresenter.editTraining(trainingsSchema.getId(), Integer.valueOf(afstand.getText().toString()),
                naam.getText().toString(), omschrijving.getText().toString(),
                soort.getSelectedItem().toString(), afstandSoort.getSelectedItem().toString(), trainingsSchema.getLatitude(), trainingsSchema.getLongitude());
    }

    private int indexOfString(String searchString, String[] domain)
    {
        for(int i = 0; i < domain.length; i++){
            if(searchString.equals(domain[i])){
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng loc;
        System.out.println("lat= " + trainingsSchema.getLatitude());
        if(trainingsSchema.getLatitude() ==  0.0){
            loc = new LatLng(51.687882, 5.286655);
        } else {
            loc = new LatLng(trainingsSchema.getLatitude(), trainingsSchema.getLongitude());
        }

        locationMarker= map.addMarker(new MarkerOptions().position(loc).title("Mijn training locatie"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
        map.moveCamera(CameraUpdateFactory.newLatLng(loc));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (locationMarker != null) {
                    locationMarker.remove();
                }
                trainingsSchema.setLatitude(latLng.latitude);
                trainingsSchema.setLongitude(latLng.longitude);
                locationMarker = map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Mijn training locatie"));

            }
        });
    }

    @Override
    public void goToHome() {
        Toast.makeText(this, R.string.training_aangepast, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}
