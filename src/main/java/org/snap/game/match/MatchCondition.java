package org.snap.game.match;

import org.snap.game.dto.Card;

public interface MatchCondition {

    boolean doCardsMatch(Card previousCard, Card currentCard);

    int getMinimumDeckNumber();

    String getDisplayRule();
}
