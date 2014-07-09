package blackjackgame;

/**
 *  This class contains main function
 * @author DANISH MOHD
 *
 */

import java.util.Scanner;
import java.util.InputMismatchException;

//remember to include bet danish
public class GameMain {

	public static void main(String[] args) {
		
		//game init
		Scanner sc = new Scanner(System.in);
		Deck newDeck = new Deck(4, true);
		
		System.out.println("Enter Your Name:\n");
		String playerName = sc.next();
		
		
		
		System.out.println(playerName+", you got 100 complimentary chips for playing");
		int balance = 100;
		boolean gameOver = false;
		
		
		//players init
		Players you = new Players(playerName);
		Players dealer = new Players("Dealer");
		
		
		
		
		// Game 
		while(balance > 0 &&  !gameOver){
			
			System.out.println(playerName+", Do you want to deal or end the game [Enter D or E]??");
			String de = sc.next();
			if(de.compareToIgnoreCase("D") == 0){
				
				int bet = 0 ;
				String msg = "Enter your bet for this game";
				while(bet<=0){
					
					try{
						System.out.println(msg);
						bet = sc.nextInt();
					}catch(InputMismatchException e){
						//System.err.println("Caught InputMismatchException: " +  e.getMessage());
						//throw e;
						sc.nextLine();
					}finally{
						msg = "Enter your bet in Integers please";
					}	
				}
				
				
				if((bet >= 1) && (bet%1 == 0) && (balance-bet>=0)){
					
					balance = balance - bet;
					
					// fresh start
					you.emptyHand();
					dealer.emptyHand();
					
					boolean youDone = false;
					boolean dealerDone = false;
					
					// Distributing initial cards
					you.addCardToPlayersHand(newDeck.dealingNextCard());
					dealer.addCardToPlayersHand(newDeck.dealingNextCard());
					you.addCardToPlayersHand(newDeck.dealingNextCard());
					dealer.addCardToPlayersHand(newDeck.dealingNextCard());
					
					
					// Dealt initial hands
					System.out.println("########## Cards Dealt ##########\n");
					dealer.printCardsInHand(false);
					you.printCardsInHand(true);
					System.out.printf("Your Score:%d\t", you.getPlayersHandTotal());
					System.out.printf("Bet:%d\t", bet);
					System.out.printf("Balance:%d\n\n", balance);
					
					//Game play starts here
					// flags- Hit, Stand, Double, Split
					
					String answer;
					
					if(you.getPlayersHandTotal()>21){
						System.out.println("Busted!!\n");
						youDone = true;
						dealerDone = true;
					}
					else if(you.getPlayersHandTotal() == 21){
						System.out.println("Hurray! BlackJack. you won");
						 youDone = true;
						 dealerDone = true;
					}
					
					
					while(!youDone || !dealerDone){
					
						//
						if(!youDone){
							
							System.out.print("Hit or Stay? [Enter H or S]");
							answer = sc.next();
							System.out.println();
							if(answer.compareToIgnoreCase("H") == 0){
								youDone = !you.addCardToPlayersHand(newDeck.dealingNextCard());
								you.printCardsInHand(true);
								System.out.printf("Your Score:%d\t", you.getPlayersHandTotal());
								System.out.printf("Bet:%d\t", bet);
								System.out.printf("Balance:%d\n\n", balance);
								
								if(you.getPlayersHandTotal()>21){
									System.out.println("You BUSTED!!");
									dealer.printCardsInHand(true);
									System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
									youDone = true;
									dealerDone = true;
								}
							}
							else{
								System.out.println("You Choose to stay, dealer's turn \n");
								youDone = true;
							}
							
						}
						else if(!dealerDone){
							
							
							if(dealer.getPlayersHandTotal() < 17){
								dealer.printCardsInHand(true);
								System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
								System.out.println("Dealer hits \n");
								dealerDone = !dealer.addCardToPlayersHand(newDeck.dealingNextCard());
								
								if(dealer.getPlayersHandTotal()>21){
									dealer.printCardsInHand(true);
									System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
									System.out.println("Dealer BUSTED!!");
									dealerDone = true;
								}
							}
							else{
								dealer.printCardsInHand(true);
								System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
								System.out.println("Dealer stays \n");
								dealerDone = true;
							}
						}
						
						System.out.println();
					}
					
					int youSum = you.getPlayersHandTotal();
					int dealerSum = dealer.getPlayersHandTotal();
					
					if(youSum>dealerSum && youSum<=21 || dealerSum >21){
						System.out.println("You win!! \n");
						System.out.printf("Your Bet was :%d\t", bet);
						System.out.printf("Your Balance was:%d\n", balance);
						System.out.printf("You win :%d\t", bet+bet);
						balance = balance + bet + bet;
						System.out.printf("Your Current Balance:%d\n", balance);
						
					}
					else if(youSum == dealerSum){
						System.out.println("PUSH!!!");
						balance = balance + bet;
						System.out.printf("Your Current Balance:%d\n", balance);
					}
					else{
						System.out.println("You Lose!! \n");
						System.out.printf("Your Current Balance:%d\n", balance);
					}
					
				}
				else{
					System.out.println("Your bet amount is wrong, it should be a natural number and should not exceed your balance");
					System.out.printf("Your Balance:%.0f\n\n", balance);
				}
			}
			else{
				gameOver = true;
			}	
		}
		
		
		//closing scanner
		sc.close();
		System.out.println(playerName+", game ended---> run again to play");

	}

}
