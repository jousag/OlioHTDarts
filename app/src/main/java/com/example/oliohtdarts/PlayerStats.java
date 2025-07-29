package com.example.oliohtdarts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class PlayerStats extends AppCompatActivity {
    private PlayerStorage playerStorage;
    ImageView playerImage;
    TextView playerName, player3DartAverage, playerGamesPlayed, playerCheckout, playerDartsThrown;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_player_stats);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.player1score), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            playerName = findViewById(R.id.textPlayerName);
            player3DartAverage = findViewById(R.id.textPlayerThreeDartAVG);
            playerGamesPlayed = findViewById(R.id.textPlayerGamesPlayed);
            playerCheckout = findViewById(R.id.textPlayerCheckout);
            playerDartsThrown = findViewById(R.id.textPlayerDartsThrown);
            playerStorage = PlayerStorage.getInstance();
            playerImage = findViewById(R.id.imageView);

            updatePlayerStats(new ArrayList<>());
            return insets;
        });
    }

    public void updatePlayerStats(List<String> selectedPlayers) {;

        if (selectedPlayers.isEmpty()) {
            playerName.setText("No player selected");
            playerImage.setImageResource(R.drawable.dartsimage);
        } else {
            String playerNameText = selectedPlayers.get(0);
            Player player = playerStorage.getPlayerByName(playerNameText);
            if (player != null) {
                playerName.setText(player.getName());
                // Set the player's image, assuming you have a method to get the image resource
                playerImage.setImageResource(player.getImage());
                player3DartAverage.setText("3 Dart Average: " + player.getThreeDartAverage());
                playerGamesPlayed.setText("Games Played: " + player.getPlayedGames());
                playerCheckout.setText("Biggest Checkout: " + player.getHighScore());
                playerDartsThrown.setText("Darts Thrown: " + player.getDartsThrown());
            } else {
                playerName.setText("Player not found");
                playerImage.setImageResource(R.drawable.dartsimage);
            }
        }
    }
    public void switchToPlayerList(View view) {
        for (Player player : PlayerStorage.getInstance().getPlayers()) {
            player.setSelected(false);
        }
        Intent intent = new Intent(this, PlayerListActivity.class);
        startActivity(intent);
    }
    public void deletePlayer(View view) {
        List<String> selectedPlayers = PlayerStorage.getInstance().getSelectedPlayers();
        String playerName = selectedPlayers.get(0);
        Player player = playerStorage.getPlayerByName(playerName);
        if (selectedPlayers.isEmpty()) {
            Toast.makeText(this, "No player selected!", Toast.LENGTH_SHORT).show();
        } else if (selectedPlayers.size() > 1) {
            Toast.makeText(this, "Please select only one player to delete!", Toast.LENGTH_SHORT).show();
        } else {
            if (player != null) {
                playerStorage.removePlayer(player);
                Toast.makeText(this, "Player " + playerName + " deleted successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Player not found!", Toast.LENGTH_SHORT).show();
            }


        }
        Intent intent = new Intent(this, PlayerListActivity.class);
        startActivity(intent);
    }
}