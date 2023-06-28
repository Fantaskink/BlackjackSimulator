package org.example;

public class Player {
    private Hand hand;
    private int chips;

    public Player(int chips) {
        this.hand = new Hand();
        this.chips = chips;
    }
}
