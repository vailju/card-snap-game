package org.snap.game.dto;

import java.util.Stack;

public class Player {
    int id;
    Stack<Card> faceDownCards;
    Stack<Card> faceUpCards;

    public Player(int id) {
        this.id = id;
        this.faceDownCards = new Stack<>();
        this.faceUpCards = new Stack<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stack<Card> getFaceDownCards() {
        return faceDownCards;
    }

    public void setFaceDownCards(Stack<Card> faceDownCards) {
        this.faceDownCards = faceDownCards;
    }

    public Stack<Card> getFaceUpCards() {
        return faceUpCards;
    }

    public void setFaceUpCards(Stack<Card> faceUpCards) {
        this.faceUpCards = faceUpCards;
    }
}
