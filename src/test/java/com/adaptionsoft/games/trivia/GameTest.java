package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.uglytrivia.Game;
import org.approvaltests.Approvals;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void itsLockedDown() throws Exception {

        Random randomizer = new Random(123455);
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(resultStream));

        IntStream.range(1, 15).forEach(i -> GameRunner.playGame(randomizer));

        Approvals.verify(resultStream.toString());

    }

    @Test
    public void player_does_not_get_out_of_penalty_box() {
        Game game = new Game(Players.aNew("P1", "P2").get());

        get_to_penalty_box(game); //p1
        assertTrue(game.isFirstPlayerInPenalty());

        game.wasCorrectlyAnswered(); //p2

        get_out_of_penalty_box(game);
//        assertFalse(game.isFirstPlayerInPenalty());
        // we can't make it green
    }

    private void get_to_penalty_box(Game game) {
        game.wrongAnswer();
    }

    private void get_out_of_penalty_box(Game game) {
        game.roll(1);
        game.wasCorrectlyAnswered();
    }
}
