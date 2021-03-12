package com.example.motivateme;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.SharedPreferences;

public class RewardsAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    String[] test = {"Entry 1", "Entry 2", "Entry 3", "Entry 4", "Entry 5"};
    public String[] percentages = new String[test.length];
    EditText ed1;

    public RewardsAdapter() {
        for(int i = 0; i < test.length; i++){
            percentages[i] = getSavedActivityData(test[i] + "_rewards");
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
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.getView().setText(test[position]);
        holder.getNumber().setText(percentages[position]);
        holder.getNumber().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                percentages[position] = s.toString();
                setSavedActivityData(test[position] + "_rewards", s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return test.length;
    }

    private String getSavedActivityData(String key){
        return PreferenceHelper.getValue(key);
    }

    private void setSavedActivityData(String key, String value){
        PreferenceHelper.setValue(key, value);
    }

}
