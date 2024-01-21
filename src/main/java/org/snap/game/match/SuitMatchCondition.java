package org.snap.game.match;

import org.snap.game.dto.Card;

public class SuitMatchCondition implements MatchCondition {

    @Override
    public boolean doCardsMatch(Card firstCard, Card secondCard) {
        if (firstCard == null || secondCard == null) {
            return false;
        }
        return firstCard.getSuit() == secondCard.getSuit();
    }

    @Override
    public int getMinimumDeckNumber() {
        return 1;
    }

    @Override
    public String getDisplayRule() {
        return "2 cards will need the same Suit to match!";
    }
}
