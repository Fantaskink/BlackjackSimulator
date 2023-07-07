package org.example;

public class Player {
    public Hand getHand() {
        return hand;
    }

    private Hand hand;
    private int chips;

    public int getChips() {
        return chips;
    }

    public Player(int chips) {
        this.hand = new Hand();
        this.chips = chips;
    }

    public int getHandValue() {
        return hand.getValue();
    }

    public void addCardToHand(Card card) {
        hand.addCard(card);
    }

    public void addChips(int chips) {
        this.chips += chips;
    }
}
