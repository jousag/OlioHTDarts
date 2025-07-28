package com.example.oliohtdarts;
import java.util.ArrayList;
import java.util.List;
public class Player {
    private String name;
    private int score;
    private int highScore;
    private int playedGames;
    private float ThreeDartAverage;
    private int LastThreeDartScore;
    private int DartsThrown;
    private boolean isSelected;
    private ArrayList<Player> players;

    public Player(String name) {
        this.name = name;
        this.score = 501; // Default starting score for a game of darts
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void resetScore() {
        this.score = 501; // Reset score to default starting value
    }
    public int getPlayedGames() {
        return playedGames;
    }
    public float getThreeDartAverage() {
        return ThreeDartAverage;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void reduceScore(int value) {
        if (value > 0 && value <= score) {
            this.score -= value;
        }
    }
}
