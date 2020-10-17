package blackjack;

import java.util.*;

public class Blackjack implements BlackjackEngine {
	
	//initialized int variables
	private int playerAccount;
	private int bet;
	private int numberOfDecks;
	private int gameStatus;
	
	//initialized array lists
	private ArrayList<Card> ogGameDeck;
	private ArrayList<Card> playerHand;
	private ArrayList<Card> dealerHand;
	
	//random generator
	private Random randomGenerator;

	/**
	 * Constructor you must provide. Initializes the player's account to 200 and the
	 * initial bet to 5. Feel free to initialize any other fields. Keep in mind that
	 * the constructor does not define the deck(s) of cards.
	 * 
	 * @param randomGenerator
	 * @param numberOfDecks
	 */
	
	public Blackjack(Random randomGenerator, int numberOfDecks) {

		this.playerAccount = 200;
		this.bet = 5;
		this.numberOfDecks = numberOfDecks;
		this.randomGenerator = randomGenerator;

	}

	public int getNumberOfDecks() {
		return this.numberOfDecks;
	}

	public void createAndShuffleGameDeck() {

		int sizeOfDeck = this.numberOfDecks;
		
		
		ogGameDeck = new ArrayList<Card>(sizeOfDeck); // Initialized with the size of the deck.

		for (int i = 0; i < sizeOfDeck; i++) {
			for (CardSuit suit : CardSuit.values()) { //Tterate through items of arrays/collections.
				
				for (CardValue value : CardValue.values()) { 
					
					Card newCard = new Card(value, suit);
					
						ogGameDeck.add(newCard);
				}
			}
		}
		
		Collections.shuffle(this.ogGameDeck, this.randomGenerator);

	}

	public Card[] getGameDeck() {
		
		int sizeOfDeck = this.ogGameDeck.size();
		
		Card[] gameDeckArray = new Card[sizeOfDeck];
		
		Card [] finalGameDeck = this.ogGameDeck.toArray(gameDeckArray);  //Used to copy a Stack to a new array.
		
		return finalGameDeck; 
	}

	public void deal() {
		
		createAndShuffleGameDeck(); 
		
		this.playerHand = new ArrayList<Card>();
		
		this.dealerHand = new ArrayList<Card>();
		
		this.playerHand.add(this.ogGameDeck.get(0));
		
		this.ogGameDeck.remove(0);

		Card x = this.ogGameDeck.get(0);
		
		this.ogGameDeck.remove(0);
		
		x.setFaceDown();
		
		this.dealerHand.add(x);

		this.playerHand.add(this.ogGameDeck.get(0));
		
		this.ogGameDeck.remove(0);
		
		this.dealerHand.add(this.ogGameDeck.get(0));
		
		this.ogGameDeck.remove(0);
		
		int newAmount = this.playerAccount - this.bet;
		
		this.setAccountAmount(newAmount);
		
		this.gameStatus = BlackjackEngine.GAME_IN_PROGRESS;
	}

	public Card[] getDealerCards() {
		
		Card [] dealerCards = cardsFromDeck(this.dealerHand);
		
		return dealerCards;
	}

	public int[] getDealerCardsTotal() {
		
		int [] dealerCardsTotal = cardHand(this.dealerHand);
		
		return dealerCardsTotal;

	}

	public int getDealerCardsEvaluation() {
		if(this.dealerHand == null) {
			
			return BlackjackEngine.BUST;
		}
		
		return cardResults(getDealerCardsTotal(), this.dealerHand);

	}

	public Card[] getPlayerCards() {
		
		Card [] playerCards = cardsFromDeck(this.playerHand);
		
		return playerCards;
	}

	public int[] getPlayerCardsTotal() {
		
		int [] playerCardsTotal = cardHand(this.playerHand);
		
		return playerCardsTotal;
		
	}

	public int getPlayerCardsEvaluation() {
		
		if(this.playerHand == null) {
			return BlackjackEngine.BUST;
		}
		
		return cardResults(getPlayerCardsTotal(), this.playerHand);
		
	}

	public void playerHit() {
		this.playerHand.add(this.ogGameDeck.remove(0));
		
		int playerCards = getPlayerCardsEvaluation();
		
		if (isGameOver(playerCards)){ 
			this.gameStatus = BlackjackEngine.DEALER_WON;
			
		} else {
			this.gameStatus = BlackjackEngine.GAME_IN_PROGRESS;
		}
	}

	public void playerStand() {
		
		int betMultiplied = (this.bet * 2);

		this.dealerHand.get(0).setFaceUp();
		
		int[] dealerNum = getDealerCardsTotal();
		
		if (!(ifNull(dealerNum) || dealerNum [dealerNum.length - 1] >= 16)) {
			Card card = this.ogGameDeck.remove(0);
			
			this.dealerHand.add(card);
		}


		int[] playerNum = getPlayerCardsTotal();
		
		if (ifNull(dealerNum)) {
			
			this.gameStatus = BlackjackEngine.PLAYER_WON;
			
		} else if (ifNull(playerNum)) {
			
			this.gameStatus = BlackjackEngine.DEALER_WON;
			
		} else if (dealerNum.length == 2 && playerNum.length == 2) {
			
			if (doesDealerWin(dealerNum[1],playerNum[1])) {
				
				this.gameStatus = BlackjackEngine.DEALER_WON;
				
			} else if (!(doesDealerWin(dealerNum[1],playerNum[1]))) {
				
				this.gameStatus = BlackjackEngine.PLAYER_WON;
								
				this.playerAccount += betMultiplied;
				
			} else {
				
				this.gameStatus = BlackjackEngine.DRAW;
				
				this.playerAccount += this.bet;
			}
			
		} else if (dealerNum.length == 2 && playerNum.length == 1) {
			
			if (doesDealerWin(dealerNum[1],playerNum[0])) {
				
				this.gameStatus = BlackjackEngine.DEALER_WON;
				
			} else if (!(doesDealerWin(dealerNum[1],playerNum[0]))) {
				
				this.gameStatus = BlackjackEngine.PLAYER_WON;
				
				this.playerAccount += betMultiplied;
				
			} else {
				
				this.gameStatus = BlackjackEngine.DRAW;
				
				this.playerAccount += this.bet;
			}
			
		} else if (dealerNum.length == 1 && playerNum.length == 2) {
			
			if (doesDealerWin(dealerNum[0],playerNum[1])) {
				
				this.gameStatus = BlackjackEngine.DEALER_WON;
				
			} else if (!(doesDealerWin(dealerNum[0],playerNum[1]))) {
				
				this.gameStatus = BlackjackEngine.PLAYER_WON;
				
				this.playerAccount += betMultiplied;
				
			} else {
				
				this.gameStatus = BlackjackEngine.DRAW;
				
				this.playerAccount += this.bet;
			}
			
		} else if (dealerNum.length == 1 && playerNum.length == 1) {
			
			if (doesDealerWin(dealerNum[0],playerNum[0])) {
				
				this.gameStatus = BlackjackEngine.DEALER_WON;
				
			} else if (!(doesDealerWin(dealerNum[0],playerNum[0]))) {
				
				this.gameStatus = BlackjackEngine.PLAYER_WON;
				
				this.playerAccount += betMultiplied;
				
			} else {
				
				this.gameStatus = BlackjackEngine.DRAW;
				
				this.playerAccount += this.bet;
			}
		}
	}

	public int getGameStatus() {
		return this.gameStatus;

	}

	public void setBetAmount(int amount) {
		this.bet = amount;

	}

	public int getBetAmount() {
		return this.bet;

	}

	public void setAccountAmount(int amount) {
		this.playerAccount = amount; //setter

	}

	public int getAccountAmount() {
		return this.playerAccount; //getter

	}

	/* Feel Free to add any private methods you might need */
	
	//private method that returns true if dealer won.
	
		private boolean isGameOver(int x) {
			
		if(x == BlackjackEngine.BUST) {
			return true;
			}
		return false;
		}
		
		//Private method that returns true if the dealer wins. 
		
		private boolean doesDealerWin(int d, int p) {
			if (d > p) {
				return true;
			}	
			
			return false;
		}
		//Private method that takes in a array of type int and returns true if array is null.
		
			
			private boolean ifNull (int [] x) {
				
				return(x == null || x.length == 0);
				
			}
			
		//Private method that returns true if there is an ace found in an array list of cards. 
			
		private boolean isThereAce(ArrayList<Card> x) {
			
			boolean thereIsAce = false;
			
			int length = x.size();
			
			for (int index = 0; index < length; index++) {
				
				if (x.get(index).getValue() == CardValue.Ace) {
					thereIsAce = true;
				}
			}
			
			return thereIsAce;
			
		}
		
	//Private method that returns the state of the game depending on the hand of cards inputed.
		
	private int cardResults(int[] handNum, ArrayList<Card> handList) {
		
		int length = handList.size();
		
		if (ifNull(handNum)) {
			
			return BlackjackEngine.BUST;
		}
			
		if (handNum.length == 2) {
			
			if (handNum[1] == 21) 
				
				if(!(length == 0)) {
					
					if (length > 2) {
					
						return BlackjackEngine.HAS_21;
					
					} else {
					
						return BlackjackEngine.BLACKJACK;
					}
				}
			
		} else {
			
			if (handNum[0] == 21) {
				
				if(!(length == 0)) {
			
					if (length > 2 ) {
					
						return BlackjackEngine.HAS_21;
					
				} else {
					
					return BlackjackEngine.BLACKJACK;
					
					}
				}
			}
		}
		return BlackjackEngine.LESS_THAN_21;
	}


	//This private method returns array of type Card with an input of cards.
	private Card[] cardsFromDeck(ArrayList<Card> cards) {
		
		int length = cards.size();
		
		Card[] handOfCards = new Card[length];
		
		Card [] ans = cards.toArray(handOfCards); //Used to copy a Stack to a new array.
		
		return ans;
	}
	
	//This private method counts the number of aces in each hand.
	private int[] cardHand(ArrayList<Card> hand) {
		
		
		int totalWithNoAces = 0;
		int totalWithAceIsOne = 0;
		int totalWithAceIsEleven = 0;
		int handLength = hand.size();
		
		
		for (int i = 0; i < handLength; i++) {
			
			 totalWithAceIsOne += hand.get(i).getValue().getIntValue();
		}
		
		for (int index = 0; index < handLength; index++) {
			
			totalWithNoAces += hand.get(index).getValue().getIntValue();
		}
		
		if (isThereAce(hand)) // Use of private method to determine if there is an ace in hand.
			
			{
			
			totalWithAceIsEleven = totalWithAceIsOne + 10;
			
			if (isLessThan21(totalWithAceIsOne)) {
				return null;
				
			} else if (!(isLessThan21(totalWithAceIsOne)) && (isLessThan21(totalWithAceIsEleven))) {
				
				int[] oneAce = { totalWithAceIsOne };
				return oneAce;
				
			} else {
				
				int[] elevenAce = { totalWithAceIsOne, totalWithAceIsEleven };
				return elevenAce;
			}
			
		} else {
			
			if (isLessThan21(totalWithNoAces)) {
				return null;
			}
			
			int[] noAce = { totalWithNoAces };
			return noAce;
		}
	}
	
	//Private method that return true if int is less than 21. 
	private boolean isLessThan21 (int num) {
		if(num > 21) {
			return true;
		}
		
		return false;
	}
	
}
