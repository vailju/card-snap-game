package org.snap.game.match;

import org.snap.game.dto.Card;

public class SuitMatchCondition implements MatchCondition {

    @Override
    public boolean doCardsMatch(Card previousCard, Card currentCard) {
        if (previousCard == null || currentCard == null) {
            return false;
        }
        return previousCard.getSuit() == currentCard.getSuit();
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
