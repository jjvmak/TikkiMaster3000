import java.util.Random;
import java.util.Scanner;

public class Gameboard {

	GUI gui = new GUI();
	
	static Player player = new Player();
	Enemy enemy = new Enemy();
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
		dealCards();
		randomStart();
		gui = new GUI();
		setGuiPlayerHand();
		setCardsToGui();
		gui.frame.setVisible(true);
		
		gui.leftInTheDeck.setText("Cards left in the deck: "+deck.deck.size());
		
		while (shouldPlay) {
			setTakerLabels();
			gui.leftInTheDeck.setText("Cards left in the deck: "+deck.deck.size());
			
			System.out.println("Cards on the table: "+cardsOnTheTable);
			
			while (cardsOnTheTable < 2) {
				
				System.out.println("Cards on the table: "+cardsOnTheTable+" (second whileloop)");
				
				
				if (isPlayersTurn) {
					
					System.out.println("Players turn!");
					gui.appendText("\n Players turn!");
					System.out.println("Enemy cards: "+enemy.toString());
					System.out.println("Your cards: "+player.toString());
					System.out.println("---------------------------------------------------------------------------------------");
					
					if (!isEnemyGiving) {
						System.out.println("You are giving! Enemy card on table: "+ enemyCardOnTable);
						gui.appendText("\n You are giving! Enemy card on table: "+ enemyCardOnTable);
						System.out.println("---------------------------------------------------------------------------------------");
					}
					
					waitForPlayerCard();

					if(!waiting) {
						System.out.println(playerCardOnTable);
						System.out.println("---------------------------------------------------------------------------------------");
						isPlayersTurn = false;
						cardsOnTheTable++;
						waiting = true;
						System.out.println("AXAXAXAXAXAXAXAXAXAXA "+gui.playerHand);
					}
					
					else {
						System.out.println("Incorrect input! Try again.");
						System.out.println("---------------------------------------------------------------------------------------");
					}
				}

				if (!isPlayersTurn && !isEnemyCardOnTable) {

					if (!isEnemyGiving) {
						System.out.println("Enemy is taking!");
						gui.appendText("\n Enemy is taking!");
						enemyCardOnTable = enemy.playCardTaking();
						gui.setEnemyCardLabel(enemyCardOnTable);
						System.out.println("enemy card: " + enemyCardOnTable);
						gui.appendText("\n enemy card: " + enemyCardOnTable);
						System.out.println("---------------------------------------------------------------------------------------");
						isPlayersTurn = true;
						isEnemyCardOnTable = true;
						cardsOnTheTable++;
					}

					if (isEnemyGiving) {
						System.out.println("Enemy is giving!");
						gui.appendText("\n Enemy is giving!");
						enemyCardOnTable = enemy.playCardGivin(playerCardOnTable);
						gui.setEnemyCardLabel(enemyCardOnTable);
						System.out.println("enemy card: " + enemyCardOnTable);
						gui.appendText("\n Enemy card: " + enemyCardOnTable);
						System.out.println("---------------------------------------------------------------------------------------");
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
			enemy.addToHand(deck.dealCard());
			
		}
		
		enemy.initLeftInDeck(deck);
		
		for (int i = 0; i < 5; i++) {
			player.addToHand(deck.dealCard());
		}
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
		
		if (roundNumber == 5) {
			System.out.println("ASDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD_ ______________");
			deck.setIsPlayable(false);
			roundNumber++;
		}
		
		turnNumber++;
		isEnemyCardOnTable = false;
		System.out.println("Evaluating turn number: "+turnNumber+".................................................");
		gui.appendText("\n Evaluating turn number: "+turnNumber);
		System.out.println("Enemy calculating cards................................................................");
		enemy.removeFromLeftInDeck(playerCard);
		System.out.println("You played: "+playerCard+" Enemy played: "+enemyCard);
		gui.appendText("\n You played: "+playerCard+" Enemy played: "+enemyCard);
		System.out.println("---------------------------------------------------------------------------------------");
		
		if (isEnemyGiving) {
			if (enemyCard.getSuit() == playerCard.getSuit()) {
				if (enemyCard.getValue() > playerCard.getValue()) {
					isPlayersTurn = false;
					isEnemyGiving = false;
					System.out.println("Enemy wins turn!");
					gui.appendText("\n Enemy wins turn!");
					System.out.println("---------------------------------------------------------------------------------------");
					cardsOnTheTable = 0;
				}
				else {
					isPlayersTurn = true;
					isEnemyGiving = true;
					System.out.println("You win turn!");
					gui.appendText("\n You win turn!");
					System.out.println("---------------------------------------------------------------------------------------");
					cardsOnTheTable = 0;
				}
			}
			else {
				isPlayersTurn = true;
				isEnemyGiving = true;
				System.out.println("You win turn!");
				gui.appendText("\n You win turn!");
				System.out.println("---------------------------------------------------------------------------------------");
				cardsOnTheTable = 0;
			}
		}

		else {
			if (enemyCard.getSuit() == playerCard.getSuit()) {
				if (enemyCard.getValue() < playerCard.getValue()) {
					isPlayersTurn = true;
					isEnemyGiving = true;
					System.out.println("You win turn!");
					gui.appendText("\n You win turn!");
					System.out.println("---------------------------------------------------------------------------------------");
					cardsOnTheTable = 0;
				}
				else {
					isPlayersTurn = false;
					isEnemyGiving = false;
					System.out.println("Enemy wins turn!");
					gui.appendText("\n Enemy wins turn!");
					System.out.println("---------------------------------------------------------------------------------------");
					cardsOnTheTable = 0;
				}
			}
			else {
				isPlayersTurn = false;
				isEnemyGiving = false;
				System.out.println("Enemy wins turn!");
				gui.appendText("\n Enemy wins turn!");
				
				System.out.println("---------------------------------------------------------------------------------------");
				cardsOnTheTable = 0;
			}
		}
		
		if (turnNumber == 5) {
			
			if(isPlayersTurn) {
				playerScore++;
				turnNumber = 0;
				System.out.println("PLAYER WINS!");
				gui.appendText("\n PLAYER WINS!");
				System.out.println("PLAYER SCORE: "+playerScore+" ENEMY SCORE: "+enemyScore);
				gui.appendText("\n PLAYER SCORE: "+playerScore+" ENEMY SCORE: "+enemyScore);
				gui.scoreLabel.setText("PLAYER SCORE: "+playerScore+" ENEMY SCORE: "+enemyScore);
				System.out.println("---------------------------------------------------------------------------------------");
				
				if (roundNumber < 5 && deck.isPlayable()) {
					dealCards();
					setCardsToGui();
					setGuiPlayerHand();
					gui.setAllActive();
				}
				
				
			}
			
			else {
				enemyScore++;
				turnNumber = 0;
				System.out.println("ENEMY WINS!");
				gui.appendText("\n ENEMY WINS!");
				System.out.println("PLAYER SCORE: "+playerScore+" ENEMY SCORE: "+enemyScore);
				gui.appendText("\n PLAYER SCORE: "+playerScore+" ENEMY SCORE: "+enemyScore);
				gui.scoreLabel.setText("PLAYER SCORE: "+playerScore+" ENEMY SCORE: "+enemyScore);
				System.out.println("---------------------------------------------------------------------------------------");
				
				if (roundNumber < 5 && deck.isPlayable()) {
					dealCards();
					setCardsToGui();
					setGuiPlayerHand();
					gui.setAllActive();
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
