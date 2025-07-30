package com.example.oliohtdarts;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class GameView extends AppCompatActivity {
    private int throwCount = 0;
    private int[] selectedThrows = new int[3];
    private int startScore = 501;
    private int currentPlayerIndex = 0;
    private int nextMultiplier = 1; // For double/triple functionality
    private int lastPlayerIndex = -1; // For undo functionality
    private int[] lastThrows = new int[3]; // For undo functionality
    private int lastScore = -1; // For undo functionality
    private EditText scoreEditText;
    private TextView player1Name;
    private TextView player2Name;
    private TextView player1Score;
    private TextView player2Score;
    private Button buttonDouble;
    private Button buttonTriple;
    private Button buttonUndo;

    // Miss and bullseye
    private Button button25;
    private Button button50;
    private Button buttonMiss;

    // Number buttons 1–20
    private Button button1, button2, button3, button4, button5;
    private Button button6, button7, button8, button9, button10;
    private Button button11, button12, button13, button14, button15;
    private Button button16, button17, button18, button19, button20;
    private TextView inputView1;
    private TextView inputView2;
    private TextView inputView3;

    private ArrayList<TextView> playerScoreViews = new ArrayList<>();
    private ArrayList<Player> selectedPlayers = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.player1score), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        player1Score = findViewById(R.id.player1Score);
        player2Score = findViewById(R.id.player2Score);
        player1Name = findViewById(R.id.player1Name);
        player2Name = findViewById(R.id.player2Name);
        // Special function buttons
        buttonDouble = findViewById(R.id.buttonDouble);
        buttonTriple = findViewById(R.id.buttonTriple);
        buttonUndo = findViewById(R.id.buttonUndo);

        // Bullseye and miss
        button25 = findViewById(R.id.button25);
        button50 = findViewById(R.id.button50);
        buttonMiss = findViewById(R.id.buttonMiss);

        // Number buttons
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        button11 = findViewById(R.id.button11);
        button12 = findViewById(R.id.button12);
        button13 = findViewById(R.id.button13);
        button14 = findViewById(R.id.button14);
        button15 = findViewById(R.id.button15);
        button16 = findViewById(R.id.button16);
        button17 = findViewById(R.id.button17);
        button18 = findViewById(R.id.button18);
        button19 = findViewById(R.id.button19);
        button20 = findViewById(R.id.button20);

        inputView1 = findViewById(R.id.inputView1);
        inputView2 = findViewById(R.id.inputView2);
        inputView3 = findViewById(R.id.inputView3);

        PlayerStorage playerStorage = PlayerStorage.getInstance();
        selectedPlayers = playerStorage.getSelected();
        
        // Initialize all selected players if none are selected (for testing)
        if (selectedPlayers.isEmpty()) {
            // Add some sample selected players for testing
            ArrayList<Player> allPlayers = playerStorage.getPlayers();
            if (!allPlayers.isEmpty()) {
                for (int i = 0; i < Math.min(2, allPlayers.size()); i++) {
                    allPlayers.get(i).setSelected(true);
                    allPlayers.get(i).resetScore(); // Reset to 501
                }
                selectedPlayers = playerStorage.getSelected();
            }
        } else {
            // Reset scores for selected players to 501
            for (Player player : selectedPlayers) {
                player.resetScore();
            }
        }

        // Set up player names and scores
        if (selectedPlayers.size() > 0) {
            player1Name.setText(selectedPlayers.get(0).getName());
            player1Score.setText(String.valueOf(selectedPlayers.get(0).getScore()));
            playerScoreViews.add(player1Score);
        }
        
        if (selectedPlayers.size() > 1) {
            player2Name.setText(selectedPlayers.get(1).getName());
            player2Score.setText(String.valueOf(selectedPlayers.get(1).getScore()));
            playerScoreViews.add(player2Score);
        }


        // Initialize UI for the first player
        updateCurrentPlayerUI();
        
        // Initialize input views
        clearInputViews();
        
        // Initialize multiplier buttons
        resetMultiplierButtons();
        
        // Set up button click listeners
        setupButtonListeners();
    }
    
    private void setupButtonListeners() {
        // Create a generic click handler using getValue
        View.OnClickListener numberButtonListener = v -> {
            int value = getValue((Button) v);
            handleScoreInput(value);
        };
        
        // Number buttons 1-20 - now using the generic handler
        button1.setOnClickListener(numberButtonListener);
        button2.setOnClickListener(numberButtonListener);
        button3.setOnClickListener(numberButtonListener);
        button4.setOnClickListener(numberButtonListener);
        button5.setOnClickListener(numberButtonListener);
        button6.setOnClickListener(numberButtonListener);
        button7.setOnClickListener(numberButtonListener);
        button8.setOnClickListener(numberButtonListener);
        button9.setOnClickListener(numberButtonListener);
        button10.setOnClickListener(numberButtonListener);
        button11.setOnClickListener(numberButtonListener);
        button12.setOnClickListener(numberButtonListener);
        button13.setOnClickListener(numberButtonListener);
        button14.setOnClickListener(numberButtonListener);
        button15.setOnClickListener(numberButtonListener);
        button16.setOnClickListener(numberButtonListener);
        button17.setOnClickListener(numberButtonListener);
        button18.setOnClickListener(numberButtonListener);
        button19.setOnClickListener(numberButtonListener);
        button20.setOnClickListener(numberButtonListener);
        
        // Special buttons - also using the generic handler
        button25.setOnClickListener(numberButtonListener);
        button50.setOnClickListener(numberButtonListener);
        buttonMiss.setOnClickListener(numberButtonListener);
        
        // Special function buttons
        buttonDouble.setOnClickListener(v -> handleDoubleTriple(2));
        buttonTriple.setOnClickListener(v -> handleDoubleTriple(3));
        buttonUndo.setOnClickListener(v -> handleUndo());
    }
    
    // Method to get value from button name/ID
    private int getValue(Button button) {
        String buttonName = getResources().getResourceEntryName(button.getId());
        
        // Handle special cases
        if (buttonName.equals("buttonMiss")) {
            return 0;
        } else if (buttonName.equals("button25")) {
            return 25;
        } else if (buttonName.equals("button50")) {
            return 50;
        } else if (buttonName.startsWith("button")) {
            // Extract number from button name (e.g., "button1" -> 1)
            String numberStr = buttonName.substring(6); // Remove "button" prefix
            try {

                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                return 0; // Default to 0 if parsing fails
            }
        }
        
        return 0; // Default value
    }
    // This method updates the UI to reflect the current player's turn
    private void updateCurrentPlayerUI() {
        // Reset both players' text color to normal
        if (player1Name != null) player1Name.setTextColor(getResources().getColor(android.R.color.black, null));
        if (player2Name != null) player2Name.setTextColor(getResources().getColor(android.R.color.black, null));
        
        // Highlight current player
        if (!selectedPlayers.isEmpty() && currentPlayerIndex < selectedPlayers.size()) {
            if (currentPlayerIndex == 0 && player1Name != null) {
                player1Name.setTextColor(getResources().getColor(android.R.color.holo_green_dark, null));
            } else if (currentPlayerIndex == 1 && player2Name != null) {
                player2Name.setTextColor(getResources().getColor(android.R.color.holo_green_dark, null));
            }
        }
        
        // Clear input views when switching to a new player (but keep them if it's the same player)
        if (throwCount == 0) {
            clearInputViews();
        }
    }
    private void handleScoreInput(int value) {
        // Check if we have selected players
        if (selectedPlayers.isEmpty()) {
            return;
        }

        if (throwCount < 3) {
            // Apply multiplier to the current throw
            int actualValue = value * nextMultiplier;
            selectedThrows[throwCount] = actualValue;
            throwCount++;
            
            // Update input views to show current throws
            updateInputViews();
            
            // Debug: Show current throw
            System.out.println("Player " + (currentPlayerIndex + 1) + " - Throw " + throwCount + ": " + value + 
                             (nextMultiplier > 1 ? " x" + nextMultiplier + " = " + actualValue : ""));
            
            // Reset multiplier and button colors after use
            nextMultiplier = 1;
            resetMultiplierButtons();
        }

        if (throwCount == 3) {
            int total = selectedThrows[0] + selectedThrows[1] + selectedThrows[2];
            Player currentPlayer = selectedPlayers.get(currentPlayerIndex);

            // Save current state for undo functionality
            lastPlayerIndex = currentPlayerIndex;
            lastScore = currentPlayer.getScore();
            lastThrows = selectedThrows.clone();

            // Debug: Show total
            System.out.println("Player " + (currentPlayerIndex + 1) + " - Total: " + total);

            // Update player score
            int newScore = currentPlayer.getScore() - total;

            // Check for valid score (can't go below 0 in darts)
            if (newScore >= 0) {
                currentPlayer.setScore(newScore);

                // Update the UI score TextView
                if (currentPlayerIndex < playerScoreViews.size()) {
                    playerScoreViews.get(currentPlayerIndex).setText(String.valueOf(newScore));
                }

                // Check for winner (score = 0)
                if (newScore == 0) {
                    System.out.println("Player " + currentPlayer.getName() + " wins!");
                    // Handle winner logic here - for now, just continue the game
                }
            } else {
                // Invalid throw - score would go below 0, this is called a "bust"
                System.out.println("BUST! Player " + currentPlayer.getName() + " - score would be " + newScore);
                // In real darts, the player's score stays the same and turn ends
            }

            // Move to next player
            currentPlayerIndex = (currentPlayerIndex + 1) % selectedPlayers.size();
            throwCount = 0;
            
            // Clear the selected throws array for next round
            selectedThrows = new int[3];
            
            // Update UI to show current player
            updateCurrentPlayerUI();
        }
    }
    
    // Handle double and triple multipliers
    private void handleDoubleTriple(int multiplier) {
        // Set the multiplier for the next dart throw
        nextMultiplier = multiplier;
        
        // Visual feedback - change button color to indicate it's active
        if (multiplier == 2) {
            buttonDouble.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light, null));
            buttonTriple.setBackgroundColor(getResources().getColor(android.R.color.darker_gray, null));
        } else if (multiplier == 3) {
            buttonTriple.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light, null));
            buttonDouble.setBackgroundColor(getResources().getColor(android.R.color.darker_gray, null));
        }
        
        System.out.println("Next dart will be multiplied by " + multiplier);
    }
    
    // Handle undo functionality
    private void handleUndo() {
        // Check if there's something to undo
        if (lastPlayerIndex == -1 || lastScore == -1) {
            System.out.println("Nothing to undo");
            return;
        }
        
        // Restore the previous player's score
        if (lastPlayerIndex < selectedPlayers.size()) {
            Player playerToRestore = selectedPlayers.get(lastPlayerIndex);
            playerToRestore.setScore(lastScore);
            
            // Update the UI
            if (lastPlayerIndex < playerScoreViews.size()) {
                playerScoreViews.get(lastPlayerIndex).setText(String.valueOf(lastScore));
            }
            
            // Go back to the previous player
            currentPlayerIndex = lastPlayerIndex;
            throwCount = 3; // Set to 3 to show all throws from the undone turn
            
            // Restore the previous throws
            selectedThrows = lastThrows.clone();
            
            // Update input views to show the restored throws
            updateInputViews();
            
            // Reset throw count to 0 for the next input
            throwCount = 0;
            
            // Reset multiplier
            nextMultiplier = 1;
            resetMultiplierButtons();
            
            // Update UI
            updateCurrentPlayerUI();
            
            System.out.println("Undid last turn for " + playerToRestore.getName() + " - Score restored to " + lastScore);
            
            // Clear undo data so it can only be used once
            lastPlayerIndex = -1;
            lastScore = -1;
            lastThrows = new int[3];
        }
    }
    
    // Helper method to reset multiplier button colors
    private void resetMultiplierButtons() {
        buttonDouble.setBackgroundColor(getResources().getColor(android.R.color.darker_gray, null));
        buttonTriple.setBackgroundColor(getResources().getColor(android.R.color.darker_gray, null));
    }
    
    // Update the input views to show individual throw scores
    private void updateInputViews() {
        // Clear all input views first
        inputView1.setText("");
        inputView2.setText("");
        inputView3.setText("");
        
        // Update based on current throw count
        if (throwCount >= 1) {
            inputView1.setText(String.valueOf(selectedThrows[0]));
        }
        if (throwCount >= 2) {
            inputView2.setText(String.valueOf(selectedThrows[1]));
        }
        if (throwCount >= 3) {
            inputView3.setText(String.valueOf(selectedThrows[2]));
        }
    }
    
    // Clear input views when starting a new player's turn
    private void clearInputViews() {
        inputView1.setText("");
        inputView2.setText("");
        inputView3.setText("");
    }


}