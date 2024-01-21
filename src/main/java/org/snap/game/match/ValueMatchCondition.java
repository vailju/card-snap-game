package org.snap.game.match;

import org.snap.game.dto.Card;

public class ValueMatchCondition implements MatchCondition {

    @Override
    public boolean doCardsMatch(Card previousCard, Card currentCard) {
        return previousCard.getValue().equals(currentCard.getValue());
    }

    @Override
    public int getMinimumDeckNumber() {
        return 1;
    }

    @Override
    public String getDisplayRule() {
        return "2 cards will need the same Value to match!";
    }
}
