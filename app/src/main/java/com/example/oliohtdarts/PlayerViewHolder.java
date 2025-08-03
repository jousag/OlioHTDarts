package com.example.oliohtdarts;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PlayerViewHolder extends RecyclerView.ViewHolder {
    TextView playerName, gamesPlayed, threeDartAverage;

    public PlayerViewHolder(@NonNull View itemView) {
        super(itemView);
        playerName = itemView.findViewById(R.id.textPlayerName);
        gamesPlayed = itemView.findViewById(R.id.textPlayedGames);
        threeDartAverage = itemView.findViewById(R.id.txtThreeDartAVG);

    }
}
