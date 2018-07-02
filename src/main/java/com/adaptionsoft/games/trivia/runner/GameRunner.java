
package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.trivia.Players;
import com.adaptionsoft.games.uglytrivia.Game;

import java.util.Optional;
import java.util.Random;


public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {
		Random rand = new Random();
		playGame(rand);

	}

	public static void playGame(Random rand) {
        final Optional<Players> players = Players.aNew("Chet", "Pat");
        Game aGame = new Game(players.get());
		aGame.add("Sue");


		do {

			aGame.roll(rand.nextInt(5) + 1);

			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}



		} while (notAWinner);
	}
}
