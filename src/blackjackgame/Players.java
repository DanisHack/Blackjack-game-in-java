package blackjackgame;

public class Players {

	private String playerName;
	private Cards[] playerHand = new Cards[10];
	private int numCardsInHand;
	
	
	public Players(String name){
		
		this.playerName = name;
		
		this.emptyHand();
	}
	
	public void emptyHand(){
		
		for(int hc=0; hc<10;hc++){
			this.playerHand[hc] = null;
		}
		
		this.numCardsInHand = 0;
		
	}
	
	public boolean addCardToPlayersHand(Cards card){
		
		if(this.numCardsInHand == 10){
			System.err.printf("%s's hand already has 10 cards; cannot add more cards", this.playerName);
			System.exit(1);
		}
		
		this.playerHand[this.numCardsInHand] = card;
		this.numCardsInHand++;
		
		return (this.getPlayersHandTotal() <=21);
		
	}
	
	public int getPlayersHandTotal(){
		
		int handTotal = 0;
		int cardNum;
		int numAces = 0;
		
		for(int c =0; c<this.numCardsInHand;c++){
			
			cardNum = this.playerHand[c].getCardNumber();
			
			if(cardNum == 1){ // Ace
				numAces++;
				handTotal += 11;
			}
			else if(cardNum >= 10){
				handTotal += 10;
			}
			else{
				handTotal += cardNum;
			}
		}
		
		while(handTotal > 21 && numAces > 0){
			handTotal -= 10;
			numAces--;
		}
		
		return handTotal;
	}

	public void printCardsInHand(boolean showFirstCard){
		
		System.out.printf("%s's cards in hand\n\n", this.playerName);
		for(int c=0; c<this.numCardsInHand;c++){
			
			if(!showFirstCard && c==0){
				System.out.printf("\t[hidden]\n");
			}
			else{
				System.out.printf("\t%s\n", this.playerHand[c]);
			}
			
		}
	}
	
	
}
