package org.example;

import java.util.List;

public class Shoe {
    private List<Deck> decks;

    public Shoe(int numberOfDecks) {
        for (int i = 0; i < numberOfDecks; i++) {
            decks.add(new Deck());
        }
    }
}
