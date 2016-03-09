package com.example.niek.project_hardloop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Entity.TrainingsSchema;
import controllers.DatabaseHandler;
import fragments.DetailFragment;
import fragments.TrainingList;
import interfaces.HomeView;
import presenters.HomePresenter;
import services.DataSync;

public class HomeActivity extends AppCompatActivity implements HomeView, TrainingList.listFragmentCallback {
    /*Fragment tags*/
    private static final String F_LIST = "List";
    private static final String F_DETAIL = "Detail";

    private Boolean listview;
    private Boolean turned;
    private HomePresenter homePresenter;
    private FloatingActionButton fab;
    private Menu menu;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String s = intent.getStringExtra(DataSync.DATASYNC_MESSAGE);
            if(s.equals(DataSync.DATASYNC_restart)) {
                startActivity(new Intent(HomeActivity.this,HomeActivity.class));
                finish();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listview = true;
        turned = false;
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        homePresenter = new HomePresenter(new DatabaseHandler(this), this);
        homePresenter.loadTrainingsSchemas();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, TrainingsSchema_toevoegen.class));
            }
        });

        /*Kijkt of de fragment nog bestaat enzo geeft de nodige data eraan*/
        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean("detailShown")){
                fab.setVisibility(View.INVISIBLE);
                turned = true;
                TrainingsSchema trainingsSchema = savedInstanceState.getParcelable("training");
                homePresenter.setSelectedTraining(trainingsSchema);
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    DetailFragment fragment = new DetailFragment();
                    fragment.setText(trainingsSchema);
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment, F_DETAIL).addToBackStack(F_DETAIL).commit();
                } else {
                    DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailview);
                    fragment.setText(trainingsSchema);
                }
            }
        }
        startService(new Intent(this, DataSync.class));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        if(homePresenter.trainingSelected()){
            savedInstanceState.putBoolean("detailShown",true);
            savedInstanceState.putParcelable("training",homePresenter.getSelectedTraining());
        } else {
            savedInstanceState.putBoolean("detailShown",false);
        }
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
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(DataSync.DATASYNC_RESULT)
        );
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.uitloggen:
                uitloggen();
                return true;
            case R.id.delete:
                deleteTrainingschema(-1);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uitloggen()
    {
        homePresenter.uitloggen();
    }

    private void deleteTrainingschema(final int position) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        LayoutInflater li = LayoutInflater.from(this);
        View errorDialog = li.inflate(R.layout.fragment_error_dialog, null);

        alertDialogBuilder.setView(errorDialog);
        // set dialog message
        alertDialogBuilder
                .setNegativeButton(R.string.nee,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if(position != -1){
                            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                            finish();
                        }
                    }
                })
                .setPositiveButton(R.string.ja,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (position == -1) {
                                    homePresenter.deleteTraining();
                                } else {
                                    homePresenter.deleteTraining(position);
                                }

                                SimpleDateFormat format = new SimpleDateFormat(DataSync.DATAFORMAT);
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
                                sharedPreferences.edit().putString(DataSync.EDITDATESP, format.format(new Date())).apply();
                                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                                finish();
                            }
                        });

        // create alert dialog
        alertDialogBuilder.setTitle(R.string.bevesting_delete);
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void togleDeleteitem(){
        MenuItem item = menu.findItem(R.id.delete);

        if(item.isVisible() && !turned){
            listview = true;
            item.setVisible(false);
        } else {
            listview = false;
            turned = false;
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
        TrainingList trainingList = new TrainingList();

        if(getFragmentManager().findFragmentById(R.id.list) == null || getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            trainingList.setList(list);
            getFragmentManager().beginTransaction().add(R.id.container, trainingList, F_LIST).commit();
        } else {
            TrainingList fragment = (TrainingList) getFragmentManager().findFragmentById(R.id.list);
            fragment.setList(list);
        }
    }

    @Override
    public void onItemSelected(int position) {
        fab.setVisibility(View.INVISIBLE);
        togleDeleteitem();
        if(getFragmentManager().findFragmentById(R.id.list) == null || getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            DetailFragment fragment;
            if( getSupportFragmentManager().findFragmentByTag(F_DETAIL) == null){
                fragment = new DetailFragment();
            } else {
                fragment = (DetailFragment) getFragmentManager().findFragmentByTag(F_DETAIL);
            }

            fragment.setText(homePresenter.getTrainingSchemaAtPosition(position));
            getFragmentManager().beginTransaction().replace(R.id.container, fragment, F_DETAIL).addToBackStack(F_DETAIL).commit();
        } else {
            DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detailview);
            fragment.setText(homePresenter.getTrainingSchemaAtPosition(position));
        }
    }

    @Override
    public void deleteTraining(int position) {
        deleteTrainingschema(position);
    }

    @Override
    public void onBackPressed() {
        // Catch back action and pops from backstack
        // (if you called previously to addToBackStack() in your transaction)
        if(!listview){
            fab.setVisibility(View.VISIBLE);
            togleDeleteitem();
            if (getFragmentManager().getBackStackEntryCount() > 0){
                getFragmentManager().popBackStack();
                homePresenter.setSelectedTraining(null);
            } else {
                if(homePresenter.trainingSelected()){
                    homePresenter.setSelectedTraining(null);
                    homePresenter.loadTrainingsSchemas();
                }
            }
        }
    }
}