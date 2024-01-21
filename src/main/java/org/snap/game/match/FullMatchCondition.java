package org.snap.game.match;

import org.snap.game.dto.Card;

public class FullMatchCondition implements MatchCondition {

    @Override
    public boolean doCardsMatch(Card previousCard, Card currentCard) {
        return previousCard.getValue().equals(currentCard.getValue()) && previousCard.getSuit() == currentCard.getSuit();
    }

    @Override
    public int getMinimumDeckNumber() {
        return 2;
    }

    @Override
    public String getDisplayRule() {
       return "2 cards will need the same Value and Suit to match!";
    }
}
