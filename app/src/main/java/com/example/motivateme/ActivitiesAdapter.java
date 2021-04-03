package com.example.motivateme;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesRecyclerViewHolder> {
    private ArrayList<String> activities = new ArrayList<String>();
    private SharedPreferences savedActivities = MainActivity.activityPreferences;
    private SharedPreferences savedNumRewards = MainActivity.numRewardsPreferences;
    private int position;
    private int numRewards;

    public ActivitiesAdapter(){
        for(int i = 0; i < PreferenceHelper.numPreferences(savedActivities); i++){
            activities.add(PreferenceHelper.getValue(savedActivities, "Activities_" + Integer.toString(i)));
        }
        numRewards = parseInt(PreferenceHelper.getValue(savedNumRewards, "numRewards"));
    }

    @Override
    public int getItemViewType(final int position){
        return R.layout.activities_view;
    }

    @NonNull
    @Override
    public ActivitiesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ActivitiesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitiesRecyclerViewHolder holder, int position) {
        holder.getView().setText(activities.get(position));
        holder.getCard().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getLayoutPosition());
                return false;
            }
        });
        holder.getCheck().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean checked = holder.getCheck().isChecked();
                if(checked == true){
                    holder.getCheck().setChecked(true);
                    numRewards++;
                    PreferenceHelper.setValue(savedNumRewards, "numRewards", Integer.toString(numRewards));
                }
                else{
                    holder.getCheck().setChecked(false);
                    if(numRewards > 0){
                        numRewards--;
                        PreferenceHelper.setValue(savedNumRewards, "numRewards", Integer.toString(numRewards));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public void updateLists(){
        clearLists();
        for(int i = 0; i < PreferenceHelper.numPreferences(savedActivities); i++){
            activities.add(PreferenceHelper.getValue(savedActivities, "Activities_" + Integer.toString(i)));
        }
    }

    public void clearLists(){
        activities.clear();
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int p){
        this.position = p;
    }

    public void reloadPreferences(){
        int i = position;
        while(PreferenceHelper.isEntry(savedActivities, "Activities_" + Integer.toString(i + 1))){
            PreferenceHelper.setValue(savedActivities, "Activities_" + Integer.toString(i), PreferenceHelper.getValue(savedActivities, "Activities_" + Integer.toString(i + 1)));
            i++;
        }
        PreferenceHelper.removeItem(savedActivities, "Activities_" + Integer.toString(i));
    }
}
