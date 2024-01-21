package org.snap.game.dto;

import java.util.ArrayDeque;
import java.util.Deque;

public class Player {
    int id;
    Deque<Card> faceDownCards;
    Deque<Card> faceUpCards;

    public Player(int id) {
        this.id = id;
        this.faceDownCards = new ArrayDeque<>();
        this.faceUpCards = new ArrayDeque<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Deque<Card> getFaceDownCards() {
        return faceDownCards;
    }

    public void setFaceDownCards(Deque<Card> faceDownCards) {
        this.faceDownCards = faceDownCards;
    }

    public Deque<Card> getFaceUpCards() {
        return faceUpCards;
    }

    public void setFaceUpCards(Deque<Card> faceUpCards) {
        this.faceUpCards = faceUpCards;
    }

    @Override
    public String toString() {
        return "Player " + this.getId();
    }
}
