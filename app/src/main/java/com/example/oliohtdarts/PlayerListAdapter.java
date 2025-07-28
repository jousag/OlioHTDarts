package com.example.oliohtdarts;

import android.content.Context;
import android.graphics.Color;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

    private Context context;
    private ArrayList<Player> players = new ArrayList<>();

    private OnPlayerClickListener listener;

    public PlayerListAdapter(@NonNull Context context, ArrayList<Player> players, OnPlayerClickListener listener) {
        super();
        this.context = context;
        this.players = players;
        this.listener = listener;
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

        holder.itemView.setBackgroundColor(
                player.isSelected() ? Color.GREEN : Color.RED
        );

        holder.itemView.setOnClickListener(v -> {
            player.setSelected(!player.isSelected());
            notifyItemChanged(position);

            if (listener != null) {
                List<String> selectedPlayers = new ArrayList<>();
                for (Player p : players) {
                    if (p.isSelected()) {
                        selectedPlayers.add(p.getName());
                    }
                }
                listener.onPlayerClick(selectedPlayers);
            }
        });

    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    // Listener for item clicks

    public interface OnPlayerClickListener {
        void onPlayerClick(List<String> selectedPlayers);
    }
}
