package org.snap.game.match;

import org.snap.game.dto.Card;

public interface MatchCondition {

    boolean doCardsMatch(Card firstCard, Card secondCard);

    int getMinimumDeckNumber();

    String getDisplayRule();
}
