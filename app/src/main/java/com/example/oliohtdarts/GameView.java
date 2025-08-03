package com.example.oliohtdarts;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameView extends AppCompatActivity {
    
    private int throwCount = 0;
    private int[] selectedThrows = new int[3];
    private int currentPlayerIndex = 0;
    private int nextMultiplier = 1;
    private final ArrayList<ThrowData> throwHistory = new ArrayList<>();
    private int GameId = 0;
    private int player1throws = 0;
    private int player2throws = 0;
    private TextView player1Name;
    private TextView player2Name;
    private TextView checkoutView;
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

    private final ArrayList<TextView> playerScoreViews = new ArrayList<>();
    private ArrayList<Player> selectedPlayers = new ArrayList<>();
    private GameStorage gameStorage;
    MediaPlayer mediaPlayer;

    private final Map<String, String> checkoutCache = new HashMap<>();
    private boolean checkoutDataLoaded = false;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.player1score), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            gameStorage = GameStorage.getInstance();
            return insets;
        });


        TextView player1Score = findViewById(R.id.player1Score);
        TextView player2Score = findViewById(R.id.player2Score);
        player1Name = findViewById(R.id.player1Name);
        player2Name = findViewById(R.id.player2Name);
        checkoutView = findViewById(R.id.checkoutView); // This might be null if not in layout
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

        mediaPlayer = MediaPlayer.create(this, R.raw.annoucer);

        PlayerStorage playerStorage = PlayerStorage.getInstance();
        selectedPlayers = playerStorage.getSelected();
        
        // Initialize all selected players if none are selected (for testing)
        if (selectedPlayers.isEmpty()) {
            ArrayList<Player> allPlayers = playerStorage.getPlayers();
            if (!allPlayers.isEmpty()) {
                for (int i = 0; i < Math.min(2, allPlayers.size()); i++) {
                    allPlayers.get(i).setSelected(true);
                    allPlayers.get(i).resetScore(); // Reset to 501
                }
                selectedPlayers = playerStorage.getSelected();
            }
        } else {
            for (Player player : selectedPlayers) {
                player.resetScore();
            }
        }

        // Set up player names and scores
        if (!selectedPlayers.isEmpty()) {
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

        // Load checkout data asynchronously
        loadCheckoutData();

        // clear selected players in PlayerStorage
        playerStorage.clearSelectedPlayers();
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
            String numberStr = buttonName.substring(6);
            try {

                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        
        return 0;
    }
    // This method updates the UI to reflect the current player's turn
    private void updateCurrentPlayerUI() {
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
    }


    private void handleScoreInput(int value) {
        if (throwCount == 0) {
            clearInputViews();
        }
        if (throwCount < 3) {
            int actualValue = value * nextMultiplier; // ActualValue = the score with multiplier
            
            // Record throw in history before applying it
            Player currentPlayer = selectedPlayers.get(currentPlayerIndex);
            throwHistory.add(new ThrowData(currentPlayerIndex, actualValue, currentPlayer.getScore(), throwCount));
            
            selectedThrows[throwCount] = actualValue; // selectedThrows[throwCount] = listed score with multiplier
            throwCount++;
            
            int newScore = setCurrentScore();

            // Call viewCheckout with the new score
            viewCheckout(newScore);

            if (newScore == 1 || newScore < 0) {
                bustTurn();
                moveToNextPlayer();
                return; // Exit early if bust
            } else if (newScore == 0 && nextMultiplier == 2 || (newScore == 0 && value == 50)) {
                finishGame(currentPlayer);
                return; // Exit early if game is finished
            } else {
            // Reset multiplier after each throw
            nextMultiplier = 1;
            resetMultiplierButtons();
            updateInputViews();
            }
        }        

        if (throwCount == 3) {
            Player currentPlayer = selectedPlayers.get(currentPlayerIndex);
            int newScore = setCurrentScore();
            if (newScore == 0 && nextMultiplier == 2 || (newScore == 0 && value == 50)) {
                finishGame(currentPlayer);
                return; // Exit early if game is finished
            } else if (newScore > 1) {
                currentPlayer.setScore(newScore);
            } else {
                bustTurn();
                return; // Exit early if bust
            }
            nextMultiplier = 1;
            resetMultiplierButtons();
            updateInputViews();
            moveToNextPlayer();
        }
        
    }

    // Handle double and triple multipliers
    private void handleDoubleTriple(int multiplier) {
        if (nextMultiplier == multiplier) { // Cancels the multiplier if already active
            nextMultiplier = 1;
            resetMultiplierButtons();
        } else { // Activate the new multiplier
            resetMultiplierButtons();
            nextMultiplier = multiplier;
            if (nextMultiplier == 2) {
                buttonDouble.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light, null));
                buttonTriple.setBackgroundColor(getResources().getColor(android.R.color.darker_gray, null));
            } else if (nextMultiplier == 3) {
                buttonTriple.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light, null));
                buttonDouble.setBackgroundColor(getResources().getColor(android.R.color.darker_gray, null));
            }
        }
    }
    
    // Handle undo functionality - undoes the last individual throw from history
    private void handleUndo() {
        if (throwHistory.isEmpty()) {
            return; // Nothing to undo
        }
        ThrowData lastThrow = throwHistory.get(throwHistory.size() - 1);
        throwHistory.remove(throwHistory.size() - 1);
        Player playerToRestore = selectedPlayers.get(lastThrow.playerIndex);
        playerToRestore.setScore(ThrowData.playerScoreBefore);
        playerScoreViews.get(lastThrow.playerIndex).setText(String.valueOf(ThrowData.playerScoreBefore));
        
        // Switch to the player who made that throw
        currentPlayerIndex = lastThrow.playerIndex;
        selectedThrows = new int[3];
        throwCount = 0;
        for (int i = throwHistory.size() - 1; i >= 0; i--) {
            ThrowData throwData = throwHistory.get(i);
            if (throwData.playerIndex == currentPlayerIndex && throwData.throwIndexInTurn < 3) {
                selectedThrows[throwData.throwIndexInTurn] = throwData.throwValue;
                throwCount = Math.max(throwCount, throwData.throwIndexInTurn + 1);
            } else {
                break;
            }
        }
        int currentScore = setCurrentScore();
        if (!selectedPlayers.isEmpty() && currentPlayerIndex < selectedPlayers.size()) {
            viewCheckout(currentScore);
        }
        
        // Update UI
        clearInputViews();
        updateCurrentPlayerUI();
        updateInputViews();
        nextMultiplier = 1;
        resetMultiplierButtons();
    }

    private void resetMultiplierButtons() {
        buttonDouble.setBackgroundColor(getResources().getColor(android.R.color.darker_gray, null));
        buttonTriple.setBackgroundColor(getResources().getColor(android.R.color.darker_gray, null));
    }
    
    // Update the input views to show individual throw scores
    private void updateInputViews() {
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

    private void finishGame(Player currentPlayer) {
        String loserName;
        if (currentPlayer == selectedPlayers.get(0)) {
            loserName = selectedPlayers.get(1).getName();
        } else {
            loserName = selectedPlayers.get(0).getName();
        }
        
        // Calculate the checkout value (score before the winning turn)
        int checkoutValue = getScoreBeforeTurn();
        
        // Update highest checkout if this checkout is higher
        if (checkoutValue > currentPlayer.getHighestCheckout()) {
            currentPlayer.setHighestCheckout(checkoutValue);
        }
        
        Game game = new Game(
                GameId,
                selectedPlayers.get(0).getName(),
                selectedPlayers.get(1).getName(),
                currentPlayer.getName(),
                loserName,
                player1throws,
                player2throws,
                selectedPlayers.get(0).getScore(),
                selectedPlayers.get(1).getScore(),
                "501");
        GameId = gameStorage.getNextGameId();
        game.setWinnerName(currentPlayer.getName());
        game.setLoserName(loserName);
        game.setPlayer1throws(player1throws);
        game.setPlayer2throws(player2throws);
        game.setPlayer1(selectedPlayers.get(0).getName());
        game.setPlayer2(selectedPlayers.get(1).getName());
        game.setGameId(GameId);
        gameStorage.addGame(game);
        Intent intent = new Intent(this, EndViewActivity.class);
        startActivity(intent);
    }

    private int getScoreBeforeTurn() {
        Player currentPlayer = selectedPlayers.get(currentPlayerIndex);
        int scoreBeforeTurn = currentPlayer.getScore();
        if (!throwHistory.isEmpty()) {
            // Look for the first throw of this turn to get the score before it
            for (int i = throwHistory.size() - 1; i >= 0; i--) {
                ThrowData throwData = throwHistory.get(i);
                if (throwData.playerIndex == currentPlayerIndex && throwData.throwIndexInTurn == 0) {
                    scoreBeforeTurn = ThrowData.playerScoreBefore;
                    break;
                }
            }
        }
        return scoreBeforeTurn;
    }

    private void bustTurn() {
        Player currentPlayer = selectedPlayers.get(currentPlayerIndex);

        int scoreBeforeTurn = getScoreBeforeTurn();

        currentPlayer.setScore(scoreBeforeTurn);
        playerScoreViews.get(currentPlayerIndex).setText(String.valueOf(scoreBeforeTurn));
        
        // Remove the throws from this busted turn from history
        while (!throwHistory.isEmpty()) {
            ThrowData lastThrow = throwHistory.get(throwHistory.size() - 1);
            if (lastThrow.playerIndex == currentPlayerIndex && ThrowData.playerScoreBefore == scoreBeforeTurn) {
                throwHistory.remove(throwHistory.size() - 1);
            } else {
                break;
            }
        }
        selectedThrows = new int[3];
        
        // Reset multiplier buttons after bust
        nextMultiplier = 1;
        resetMultiplierButtons();
    }

    private void moveToNextPlayer() {
        throwCounter();
        currentPlayerIndex = (currentPlayerIndex + 1) % selectedPlayers.size();
        throwCount = 0;
        selectedThrows = new int[3];
        updateCurrentPlayerUI();
        Player currentPlayer = selectedPlayers.get(currentPlayerIndex);
        viewCheckout(currentPlayer.getScore());
    }

    private int setCurrentScore() {
        if (currentPlayerIndex < selectedPlayers.size()) {
            Player currentPlayer = selectedPlayers.get(currentPlayerIndex);
            
            // Calculate potential newScore based on current throws
            int currentTotal = 0;
            for (int i = 0; i < throwCount; i++) {
                currentTotal += selectedThrows[i];
            }
            int newScore = currentPlayer.getScore() - currentTotal;
            if (throwCount == 3 && currentTotal == 180) {
                mediaPlayer.start();
            }
            // Only update the UI display, don't change the actual player score yet
            playerScoreViews.get(currentPlayerIndex).setText(String.valueOf(newScore));
            
            return newScore;
        }
        return -1; // Return -1 if no valid player
    }
    private void throwCounter(){
        if (currentPlayerIndex == 0) {
            player1throws += throwCount;
        } else if (currentPlayerIndex == 1) {
            player2throws += throwCount;
        }
    }

    @SuppressLint("SetTextI18n")
    private void viewCheckout(int newScore) {
        if (checkoutDataLoaded && checkoutCache.containsKey(String.valueOf(newScore))) {
            String checkout = checkoutCache.get(String.valueOf(newScore));
            if (checkoutView != null) {
                checkoutView.setText(checkout);
            }
        } else if (checkoutDataLoaded) {
            if (checkoutView != null) {
                checkoutView.setText(getString(R.string.checkout_no_checkout));
            }
        }
    }

    private void loadCheckoutData() {
        executorService.execute(() -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode checkoutData = objectMapper.readTree(new URL("https://mocki.io/v1/b33ccff8-afc8-4185-a1dd-37484cc1f180"));
                checkoutCache.clear();
                checkoutData.fieldNames().forEachRemaining(fieldName ->
                        checkoutCache.put(fieldName, checkoutData.get(fieldName).asText())
                );
                checkoutDataLoaded = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

}