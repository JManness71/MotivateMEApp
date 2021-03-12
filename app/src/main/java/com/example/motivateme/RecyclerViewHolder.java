package com.example.motivateme;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView view;
    private EditText number;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.randomText);
        number = itemView.findViewById(R.id.editTextNumber2);
    }

    public TextView getView(){
        return view;
    }

    public EditText getNumber(){
        return number;
    }
}
