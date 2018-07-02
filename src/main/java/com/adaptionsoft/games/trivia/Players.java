package com.adaptionsoft.games.trivia;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Players {
    public final List<String> list;

    private Players(String[] players) {
        this.list = Arrays.asList(players);
    }

    public static Optional<Players> aNew(String... playerNames) {
        if (playerNames.length >= 2 && playerNames.length < 6) {
            return Optional.of(new Players(playerNames));
        }
        return Optional.empty();
    }
}
