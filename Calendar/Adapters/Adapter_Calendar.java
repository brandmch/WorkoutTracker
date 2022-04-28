package com.example.workouttrackerv5.Calendar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerv5.Calendar.MainDatabaseHelper_Calendar;
import com.example.workouttrackerv5.R;

import java.util.ArrayList;

public class Adapter_Calendar  extends RecyclerView.Adapter<Adapter_Calendar.ViewHolder> {

    private final LayoutInflater mlayoutInflater;
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final CalendarRVOnClickListener calendarRVOnClickListener;

    private final ArrayList<String> dayNumbers;

    private final String monthName;
    private final String yearNumber;

    private final MainDatabaseHelper_Calendar mdbh;


    public Adapter_Calendar(MainDatabaseHelper_Calendar mdbh, Context context, ArrayList<String> dayNumbers, String monthName, String yearNumber, CalendarRVOnClickListener calendarRVOnClickListener) {
        this.mdbh = mdbh;
        this.mlayoutInflater = LayoutInflater.from(context);
        this.dayNumbers = dayNumbers;
        this.yearNumber = yearNumber;
        this.monthName = monthName;
        this.calendarRVOnClickListener = calendarRVOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mlayoutInflater.inflate(R.layout.calendar_day_block, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.2);
        return new ViewHolder(view, calendarRVOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String date = dayNumbers.get(position);
        holder.tv.setText(date);

        String q = String.valueOf(getMonthNumber(monthName));

        if (Integer.parseInt(q) < 10){
            q = "0" + q;
        }
        if (!date.equals("") && Integer.parseInt(date) < 10) {
            date = "0" + date;
        }
        String p = yearNumber + q + date;
        int x = Integer.parseInt(p);
        String nameq = mdbh.getWorkoutDay(x);


        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rv.getContext(), LinearLayoutManager.VERTICAL, false);
        Adapter_CalendarItem aci = new Adapter_CalendarItem(mdbh, nameq);
        holder.rv.setLayoutManager(layoutManager);
        holder.rv.setAdapter(aci);
        holder.rv.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return dayNumbers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv;
        RecyclerView rv;
        CalendarRVOnClickListener calendarRVOnClickListener;
        public ViewHolder(@NonNull View itemView, CalendarRVOnClickListener calendarRVOnClickListener) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_calendar_dayblock);
            rv = itemView.findViewById(R.id.rv_calendar_dayblock);
            this.calendarRVOnClickListener = calendarRVOnClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            calendarRVOnClickListener.calendarRVOnClickListener(getAdapterPosition());
        }
    }

    public interface CalendarRVOnClickListener{
        void calendarRVOnClickListener(int position);
    }

    public int getMonthNumber(String month){
        int x;
        switch (month) {
            case "JANUARY":
                x = 1;
                break;
            case "FEBRUARY":
                x = 2;
                break;
            case "MARCH":
                x = 3;
                break;
            case "APRIL":
                x = 4;
                break;
            case "MAY":
                x = 5;
                break;
            case "JUNE":
                x = 6;
                break;
            case "JULY":
                x = 7;
                break;
            case "AUGUST":
                x = 8;
                break;
            case "SEPTEMBER":
                x = 9;
                break;
            case "OCTOBER":
                x = 10;
                break;
            case "NOVEMBER":
                x = 11;
                break;
            case "DECEMBER":
                x = 12;
                break;
            default:
                x = 0;
        }
        return x;
    }
}
