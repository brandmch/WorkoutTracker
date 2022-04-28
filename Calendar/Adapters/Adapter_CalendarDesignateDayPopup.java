package com.example.workouttrackerv5.Calendar.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerv5.Calendar.MainDatabaseHelper_Calendar;
import com.example.workouttrackerv5.R;

import java.util.ArrayList;

public class Adapter_CalendarDesignateDayPopup extends RecyclerView.Adapter<Adapter_CalendarDesignateDayPopup.ViewHolder> {

    MainDatabaseHelper_Calendar mdbh;
    ArrayList<String> names;
    private final int dayCode;
    private final AddpOnClickListener addpOnClickListener;

    public Adapter_CalendarDesignateDayPopup(MainDatabaseHelper_Calendar mdbh, int dayCode, AddpOnClickListener addpOnClickListener) {
        this.mdbh = mdbh;
        names = mdbh.getWorkoutDayNamesList();
        this.dayCode = dayCode;
        this.addpOnClickListener = addpOnClickListener;
    }

    @NonNull
    @Override
    public Adapter_CalendarDesignateDayPopup.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.textbox, parent, false);
        return new ViewHolder(view, addpOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_CalendarDesignateDayPopup.ViewHolder holder, int position) {
        holder.tv.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv;
        AddpOnClickListener addpOnClickListener;
        public ViewHolder(@NonNull View itemView, AddpOnClickListener addpOnClickListener) {
            super(itemView);
            tv = itemView.findViewById(R.id.textView10);
            this.addpOnClickListener = addpOnClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String workout = names.get(getAdapterPosition());
            addpOnClickListener.addpOnClickListener(workout, dayCode, getAdapterPosition());
        }
    }

    public interface AddpOnClickListener{
        void addpOnClickListener(String workoutName, int dayCode, int position);
    }
}
