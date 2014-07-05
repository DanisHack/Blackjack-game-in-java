package blackjackgame;

import java.util.Random ;
/**
 * Deck of cards-- forming a dynamic deck
 * 1 Pack = 52 cards;
 * Deck = one or more packs;
 * @author DANISH MOHD
 *
 */
public class Deck {
	
	private Cards[] cardsInDeck;
	private int numOfCardsInDeck;
	private int onePack = 52;
	
	/**
	 * constructor for default shuffled deck consisting of 1 pack i.e 52 cards
	 */
	
	public Deck(){
		this(1, true);
	}
	
	/**
	 * 
	 * Deck constructor to define number of cards in a Deck && whether it should be shuffled or not
	 * 
	 * @param numPacks
	 * @param shuffle
	 */
	public Deck(int numPacks, boolean shuffle){
		
		this.numOfCardsInDeck = numPacks*this.onePack;
		this.cardsInDeck = new Cards[this.numOfCardsInDeck];
		
		int c = 0;
		
		// for each deck
		for(int d=0;d<numPacks;d++){
			
			// for each suit
			for(int s=0; s<4;s++){
				
				// for each number
				for(int n=1;n<=13;n++){
					this.cardsInDeck[c] = new Cards(Suits.values()[s], n);
					c++;
				}
			}
		}
		
		//shuffle
		if(shuffle){
			this.shuffle();
		}
	}
	
	public boolean shuffle(){
		
		
	}
}
