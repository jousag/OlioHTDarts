package com.example.oliohtdarts;

import java.util.ArrayList;
public class GameStorage {
    final private String FILENAME;
    private ArrayList<Game> games;
    private Game game;

    private int gameIdCounter = 0;

    public GameStorage() {
        games = new ArrayList<>();
        FILENAME = "games.data";
    }
    public static GameStorage getInstance() {
        if (gameStorage == null) {
            gameStorage = new GameStorage();
        }
        return gameStorage;
    }
    
    public void addGame(Game game) {
        games.add(game);
    }

    public Game getGame(int index) {
        if (index >= 0 && index < games.size()) {
            return games.get(index);
        }
        return null;
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    public ArrayList<Game> getAllGames() {
        return games;
    }
    public int getNextGameId() {
        return games.size() + 1; // Simple ID generation based on size
    }


    private static GameStorage gameStorage;

    public int getGameIdCounter() {
            return gameIdCounter;
        }
}

