package com.example.oliohtdarts;

public class Game {
    private int gameId;
    private String player1;
    private String player2;
    private String winnerName;
    private int player1throws;
    private int player2throws;
    private int player1score;
    private int player2score;
    private String gameType; //"301", "501", "701", etc.

    public Game(int gameId, String player1, String player2, String winnerName, int player1throws, int player2throws, int player1score, int player2score, String gameType) {
        this.gameId = gameId;
        this.player1 = player1;
        this.player2 = player2;
        this.winnerName = winnerName;
        this.player1throws = player1throws;
        this.player2throws = player2throws;
        this.player1score = player1score;
        this.player2score = player2score;
        this.gameType = gameType;
    }
    public int getGameId() {
        return gameId;
    }
    public String getPlayer1() {
        return player1;
    }
    public String getPlayer2() {
        return player2;
    }
    public String getWinnerName() {return winnerName;}
    public int getPlayer1throws() {
        return player1throws;
    }

    public int getPlayer2throws() {
        return player2throws;
    }

    public int getPlayer1score() {
        return player1score;
    }

    public int getPlayer2score() {
        return player2score;
    }

    public String getGameType() {
        return gameType;
    }
    public void setGameId(int id) {
        this.gameId = gameId;
    }
    public void setPlayer1(String player1) {
        this.player1 = player1;
    }
    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
    public void setPlayer1throws(int player1throws) {
        this.player1throws = player1throws;
    }
    public void setPlayer2throws(int player2throws) {
        this.player2throws = player2throws;
    }
    public void setPlayer1score(int player1score) {
        this.player1score = player1score;
    }
    public void setPlayer2score(int player2score) {
        this.player2score = player2score;
    }
    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
