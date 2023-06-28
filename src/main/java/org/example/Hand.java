package org.example;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private int value;
    private List<Card> cards;

    public Hand() {
        this.value = 0;
        this.cards = new ArrayList<>();
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getValue() {
        return value;
    }

}
