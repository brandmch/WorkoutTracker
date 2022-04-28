package com.example.workouttrackerv5.MenuItems.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerv5.Calendar.MainDatabaseHelper_Calendar;
import com.example.workouttrackerv5.R;

import java.util.ArrayList;

public class Adapter_MenuItem_AddWorkout extends RecyclerView.Adapter<Adapter_MenuItem_AddWorkout.ViewHolder> {

    private final LayoutInflater mlayoutInflater;
    MainDatabaseHelper_Calendar mdbh;
    ArrayList<String> names;

    public Adapter_MenuItem_AddWorkout(Context context, MainDatabaseHelper_Calendar mdbh, ArrayList<String> names) {
        this.mlayoutInflater = LayoutInflater.from(context);
        this.mdbh = mdbh;
        this.names = names;
    }

    @NonNull
    @Override
    public Adapter_MenuItem_AddWorkout.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mlayoutInflater.inflate(R.layout.textbox, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_MenuItem_AddWorkout.ViewHolder holder, int position) {
        String name = names.get(position);
        holder.tv.setText(name);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textView10);
        }
    }
}
