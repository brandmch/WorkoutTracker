package com.example.workouttrackerv5.MenuItems;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerv5.Calendar.Day;
import com.example.workouttrackerv5.Calendar.MainDatabaseHelper_Calendar;
import com.example.workouttrackerv5.MenuItems.Adapters.Adapter_MenuItem_AddWorkout;
import com.example.workouttrackerv5.R;

import java.util.ArrayList;

public class AddWorkout extends AppCompatActivity {

    MainDatabaseHelper_Calendar mdbh;
    Button btn_add;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        System.out.println("AddWOrkout.class Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item_add_workout);

        mdbh = new MainDatabaseHelper_Calendar(this);
        setAdapter();

        et = findViewById(R.id.editText);
        btn_add = findViewById(R.id.btn_menu_item_add_workout_day);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = et.getText().toString();
                System.out.println("WORKOUT NAME = " + newName + "NEXT DAY CODE IS " + mdbh.getNextDayCode());
                Day newd = new Day(0, newName);
                mdbh.addOne(newd);
                System.out.println(mdbh.getEverything());
                setAdapter();
            }
        });
    }

    public void setAdapter(){

        ArrayList<String> names = new ArrayList<>();
        names = mdbh.getWorkoutDayNamesList();

        RecyclerView rv_addWorkout = findViewById(R.id.rv_menu_addWorkout);
        LinearLayoutManager lm = new LinearLayoutManager(AddWorkout.this);
        Adapter_MenuItem_AddWorkout adapt = new Adapter_MenuItem_AddWorkout(this, mdbh, names);
        rv_addWorkout.setLayoutManager(lm);
        rv_addWorkout.setAdapter(adapt);
    }

}
