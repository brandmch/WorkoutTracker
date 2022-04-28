package com.example.workouttrackerv5.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainDatabaseHelper_Calendar extends SQLiteOpenHelper {

    public final String CALENDAR_TABLE = "CALENDAR_TABLE";
    public final String COLUMN_DAYCODE = "COLUMN_DAYCODE";
    public final String COLUMN_WORKOUTMUSCLEGROUP = "COLUMN_WORKOUTMUSCLEGROUP";
    public final String COLUMN_ID = "ID";


    public MainDatabaseHelper_Calendar(@Nullable Context context) {
        super(context, "calendar.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + CALENDAR_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DAYCODE + " INT, " + COLUMN_WORKOUTMUSCLEGROUP + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(Day day){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DAYCODE, day.getDayCode());
        cv.put(COLUMN_WORKOUTMUSCLEGROUP, day.getWorkoutMuscleGroup());
        long insert = db.insert(CALENDAR_TABLE, null, cv);
        return insert != 1;
    }

    public ArrayList<Day> getEverything(){
        ArrayList<Day> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + CALENDAR_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);
        if (c.moveToFirst()) {
            do {
                int daycode = c.getInt(1);
                String name = c.getString(2);
                Day newDay = new Day(daycode, name);
                returnList.add(newDay);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return returnList;
    }

    public String getWorkoutDay(int x){
        String workoutDay;

        String queryString = "SELECT * FROM " + CALENDAR_TABLE + " WHERE " + COLUMN_DAYCODE + "=" + x;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);
        if (c.moveToFirst()) {
            do {
                if (c.getInt(1) == x) {
                    workoutDay = c.getString(2);
                } else {
                    workoutDay = "no name retrieved";
                }
            } while (c.moveToNext());
        } else {
            workoutDay = "no name retrieved";
        }
        db.close();
        c.close();
        return workoutDay;
    }

    public ArrayList<String> getWorkoutDayNamesList(){
        ArrayList<String> names = new ArrayList<>();

        String queryString = "SELECT * FROM " + CALENDAR_TABLE + " WHERE " + COLUMN_DAYCODE + "=" + 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);
        if (c.moveToFirst()) {
            do {
                if (!names.contains(c.getString(2))) {
                    names.add(c.getString(2));
                }
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return names;
    }

}



































