package org.example;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    public void setValue(int value) {
        this.value = value;
    }
    private int value;
    private final List<Card> cards;
    private Boolean standed;

    public Hand() {
        this.value = 0;
        this.cards = new ArrayList<>();
        this.standed = false;
    }

    public Boolean getStanded() {
        return standed;
    }

    public void setStanded(Boolean hasStanded) {
        this.standed = hasStanded;
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
                value += card.getRank().getValue();
            }
        }
        return value;
    }

    public int getValue() {
        return value;
    }

    public Boolean hasBust() {
        return getValue() > 21;
    }

    public Boolean hasSoftSeventeen() {
        return getValue() == 17 && getCards().stream().anyMatch(card -> card.getRank() == Rank.ACE);
    }

    public void clearHand() {
        cards.clear();
    }

    public int getNumberOfCards() {
        return getCards().size();
    }

    public void printHand() {
        StringBuilder stringBuilder = new StringBuilder();
        getCards().forEach(card -> stringBuilder.append("\n" + card.toString()));
        System.out.println(stringBuilder);
    }

    public void printHandWithHiddenCard() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n" + getCards().get(0).toString());
        stringBuilder.append("\n" + "Hidden Card");
        System.out.println(stringBuilder);
    }

    public Boolean hasBlackjack() {
        return getNumberOfCards() == 2 && getValue() == 21;
    }
}
