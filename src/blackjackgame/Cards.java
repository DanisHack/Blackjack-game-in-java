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
	private String[] numString = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	
	/**
	 * Cards constructor
	 * @param sType
	 * @param sNum
	 */
	public Cards(Suits stype, int snum){
		 
		this.cardSuit = stype;
		
		if(snum >=1 && snum <= 13)
			this.cardNum  = snum;
		else{
			
			System.err.println(snum+" is not a valid card number\n");
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
