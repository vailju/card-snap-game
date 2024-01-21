package org.snap.game.engine;

import org.snap.game.dto.Card;
import org.snap.game.dto.Player;
import org.snap.game.dto.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.snap.game.dto.Constants.STANDARD_DECK_VALUES;

public class Dealer {

    private Dealer() {

    }

    public static List<Card> createDecks(int numDecks, List<String> values, Suit[] suits, boolean toShuffle) {
        List<Card> singleDeck = createSingleDeck(values, suits);

        List<Card> deck = new ArrayList<>();
        for (int i = 0; i < numDecks; i++) {
            deck.addAll(singleDeck);
        }
        if (toShuffle) {
            Collections.shuffle(deck);
        }
        return deck;
    }

    private static List<Card> createSingleDeck(List<String> values, Suit[] suits) {
        List<Card> deck = new ArrayList<>();
        values.stream().map(v -> Arrays.stream(suits).map(s -> new Card(v, s)).collect(Collectors.toList())).forEach(deck::addAll);
        return deck;
    }

    public static List<Card> createStandardDecks(int numDecks, boolean toShuffle) {

        return createDecks(numDecks, STANDARD_DECK_VALUES, Suit.values(), toShuffle);
    }

    public static List<Card> createStandardShuffledDecks(int numDecks) {

        return createStandardDecks(numDecks,true);
    }

    public static void dealCards(List<Card> deck, List<Player> players) {
        int numPlayers = players.size();
        for (int i = 0 ; i < deck.size() ; i++) {
            int currentPlayer = i % numPlayers;
            players.get(currentPlayer).getFaceDownCards().push(deck.get(i));
        }
    }
}
