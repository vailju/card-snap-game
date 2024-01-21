package org.snap.game;

import org.snap.game.dto.Card;
import org.snap.game.dto.Player;
import org.snap.game.engine.Dealer;
import org.snap.game.match.MatchCondition;
import org.snap.game.match.MatchConditionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.snap.game.dto.Constants.PLAYER_STRING;
import static org.snap.game.engine.Dealer.dealCards;
import static org.snap.game.engine.SnapGameLogic.*;
import static org.snap.game.engine.SnapRuleGenerator.getNumberOfDecks;
import static org.snap.game.engine.SnapRuleGenerator.getNumberOfPlayers;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Let's play Snap!!");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("How do you want the card to match? Type 'suit' for Suit, 'full' for Value and Suit, or anything you'd like for Value.");
        MatchCondition matchCondition = MatchConditionFactory.create(reader.readLine());

        int numPlayers = getNumberOfPlayers(reader);

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i + 1));
        }

        int numDecks = getNumberOfDecks(reader, matchCondition);

        System.out.println("\n");

        List<Card> deck = Dealer.createStandardShuffledDecks(numDecks);
        dealCards(deck, players);

        Player winner = playSnap(players, deck, numPlayers, reader, matchCondition, 10);
        System.out.println(PLAYER_STRING + winner.getId() + " wins!");
    }
}