package com.example.oliohtdarts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewMatchActivity extends AppCompatActivity {

    private PlayerStorage playerStorage;
    private RecyclerView recyclerView;
    private TextView txtSelectedPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_match);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            playerStorage = PlayerStorage.getInstance();
            recyclerView = findViewById(R.id.ListPlayersRV);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new PlayerListAdapter(getApplicationContext(),
                    playerStorage.getPlayers(),
                    selectedPlayers ->  onPlayerClick(selectedPlayers)));
            txtSelectedPlayers = findViewById(R.id.txtSelectedPlayers);
            txtSelectedPlayers.setText("No players selected");

            return insets;
        });
    }

    protected void onResume() {
        super.onResume();
        if (recyclerView != null && playerStorage != null) {
            recyclerView.setAdapter(new PlayerListAdapter(getApplicationContext(),
                    playerStorage.getPlayers(),
                    selectedPlayers ->  onPlayerClick(selectedPlayers)));
        } // Update the RecyclerView adapter with the latest player data
    }

    public void switchToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void switchToAddPlayer(View view) {
        Intent intent = new Intent(this, AddPlayerActivity.class);
        startActivity(intent);
    }

    public void onPlayerClick(List<String> selectedPlayers) {
        if (selectedPlayers.isEmpty()) {
            txtSelectedPlayers.setText("No players selected");
        } else {
            StringBuilder builder = new StringBuilder("Selected Players: ");
            for (String player : selectedPlayers) {
                builder.append(player).append(", ");
            }
            builder.setLength(builder.length() - 2); // Remove the last comma and space
            txtSelectedPlayers.setText(builder.toString());
        }
    }
}