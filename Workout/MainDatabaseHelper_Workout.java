package com.example.workouttrackerv5.Workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainDatabaseHelper_Workout extends SQLiteOpenHelper {

    public static final String WORKOUT_TABLE = "WORKOUT_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_WORKOUT_DAYCODE = "COLUMN_WORKOUT_DAYCODE";
    public static final String COLUMN_WORKOUT_NAME = "COLUMN_WORKOUT_NAME";
    public static final String COLUMN_WORKOUT_REPS = "COLUMN_WORKOUT_REPS";
    public static final String COLUMN_WORKOUT_WEIGHT = "COLUMN_WORKOUT_WEIGHT";



    public MainDatabaseHelper_Workout(@Nullable Context context) {
        super(context, "workout.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryString = "CREATE TABLE " + WORKOUT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_WORKOUT_DAYCODE + " INT, " + COLUMN_WORKOUT_NAME + " TEXT, " + COLUMN_WORKOUT_REPS + " INT, " + COLUMN_WORKOUT_WEIGHT + " INT)";
        sqLiteDatabase.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
                                                                                                    // IDS = 0
                                                                                                    // DAYCODE = 1
                                                                                                    // NAME = 2
                                                                                                    // REPS = 3
                                                                                                    // WEIGHT = 4
    public boolean addOne(Workout workout){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WORKOUT_DAYCODE, workout.getDayCode());
        cv.put(COLUMN_WORKOUT_NAME, workout.getName());
        cv.put(COLUMN_WORKOUT_WEIGHT, workout.getWeight());
        cv.put(COLUMN_WORKOUT_REPS, workout.getReps());

        long insert = db.insert(WORKOUT_TABLE, null, cv);
        System.out.println(workout);
        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }
    public ArrayList<Workout> getEverything(){
        ArrayList<Workout> everything = new ArrayList<>();
        String queryString = "SELECT * FROM " + WORKOUT_TABLE + " WHERE " + COLUMN_WORKOUT_DAYCODE + ">" + 1000;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);

        if(c.moveToFirst()) {
            do {
                int daycode = c.getInt(1);
                String name = c.getString(2);
                int reps = c.getInt(3);
                int weight = c.getInt(4);
                everything.add(new Workout(daycode, name, reps, weight));
            } while (c.moveToNext());
        } else {
            System.out.println("SWW: mdbh_workout.getEverything()");
        }
        return everything;
    }
    public ArrayList<String> getWorkoutNames(int dayCode){
        ArrayList<String> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + WORKOUT_TABLE + " WHERE " + COLUMN_WORKOUT_DAYCODE + "=" + dayCode;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);
        if (c.moveToFirst()) {
            do {
                String name = c.getString(2);
                if (!list.contains(name)){
                    list.add(name);
                } else {

                }
            } while (c.moveToNext());
        } else {
            System.out.println("SWW mdbh_workout.getWorkoutNames()");
        }
        c.close();
        db.close();
        return list;
    }
    public ArrayList<String> getRepsForSet(String workoutName, int dayCode){
        ArrayList<String> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + WORKOUT_TABLE + " WHERE " + COLUMN_WORKOUT_NAME + "=" + "'" + workoutName + "' "+ " AND " + COLUMN_WORKOUT_DAYCODE + "=" + dayCode;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);
        if(c.moveToFirst()){
            do{
                String a = String.valueOf(c.getInt(3));
                list.add(a);
            } while (c.moveToNext());
        } else {
            System.out.println("SWW mdbh_workout.getRepsForSet()");
        }
        db.close();
        c.close();
        return list;
    }
    public ArrayList<String> getWeightForSet(String workoutName, int dayCode){
        ArrayList<String> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + WORKOUT_TABLE + " WHERE " + COLUMN_WORKOUT_NAME + "=" + "'" + workoutName + "' "+ " AND " + COLUMN_WORKOUT_DAYCODE + "=" + dayCode;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);
        if(c.moveToFirst()){
            do{
                String a = String.valueOf(c.getInt(4));
                list.add(a);
            } while (c.moveToNext());
        } else {
            System.out.println("SWW mdbh_workout.getWeightForSet()");
        }
        db.close();
        c.close();
        return list;
    }
    public ArrayList<String> getIDsForSet(String workoutName, int dayCode){
        ArrayList<String> list = new ArrayList<>();
        String queryString = "SELECT * FROM " + WORKOUT_TABLE + " WHERE " + COLUMN_WORKOUT_NAME + "=" + "'" + workoutName + "' "+ " AND " + COLUMN_WORKOUT_DAYCODE + "=" + dayCode;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);
        if(c.moveToFirst()){
            do{
                String a = String.valueOf(c.getInt(0));
                list.add(a);
            } while (c.moveToNext());
        } else {
            System.out.println("SWW mdbh_workout.getIDsForSet()");
        }
        db.close();
        c.close();
        return list;
    }
    public void deleteWorkout(String workoutName, int dayCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + WORKOUT_TABLE + " WHERE " + COLUMN_WORKOUT_DAYCODE + "=" + dayCode + " AND " + COLUMN_WORKOUT_NAME + "=" + "'" + workoutName + "'";
        Cursor c = db.rawQuery(queryString, null);
        if (c.moveToFirst()) {
            do {
            } while (c.moveToNext());
        }
        c.close();
        db.close();
    }
    public boolean checkIfInitialSet(String workoutName, int dayCode){
        String queryString = "SELECT * FROM " + WORKOUT_TABLE + " WHERE " + COLUMN_WORKOUT_DAYCODE + "=" + dayCode + " AND " + COLUMN_WORKOUT_NAME + "=" + "'" + workoutName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryString, null);
        if(c.moveToFirst()) {
            do {
                if(c.getInt(3) == 0 && c.getInt(4) == 0) {
                    return true;
                } else {
                    return false;
                }
            } while (c.moveToNext());
        } else {
            System.out.println("SWW mdbh_workout.checkIfInitialSet");
            return false;
        }



    }
    public void updateInitialSet(int reps, int weight, String workoutName, int dayCode) {
        String queryString = "UPDATE " + WORKOUT_TABLE + " SET " + COLUMN_WORKOUT_REPS + "=" + reps + ", " + COLUMN_WORKOUT_WEIGHT + "=" + weight + " WHERE " + COLUMN_WORKOUT_DAYCODE + "=" + dayCode + " AND " + COLUMN_WORKOUT_NAME + "=" + "'" + workoutName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(queryString, null);
        if (c.moveToFirst()) {
            do {

            } while (c.moveToNext());
        }
        db.close();
        c.close();
    }
    public void deleteSet(int id, int dayCode){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + WORKOUT_TABLE + " WHERE " + COLUMN_ID + "=" + id + " AND " + COLUMN_WORKOUT_DAYCODE + "=" + dayCode;
        Cursor c = db.rawQuery(queryString, null);
        if(c.moveToFirst()) {
            do {

            } while (c.moveToNext());
        } else {

        }
        c.close();
        db.close();
    }

}
























