package com.example.oliohtdarts;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class GameView extends AppCompatActivity {
    private int throwCount = 0;
    private int[] selectedThrows = new int[3];
    private EditText scoreEditText;
    private TextView player1Name;
    private TextView player2Name;
    private int currentPlayerIndex = 0;
    private ArrayList<TextView> playerScoreViews = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        scoreEditText = findViewById(R.id.scoreEditText1);
        scoreEditText = findViewById(R.id.scoreEditText2);
        player1Name = findViewById(R.id.player1Name);
        player2Name = findViewById(R.id.player2Name);

        PlayerStorage playerStorage = PlayerStorage.getInstance();
        ArrayList<Player> selectedPlayers = playerStorage.getSelected();

        Player player = Player.getInstance();
        score = Player.getScore();

        for (int i = 0; i < selectedPlayers.size(); i++) {
            String textViewId = "player" + (i + 1) + "Name"; // e.g., player1Name, player2Name...
            @SuppressLint("DiscouragedApi") int resID = getResources().getIdentifier(textViewId, "id", getPackageName());
            TextView playerNameView = findViewById(resID);

            if (playerNameView != null) {
                playerNameView.setText(selectedPlayers.get(i).getName());
            }
        }
    }

        for (int i = 0; i < selectedPlayers.size(); i++) {
            String scoreViewId = "player" + (i + 1) + "Score"; // e.g., player1Score
            @SuppressLint("DiscouragedApi")
            int resID = getResources().getIdentifier(scoreViewId, "id", getPackageName());
            TextView scoreView = findViewById(resID);

            if (scoreView != null) {
                playerScoreViews.add(scoreView);
                // Initialize display with starting score
                scoreView.setText(String.valueOf(selectedPlayers.get(i).getScore()));
            }
        }

        for (int i = 0; i <= 20; i++) {
            String buttonID = "button" + i;
            @SuppressLint("DiscouragedApi") int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            Button button = findViewById(resID);

            if (button != null) {
                final int scoreValue = i; // capture i for the lambda
                button.setOnClickListener(v -> handleScoreInput(scoreValue));
            }
        }
    }
//    private void setSelectedPlayerNames(ArrayList<Player> selectedPlayers) {
//        TextView player1Name = findViewById(R.id.player1Name);
//        TextView player2Name = findViewById(R.id.player2Name);
//
//        if (selectedPlayers.size() >= 1) {
//            player1Name.setText(selectedPlayers.get(0).getName());
//        }
//        if (selectedPlayers.size() >= 2) {
//            player2Name.setText(selectedPlayers.get(1).getName());
//        }
//    }
    private void handleScoreInput(int value) {
        if (throwCount < 3) {
            selectedThrows[throwCount] = value;
            throwCount++;
        }

        if (throwCount == 3) {
            int total = selectedThrows[0] + selectedThrows[1] + selectedThrows[2];
            Player currentPlayer = selectedPlayers.get(currentPlayerIndex);

            // Update player score
            int newScore = currentPlayer.getScore() - total;
            currentPlayer.setScore(newScore);

            // Update the UI score TextView
            if (currentPlayerIndex < playerScoreViews.size()) {
                playerScoreViews.get(currentPlayerIndex).setText(String.valueOf(newScore));
            }

            // Move to next player
            currentPlayerIndex = (currentPlayerIndex + 1) % selectedPlayers.size();
            throwCount = 0;
        }
    }

    private void setSelectedPlayerNames(@NonNull ArrayList<Player> selectedPlayers) {
        for (int i = 0; i < selectedPlayers.size(); i++) {
            String textViewId = "player" + (i + 1) + "Name"; // e.g., player1Name, player2Name...
            @SuppressLint("DiscouragedApi") int resID = getResources().getIdentifier(textViewId, "id", getPackageName());
            TextView playerNameView = findViewById(resID);

            if (playerNameView != null) {
                playerNameView.setText(selectedPlayers.get(i).getName());
            }
        }
    }



}