package com.example.oliohtdarts;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GameViewHolder extends RecyclerView.ViewHolder {

    TextView gameId, winnerName, gameTimeAndDate, loserName;

    public GameViewHolder(@NonNull View itemView) {
        super(itemView);
        gameId = itemView.findViewById(R.id.textGameID);
        winnerName = itemView.findViewById(R.id.textListWinnerName);
        gameTimeAndDate = itemView.findViewById(R.id.textGameTimeAndDate);
        loserName = itemView.findViewById(R.id.textListLoserName);
    }


}
