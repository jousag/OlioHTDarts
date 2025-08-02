package com.example.oliohtdarts;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayerStorage {
    final private String FILENAME;
    private static PlayerStorage playerStorage;
    private ArrayList<Player> players;
    private ArrayList<Player> selected;


    public PlayerStorage() {
        players = new ArrayList<>();
        FILENAME = "players.data";
    }


    public static PlayerStorage getInstance() {
        if (playerStorage == null) {
            playerStorage = new PlayerStorage();
        }
        return playerStorage;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int index) {
        if (players != null && index >= 0 && index < players.size()) {
            return players.get(index);
        }
        System.out.println("Pelaajaa ei löytynyt.");
        return null;
    }

    public void addPlayer(Player player) {
        if (players == null) {
            players = new ArrayList<>();
        }
        players.add(player);
    }

    public ArrayList<Player> getSelected() {
        ArrayList<Player> selected = new ArrayList<>();
        for (Player player : players) {
            if (player.isSelected()) {
                selected.add(player);
            }
        }
        return selected;
    }

    public void listPlayers() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println((i + 1) + ". " + player.getName() + " - Score: " + player.getScore());
        }
    }

    public void removePlayer(Player player) {
        if (players != null) {
            players.remove(player);
        }
    }

    public void savePlayers(Context context) {
        try {
            ObjectOutputStream entityWriter = new ObjectOutputStream(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            entityWriter.writeObject(players);
            entityWriter.close();
            System.out.println("Pelaajat tallennettu onnistuneesti.");
        } catch (Exception e) {
            System.out.println("Virhe pelaajien tallentamisessa: " + e.getMessage());
        }
    }

    public void loadPlayers(Context context) {
        try {
            ObjectInputStream entityReader = new ObjectInputStream(context.openFileInput(FILENAME));
            players = (ArrayList<Player>) entityReader.readObject();
            entityReader.close();
            System.out.println("Pelaajat ladattu onnistuneesti.");
        } catch (Exception e) {
            System.out.println("Virhe pelaajien lataamisessa: " + e.getMessage());
        }
    }

    public Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        System.out.println("Pelaajaa ei löytynyt nimellä: " + name);
        return null;
    }

    public List<String> getSelectedPlayers() {
        List<String> selectedPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.isSelected()) {
                selectedPlayers.add(player.getName());
            }
        }
        return selectedPlayers;
    }
    public void clearSelectedPlayers() {
        for (Player player : PlayerStorage.getInstance().getPlayers()) {
            player.setSelected(false);
        }
    }
    private Context getContext() {return null;}
}
