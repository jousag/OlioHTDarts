package com.example.oliohtdarts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameListAdapter extends RecyclerView.Adapter<GameViewHolder> {

    private Context context;
    private ArrayList<Game> games = new ArrayList<>();

    public GameListAdapter(@NonNull Context context, ArrayList<Game> games) {
        super();
        this.context = context;
        this.games = games;
    }
    @NonNull
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameViewHolder(LayoutInflater.from(context).inflate(R.layout.gameview, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        holder.gameId.setText(String.valueOf(games.get(position).getGameId()));
        holder.winnerName.setText(games.get(position).getWinnerName());
        holder.gameTimeAndDate.setText(games.get(position).getTimeAndDate());
        holder.loserName.setText(games.get(position).getLoserName());
    }
    @Override
    public int getItemCount() {return games.size();}
}
