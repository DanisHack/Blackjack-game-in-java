package blackjackgame;

/**
 *  This class contains main function
 * @author DANISH MOHD
 *
 */

import java.util.Scanner;

public class GameMain {

	public static void main(String[] args) {
		
		//game init
		Scanner sc = new Scanner(System.in);
		Deck newDeck = new Deck(2, true);
		
		
		//players init
		Players you = new Players("Danish Mohd");
		Players dealer = new Players("Dealer");
		
		

	}

}
