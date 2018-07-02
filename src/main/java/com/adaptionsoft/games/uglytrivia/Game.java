package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.trivia.MaximumNumberOfPlayersException;
import com.adaptionsoft.games.trivia.Players;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses  = new int[6];
    boolean[] inPenaltyBox  = new boolean[6];

    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
	private Questions questions;

	public Game(Players players) {
		questions = new Questions();
		players.list.stream().forEach(this::add);
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public boolean add(String playerName) {
		if (players.size() >= 5) {
			throw new MaximumNumberOfPlayersException();
		}
		
	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				movePlayerAndAskQuestion(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {

			movePlayerAndAskQuestion(roll);
		}
		
	}

	private void movePlayerAndAskQuestion(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		if (currentCategory() == "Pop") {
			printQuestion(questions.popQuestions);
		}
		if (currentCategory() == "Science")
			printQuestion(questions.scienceQuestions);
		if (currentCategory() == "Sports")
			printQuestion(questions.sportsQuestions);
		if (currentCategory() == "Rock")
			printQuestion(questions.rockQuestions);
	}

	private void printQuestion(LinkedList questions) {
		final Object question = questions.removeFirst();
		System.out.println(question);
		questions.addLast(question);
	}


	private String currentCategory() {
		return questions.at(places[currentPlayer]);
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}

	public boolean isFirstPlayerInPenalty() {
		return inPenaltyBox[0];
	}

	private class Questions {
		LinkedList popQuestions = new LinkedList();
		LinkedList scienceQuestions = new LinkedList();
		LinkedList sportsQuestions = new LinkedList();
		LinkedList rockQuestions = new LinkedList();

		public Questions() {
			for (int i = 0; i < 50; i++) {
				popQuestions.addLast("Pop Question " + i);
				scienceQuestions.addLast(("Science Question " + i));
				sportsQuestions.addLast(("Sports Question " + i));
				rockQuestions.addLast("Rock Question " + i);
			}
		}

		public String at(int place) {
			if (place % 4 == 0) return "Pop";
			if (place % 4 == 1) return "Science";
			if (place % 4 == 2) return "Sports";
			return "Rock";
		}
	}
}
