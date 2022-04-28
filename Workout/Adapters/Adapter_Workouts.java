package com.example.workouttrackerv5.Workout.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerv5.R;
import com.example.workouttrackerv5.Workout.MainDatabaseHelper_Workout;

import java.util.ArrayList;

public class Adapter_Workouts extends RecyclerView.Adapter<Adapter_Workouts.ViewHolder> implements Adapter_Sets.SetRVOnClickListener{

    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final WorkoutRVOnClickListener workoutRVOnClickListener;
    private final MainDatabaseHelper_Workout mdbh;
    private final int dayCode;
    private final grandSetRVOnClickListener grandSetRVOnClickListener;

    public Adapter_Workouts(MainDatabaseHelper_Workout mdbh, int dayCode, WorkoutRVOnClickListener workoutRVOnClickListener, grandSetRVOnClickListener grandSetRVOnClickListener) {
        this.mdbh = mdbh;
        this.dayCode = dayCode;
        this.workoutRVOnClickListener = workoutRVOnClickListener;
        this.grandSetRVOnClickListener = grandSetRVOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_workout, parent, false);
        return new ViewHolder(view, workoutRVOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<String> workoutNameList = mdbh.getWorkoutNames(dayCode);
        String workoutName = workoutNameList.get(position);
        holder.tv_workoutName.setText(workoutName);

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rv_workouts.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(workoutNameList.size());
        Adapter_Sets as = new Adapter_Sets(mdbh, workoutName, dayCode, this);
        holder.rv_workouts.setLayoutManager(layoutManager);
        holder.rv_workouts.setAdapter(as);
        holder.rv_workouts.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return mdbh.getWorkoutNames(dayCode).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView tv_workoutName;
        private final RecyclerView rv_workouts;
        WorkoutRVOnClickListener workoutRVOnClickListener;
        public ViewHolder(@NonNull View itemView, WorkoutRVOnClickListener workoutRVOnClickListener) {
            super(itemView);
            tv_workoutName = itemView.findViewById(R.id.workout_container_tv_workoutName);
            rv_workouts = itemView.findViewById(R.id.workout_container_rv);
            Button btn_addSet = itemView.findViewById(R.id.workout_container_btn_addSet);
            this.workoutRVOnClickListener = workoutRVOnClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            btn_addSet.setOnClickListener(view -> workoutRVOnClickListener.workoutRVOnClickListener(getAdapterPosition(), dayCode));
        }

        @Override
        public void onClick(View view) {
            workoutRVOnClickListener.workoutRVOnClickListener(getAdapterPosition(), dayCode);
        }

        @Override
        public boolean onLongClick(View view) {
            workoutRVOnClickListener.workoutRVonLongClickListener(getAdapterPosition(), dayCode);
            return false;
        }
    }

    public interface WorkoutRVOnClickListener{
        void workoutRVOnClickListener(int position, int dayCode);
        void workoutRVonLongClickListener(int position, int dayCode);
    }

    @Override
    public void setRVOnClickListener(int id, int dayCode) {
        grandSetRVOnClickListener.grandSetRVOnClickListener(id, dayCode);
    }

    public interface grandSetRVOnClickListener {
        void grandSetRVOnClickListener(int id, int dayCode);
    }
}
