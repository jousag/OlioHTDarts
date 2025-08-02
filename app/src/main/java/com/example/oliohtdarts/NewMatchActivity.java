package com.example.oliohtdarts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
        PlayerStorage.getInstance().loadPlayers(this); // Load players from storage
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.player1score), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            playerStorage = PlayerStorage.getInstance();
            recyclerView = findViewById(R.id.ListPlayersRV);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new PlayerListAdapter(getApplicationContext(),
                    playerStorage.getPlayers(),
                    selectedPlayers ->  onPlayerClick(selectedPlayers)));
            txtSelectedPlayers = findViewById(R.id.txtSelectedPlayers);

            updateSelectedPlayersText(new ArrayList<>());
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

//    @Override
//    protected void onPause() {
//        super.onPause();
//        for (Player player : PlayerStorage.getInstance().getPlayers()) {
//            player.setSelected(false);
//        }
//        PlayerStorage.getInstance().savePlayers(this);
//    }


    public void switchToMain(View view) {
        playerStorage.clearSelectedPlayers();
        updateSelectedPlayersText(new ArrayList<>());
        playerStorage.savePlayers(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void switchToAddPlayer(View view) {
        playerStorage.clearSelectedPlayers();
        updateSelectedPlayersText(new ArrayList<>());
        playerStorage.savePlayers(this);
        Intent intent = new Intent(this, AddPlayerActivity.class);
        startActivity(intent);
    }

    public void onPlayerClick(List<String> selectedPlayers) {
        updateSelectedPlayersText(selectedPlayers);
    }
    public void updateSelectedPlayersText(List<String> selectedPlayers) {
        if (selectedPlayers.isEmpty()) {
            txtSelectedPlayers.setText("No players selected");
        } else {
            StringBuilder sb = new StringBuilder("Selected Players: ");
            for (String player : selectedPlayers) {
                sb.append(player).append(", ");
            }
            // Remove the last comma and space
            sb.setLength(sb.length() - 2);
            txtSelectedPlayers.setText(sb.toString());
        }
    }

    public void switchToMatch(View view) {
        if (playerStorage.getSelectedPlayers().size() == 2) {
            Intent intent = new Intent(this, GameView.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please select exactly two players to start a match!", Toast.LENGTH_LONG).show();
        }
    }
}
