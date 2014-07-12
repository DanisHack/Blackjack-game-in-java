Blackjack-game-in-java
======================

Text based Blackjack game in Java.



How To Run:
===========

Prequisite: JDK1.7

Download jar file from the link: https://www.dropbox.com/s/720k3ou22s9puj4/BlackJack.jar

use command: java -jar /path-to-jar-file/BlackJack.jar


Test Cases:
===========

1.	Bet amount should not be greater than balance (i.e 100 or whatever (winning condition)).
2.	Bet amount should be a natural number (>0).
3.	If balance is 0, game should end.
4.	Check every inputMismatch exception.
5.	After bet has been placed, balance = balance – bet.
6.	Check  balance>=bet,  for showing Double down option.
7.	Double down option should not be shown,  after first Hit during a Deal.
8.	At every point,  bet + balance = initial balance before dealing a hand.
9.	BlackJack  or Natural 21 is calculated for two card hand, every Deal.


Note:
=====

•	A four deck shoe is used. 
•	Each chip is $1.
•	The player may double down on any initial two card hand. 



