package org.example;

enum Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
