package com.example.oliohtdarts;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int score;
    private int highScore;
    private int playedGames;
    private float ThreeDartAverage;
    private int LastThreeDartScore;
    private int DartsThrown;
    private boolean isSelected;
    private int image;

    public Player(String name) {
        this.name = name;
        this.score = 501; // Default starting score for a game of darts
        this.isSelected = false;

        image = R.drawable.dartsimage;
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
    public boolean isSelected() {return isSelected;}
    public void setSelected(boolean selected) {isSelected = selected;}
    public int getImage() {return image;}
    public int getHighScore() {return highScore;}
    public int getDartsThrown() {return DartsThrown;}
    public void setDartsThrown(int dartsThrown) {DartsThrown = dartsThrown;}
    public void setPlayedGames(int playedGames) {this.playedGames = playedGames;}
    public void setThreeDartAverage (float threeDartAverage) {ThreeDartAverage = threeDartAverage;}

}
