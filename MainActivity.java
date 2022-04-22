package com.example.workouttrackerv5;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.workouttrackerv5.Calendar.Adapters.Adapter_Calendar;
import com.example.workouttrackerv5.Calendar.Adapters.Adapter_CalendarDesignateDayPopup;
import com.example.workouttrackerv5.Calendar.Day;
import com.example.workouttrackerv5.Calendar.MainDatabaseHelper_Calendar;
import com.example.workouttrackerv5.MenuItems.AddWorkout;
import com.example.workouttrackerv5.Workout.Workout_MainActivity;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements Adapter_Calendar.CalendarRVOnClickListener, Adapter_CalendarDesignateDayPopup.AddpOnClickListener{

    LocalDate dateT;
    ArrayList<String> dayNumbers;
    YearMonth yearMonth;
    MainDatabaseHelper_Calendar mdbh;

    // these two values are ONLY used to transfer info from addpOnClickListener to createDesignateDayPopup;
    String NEWWORKOUTNAME;
    int NEWDAYCODE;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateT = LocalDate.now();
        System.out.println(getDayCode(dateT));

        mdbh = new MainDatabaseHelper_Calendar(MainActivity.this);
        if (mdbh.getEverything().size() == 0){
            mdbh.addOne(new Day(getDayCode(dateT), "Chest"));
            mdbh.addOne(new Day(20220410, "Back"));
            mdbh.addOne(new Day(20220419, "Shoulders"));
            mdbh.addOne(new Day(20220502, "Legs"));
            mdbh.addOne(new Day(0, "Chest"));
            mdbh.addOne(new Day(0, "Back"));
            mdbh.addOne(new Day(0, "Shoulders"));
            mdbh.addOne(new Day(0, "Legs"));
        }


        setAdapter();
        System.out.println(dateT.getYear());

        Button btn_nextMonth = findViewById(R.id.btn_calendar_nextMonth);
        Button btn_lastMonth = findViewById(R.id.btn_calendar_lastMonth);
        btn_nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateT = dateT.plusMonths(1);
                setAdapter();
            }
        });
        btn_lastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateT = dateT.minusMonths(1);
                setAdapter();
            }
        });

    }

    public void setAdapter(){
        RecyclerView rv_calendar = findViewById(R.id.rv_calendar);
        TextView tv_monthName = findViewById(R.id.tv_calendar_month);
        tv_monthName.setText(String.valueOf(dateT.getMonth()));

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 7);
        Adapter_Calendar ac = new Adapter_Calendar(mdbh, this, getDayNumbersString(), String.valueOf(dateT.getMonth()), String.valueOf(dateT.getYear()), this);
        rv_calendar.setLayoutManager(layoutManager);
        rv_calendar.setAdapter(ac);

    }
    public ArrayList<String> getDayNumbersString(){
        dayNumbers = new ArrayList<String>();

        LocalDate firstOfMonth = dateT.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        int year = dateT.getYear();
        int month = dateT.getMonthValue();
        yearMonth = YearMonth.of(year,month);
        int daysInMonth = yearMonth.lengthOfMonth();

        int y = 35;
        if(dayOfWeek > 6) {
            dayOfWeek = 0;
        }
        if(daysInMonth + dayOfWeek >= 36) {
            y = 42;
        }
        int x = daysInMonth + dayOfWeek + 1;

        for (int i = 1; i <= y; i++) {
            if (i <= dayOfWeek || i >= x){
                dayNumbers.add("");
            } else {
                dayNumbers.add(String.valueOf(i - dayOfWeek));
            }
        }

        return dayNumbers;
    }
    public int getDayCode(LocalDate localDate){
        LocalDate datee = localDate;
        int x;
        String a = String.valueOf(datee);
        a = a.replace("-", "");
        System.out.println("YOLO " + a);
        x = Integer.parseInt(a);
        return x;
    }

    public void createDesignateDayPopup(int dayCode){
        final View setPopupView = getLayoutInflater().inflate(R.layout.calendar_popup_designatedayworkout, null);
        RecyclerView rv_des = setPopupView.findViewById(R.id.rv_designateDayWorkout);
        Button btn_done = setPopupView.findViewById(R.id.btn_designateDayWorkout);
        Button btn_begin_workout = setPopupView.findViewById(R.id.btn_begin_workout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Adapter_CalendarDesignateDayPopup addp = new Adapter_CalendarDesignateDayPopup(mdbh, dayCode, this);

        rv_des.setLayoutManager(layoutManager);
        rv_des.setAdapter(addp);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(setPopupView);
        AlertDialog dialog = dialogBuilder.create();

        dialog.show();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("this is the btuton");
                dialog.dismiss();
                mdbh.addOne(new Day(NEWDAYCODE, NEWWORKOUTNAME));
                setAdapter();
                NEWDAYCODE = 0;
                NEWWORKOUTNAME = "";
            }
        });
        btn_begin_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialog.dismiss();
                Intent i = new Intent(MainActivity.this, Workout_MainActivity.class);
                i.putExtra("dateT", getDayCode(dateT));
                System.out.println("dateT in mainact = " + dateT);
                dialog.dismiss();
                startActivity(i);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem i){
        switch (i.getItemId()) {
            case R.id.menu_item_add_workout_day:
                System.out.println("shit has been clicked");
                Intent p = new Intent(MainActivity.this, AddWorkout.class);
                startActivity(p);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void calendarRVOnClickListener(int position) {
        String a = String.valueOf(dayNumbers.get(position));
        if (Integer.parseInt(a) < 10) {
            a = "0" + a;
        }
        System.out.println("day = " + a + " yearMonth = " + yearMonth);
        String b = String.valueOf(yearMonth).replace("-", "");
        String c = b + a;
        System.out.println(c);
        dateT = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), Integer.parseInt(a));
        createDesignateDayPopup(Integer.parseInt(c));
    }


    @Override
    public void addpOnClickListener(String workoutName, int dayCode, int position) {
        System.out.println("FUCK " + workoutName + " " + dayCode);
        NEWDAYCODE = dayCode;
        NEWWORKOUTNAME = workoutName;
    }
}
































