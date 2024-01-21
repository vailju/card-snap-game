package org.snap.game.dto;

import java.util.Arrays;
import java.util.List;

public final class Constants {

    private Constants() {
    }

    public static final List<String> STANDARD_DECK_VALUES = Arrays.asList("A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2");
    public static final String PLAYER_STRING = "Player ";
    public static final String NO_VALID_SNAP = "Not sure who snapped!\n";
}
