package org.snap.game.engine;

import org.snap.game.dto.Card;
import org.snap.game.dto.Player;
import org.snap.game.match.MatchCondition;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import static org.snap.game.dto.Constants.NO_VALID_SNAP;

public class SnapGameLogic {

    private SnapGameLogic() {
    }

    public static Player playSnap(List<Player> players, List<Card> deck, int numPlayers, BufferedReader reader, MatchCondition matchCondition, int secondsToSnap) throws IOException {
        displayIntro(players, secondsToSnap);
        int turnNumber = 0;
        while (hasNextTurn(players, deck.size())) {
            Player currentPlayer = players.get(turnNumber % numPlayers);
            if (canPlayerDraw(currentPlayer)) {
                Card currentCard = currentPlayer.getFaceDownCards().pop();
                currentPlayer.getFaceUpCards().push(currentCard);
                System.out.println(currentPlayer + " draws: " + currentCard);
                long startTime = System.currentTimeMillis();
                while ((System.currentTimeMillis() - startTime) < secondsToSnap * 1000L && !reader.ready()) {
                    // time for players to snap
                }

                if (reader.ready()) {
                    int snapperId;
                    try {
                        snapperId = Integer.parseInt(reader.readLine());
                        handleSnap(players, numPlayers, matchCondition, snapperId);
                    } catch (Exception e) {
                        System.out.println("NEXT!");
                    }
                }
            }
            displayPlayerCards(players);
            turnNumber++;
        }
        return findWinner(players);
    }

    private static void displayIntro(List<Player> players, int secondsToSnap) {
        System.out.println("Players " + players.stream().map(Player::getId).map(String::valueOf).collect(Collectors.joining(", ")) + " are ready to play!");
        System.out.println("After a draw, type your Player Number to snap, or any other keys to continue!");
        System.out.println("The next draw will happen automatically after " + secondsToSnap + " seconds.");
        System.out.println("If you run out of cards, don't lose hope! You can still snap and get back in the game.");
        System.out.println("The player who finishes with all the cards win!");
        System.out.println("Let's play!\n");
    }

    private static boolean hasNextTurn(List<Player> players, int deckSize) {
        return players.stream().filter(p -> p.getFaceDownCards().size() + p.getFaceUpCards().size() == deckSize).count() != 1;
    }

    private static boolean canPlayerDraw(Player player) {
        if (player.getFaceDownCards().isEmpty()) {
            if (player.getFaceUpCards().isEmpty()) {
                System.out.println(player + " is out of cards! They'll have to snap to get back in the game. Next player!\n");
                return false;
            }
            System.out.println(player + " has no cards down. Let's reuse your up cards!\n");
            reverseEmptyDequeIntoOther(player.getFaceUpCards(), player.getFaceDownCards());
        }
        return true;
    }

    private static void handleSnap(List<Player> players, int numPlayers, MatchCondition matchCondition, int snapperId) {
        if (snapperId > 0 && snapperId <= numPlayers) {
            Player snapperPlayer = players.get(snapperId - 1);
            System.out.println(snapperPlayer + " snapped!");
            List<Player> snappedPlayers = getSnappedPlayers(snapperPlayer, players, matchCondition);
            if (!snappedPlayers.isEmpty()) {
                updatePlayersStackAfterSnap(snappedPlayers, players, snapperId);
            } else {
                System.out.println(NO_VALID_SNAP);
            }
        } else {
            System.out.println(NO_VALID_SNAP);
        }
    }

    private static List<Player> getSnappedPlayers(Player snappingPlayer, List<Player> players, MatchCondition matchCondition) {
        List<Player> snappedPlayers = new ArrayList<>();
        for (int i = 0; i < players.size() - 1; i++) {
            for (int j = i + 1; j < players.size(); j++) {
                if (matchCondition.doCardsMatch(players.get(i).getFaceUpCards().peek(), players.get(j).getFaceUpCards().peek())) {
                    snappedPlayers.add(players.get(i));
                    snappedPlayers.add(players.get(j));
                }
            }
        }
        return snappedPlayers.stream().filter(sp -> sp.getId() != snappingPlayer.getId()).collect(Collectors.toList());
    }

    private static void updatePlayersStackAfterSnap(List<Player> snappedPlayers, List<Player> players, int snapperId) {
        Player snapperPlayer = players.get(snapperId - 1);
        for (Player snappedPlayer : snappedPlayers) {
            reverseEmptyDequeIntoOther(snappedPlayer.getFaceUpCards(), snapperPlayer.getFaceDownCards());
        }
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
        System.out.println("\n");
        for (Player player : players) {
            Card cardUp = player.getFaceUpCards().peek();
            String cardUpToShow = cardUp != null ? player.getFaceUpCards().size() + " up cards, " + cardUp : "No Card";
            System.out.println(player + " " + player.getFaceDownCards().size() + " down cards, " + cardUpToShow);
        }
        System.out.println("\n");
    }

    private static Player findWinner(List<Player> players) {
        Player winningPlayer = null;
        int currentNumCards = 0;
        for (Player player : players) {
            int playerNumCards = player.getFaceUpCards().size() + player.getFaceDownCards().size();
            if (playerNumCards > currentNumCards) {
                winningPlayer = player;
            }
        }
        return winningPlayer;
    }
}
