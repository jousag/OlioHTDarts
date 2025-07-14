package com.example.oliohtdarts;

import java.util.ArrayList;
public class PlayerStorage {
    private static PlayerStorage playerStorage = null;
    private ArrayList<Player> players = new ArrayList<>();

    public static PlayerStorage getInstance() {
        if (playerStorage == null) {
            playerStorage = new PlayerStorage();
        }
        return playerStorage;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
