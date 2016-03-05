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

import controllers.DatabaseHandler;
import interfaces.TrainingsSchemaToevoegenPresenter;
import presenters.AddSchemaPresenter;

public class TrainingsSchema_toevoegen extends AppCompatActivity implements TrainingsSchemaToevoegenPresenter{

    private AddSchemaPresenter addSchemaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainings_schema_toevoegen);
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
                spinnerSoort.getSelectedItem().toString(),spinnerLengtheSoort.getSelectedItem().toString());
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

    @Override
    public void goToHome() {
        Toast.makeText(this, R.string.training_toegevoegd, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}
