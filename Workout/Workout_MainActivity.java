package com.example.workouttrackerv5.Workout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerv5.R;
import com.example.workouttrackerv5.Workout.Adapters.Adapter_Workouts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class Workout_MainActivity extends AppCompatActivity implements Adapter_Workouts.WorkoutRVOnClickListener, Adapter_Workouts.grandSetRVOnClickListener {

    MainDatabaseHelper_Workout mdbh;
    int dateTdayCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_activitymain);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        dateTdayCode = (int) getIntent().getExtras().get("dateT");

        mdbh = new MainDatabaseHelper_Workout(this);

        getAdapter(dateTdayCode);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton_on_activity_main);
        fab.setOnClickListener(view -> createNewWorkoutPopup());
    }

    public void getAdapter(int dayCode){
        RecyclerView rv = findViewById(R.id.rv_mainActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Workout_MainActivity.this);
        Adapter_Workouts aw = new Adapter_Workouts(mdbh, dayCode, this, this);
        rv.setAdapter(aw);
        rv.setLayoutManager(layoutManager);
    }

    public void createNewWorkoutPopup(){
        AlertDialog.Builder db = new AlertDialog.Builder(this);
        final View setPopupView = getLayoutInflater().inflate(R.layout.popup_add_workout_to_list, null);

        EditText et_name = setPopupView.findViewById(R.id.et_workoutName_pop_up_add_workout_to_list);
        Button btn_done = setPopupView.findViewById(R.id.btn_done_pop_up_add_workout_to_list);

        db.setView(setPopupView);
        AlertDialog dialog = db.create();
        dialog.show();

        btn_done.setOnClickListener(view -> {
            if (!et_name.getText().toString().equals("")) {
                Workout newWorkout = new Workout(dateTdayCode, et_name.getText().toString(), 0, 0);
                mdbh.addOne(newWorkout);
            } else {
                Toast.makeText(Workout_MainActivity.this, "Workout name field cannot be empty", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            dialog.dismiss();
            getAdapter(dateTdayCode);
        });
    }

    public void createNewSetPopup(int position) {
        AlertDialog.Builder db = new AlertDialog.Builder(this);
        final View setPopupView = getLayoutInflater().inflate(R.layout.popup_add_set_to_workout, null);

        EditText reps = setPopupView.findViewById(R.id.et_add_reps);
        EditText weight = setPopupView.findViewById(R.id.et_add_weight);
        Button done = setPopupView.findViewById(R.id.btn_done_pop_up_add_set_to_workout);
        Button nextSet = setPopupView.findViewById(R.id.btn_done_NEXT_SET);

        db.setView(setPopupView);
        AlertDialog d = db.create();
        d.show();

        done.setOnClickListener(view -> {
            if (!weight.getText().toString().equals("") && !reps.getText().toString().equals("")) {
                Workout newWorkout = new Workout(dateTdayCode, mdbh.getWorkoutNames(dateTdayCode).get(position), Integer.parseInt(reps.getText().toString()), Integer.parseInt(weight.getText().toString()));
                if (mdbh.checkIfInitialSet(mdbh.getWorkoutNames(dateTdayCode).get(position), dateTdayCode)) {
                    mdbh.updateInitialSet(Integer.parseInt(reps.getText().toString()), Integer.parseInt(weight.getText().toString()), mdbh.getWorkoutNames(dateTdayCode).get(position), dateTdayCode);
                } else {
                    mdbh.addOne(newWorkout);
                }
            } else {
                Toast.makeText(Workout_MainActivity.this, "Weight and/or Reps cannot be empty", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
            d.dismiss();
            getAdapter(dateTdayCode);
        });
        nextSet.setOnClickListener(view -> {
            if (!weight.getText().toString().equals("") && !reps.getText().toString().equals("")) {
                Workout newWorkout = new Workout(dateTdayCode, mdbh.getWorkoutNames(dateTdayCode).get(position), Integer.parseInt(reps.getText().toString()), Integer.parseInt(weight.getText().toString()));
                if (mdbh.checkIfInitialSet(mdbh.getWorkoutNames(dateTdayCode).get(position), dateTdayCode)) {
                    mdbh.updateInitialSet(Integer.parseInt(reps.getText().toString()), Integer.parseInt(weight.getText().toString()), mdbh.getWorkoutNames(dateTdayCode).get(position), dateTdayCode);
                } else {
                    mdbh.addOne(newWorkout);
                }
                getAdapter(dateTdayCode);
                reps.setText("");
                weight.setText("");
            } else {
                Toast.makeText(Workout_MainActivity.this, "Weight and/or Reps cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createDeleteWorkoutPopup(int position){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View setPopupView = getLayoutInflater().inflate(R.layout.popup_delete_workout, null);
        Button yes = setPopupView.findViewById(R.id.btn_yes_delete_workout);
        Button no = setPopupView.findViewById(R.id.btn_no_delete_workout);

        dialogBuilder.setView(setPopupView);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        yes.setOnClickListener(view -> {
            String workoutName = mdbh.getWorkoutNames(dateTdayCode).get(position);
            mdbh.deleteWorkout(workoutName, dateTdayCode);
            dialog.dismiss();
            getAdapter(dateTdayCode);
        });
        no.setOnClickListener(view -> dialog.dismiss());
    }

    public void createDeleteSetPopup(int id, int dayCode){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View setPopupView = getLayoutInflater().inflate(R.layout.popup_delete_set, null);
        Button yes = setPopupView.findViewById(R.id.btn_yes_delete);
        Button no = setPopupView.findViewById(R.id.btn_no_delete);
        dialogBuilder.setView(setPopupView);
        AlertDialog d = dialogBuilder.create();
        d.show();
        yes.setOnClickListener(view -> {
            mdbh.deleteSet(id, dayCode);
            d.dismiss();
            getAdapter(dateTdayCode);
        });
        no.setOnClickListener(view -> d.dismiss());
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void workoutRVOnClickListener(int position, int dayCode) {
        createNewSetPopup(position);
    }

    @Override
    public void workoutRVonLongClickListener(int position, int dayCode) {
        createDeleteWorkoutPopup(position);
    }

    @Override
    public void grandSetRVOnClickListener(int id, int dayCode) {
        createDeleteSetPopup(id, dayCode);
    }
}































