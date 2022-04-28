package com.example.workouttrackerv5.Calendar;

import androidx.annotation.NonNull;

public class Day {

    int dayCode;
    String workoutMuscleGroup;


    // CONSTRUCTOR
    public Day(int dayCode, String workoutMuscleGroup) {
        this.dayCode = dayCode;
        this.workoutMuscleGroup = workoutMuscleGroup;
    }

    // ToString
    @NonNull
    @Override
    public String toString() {
        return "Day{" +
                "dayCode=" + dayCode +
                ", workoutMuscleGroup='" + workoutMuscleGroup + '\'' +
                '}';
    }

    // GETTERS AND SETTERS
    public int getDayCode() {
        return dayCode;
    }
    public void setDayCode(int dayCode) {
        this.dayCode = dayCode;
    }
    public String getWorkoutMuscleGroup() {
        return workoutMuscleGroup;
    }
    public void setWorkoutMuscleGroup(String workoutMuscleGroup) {
        this.workoutMuscleGroup = workoutMuscleGroup;
    }
}
