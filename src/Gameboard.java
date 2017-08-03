import java.util.Random;
import java.util.Scanner;

public class Gameboard {

	GUI gui = new GUI();

	static Player player = new Player();
	static Enemy enemy = new Enemy();
	Deck deck = new Deck();

	static boolean isPlayersTurn = true;
	boolean playerCorrectInput;
	boolean isEnemyGiving = true; // true giving false taking
	boolean waitForBothCard = true;
	boolean isEnemyCardOnTable = false;
	static boolean waiting = true;
	boolean shouldPlay;

	int playerScore = 0;
	int enemyScore = 0;
	int roundNumber = 0;
	int turnNumber = 0;
	static int cardsOnTheTable = 0;

	static Card playerCardOnTable;
	Card enemyCardOnTable;

	Scanner scn = new Scanner(System.in);

	public void gameLoop() {

		initDeck();
		enemy.initLeftInDeck(deck);
		dealCards();
		randomStart();
		gui = new GUI();
		setGuiPlayerHand();
		setCardsToGui();
		gui.frame.setVisible(true);
		gui.leftInTheDeck.setText("Cards left in the deck: "+deck.deck.size());

		while (shouldPlay) {
			gui.appendText(enemy.possibilitiesss());
			setTakerLabels();
			gui.leftInTheDeck.setText("Cards left in the deck: "+deck.deck.size());

			while (cardsOnTheTable < 2) {
				
				if (isPlayersTurn) {
					waitForPlayerCard();

					if(!waiting) {
						isPlayersTurn = false;
						cardsOnTheTable++;
						waiting = true;
					
					}
				}

				if (!isPlayersTurn && !isEnemyCardOnTable) {
					
					if (!isEnemyGiving) {
						enemyCardOnTable = enemy.playCardTaking();
						gui.setEnemyCardLabel(enemyCardOnTable);
						isPlayersTurn = true;
						isEnemyCardOnTable = true;
						cardsOnTheTable++;
						
					}

					if (isEnemyGiving) {
						enemyCardOnTable = enemy.playCardGivin(playerCardOnTable);
						gui.setEnemyCardLabel(enemyCardOnTable);
						cardsOnTheTable++;
						isEnemyCardOnTable = true;
					}
				}
			}

			evaluateTurn(enemyCardOnTable, playerCardOnTable);
		} 
	}

	public void initDeck() {
		
		deck.initDeck();
		deck.shuffleDeck();
		roundNumber = 1;
		shouldPlay = true;
		
	}

	public void dealCards() {

		for (int j = 0; j < 5; j++) {
			Card tmp = deck.dealCard();
			enemy.removeFromLeftInDeck(tmp);
			enemy.addToHand(tmp);
			

		}
	
		for (int i = 0; i < 5; i++) {
			player.addToHand(deck.dealCard());
			
		}
		
		enemy.resetPredictCurrentPlayerHand();
	}

	public void randomStart() {
		Random rnd = new Random();
		int randomed = rnd.nextInt(100)+1;

		if(randomed % 2 == 0){
			isPlayersTurn = true;
			isEnemyGiving = true;

		}
		else {
			isPlayersTurn = false;
			isEnemyGiving = false;

		}	
	}

	public void evaluateTurn(Card enemyCard, Card playerCard) {
		turnNumber++;

		System.out.println("turn: "+turnNumber+" enemycard: "+enemyCard+" playercard: "+playerCard);
		gui.appendText(enemy.possibilitiesss());
		if (roundNumber == 5) {
			deck.setIsPlayable(false);
			roundNumber++;
			
		}

		isEnemyCardOnTable = false;
	
		if (isEnemyGiving) {
			if (enemyCard.getSuit() == playerCard.getSuit()) {
				if (enemyCard.getValue() > playerCard.getValue()) {
					isPlayersTurn = false;
					isEnemyGiving = false;
					cardsOnTheTable = 0;
					System.out.println("enemy wins!");
					
				}
				else {
					isPlayersTurn = true;
					isEnemyGiving = true;
					cardsOnTheTable = 0;
					System.out.println("You win turn!");

					
				}
			}
			else {
				isPlayersTurn = true;
				isEnemyGiving = true;
				System.out.println("You win turn!");
				cardsOnTheTable = 0;
				
			}
		}

		else {
			if (enemyCard.getSuit() == playerCard.getSuit()) {
				if (enemyCard.getValue() < playerCard.getValue()) {
					isPlayersTurn = true;
					isEnemyGiving = true;
					cardsOnTheTable = 0;
					System.out.println("you win turn!");
					
				}
				else {
					isPlayersTurn = false;
					isEnemyGiving = false;
					System.out.println("Enemy wins turn!");
					cardsOnTheTable = 0;
					
				}
			}
			else {
				enemy.predictCurrentPlayerHand(enemyCard);
				isPlayersTurn = false;
				isEnemyGiving = false;
				cardsOnTheTable = 0;
				System.out.println("Enemy wins turn!");
				
				
			}
		}

		if (turnNumber == 5) {

			if(isPlayersTurn) {
				playerScore++;
				turnNumber = 0;
				gui.scoreLabel.setText("PLAYER SCORE: "+playerScore+" ENEMY SCORE: "+enemyScore);

				if (roundNumber < 5 && deck.isPlayable()) {
					dealCards();
					setCardsToGui();
					setGuiPlayerHand();
					gui.setAllActive();
					
				}
				
				else {
					deck.deck.removeAll(deck.deck);
					gameLoop();
				}


			}

			else {
				enemyScore++;
				turnNumber = 0;
				gui.scoreLabel.setText("PLAYER SCORE: "+playerScore+" ENEMY SCORE: "+enemyScore);
		
				if (roundNumber < 5 && deck.isPlayable()) {
					dealCards();
					setCardsToGui();
					setGuiPlayerHand();
					gui.setAllActive();
					
				}
				
				else {
					deck.deck.removeAll(deck.deck);
					gameLoop();
				}
				
				
			}
			
			roundNumber++;
		}
	}

	public void setCardsToGui() {
		gui.setCardNames(player.hand);
	}

	public void setTakerLabels() {
		if (isEnemyGiving) {
			gui.playerTakerLabel.setText("Taker!");
			gui.enemyTakerLabel.setText("");
		}
		else {
			gui.playerTakerLabel.setText("");
			gui.enemyTakerLabel.setText("Taker!");
		}
	}

	public static void playCard(Card card) {
		isPlayersTurn = false;
		cardsOnTheTable++;
		playerCardOnTable = card;
		player.removeCard(playerCardOnTable);
		enemy.removeFromLeftInDeck(playerCardOnTable);
		waiting = false;

	}

	public void waitForPlayerCard() {
		System.out.println("waiting for card");
		while (waiting) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setGuiPlayerHand() {
		gui.playerHand.removeAll(gui.playerHand);
		for  (int i = 0; i < player.hand.size(); i++ ) {
			gui.setPlayerHand(player.hand.get(i));
		}
	}

	public void checkPlayerInput(String inp) {
		//This method should be used only if GUI is not in use.

		String suit = inp.substring(0,1);
		int number;

		try {
			number = Integer.parseInt(inp.substring(1));
		} catch (Exception e) {
			number = -1;
		}

		Suit eSuit = null;
		boolean isInit = false;

		switch (suit) {

		case "s":
			eSuit = Suit.SPADE;
			isInit = true;
			break;

		case "c":
			eSuit = Suit.CLUB;
			isInit = true;
			break;

		case "d":
			eSuit = Suit.DIAMOND;
			isInit = true;
			break;

		case "h":
			eSuit = Suit.HEART;
			isInit = true;
			break;

		default:
			break;
		}

		Card tempCard = new Card(number, eSuit);

		if (isInit && number > 0 && number < 14 && player.isInHand(tempCard)) {
			playerCorrectInput = true;
			playerCardOnTable = new Card(number, eSuit);
			player.removeCard(playerCardOnTable);
		}

		else {
			playerCorrectInput = false;
		}
	}
}