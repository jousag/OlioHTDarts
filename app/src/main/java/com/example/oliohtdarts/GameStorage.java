package com.example.oliohtdarts;

import java.util.ArrayList;
public class GameStorage {
    private ArrayList<Game> games;
    private Game game;

    public GameStorage(Game game) {
        this.game = game;
        games = new ArrayList<>();
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
    public static GameStorage getInstance() {
        
        return gameStorage;
    }

    private static GameStorage gameStorage;
}
