import java.util.ArrayList;

public class Player {
	
	ArrayList<Card> hand = new ArrayList<>();
	
	public void addToHand(Card card) {
		hand.add(card);
	}
	
	public void removeHand() {
		hand.removeAll(hand);
	}
	
	public void removeCard(Card card) {
		for (int i = 0; i < hand.size(); i++) {
			if (card.equals(hand.get(i))) {
				hand.remove(i);
			}
		}
	}
	
	public boolean isInHand (Card card) {
		for (int i = 0; i < hand.size(); i++) {
			if (card.equals(hand.get(i))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return hand+"";
	}
	
	public void sort(ArrayList<Card> tmp) {
		for (int i = 0; i < tmp.size() - 1; i++) {
			int index = i;
			for (int j = i + 1; j < tmp.size(); j++) {
				if (tmp.get(j).getValue() < tmp.get(i).getValue()) {
					index = j;
				}
			}
			Card smallerCard = tmp.get(index);
			tmp.set(index, tmp.get(i));
			tmp.set(i, smallerCard);
		}
	}
	
	
}
