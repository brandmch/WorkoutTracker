package com.example.workouttrackerv5.Calendar.Adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerv5.Calendar.MainDatabaseHelper_Calendar;
import com.example.workouttrackerv5.R;

import java.util.ArrayList;

public class Adapter_CalendarItem extends RecyclerView.Adapter<Adapter_CalendarItem.ViewHolder> {

    ArrayList<String> workoutMuscleGroups;
    MainDatabaseHelper_Calendar mdbh;


    public Adapter_CalendarItem(MainDatabaseHelper_Calendar mdbh, String workoutName) {
        this.mdbh = mdbh;
        workoutMuscleGroups = new ArrayList<>();
        workoutMuscleGroups.add(workoutName);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.textbox, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = workoutMuscleGroups.get(position);
        if (!name.equals("no name retrieved")) {
            holder.tv.setText(name);
        } else {
            holder.tv.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return workoutMuscleGroups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textView10);
        }
    }
}
