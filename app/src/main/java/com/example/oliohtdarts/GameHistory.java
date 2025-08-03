package com.example.oliohtdarts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class GameHistory extends AppCompatActivity {

    private GameStorage gameStorage;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_history);
        GameStorage.getInstance().loadGames(this); // Load games from storage
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            gameStorage = GameStorage.getInstance();

            recyclerView = findViewById(R.id.ListGamesRV);
            recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
            recyclerView.setAdapter(new GameListAdapter(getApplicationContext(), gameStorage.getAllGames()));
            return insets;
        });
    }

    public void switchToMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerView != null && gameStorage != null) {
            recyclerView.setAdapter(new GameListAdapter(getApplicationContext(), gameStorage.getAllGames()));
        } // Update the RecyclerView adapter with the latest game data
    }
    @Override
    protected void onPause() {
        super.onPause();
        gameStorage.saveGames(this); // Save games to storage when the activity is paused
    }
    public void clearGameHistory(View view) {
        gameStorage.clearGames();
        recyclerView.setAdapter(new GameListAdapter(getApplicationContext(), gameStorage.getAllGames()));
    }
}