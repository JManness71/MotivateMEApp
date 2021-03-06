package com.example.motivateme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ActivitiesAdapter  extends RecyclerView.Adapter<RecyclerViewHolder> {
    String[] test = {"Entry 1", "Entry 2", "Entry 3", "Entry 4", "Entry 5", "Entry 6"};

    public ActivitiesAdapter() {
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
    }

    @Override
    public int getItemCount() {
        return test.length;
    }
}
