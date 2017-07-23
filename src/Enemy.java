import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Enemy extends Player {

	ArrayList<Card> cardsLeftInDeck = new ArrayList<Card>();
	
	public Card playCardTaking() {
		Card tmp = hand.get(0);
		hand.remove(0);
		return tmp;
	}

	public Card playCardGivin(Card card) {
		//System.out.println("Analyzing..............................");
		ArrayList<Card> tempHand = new ArrayList<>();
		
		for (int i = 0; i < hand.size(); i++) {
			//System.out.println("Looking for: "+card.getSuit());
			if (card.getSuit() == hand.get(i).getSuit()) {
				//System.out.println("Card matching suit: "+hand.get(i).getSuit());
				tempHand.add(hand.get(i));
			}
		}

		if (tempHand.size() >= 1) {

			sort(tempHand);
			//System.out.println("Enemy tempHand: "+tempHand);

			for (int j = 0; j < tempHand.size(); j++) {

				if (tempHand.get(j).getValue() > card.getValue()) {
					//System.out.println("Enemy has larger same suit!");
					Card tmp = tempHand.get(j);
					//System.out.println("Enemy playing: "+tmp);
					removeCard(tmp);
					return tmp;
				}
			}
			//System.out.println("Enemy has smaller same suit!");
			Card tmp = tempHand.get(0);
			removeCard(tempHand.get(0));
			//System.out.println("Enemy playing" +tmp);
			return tmp;
		}
		
		else {
			Card tmp = hand.get(0);
			//System.out.println("Enemy does not have same suit, playing: "+tmp);
			hand.remove(0);
			return tmp;
		}
	}
	
	public void initLeftInDeck(Deck deck) {
		cardsLeftInDeck = deck.deck;
		removeOwnHandFromLeftInDeck();
		//System.out.println("CALCULATING CARDS INITILIZED");
		//System.out.println("Possible cards: "+cardsLeftInDeck.size());
	}
	
	public void removeFromLeftInDeck(Card card) {
		//System.out.println("REMOVING FROM POSSIBILITIES: "+card);
		for (int i = 0; i < cardsLeftInDeck.size(); i++) {
			if (card.equals(cardsLeftInDeck.get(i))) {
				System.out.println("FASDAS");
				cardsLeftInDeck.remove(i);
				
			}
		}
		//System.out.println("Possible cards: "+cardsLeftInDeck.size());
	}
	
	public void removeOwnHandFromLeftInDeck() {
		for (int i = 0; i < hand.size(); i++) {
			removeFromLeftInDeck(hand.get(i));
			//System.out.println("REMOVING: "+hand.get(i));
		}
	}

	

}
