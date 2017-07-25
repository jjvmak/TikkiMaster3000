import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Enemy extends Player {

	ArrayList<Card> cardsLeftInDeck = new ArrayList<Card>();
	ArrayList<Card> tempHand = new ArrayList<Card>();

	public Card playCardTaking() {
		Card tmp = hand.get(0);
		hand.remove(0);
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
			Card tmp = hand.get(0);
			hand.remove(0);
			return tmp;
			
		}
	}

	public void initLeftInDeck(Deck deck) {
		cardsLeftInDeck = deck.deck;
		removeOwnHandFromLeftInDeck();
		
	}

	public void removeFromLeftInDeck(Card card) {
		
		for (int i = 0; i < cardsLeftInDeck.size(); i++) {
			if (card.equals(cardsLeftInDeck.get(i))) {
				cardsLeftInDeck.remove(i);

			}
		}
	}

	public void removeOwnHandFromLeftInDeck() {
		for (int i = 0; i < hand.size(); i++) {
			removeFromLeftInDeck(hand.get(i));
			
		}
	}

	public String possibilities(){
		int heartsLeft = 0;
		int diamondsLeft = 0;
		int clubsLeft = 0;
		int spadesLeft = 0;
		
		for (int i = 0; i < this.cardsLeftInDeck.size(); i++) {

			String suitStr = this.cardsLeftInDeck.get(i).getSuit().toString().toLowerCase();
			
			if (suitStr.equals("heart")) heartsLeft++;
			if (suitStr.equals("club")) clubsLeft++;
			if (suitStr.equals("diamond")) diamondsLeft++;
			if (suitStr.equals("spade")) spadesLeft++;
		}
		
		String p = "Enemy hand:"+hand+
				   "\n"+
				   "\nHearts: "+heartsLeft+
				   "\nDiamonds: "+diamondsLeft+
				   "\nClubs: "+clubsLeft+
				   "\nSpades: "+spadesLeft;
			
		return p;
	}

	public void makeTempHand() {
		tempHand = new ArrayList<>();
		tempHand = hand;
	}
}
