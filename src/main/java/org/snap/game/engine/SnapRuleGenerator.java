package org.snap.game.engine;

import org.snap.game.match.MatchCondition;

import java.io.BufferedReader;

import static org.snap.game.dto.Constants.DEFAULT_SECONDS_BETWEEN_DRAW;
import static org.snap.game.dto.Constants.printNewLine;

public class SnapRuleGenerator {

    private SnapRuleGenerator() {

    }

    public static int getSecondsBetweenDraw(String param) {
        try {
            return Integer.parseInt(param);
        } catch (Exception e) {
            int defaultSecondsBetweenDraw = DEFAULT_SECONDS_BETWEEN_DRAW;
            System.out.println("The parameter was not a number, defaulting to " + defaultSecondsBetweenDraw + " seconds between draws.");
            printNewLine();
            return defaultSecondsBetweenDraw;
        }
    }

    public static int getNumberOfPlayers(BufferedReader reader) {
        return getNumber(reader, 2, "players");
    }

    public static int getNumberOfDecks(BufferedReader reader, MatchCondition matchCondition) {
        int minDecks = matchCondition.getMinimumDeckNumber();
        if (minDecks > 1) {
            System.out.println("Your match choice requires multiple decks");
        }
        return getNumber(reader, minDecks, "decks");
    }

    private static int getNumber(BufferedReader reader, int minimum, String displayName) {
        int number = 0;
        while (number < minimum) {
            System.out.println("Type the Number of " + displayName + ". It has to be at least " + minimum + ": ");
            number = getTypedNumber(reader, minimum, displayName);
        }
        return number;
    }

    private static int getTypedNumber(BufferedReader reader, int minimum, String displayName) {
        int number = 0;
        try {
            number = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            System.out.println("Not a number, try again!");
            return number;
        }
        if (number < minimum) {
            System.out.println("Not enough " + displayName + ", try again!");
        }
        return number;
    }
}
