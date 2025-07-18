package com.example.oliohtdarts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
    }

    public void addPlayer(View view) {
        EditText playerNameInput = findViewById(R.id.playerNameInput);
        String playerName = playerNameInput.getText().toString().trim();

        if (playerName.isEmpty()) {
            Toast.makeText(this, "Please enter a player name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the player to storage
        PlayerStorage.getInstance().addPlayer(new Player(playerName));
        
        Toast.makeText(this, "Player " + playerName + " added successfully!", Toast.LENGTH_SHORT).show();

        // Clear the input field
        playerNameInput.setText("");
        
        // Optionally, you can navigate back to the previous activity
        finish();
    }

    public void goBack(View view) {finish();}

}