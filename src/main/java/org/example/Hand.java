package org.example;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    public void setValue(int value) {
        this.value = value;
    }

    private int value;
    private List<Card> cards;

    public Hand() {
        this.value = 0;
        this.cards = new ArrayList<>();
    }

    public void updateValue() {
        setValue(calculateValue());
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public int calculateValue() {
        int value = 0;
        for (Card card : cards) {
            if (card.getRank() == Rank.ACE) {
                if (value + 11 > 21) {
                    value += 1;
                } else {
                    value += 11;
                }
            } else {
                value += card.getRank().ordinal();
            }
        }
        return value;
    }

    public int getValue() {
        return value;
    }

    public int getNumberOfCards() {
        return getCards().size();
    }

    public void printHand() {
        StringBuilder stringBuilder = new StringBuilder();
        getCards().forEach(card -> stringBuilder.append(card.toString() + "\n"));
        System.out.println(stringBuilder);
    }
}
