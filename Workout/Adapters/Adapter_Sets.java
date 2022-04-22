package com.example.workouttrackerv5.Workout.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttrackerv5.R;
import com.example.workouttrackerv5.Workout.MainDatabaseHelper_Workout;
import com.example.workouttrackerv5.Workout.Set;

import java.util.ArrayList;

public class Adapter_Sets extends RecyclerView.Adapter<Adapter_Sets.ViewHolder> {

    MainDatabaseHelper_Workout mdbh;
    String workoutName;
    int dayCode;
    private SetRVOnClickListener setRVOnClickListener;

    public Adapter_Sets(MainDatabaseHelper_Workout mdbh, String workoutName, int dayCode, SetRVOnClickListener setRVOnClickListener) {
        this.mdbh = mdbh;
        this.workoutName = workoutName;
        this.dayCode = dayCode;
        this.setRVOnClickListener = setRVOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_set, parent, false);
        return new ViewHolder(view, setRVOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<String> repsList = mdbh.getRepsForSet(workoutName, dayCode);
        ArrayList<String> weightList = mdbh.getWeightForSet(workoutName, dayCode);
        ArrayList<String> IDList = mdbh.getIDsForSet(workoutName, dayCode);
        String reps = repsList.get(position);
        String weight = weightList.get(position);
        String id = IDList.get(position);
        holder.tv_reps.setText(reps);
        holder.tv_weight.setText(weight);
        holder.tv_id.setText(id);
    }

    @Override
    public int getItemCount() {
        return mdbh.getWeightForSet(workoutName, dayCode).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_reps;
        TextView tv_weight;
        TextView tv_id;
        SetRVOnClickListener setRVOnClickListener;
        public ViewHolder(@NonNull View itemView, SetRVOnClickListener setRVOnClickListener) {
            super(itemView);
            tv_reps = itemView.findViewById(R.id.set_container_tv_reps);
            tv_weight = itemView.findViewById(R.id.set_container_tv_weight);
            tv_id = itemView.findViewById(R.id.hidden_id_view);
            this.setRVOnClickListener = setRVOnClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int x = Integer.parseInt(String.valueOf(tv_id.getText()));
            setRVOnClickListener.setRVOnClickListener(x, dayCode);
        }
    }
    public interface SetRVOnClickListener{
        void setRVOnClickListener(int id, int dayCode);
    }
}












