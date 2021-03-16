package com.example.motivateme;

import android.view.ContextMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ActivitiesRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
    private TextView view;
    private CardView card;

    public ActivitiesRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.ActivityText);
        card = itemView.findViewById(R.id.cv2);
        itemView.setOnCreateContextMenuListener(this);
    }

    public TextView getView(){
        return view;
    }

    public CardView getCard(){
        return card;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }
}
