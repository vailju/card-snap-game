package org.snap.game.dto;

import java.util.Arrays;
import java.util.List;

public final class Constants {

    private Constants() {
    }
    public static final List<String> STANDARD_DECK_VALUES = Arrays.asList("A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2");
    public static final String NEW_LINE = "\n";
    public static final String NO_VALID_SNAP = "Not a snap!" + NEW_LINE;
    public static final String NO_VALID_SNAPPPER = "Not sure who snapped!" + NEW_LINE;
    public static final int DEFAULT_SECONDS_BETWEEN_DRAW = 20;

    public static void printNewLine() {
        System.out.println(NEW_LINE);
    }
}
