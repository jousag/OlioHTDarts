package com.example.oliohtdarts;

import javax.swing.text.html.ImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.Recyclerview;

public class ListPlayersActivity extends RecyclerView.ViewHolder {
    TextView playerName;

    public PlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        PlayerName = itemView.findViewByID(R.id.txtPlayerName);
    }
}