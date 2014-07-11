package blackjackgame;

/**
 *  This class contains main function
 * @author DANISH MOHD
 *
 */

import java.util.Scanner;
import java.util.InputMismatchException;


public class GameMain {
	
	private Deck newDeck;
	private String playerName;
	private float balance;
	private float bet;
	private boolean youDone;
	private boolean dealerDone;
	private Players dealer;
	private Players you;
	private Scanner sc = new Scanner(System.in);
	
	
	GameMain(){
		
		this.balance = 100;
		this.newDeck = new Deck(4, true);
		boolean gameOver = false;
		
		System.out.println("Enter Your Name:\n");
		playerName = sc.next();
		
		System.out.println("\nCongratulations!! "+playerName+", you have got 100 complimentary chips for playing. Enjoy!\n");
		
		// Players init
		you = new Players(playerName);
		dealer = new Players("Dealer");
		
		
		// Game Starts here --->
		while(this.balance > 0 &&  !gameOver){
					
			System.out.println("\n"+playerName+", Do you want to deal or end the game [Enter D or E(or else)]??");
			String gameInit = sc.next();
					
			if(gameInit.compareToIgnoreCase("D") == 0){
					
				this.dealTheGame();			
			}
			else{
						
				gameOver = true;
			}	
		}
		
		//closing scanner
		sc.close();
		
	}
	
	
	
	private void dealTheGame(){
		
		boolean blackjack = false;
		float bet = 0 ;
		
		System.out.printf("\nBalance:$%.1f\n", balance);
		String msg = "Enter your bet for this game:";
		
		while(bet<=0){
			
			try{
				
				System.out.println("\n"+msg);
				bet = sc.nextFloat();
			}catch(InputMismatchException e){
				
				//System.err.println("Caught InputMismatchException: " +  e.getMessage());
				//throw e;
				sc.nextLine();
			}finally{
				
				msg = "Enter your bet in Integers (natural numbers) please:";
			}	
		}
		
		
		if((bet >= 1) && (bet%1 == 0) && (this.balance-bet>=0)){
			
			this.balance = this.balance - bet;
			
			// players start with empty hands
			you.emptyHand();
			dealer.emptyHand();
			
			this.youDone = false;
			this.dealerDone = false;
			
			// Distributing initial cards to players
			you.addCardToPlayersHand(newDeck.dealingNextCard());
			dealer.addCardToPlayersHand(newDeck.dealingNextCard());
			you.addCardToPlayersHand(newDeck.dealingNextCard());
			dealer.addCardToPlayersHand(newDeck.dealingNextCard());
			
			
			// Cards Dealt
			System.out.println("\n########## CARDS DEALT ##########\n");
			dealer.printCardsInHand(false);
			you.printCardsInHand(true);
			
			System.out.printf("Your Score:%d\t", you.getPlayersHandTotal());
			System.out.printf("Bet:$%.0f\t", bet);
			System.out.printf("Balance:$%.1f\n\n", balance);
			
			
		
			// checking state on initial card -- if BlackJack
			blackjack = this.checkIfBlackJack();
			
			
			
			// boolean splitDone = false;
			
			while(!youDone || !dealerDone){
			
				//
				if(!youDone){
					
					this.yourPlay();
					
				}
				else if(!dealerDone){
					
					this.dealersPlay();
				}
				
				System.out.println();
			}
			
			
			
			if(!blackjack){
				
				this.decideWinner();
					
			}	
		}
		else{
			
			System.out.println("\nYour bet amount is wrong, it should be a natural number and should not exceed your balance");
			System.out.printf("Your Balance:$%.1f\n\n", balance);
		}
		
	}
	
	private boolean checkIfBlackJack(){
		
		/*if(you.getPlayersHandTotal() > 21){
			
			//System.out.println("\tBusted!!\n");
			
			youDone = true;
			dealerDone = true;
		}*/
		boolean blackJack = false;
		
		if(you.getPlayersHandTotal() == 21){
			
			 youDone = true;
			 dealerDone = true;
			 
			 if(you.getPlayersHandTotal() > dealer.getPlayersHandTotal() || dealer.getPlayersHandTotal() > 21){
				 
				 System.out.println("\tHurray! BlackJack. you WON!!!");
				 dealer.printCardsInHand(true);
				 
				 System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()); 
				 System.out.printf("Your Bet was :$%.0f\t", bet);
				 System.out.printf("Your Balance was:$%.1f\n", balance);
				 System.out.printf("You win[3:2]:$%.1f\t", (3*bet)/2);
				 
				 balance = balance + (3*bet)/2 + bet;
				 System.out.printf("Your Current Balance:$%0.1f\n", balance);
				 
				 blackJack = true;
			 }
			 else{
				 
				 System.out.println("\tIt could have been a BlackJack for you...\n");
				 dealer.printCardsInHand(true);
				 
				 System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()); 
				 
				 blackJack = false;
				 /*System.out.println("!!!PUSH!!!");
				 
				 balance = balance + bet;
				 System.out.printf("Your Current Balance:$%.1f\n", balance);*/
			 }
		}
		
		return blackJack;
	}
	
	private void yourPlay(){
		
		String answer;
		boolean doubleDownAllowed = false;
		/*
		 * flags- Hit, Stand, Double, Split
		 */
		if(balance >= bet){
			
			doubleDownAllowed = true;
			System.out.print("Hit or Stay or Double Down? [Enter H or S or DD]");
		}
		else{
			
			doubleDownAllowed = false;
			System.out.print("Hit or Stay? [Enter H or S]");
		}
		
		answer = sc.next();
		System.out.println();
		
		if(answer.compareToIgnoreCase("H") == 0){
			
			this.hit();
		}
		else if(answer.compareToIgnoreCase("DD") == 0 && doubleDownAllowed){
			
			this.doubleDown();
		}
		else if(answer.compareToIgnoreCase("SS") == 0){
			
			//this.split();	
		}
		else{
			
			this.stay();
		}
	}
	
	private void hit(){
		
		System.out.println("\tYou Choose to Hit.\n");
		youDone = !you.addCardToPlayersHand(newDeck.dealingNextCard());
		you.printCardsInHand(true);
		System.out.printf("Your Score:%d\t", you.getPlayersHandTotal());
		System.out.printf("Bet:$%.0f\t", bet);
		System.out.printf("Balance:$%.1f\n\n", balance);
		
		if(you.getPlayersHandTotal()>21){
			
			System.out.println("\tYou BUSTED!!");
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			youDone = true;
			dealerDone = true;
		}
		
	}
	
	private void stay(){
		
		System.out.println("\tYou Choose to Stay, Dealer's turn \n");
		youDone = true;
	}
	
	private void doubleDown(){
		
		System.out.println("\tYou Choose to Double Down.\n");
		youDone = you.addCardToPlayersHand(newDeck.dealingNextCard());
		balance = balance - bet;
		bet = 2*bet;
		youDone = true;
		you.printCardsInHand(true);
		System.out.printf("Your Score:%d\t", you.getPlayersHandTotal());
		System.out.printf("Bet:$%.0f\t", bet);
		System.out.printf("Balance:$%.1f\n\n", balance);
		
		if(you.getPlayersHandTotal()>21){
			
			System.out.println("\tYou BUSTED!!");
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			youDone = true;
			dealerDone = true;
		}
		
		System.out.println("Now , Dealer's turn \n");
	}
	
	private void dealersPlay(){
		
		if(dealer.getPlayersHandTotal() < 17){
			
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			System.out.println("\tDealer Hits \n");
			dealerDone = !dealer.addCardToPlayersHand(newDeck.dealingNextCard());
			
			if(dealer.getPlayersHandTotal()>21){
				
				dealer.printCardsInHand(true);
				System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
				System.out.println("\tDealer BUSTED!!");
				dealerDone = true;
			}
		}
		else{
			
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			System.out.println("\tDealer Stays \n");
			dealerDone = true;
		}
	}
	
	/*private void split(){
		
		System.out.print(you.splitPossible());
		you.splitHands();
		you.addCardToPlayersHand(newDeck.dealingNextCard());
		// add card to split hand
		while(!splitDone){
			
			
			
			
		}
	}*/
	
	
	private void decideWinner(){
		
		int youSum = you.getPlayersHandTotal();
		int dealerSum = dealer.getPlayersHandTotal();
		
		if(youSum>dealerSum && youSum<=21 || dealerSum >21){
			
			System.out.println("\tYou WON!! \n");
			System.out.printf("Your Bet was :$%.0f\t", bet);
			System.out.printf("Your Balance was:$%.1f\n", balance);
			System.out.printf("You win[1:1] :$%.0f\t", bet);
			balance = balance + bet + bet;
			System.out.printf("Your Current Balance:$%.1f\n", balance);
			
		}
		else if(youSum == dealerSum){
			
			System.out.println("\t!!!PUSH!!!\n");
			balance = balance + bet;
			System.out.printf("Your Current Balance:$%.1f\n", balance);
		}
		else{
			
			System.out.println("\tYou LOST!! \n");
			System.out.printf("You lose[1:1]: $%.0f!!\n", bet);
			System.out.printf("Your Current Balance:$%.1f\n", balance);
		}
	}
	

	public static void main(String[] args) {
		
		new GameMain();
		
		//System.out.println("\n"+playerName+", game ended---> run again to play");

	}

}
