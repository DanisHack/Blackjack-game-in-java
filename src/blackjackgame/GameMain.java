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
	private boolean doubleDownAllowed;
	
	
	GameMain(String pName){
		
		this.balance = 100;
		this.newDeck = new Deck(4, true);
		boolean gameOver = false;
		this.playerName  = pName;
		
		System.out.println("######################################################################################");
		System.out.println("# Congratulations!! "+this.playerName+", you have got 100 complimentary chips for playing. Enjoy! #");
		System.out.println("######################################################################################");
		// Players init
		you = new Players(this.playerName);
		dealer = new Players("Dealer");
		
		
		// Game Starts here --->
		while(this.balance > 0 &&  !gameOver){
					
			System.out.println("\n"+this.playerName+", Do you want to DEAL or END the game [D or E]??");
			String gameInit = sc.next();
					
			if(gameInit.compareToIgnoreCase("D") == 0){
					
				this.dealTheGame();			
			}
			else{
						
				gameOver = true;
			}	
		}
		
		System.out.println("\n"+this.playerName+", !!!! Game Ended !!!");
		
		// To play again
		System.out.println("\n"+this.playerName+", Do you want to play again [Y or N]");
		String Y = sc.next();
		if(Y.compareToIgnoreCase("Y") == 0){
			
			new GameMain(this.playerName);
		}
		
		//closing scanner
		sc.close();
		
	}
	
	// Deal the game
	private void dealTheGame(){
		
		boolean blackjack = false;
		this.bet = 0 ;
		this.doubleDownAllowed = true;
		
		System.out.printf("\nBalance:$%.1f\n", this.balance);
		String msg = "Enter your bet for this game:";
		
		while(this.bet<=0){
			
			try{
				
				System.out.println("\n"+msg);
				this.bet = sc.nextFloat();
			}catch(InputMismatchException e){
				
				//System.err.println("Caught InputMismatchException: " +  e.getMessage());
				//throw e;
				sc.nextLine();
			}finally{
				
				msg = "Enter your bet in Integers (natural numbers) please:";
			}	
		}
		
		
		if((this.bet >= 1) && (this.bet%1 == 0) && (this.balance-this.bet>=0)){
			
			this.balance = this.balance - this.bet;
			
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
			System.out.printf("Bet:$%.0f\t", this.bet);
			System.out.printf("Balance:$%.1f\n\n", this.balance);
			
			// checking state on initial card -- if BlackJack
			blackjack = this.checkIfBlackJack();
			
			while(!youDone || !dealerDone){
			
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
			System.out.printf("Your Balance:$%.1f\n\n", this.balance);
		}
		
	}
	
	// Natural 21 check on initial cards
	private boolean checkIfBlackJack(){
		
		boolean blackJack = false;
		
		if(you.getPlayersHandTotal() == 21){
			
			 youDone = true;
			 dealerDone = true;
			 
			 if(you.getPlayersHandTotal() > dealer.getPlayersHandTotal() || dealer.getPlayersHandTotal() > 21){
				 
				 System.out.println("\t\t\t\t#################################");
				 System.out.println("\t\t\t\t#                               #");
				 System.out.println("\t\t\t\t# HURRAY!!...BLACKJACK, YOU WON #");
				 System.out.println("\t\t\t\t#                               #");
				 System.out.println("\t\t\t\t#################################\n");
				 dealer.printCardsInHand(true);
				 
				 System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()); 
				 System.out.printf("Your Bet was :$%.0f\t", this.bet);
				 System.out.printf("Your Balance was:$%.1f\n", this.balance);
				 System.out.printf("You win[3:2]:$%.1f\t", (3*this.bet)/2);
				 
				 this.balance = this.balance + (3*this.bet)/2 + this.bet;
				 System.out.printf("Your Current Balance:$%0.1f\n", this.balance);
				 
				 blackJack = true;
			 }
			 else{
				 
				 System.out.println("\tIt could have been a BlackJack for you...\n");
				 dealer.printCardsInHand(true);
				 
				 System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()); 
				 
				 blackJack = false;
			 }
		}
		
		return blackJack;
	}
	
	// Player's Play Turn
	private void yourPlay(){
		
		String answer;
		/*
		 * flags- Hit, Stand, Double, Split
		 * ---------------------------------
		 */
		
		if(this.balance >= this.bet && this.doubleDownAllowed){
			
			System.out.print("Hit or Stay or Double Down? [Enter H or S or DD]");
		}
		else{
			
			this.doubleDownAllowed = false;
			System.out.print("Hit or Stay? [Enter H or S(or press any letter to Stay)]");
		}
		
		answer = sc.next();
		System.out.println();
		
		if(answer.compareToIgnoreCase("H") == 0){
			
			this.hit();
			this.doubleDownAllowed = false;
		}
		else if(answer.compareToIgnoreCase("DD") == 0 && this.doubleDownAllowed){
			
			this.doubleDown();
		}
		else if(answer.compareToIgnoreCase("SS") == 0){
			
			//this.split();	
			this.doubleDownAllowed = false;
		}
		else{
			
			this.stay();
		}
	}
	
	// Player's Hit
	private void hit(){
		
		System.out.println("\tYou Choose to Hit.\n");
		youDone = !you.addCardToPlayersHand(newDeck.dealingNextCard());
		you.printCardsInHand(true);
		System.out.printf("Your Score:%d\t", you.getPlayersHandTotal());
		System.out.printf("Bet:$%.0f\t", this.bet);
		System.out.printf("Balance:$%.1f\n\n", this.balance);
		
		if(you.getPlayersHandTotal()>21){
			
			System.out.println("\t\t\t\t##############");
			System.out.println("\t\t\t\t#            #");
			System.out.println("\t\t\t\t# YOU BUSTED #");
			System.out.println("\t\t\t\t#            #");
			System.out.println("\t\t\t\t##############\n");
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			youDone = true;
			dealerDone = true;
		}
		
	}
	
	// Player's Stay
	private void stay(){
		
		System.out.println("\tYou Choose to Stay, Dealer's turn \n");
		youDone = true;
	}
	
	// Player's Double Down
	private void doubleDown(){
		
		System.out.println("\tYou Choose to Double Down.\n");
		
		youDone = you.addCardToPlayersHand(newDeck.dealingNextCard());
		this.balance = this.balance - this.bet;
		this.bet = 2*this.bet;
		youDone = true;
		you.printCardsInHand(true);
		
		System.out.printf("Your Score:%d\t", you.getPlayersHandTotal());
		System.out.printf("Bet:$%.0f\t", this.bet);
		System.out.printf("Balance:$%.1f\n\n", this.balance);
		
		if(you.getPlayersHandTotal()>21){
			
			System.out.println("\t\t\t\t##############");
			System.out.println("\t\t\t\t#            #");
			System.out.println("\t\t\t\t# YOU BUSTED #");
			System.out.println("\t\t\t\t#            #");
			System.out.println("\t\t\t\t##############\n");
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			youDone = true;
			dealerDone = true;
		}
		
		System.out.println("Now , Dealer's turn \n");
	}
	
	// Dealer's Play Turn
	private void dealersPlay(){
		
		if(dealer.getPlayersHandTotal() < 17){
			
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			System.out.println("\tDealer Hits \n");
			dealerDone = !dealer.addCardToPlayersHand(newDeck.dealingNextCard());
			
			if(dealer.getPlayersHandTotal()>21){
				
				dealer.printCardsInHand(true);
				System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
				System.out.println("\t\t\t\t#################");
				System.out.println("\t\t\t\t#               #");
				System.out.println("\t\t\t\t# DEALER BUSTED #");
				System.out.println("\t\t\t\t#               #");
				System.out.println("\t\t\t\t#################\n");
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
	
	// Deciding a Winner
	private void decideWinner(){
		
		int youSum = you.getPlayersHandTotal();
		int dealerSum = dealer.getPlayersHandTotal();
		
		if(youSum>dealerSum && youSum<=21 || dealerSum >21){
			
			System.out.println("\t\t\t\t############");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t# YOU WON  #");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t############\n");
			System.out.printf("Your Bet was :$%.0f\t", this.bet);
			System.out.printf("Your Balance was:$%.1f\n", this.balance);
			System.out.printf("You win[1:1] :$%.0f\t", this.bet);
			
			this.balance = this.balance + this.bet + this.bet;
			System.out.printf("Your Current Balance:$%.1f\n", balance);
			
		}
		else if(youSum == dealerSum){
			
			System.out.println("\t\t\t\t############");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t#   PUSH   #");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t############\n");
			this.balance = this.balance + this.bet;
			System.out.printf("Your Current Balance:$%.1f\n", this.balance);
		}
		else if(dealerSum == 21){
			
			System.out.println("\t\t\t\t#################################");
			System.out.println("\t\t\t\t#                               #");
			System.out.println("\t\t\t\t# DEALER's BLACKJACK, YOU LOST  #");
			System.out.println("\t\t\t\t#                               #");
			System.out.println("\t\t\t\t#################################\n");
				 
			System.out.printf("You lose[1:1]: $%.0f!!\n", this.bet);
			System.out.printf("Your Current Balance:$%.1f\n", this.balance);
		}
		else{
			
			System.out.println("\t\t\t\t############");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t# YOU LOST #");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t############\n");
			System.out.printf("You lose[1:1]: $%.0f!!\n", this.bet);
			System.out.printf("Your Current Balance:$%.1f\n", this.balance);
		}
	}
	

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String playerName;
		
		System.out.println("\n\t\t\t\t#######################################");
		System.out.println("\t\t\t\t#                                     #");
		System.out.println("\t\t\t\t#           BLACKJACK 0.1             #");
		System.out.println("\t\t\t\t# Insight Fellowship Coding Challenge #");
		System.out.println("\t\t\t\t#                                     #");
		System.out.println("\t\t\t\t#######################################\n");
	
		System.out.println("Enter Your Name:\n");
		playerName = scanner.nextLine();
		
		new GameMain(playerName);
		
		scanner.close();
	}

}
