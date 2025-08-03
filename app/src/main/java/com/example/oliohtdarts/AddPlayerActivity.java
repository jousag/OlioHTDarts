package com.example.oliohtdarts;

import androidx.appcompat.app.AppCompatActivity;

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
        PlayerStorage.getInstance().addPlayer(new Player(playerName));
        Toast.makeText(this, "Player " + playerName + " added successfully!", Toast.LENGTH_SHORT).show();
        playerNameInput.setText("");
        finish();
    }
    public void goBack(View view) {finish();}
}