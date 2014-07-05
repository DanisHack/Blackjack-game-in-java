package blackjackgame;

/**
 * 
 * Card type
 * 
 * @author DANISH MOHD
 *
 */

public class Cards {
	
	private Suits cardSuit;
	private int cardNum;
	
	
	/**
	 * Cards constructor
	 * @param sType
	 * @param sNum
	 */
	public Cards(Suits sType, int sNum){
		 
		this.cardSuit = sType;
		this.cardNum  = sNum;
	}
	
	public int getCardNumber(){
		return this.cardNum;
	}
	
	
	
	
}
