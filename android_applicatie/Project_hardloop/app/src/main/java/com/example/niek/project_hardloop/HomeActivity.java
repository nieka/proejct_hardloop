package com.example.niek.project_hardloop;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import Entity.TrainingsSchema;
import controllers.DatabaseHandler;
import fragments.DetailFragment;
import fragments.TrainingList;
import interfaces.HomeView;
import presenters.HomePresenter;

public class HomeActivity extends AppCompatActivity implements HomeView, TrainingList.OnitemSelect {

    private TrainingList trainingList;
    private HomePresenter homePresenter;
    private FloatingActionButton fab;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        System.out.println("oncreate");
        homePresenter = new HomePresenter(new DatabaseHandler(this), this);
        homePresenter.loadTrainingsSchemas();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, TrainingsSchema_toevoegen.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_home, menu);
        togleDeleteitem();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.uitloggen:
                uitloggen();
                return true;
            case R.id.delete:
                deleteTrainingschema();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uitloggen()
    {
        homePresenter.uitloggen();
    }

    private void deleteTrainingschema(){
        homePresenter.deleteTraining();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void togleDeleteitem(){
        MenuItem item = menu.findItem(R.id.delete);
        if(item.isVisible()){
            item.setVisible(false);
        } else {
            item.setVisible(true);
        }
    }

    @Override
    public void toLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void loadTrainingData(List<TrainingsSchema> list)
    {
        trainingList = new TrainingList();
        System.out.println(getResources().getConfiguration().orientation);
        if(getFragmentManager().findFragmentById(R.id.list) == null || getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            trainingList.setList(list);
            getFragmentManager().beginTransaction().add(R.id.container, trainingList, "list").commit();
        } else {
            TrainingList fragment = (TrainingList) getFragmentManager().findFragmentById(R.id.list);
            fragment.setList(list);
        }
    }
    @Override
    public void onItemSelected(int position) {
        fab.setVisibility(View.INVISIBLE);
        togleDeleteitem();
        if(getFragmentManager().findFragmentById(R.id.list) == null){
            DetailFragment fragment = new DetailFragment();
            fragment.setText(homePresenter.getTrainingSchemaAtPosition(position));
            getFragmentManager().beginTransaction().replace(R.id.container, fragment, "detail").addToBackStack("detail").commit();
        } else {
            DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailview);
            fragment.setText(homePresenter.getTrainingSchemaAtPosition(position));
        }
    }

    @Override
    public void onBackPressed() {
        fab.setVisibility(View.VISIBLE);
        togleDeleteitem();
        // Catch back action and pops from backstack
        // (if you called previously to addToBackStack() in your transaction)
        if (getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        }
        // Default action on back pressed
        else super.onBackPressed();
    }
}
