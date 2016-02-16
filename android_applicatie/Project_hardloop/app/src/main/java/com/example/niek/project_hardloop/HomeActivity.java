package com.example.niek.project_hardloop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import Adapters.TrainingslistAdapter;
import Entity.TrainingsSchema;
import controllers.DatabaseHandler;
import interfaces.HomeView;
import presenters.HomePresenter;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private RecyclerView traininglist;
    private LinearLayoutManager mLayoutManager;

    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        homePresenter = new HomePresenter(new DatabaseHandler(this), this);

        //set recylerView met data;
        traininglist = (RecyclerView) findViewById(R.id.traininglijst);
        mLayoutManager = new LinearLayoutManager(this);
        traininglist.setLayoutManager(mLayoutManager);

        homePresenter.loadTrainingsSchemas();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, TrainingsSchema_toevoegen.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.trainingsschemaOpslaan:
                uitloggen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uitloggen()
    {
        // TODO: 12-2-2016 Zorgen dat de gebruiker uitlogt

    }

    @Override
    public void showTrainingsSchema() {

    }

    @Override
    public void toLogin() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    public void loadTrainingData(List<TrainingsSchema> list) {
        TrainingslistAdapter adapter = new TrainingslistAdapter(list);
        traininglist.setAdapter(adapter);
    }

    /*Test methodes*/

    private void showlist(List<TrainingsSchema> list){
        for(int i=0; i< list.size(); i++){
            System.out.println("naam: " + list.get(i).getNaam() + " omschrijving: " + list.get(i).getOmschrijving()
                    + " lengthe: " + list.get(i).getLengte() + " soort: " + list.get(i).getLengteSoort() + " lengthe soort: " + list.get(i).getLengteSoort() + " id: " + list.get(i).getId());
        }
    }

}
