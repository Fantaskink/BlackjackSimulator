package org.example;

import java.util.List;

public class Dealer {
    private Hand hand;
    private Shoe shoe;

    public Dealer(int numberOfDecks) {
        this.hand = new Hand();
        this.shoe = new Shoe(numberOfDecks);
    }

    public Hand getHand() {
        return hand;
    }

    public int getHandValue() {
        return hand.getValue();
    }

    public Card drawCardFromShoe() {
        return shoe.drawCard();
    }

    public void addCardToHand(Card card) {
        hand.addCard(card);
    }

    public void addCardsToShoe(List<Card> cards) {
        shoe.getCards().addAll(cards);
    }
}
