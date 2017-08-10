import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Enemy extends Player {

	ArrayList<Card> cardsLeftInDeck = new ArrayList<Card>();
	ArrayList<Card> tempHand = new ArrayList<Card>();


	int heartsLeft;
	int diamondsLeft;
	int clubsLeft;
	int spadesLeft;


	//VARIABLES FOR PREDICTING PLAYERS CURRENT HAND
	private Card playerLastCard; 
	private Card enemyLastCard;
	private boolean isSpadeInPlayersHand = true;
	private boolean isHeartInPlayersHand = true;
	private boolean isClubInPlayersHand = true;
	private boolean isDiamondInPlayersHand = true;
	private int heartRisk; 
	private int diamondRisk;
	private int clubdRisk;
	private int spadeRisk;
	private Suit currentHighRisk;


	public Card playCardTaking() {

		ArrayList<Card> tempHand = new ArrayList<>();
		
		for (int i = 0; i < hand.size(); i++) {
			tempHand.add(hand.get(i));
		}
		
		sort(tempHand);
		//System.out.println("tmp hand" +tempHand);
		
		for (int j = 0; j < tempHand.size(); j++) {
			if (tempHand.get(j).getSuit() == currentHighRisk) {
				//System.out.println("currentrisk "+currentHighRisk+" tmp hand suit: "+tempHand.get(j).getSuit());
				Card tmp = tempHand.get(j);
				//System.out.println(tmp);
				//hand.remove(0);
				removeCard(tmp);
				//enemyLastCard = tmp;
				return tmp;
			}
		}
		

		Card tmp = tempHand.get(0);
		//hand.remove(tmp);
		removeCard(tmp);
		//enemyLastCard = tmp;
		return tmp;
	}

	public Card playCardGivin(Card card) {

		ArrayList<Card> tempHand = new ArrayList<>();

		for (int i = 0; i < hand.size(); i++) {

			if (card.getSuit() == hand.get(i).getSuit()) {
				tempHand.add(hand.get(i));

			}
		}

		if (tempHand.size() >= 1) {
			sort(tempHand);

			for (int j = 0; j < tempHand.size(); j++) {

				if (tempHand.get(j).getValue() > card.getValue()) {
					Card tmp = tempHand.get(j);
					removeCard(tmp);
					return tmp;

				}
			}
			Card tmp = tempHand.get(0);
			removeCard(tempHand.get(0));
			return tmp;
		}

		else {
			Card tmp = playCardTaking();
			//hand.remove(tmp);
			removeCard(tmp);
			return tmp;

		}
	}

	public void initLeftInDeck(Deck deck) {
		cardsLeftInDeck.removeAll(cardsLeftInDeck);
		for (int i = 0; i < deck.deck.size(); i++) {
			cardsLeftInDeck.add(deck.deck.get(i));
		}
	}

	public void removeFromLeftInDeck(Card card) {

		for (int i = 0; i < cardsLeftInDeck.size(); i++) {
			if (card.equals(cardsLeftInDeck.get(i))) {
				cardsLeftInDeck.remove(i);

			}
		}
	}

	public String possibilitiesss(){

		heartsLeft = 0;
		diamondsLeft = 0;
		clubsLeft = 0;
		spadesLeft = 0;

		for (int i = 0; i < cardsLeftInDeck.size(); i++) {

			String suitStr = cardsLeftInDeck.get(i).getSuit().toString().toLowerCase();

			if (suitStr.equals("heart")) heartsLeft++;
			if (suitStr.equals("club")) clubsLeft++;
			if (suitStr.equals("diamond")) diamondsLeft++;
			if (suitStr.equals("spade")) spadesLeft++;
		}

		highestRisk();

		String p = "Enemy hand:"+hand+
				"\n"+
				"\nHearts: "+heartsLeft+
				"\nDiamonds: "+diamondsLeft+
				"\nClubs: "+clubsLeft+
				"\nSpades: "+spadesLeft+
				"\n"+
				"\nPredictions: "+
				"\nHeart in hand: "+isHeartInPlayersHand+" Risklevel: "+heartRisk+
				"\nDiamond in hand: "+isDiamondInPlayersHand+" Risklevel: "+diamondRisk+
				"\nClub in hand: "+isClubInPlayersHand+" Risklevel: "+clubdRisk+
				"\nSpade in hand: "+isSpadeInPlayersHand+" Risklevel: "+spadeRisk+
				"\nCurrent high risk: "+currentHighRisk;

		return p;
	}

	public void makeTempHand() {
		tempHand = new ArrayList<>();
		tempHand = hand;
	}

	public void setPlayerLastCard(Card card) {
		playerLastCard = card;
	}

	public void predictCurrentPlayerHand(Card card) {
		String suitStr = card.getSuit().toString().toLowerCase();
		//System.out.println("PREDICT");

		if (suitStr.equals("heart")) isHeartInPlayersHand = false;
		if (suitStr.equals("club")) isClubInPlayersHand = false;
		if (suitStr.equals("diamond")) isDiamondInPlayersHand = false;
		if (suitStr.equals("spade")) isSpadeInPlayersHand = false;

		if (heartsLeft == 0) isHeartInPlayersHand = false;
		if (clubsLeft == 0) isClubInPlayersHand = false;
		if (diamondsLeft == 0) isDiamondInPlayersHand = false;
		if (spadesLeft == 0) isSpadeInPlayersHand = false;



	}

	public void resetPredictCurrentPlayerHand() {
		isSpadeInPlayersHand = true;
		isHeartInPlayersHand = true;
		isClubInPlayersHand = true;
		isDiamondInPlayersHand = true;
	}

	public void highestRisk() {

		heartRisk = heartsLeft;
		diamondRisk = diamondsLeft;
		clubdRisk = clubsLeft;
		spadeRisk = spadesLeft;

		if(!isHeartInPlayersHand) heartRisk = 0;
		if(!isDiamondInPlayersHand) diamondRisk = 0;
		if(!isClubInPlayersHand) clubdRisk = 0;
		if(!isSpadeInPlayersHand) spadeRisk = 0;

		//#FML
		if (heartRisk > diamondRisk &&
				heartRisk > clubdRisk &&
				heartRisk > spadeRisk) {
			
			currentHighRisk = Suit.HEART;
		}

		if (diamondRisk > heartRisk &&
				diamondRisk > clubdRisk &&
				diamondRisk > spadeRisk) {
			
			currentHighRisk = Suit.DIAMOND;
		}
		
		if (clubdRisk > heartRisk &&
				clubdRisk > diamondRisk &&
				clubdRisk > spadeRisk) {
			
			currentHighRisk = Suit.CLUB;
		}
		
		if (spadeRisk > heartRisk &&
				spadeRisk > diamondRisk &&
				spadeRisk > clubdRisk) {
			
			currentHighRisk = Suit.SPADE;
		}

	}
}
