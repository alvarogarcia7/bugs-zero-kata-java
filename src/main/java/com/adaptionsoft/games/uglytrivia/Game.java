package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.trivia.Players;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

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
		players.list.stream().forEach(this::addPlayer);
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	private boolean addPlayer(String playerName) {
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
		Question question = this.questions.pop(places[currentPlayer]);
		printQuestion(question);
	}

	private void printQuestion(Question question) {
		System.out.println("The category is " + question.category);
		System.out.println(question.text);
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
		Vector<LinkedList<Question>> questions = new Vector<>();

		public Question pop(int place) {
			final LinkedList<Question> selectedCategory = this.questions.get(place % questions.size());
			Question question = selectedCategory.removeFirst();
			selectedCategory.addLast(question);
			return question;
		}

		public Questions() {
			questions.add(new LinkedList<>());
			questions.add(new LinkedList<>());
			questions.add(new LinkedList<>());
			questions.add(new LinkedList<>());

			for (int i = 0; i < 50; i++) {
				questions.get(0).add(new Question("Pop Question " + i, "Pop"));
				questions.get(1).add(new Question("Science Question " + i, "Science"));
				questions.get(2).add(new Question("Sports Question " + i, "Sports"));
				questions.get(3).add(new Question("Rock Question " + i, "Rock"));
			}
		}
	}

	private class Question {
		private final String text;
		private final String category;

		public Question(String text, String category) {
			this.text = text;
			this.category = category;
		}
	}
}
