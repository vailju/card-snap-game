package org.snap.game;

import org.snap.game.dto.Card;
import org.snap.game.dto.Player;
import org.snap.game.match.MatchCondition;
import org.snap.game.match.MatchConditionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.snap.game.dto.Constants.*;
import static org.snap.game.engine.Dealer.createStandardShuffledDecks;
import static org.snap.game.engine.Dealer.dealCards;
import static org.snap.game.engine.SnapGamePlayer.playSnap;
import static org.snap.game.engine.SnapRuleGenerator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Let's play Snap!!");
        printNewLine();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("How do you want the cards to match? Type 'suit' for Suit, 'full' for Value And Suit, or anything you'd like for Value.");
        MatchCondition matchCondition = MatchConditionFactory.create(reader.readLine());

        int numPlayers = getNumberOfPlayers(reader);

        List<Player> players = IntStream.rangeClosed(1, numPlayers).mapToObj(Player::new).collect(Collectors.toList());
        printNewLine();

        int numDecks = getNumberOfDecks(reader, matchCondition);

        printNewLine();

        List<Card> deck = createStandardShuffledDecks(numDecks);
        dealCards(deck, players);

        int secondsToSnap = args.length > 0 ? getSecondsBetweenDraw(args[0]) : DEFAULT_SECONDS_BETWEEN_DRAW;

        Player winner = playSnap(reader, players, deck, numPlayers, matchCondition, secondsToSnap);
        System.out.println(winner + " wins!");
    }
}