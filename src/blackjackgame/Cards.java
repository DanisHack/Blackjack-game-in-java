package blackjackgame;

/**
 * 
 * Cards suit and number
 * 
 * @author DANISH MOHD
 *
 */

public class Cards {
	
	private Suits cardSuit;
	private int cardNum;
	private String[] numString = {"Ace", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Jack", "Queen", "King"};
	
	/**
	 * Cards constructor
	 * @param sType
	 * @param sNum
	 */
	public Cards(Suits type, int num){
		 
		this.cardSuit = type;
		
		if(num >=1 && num <= 13)
			this.cardNum  = num;
		else{
			System.err.println(num+" is not a valid card number\n");
			System.exit(1);
		}
	}
	
	public int getCardNumber(){
		return this.cardNum;
	}
	
	public String toString(){
		
		return this.numString[this.cardNum - 1]+" of "+this.cardSuit.toString();
	}
	
	
	
}
