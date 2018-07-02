package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.uglytrivia.Game;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.stream.IntStream;

public class GameTest {

    @Test
    public void itsLockedDown() throws Exception {

        Random randomizer = new Random(123455);
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(resultStream));

        IntStream.range(1, 15).forEach(i -> GameRunner.playGame(randomizer));

        Approvals.verify(resultStream.toString());

    }

    @Test(expected = MaximumNumberOfPlayersException.class)
    public void no_more_than_6_players() {
        Game aGame = new Game("Player1", "Player2");

        aGame.add("p3");
        aGame.add("p4");
        aGame.add("p5");
        aGame.add("p6_INVALID_PLAYER");
        aGame.roll(1);
    }
}
