package com.example.oliohtdarts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewMatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_match);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void switchToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void switchToAddPlayer(View view) {
        Intent intent = new Intent(this, AddPlayerActivity.class);
        startActivity(intent);
    }
     public void switchToListPlayers(View view) {
        Intent intent = new Intent(this, ListPlayersActivity.class);
        startActivity(intent);
    }

    private void initializeViews() {
        playersRecyclerView = findViewById(R.id.playersRecyclerView);
        playerStorage = PlayerStorage.getInstance();
    }

    private void setupRecyclerView() {
        playersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        players = playerStorage.getPlayers();
        playerAdapter = new PlayerAdapter(players, this);
        playersRecyclerView.setAdapter(playerAdapter);
    }

    private void loadPlayers() {
        // Add some sample players if the list is empty
        if (players.isEmpty()) {
            playerStorage.addPlayer(new Player("John Doe"));
            playerStorage.addPlayer(new Player("Jane Smith"));
            playerStorage.addPlayer(new Player("Bob Johnson"));
            playerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPlayerRemove(Player player, int position) {
        playerStorage.removePlayer(player);
        playerAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "Player " + player.getName() + " removed", Toast.LENGTH_SHORT).show();
    }

    public void goBackToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}