package com.example.workouttrackerv5.Workout;

import androidx.annotation.NonNull;

public class Workout {

    private int dayCode, reps, weight;
    private String name;

    // CONSTRUCTOR
    public Workout(int dayCode, String name, int reps, int weight) {
        this.dayCode = dayCode;
        this.reps = reps;
        this.weight = weight;
        this.name = name;
    }

    // ToSTRING
    @NonNull
    @Override
    public String toString() {
        return "Workout{" +
                "dayCode=" + dayCode +
                ", reps=" + reps +
                ", weight=" + weight +
                ", name='" + name + '\'' +
                '}';
    }

    // GETTERS AND SETTERS
    public int getDayCode() {
        return dayCode;
    }
    public void setDayCode(int dayCode) {
        this.dayCode = dayCode;
    }
    public int getReps() {
        return reps;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
