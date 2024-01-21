package org.snap.game.dto;

public class Card {
    private String value;
    private Suit suit;

    public Card(String value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return value.toUpperCase() + " " + suit.name().toLowerCase();
    }
}
