import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	ArrayList<Card> deck = new ArrayList<Card>();
	
	public void initDeck() {
		
		for (int i = 1; i < 14; i++) {
			deck.add(new Card(i, Suit.HEART));
			deck.add(new Card(i, Suit.CLUB));
			deck.add(new Card(i, Suit.DIAMOND));
			deck.add(new Card(i, Suit.SPADE));
		}
		
	}
	
	public void shuffleDeck() {
		Collections.shuffle(deck);
		//System.out.println(deck);
	}
	
	public Card dealCard() {
		
		if (deck.size() > 0) {
			Card temp = deck.get(0);
			deck.remove(0);
			return temp;
		}
		
		else {
			return null;
		}
	}
	
	public boolean isPlayable() {
		if (deck.size() >= 10) return true;
		else return false;
	}

}
