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
					youDone = !you.addCardToPlayersHand(newDeck.dealingNextCard());
					you.printCardsInHand(true);
				}
				else{
					youDone = true;
				}
				
			}
			
			if(!dealerDone){
				if(dealer.getPlayersHandTotal() < 17){
					
					System.out.println("dealer hits \n");
					dealerDone = !dealer.addCardToPlayersHand(newDeck.dealingNextCard());
					dealer.printCardsInHand(false);
				}
				else{
					System.out.println("dealer stays \n");
					dealerDone = true;
				}
			}
			
			System.out.println();
		}
		
		//closing scanner
		sc.close();
		
		you.printCardsInHand(true);
		dealer.printCardsInHand(true);
		
		int youSum = you.getPlayersHandTotal();
		int dealerSum = dealer.getPlayersHandTotal();
		
		if(youSum>dealerSum && youSum<=21 || dealerSum >21){
			System.out.println("You win!! \n");
		}
		else{
			System.out.println("dealer wins!! \n");
		}

	}

}
