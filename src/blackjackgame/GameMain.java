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
		
		// distributing initial cards
		you.addCardToPlayersHand(newDeck.dealingNextCard());
		dealer.addCardToPlayersHand(newDeck.dealingNextCard());
		you.addCardToPlayersHand(newDeck.dealingNextCard());
		dealer.addCardToPlayersHand(newDeck.dealingNextCard());
		
		// dealt initial hands
		System.out.println("******Cards Dealt*****\n");
		dealer.printCardsInHand(false);
		you.printCardsInHand(true);
		System.out.printf("Score:%d\n\n", you.getPlayersHandTotal());
		
		// flags- Hit, Stand
		
		boolean youDone = false;
		boolean dealerDone = false;
		
		String answer;
		
		while(!youDone || !dealerDone){
			
			//
			if(!youDone){
				
				System.out.print("Hit or Stay? [Enter H or S]");
				answer = sc.next();
				System.out.println();
				if(answer.compareToIgnoreCase("H") == 0){
					
				}
				
			}
		}
		
		
		
		
		

	}

}
