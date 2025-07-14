package com.example.oliohtdarts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_player);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            playerNameInput = findViewById(R.id.editTextText); // En saa jostai syystä vaihettuu id:tä xml:stä nii ei toimi viel.

            return insets;
        });
    }

    public void switchToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent); //Saisko tän jotenki liitettyy addPlayer nappii ni palais edelliseen activityyn suoraan?
    }

    public void addPlayer(View view) {
        String playerName = ((android.widget.EditText) playerNameInput.getText().toString();
        PlayerStorage.getInstance().addPlayer(new Player(playerName));
    }
}