package com.example.motivateme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class RewardsAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<String> rewards = new ArrayList<String>();
    private ArrayList<String> percentages = new ArrayList<String>();
    private SharedPreferences savedRewards = MainActivity.rewardsPreferences;
    private SharedPreferences savedPercentages = MainActivity.percentagesPreferences;
    private int position;
    private int sumPercent = 0;
    private Context context;

    public RewardsAdapter() {
        for(int i = 0; i < PreferenceHelper.numPreferences(savedPercentages); i++){
            percentages.add(PreferenceHelper.getValue(savedPercentages, "Percentages_" + Integer.toString(i)));
            rewards.add(PreferenceHelper.getValue(savedRewards, "Rewards_" + Integer.toString(i)));
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.frame_textview;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        context = parent.getContext();
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.getView().setText(rewards.get(position));
        holder.getNumber().setText(percentages.get(position));
        holder.getCard().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getLayoutPosition());
                return false;
            }
        });
        holder.getNumber().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                percentages.set(position, s.toString());
                PreferenceHelper.setValue(savedPercentages, "Percentages_" + Integer.toString(position), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    public void updateLists(){
        clearLists();
        for(int i = 0; i < PreferenceHelper.numPreferences(savedPercentages); i++){
            percentages.add(PreferenceHelper.getValue(savedPercentages, "Percentages_" + Integer.toString(i)));
            rewards.add(PreferenceHelper.getValue(savedRewards, "Rewards_" + Integer.toString(i)));
        }
    }

    public void clearLists(){
        percentages.clear();
        rewards.clear();
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int p){
        this.position = p;
    }

    public void reloadPreferences(){
        int i = position;
        while(PreferenceHelper.isEntry(savedRewards, "Rewards_" + Integer.toString(i + 1))){
            PreferenceHelper.setValue(savedRewards, "Rewards_" + Integer.toString(i), PreferenceHelper.getValue(savedRewards, "Rewards_" + Integer.toString(i + 1)));
            PreferenceHelper.setValue(savedPercentages, "Percentages_" + Integer.toString(i), PreferenceHelper.getValue(savedPercentages, "Percentages_" + Integer.toString(i + 1)));
            i++;
        }
        PreferenceHelper.removeItem(savedRewards, "Rewards_" + Integer.toString(i));
        PreferenceHelper.removeItem(savedPercentages, "Percentages_" + Integer.toString(i));
    }

    private int TryParse(String s){
        try{
            return Integer.parseInt(s);
        }
        catch(NumberFormatException e){
            return 0;
        }
    }

    private int GetNumFromString(String s){
        int returnedVal;
        try{
            returnedVal = Integer.parseInt(s);
            return returnedVal;
        }
        catch(NumberFormatException e){
            return 0;
        }
    }
}
