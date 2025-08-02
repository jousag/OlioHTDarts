package com.example.oliohtdarts;

import android.content.Context;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class GameStorage {
    final private String FILENAME;
    private ArrayList<Game> games;
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


    public void saveGames(Context context) {
        try {
            ObjectOutputStream entityWriter = new ObjectOutputStream(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            entityWriter.writeObject(games);
            entityWriter.close();
            System.out.println("Pelit tallennettu onnistuneesti.");
        } catch (Exception e) {
            System.out.println("Virhe pelaajien tallentamisessa: " + e.getMessage());
        }
    }

    public void loadGames(Context context) {
        try {
            ObjectInputStream entityReader = new ObjectInputStream(context.openFileInput(FILENAME));
            games = (ArrayList<Game>) entityReader.readObject();
            entityReader.close();
            System.out.println("Pelit ladattu onnistuneesti.");
        } catch (Exception e) {
            System.out.println("Virhe pelaajien lataamisessa: " + e.getMessage());
        }
    }
    public void clearGames() {
        games.clear();
        gameIdCounter = 0;
    }
}
