package com.example.oliohtdarts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

    private Context context;
    private ArrayList<Player> players = new ArrayList<>();

    public PlayerListAdapter(@NonNull Context context, ArrayList<Player> players) {
        super();
        this.context = context;
        this.players = players;
    }

    @NonNull
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.playerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.playerName.setText(player.getName());
        holder.gamesPlayed.setText(String.valueOf(player.getPlayedGames()));
        holder.threeDartAverage.setText(String.format("%.2f", player.getThreeDartAverage()));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
