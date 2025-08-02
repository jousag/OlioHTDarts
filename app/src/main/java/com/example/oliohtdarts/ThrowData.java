package com.example.oliohtdarts;

public class ThrowData {
    int playerIndex;
    int throwValue;
    int playerScoreBefore;
    int throwIndexInTurn;
    
    public ThrowData(int playerIndex, int throwValue, int playerScoreBefore, int throwIndexInTurn) {
        this.playerIndex = playerIndex;
        this.throwValue = throwValue;
        this.playerScoreBefore = playerScoreBefore;
        this.throwIndexInTurn = throwIndexInTurn;
    }
}
