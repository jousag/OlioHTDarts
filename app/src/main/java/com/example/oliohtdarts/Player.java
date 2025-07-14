package com.example.oliohtdarts;

public class Player {
    private String name;
    private int score;

    private int highScore;

    private int playedGames;

    private float ThreeDartAverage;

    private int LastThreeDartScore;

    private int DartsThrown;




    public Player(String name) {
        this.name = name;
        this.score = 501; // Default starting score for a game of darts
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

}
