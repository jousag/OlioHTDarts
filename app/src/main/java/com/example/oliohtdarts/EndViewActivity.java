package com.example.oliohtdarts;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EndViewActivity extends AppCompatActivity {

    private final int[] rainbowColors = {
            Color.RED,
            Color.MAGENTA,
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.YELLOW,
            Color.RED
    };
    private GameStorage gameStorage;
    TextView winnerNameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            gameStorage = GameStorage.getInstance();
            winnerNameTextView = findViewById(R.id.textWinnerName);
            winnerNameTextView.setText(gameStorage.getAllGames().get(gameStorage.getAllGames().size() - 1).getWinnerName());

            GameStorage.getInstance().saveGames(this); // Save the game data
            updatePlayerData(); // Update player data with the last game's results
            animateSmoothRainbow(winnerNameTextView);
            return insets;
        });
    }
    public void switchToNewMatch(View view) {
        PlayerStorage.getInstance().clearSelectedPlayers();
        updatePlayerData();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void switchToRematch(View view) {
        Intent intent = new Intent(this, GameView.class);
        startActivity(intent);
    }
    // Animates the winner's name with a smooth rainbow effect
    private void animateSmoothRainbow(final TextView textView) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(animation -> {
            float fraction = animation.getAnimatedFraction();
            int startColor = getCurrentRainbowColor(fraction);
            textView.setTextColor(startColor);
        });
        animator.start();
    }
    // Interpolate between multiple rainbow colors
    private int getCurrentRainbowColor(float fraction) {
        int index = (int) (fraction * (rainbowColors.length - 1));
        int nextIndex = (index + 1) % rainbowColors.length;
        float localFraction = (fraction * (rainbowColors.length - 1)) - index;

        return blendColors(rainbowColors[index], rainbowColors[nextIndex], localFraction);
    }
    private int blendColors(int color1, int color2, float ratio) {
        final float inverseRatio = 1 - ratio;
        float r = Color.red(color1) * inverseRatio + Color.red(color2) * ratio;
        float g = Color.green(color1) * inverseRatio + Color.green(color2) * ratio;
        float b = Color.blue(color1) * inverseRatio + Color.blue(color2) * ratio;
        return Color.rgb((int) r, (int) g, (int) b);
    }
    private void updatePlayerData(){
        Game lastGame = gameStorage.getAllGames().get(gameStorage.getAllGames().size() - 1);
        Player player1 = PlayerStorage.getInstance().getPlayerByName(lastGame.getPlayer1());
        Player player2 = PlayerStorage.getInstance().getPlayerByName(lastGame.getPlayer2());

        if (player1 != null) {
            player1.setScore(lastGame.getPlayer1score());
            player1.setDartsThrown(lastGame.getPlayer1throws() + player1.getDartsThrown());
            player1.setPlayedGames(player1.getPlayedGames() + 1);
            player1.setThreeDartAverage((player1.getThreeDartAverage() + ((501 - lastGame.getPlayer1score()) / (float) (lastGame.getPlayer1throws() / 3)))/2);
            System.out.println(lastGame.getPlayer1score() + " " + lastGame.getPlayer1throws() + " " + player1.getThreeDartAverage());
        }
        if (player2 != null) {
            player2.setScore(lastGame.getPlayer2score());
            player2.setDartsThrown(lastGame.getPlayer2throws() + player2.getDartsThrown());
            player2.setPlayedGames(player2.getPlayedGames() + 1);
            player2.setThreeDartAverage((player2.getThreeDartAverage() + ((501 - lastGame.getPlayer2score()) / (float) (lastGame.getPlayer2throws() / 3)))/2);
        }
    PlayerStorage.getInstance().savePlayers(this);// save the player data from the last game
    }

}