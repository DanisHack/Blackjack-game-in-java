package blackjackgame;

/**
 *  This class contains main function
 * @author DANISH MOHD
 *
 */

public class GameMain {

	public static void main(String[] args) {
		
		Deck newDeck = new Deck(2, true);
		newDeck.printDeckCards(10);

	}

}
