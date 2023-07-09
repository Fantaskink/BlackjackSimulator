package org.example;

public class Player {
    private Hand mainHand;
    private Hand splitHand;
    private int chips;

    private Boolean hasSplit;

    public Hand getMainHand() {
        return mainHand;
    }

    public Hand getSplitHand() {
        return splitHand;
    }

    public Boolean getHasSplit() {
        return hasSplit;
    }

    public int getChips() {
        return chips;
    }

    public Player(int chips) {
        this.mainHand = new Hand();
        this.splitHand = new Hand();
        this.chips = chips;
        this.hasSplit = false;
    }

    public int getMainHandValue () {
        return mainHand.getValue();
    }
    public int getSplitHandValue () {
        return splitHand.getValue();
    }

    public void addCardToMainHand(Card card) {
        mainHand.addCard(card);
    }

    public void addCardToSplitHand(Card card) {
        splitHand.addCard(card);
    }

    public void addChips(int chips) {
        this.chips += chips;
    }

    public void removeChips(int chips) {
        this.chips -= chips;
    }

    public void splitHand() {
        splitHand.addCard(mainHand.getCards().remove(1));
        hasSplit = true;
        mainHand.updateValue();
        splitHand.updateValue();
    }
}
