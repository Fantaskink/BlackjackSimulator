package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe {
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public Shoe(int numberOfDecks) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < numberOfDecks; i++) {
            cards.addAll(new Deck().getCards());
        }
        this.cards = cards;
        shuffleCards();
    }

    public void shuffleCards() {
        Collections.shuffle(getCards());
    }

    public Card drawCard() {
        return getCards().remove(0);
    }
}

