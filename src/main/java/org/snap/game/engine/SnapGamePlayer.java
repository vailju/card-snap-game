package org.snap.game.engine;

import org.snap.game.dto.Card;
import org.snap.game.dto.Player;
import org.snap.game.match.MatchCondition;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.snap.game.dto.Constants.*;

public class SnapGamePlayer {

    private SnapGamePlayer() {
    }

    public static Player playSnap(BufferedReader reader, List<Player> players, List<Card> deck, int numPlayers, MatchCondition matchCondition, int secondsToSnap) {
        displayIntro(players, secondsToSnap);
        int turnNumber = 0;
        Optional<Player> winningPlayer = Optional.empty();
        while (winningPlayer.isEmpty()) {
            Player currentPlayer = players.get(turnNumber % numPlayers);
            if (canPlayerDraw(currentPlayer)) {
                boolean isSnap = drawCardAndWaitForSnap(reader, secondsToSnap, currentPlayer);
                if (isSnap) {
                    handleSnapTry(reader, players, numPlayers, matchCondition);
                }
            }
            displayPlayerCards(players);
            turnNumber++;
            winningPlayer = getWinningPlayer(players, deck.size());
        }
        return winningPlayer.get();
    }

    private static void displayIntro(List<Player> players, int secondsToSnap) {
        System.out.println("Players " + players.stream().map(Player::getId).map(String::valueOf).collect(Collectors.joining(", ")) + " are ready to play!");
        System.out.println("After a draw, type your Player Number to snap, or any other keys to continue!");
        System.out.println("The next draw will happen automatically after " + secondsToSnap + " seconds.");
        System.out.println("If you run out of cards, don't lose hope! You can still snap and get back in the game.");
        System.out.println("The player who finishes with all the cards win!");
        System.out.println("Let's play!" + NEW_LINE);
    }

    private static boolean canPlayerDraw(Player player) {
        if (player.getFaceDownCards().isEmpty()) {
            if (player.getFaceUpCards().isEmpty()) {
                System.out.println(player + " is out of cards! They'll have to snap to get back in the game. Next player!" + NEW_LINE);
                return false;
            }
            System.out.println(player + " has no cards down. Let's reuse your up cards!" + NEW_LINE);
            reverseEmptyDequeIntoOther(player.getFaceUpCards(), player.getFaceDownCards());
        }
        return true;
    }

    private static boolean drawCardAndWaitForSnap(BufferedReader reader, int secondsToSnap, Player currentPlayer) {
        Card currentCard = currentPlayer.getFaceDownCards().pop();
        currentPlayer.getFaceUpCards().push(currentCard);
        System.out.println(currentPlayer + " draws: " + currentCard);
        long startTime = System.currentTimeMillis();
        try {
            while ((System.currentTimeMillis() - startTime) < secondsToSnap * 1000L && !reader.ready()) {
                // time for players to snap
            }
            return reader.ready();
        } catch (IOException e) {
            return false;
        }
    }

    private static void handleSnapTry(BufferedReader reader, List<Player> players, int numPlayers, MatchCondition matchCondition) {
        int snapperId;
        try {
            snapperId = Integer.parseInt(reader.readLine());
            if (snapperId > 0 && snapperId <= numPlayers) {
                handleSnapByPlayer(players, snapperId, matchCondition);
            } else {
                System.out.println(NO_VALID_SNAPPPER);
            }
        } catch (Exception e) {
            System.out.println("NEXT!");
        }
    }

    private static void handleSnapByPlayer(List<Player> players, int snapperId, MatchCondition matchCondition) {
        Player snapperPlayer = players.get(snapperId - 1);
        System.out.println(snapperPlayer + " snapped!");
        List<Player> snappedPlayers = getSnappedPlayers(players, snapperPlayer, matchCondition);
        if (!snappedPlayers.isEmpty()) {
            updatePlayersStacksAfterSnap(snappedPlayers, snapperPlayer);
        } else {
            System.out.println(NO_VALID_SNAP);
        }
    }

    private static List<Player> getSnappedPlayers(List<Player> players, Player snapperPlayer, MatchCondition matchCondition) {
        List<Player> snappedPlayers = new ArrayList<>();
        for (int i = 0; i < players.size() - 1; i++) {
            Player firstPlayer = players.get(i);
            for (int j = i + 1; j < players.size(); j++) {
                Player secondPlayer = players.get(j);
                if (matchCondition.doCardsMatch(firstPlayer.getFaceUpCards().peek(), secondPlayer.getFaceUpCards().peek())) {
                    snappedPlayers.add(firstPlayer);
                    snappedPlayers.add(secondPlayer);
                }
            }
        }
        return snappedPlayers.stream().filter(sp -> sp.getId() != snapperPlayer.getId()).collect(Collectors.toList());
    }

    private static void updatePlayersStacksAfterSnap(List<Player> snappedPlayers, Player snapperPlayer) {
        snappedPlayers.forEach(sp -> reverseEmptyDequeIntoOther(sp.getFaceUpCards(), snapperPlayer.getFaceDownCards()));
        reverseEmptyDequeIntoOther(snapperPlayer.getFaceUpCards(), snapperPlayer.getFaceDownCards());
    }

    public static void reverseEmptyDequeIntoOther(Deque<Card> dequeToEmpty, Deque<Card> dequeToPopulate) {
        Deque<Card> tempDeque = new ArrayDeque<>();
        while (!dequeToEmpty.isEmpty()) {
            tempDeque.push(dequeToEmpty.pop());
        }
        while (!tempDeque.isEmpty()) {
            dequeToPopulate.addLast(tempDeque.pop());
        }
    }

    private static void displayPlayerCards(List<Player> players) {
        printNewLine();
        players.forEach(SnapGamePlayer::displayPlayerCard);
        printNewLine();
    }

    private static void displayPlayerCard(Player player) {
        Card cardUp = player.getFaceUpCards().peek();
        String cardUpToShow = cardUp != null ? player.getFaceUpCards().size() + " up cards, " + cardUp : "No Card";
        System.out.println(player + " " + player.getFaceDownCards().size() + " down cards, " + cardUpToShow);
    }

    private static Optional<Player> getWinningPlayer(List<Player> players, int deckSize) {
        return players.stream().filter(p -> p.getFaceDownCards().size() + p.getFaceUpCards().size() == deckSize).findFirst();
    }
}
