package com.example.oliohtdarts;

import java.util.ArrayList;

public class PlayerStorage {
    private static PlayerStorage playerStorage;
    private ArrayList<Player> players;

    private PlayerStorage() {
        players = new ArrayList<>();
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
}
