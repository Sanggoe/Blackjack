package com.sangmin.blackjack.Control;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.sangmin.blackjack.Construction.Card;
import com.sangmin.blackjack.Construction.CardDeck;
import com.sangmin.blackjack.Construction.Dealer;
import com.sangmin.blackjack.Construction.Gamer;
import com.sangmin.blackjack.Construction.Player;
import com.sangmin.blackjack.Construction.Rule;

public class Game {
	private static final int INIT_RECEIVE_CARD_COUNT = 2;
	private static final String STOP_RECEIVE_CARD = "0";
	
	public void play() {
		System.out.println("===== Blackjack =====");
		Scanner sc = new Scanner(System.in);
		Rule rule = new Rule();
		CardDeck cardDeck = new CardDeck();
		
		List<Player> players = Arrays.asList(new Gamer("User1"), new Dealer()); 
		List<Player> initAfterPlayers = initPhase(cardDeck, players);
		List<Player> playingAfterPlayers = playingPhase(sc, cardDeck, initAfterPlayers);
		
		Player winner = rule.getWinner(playingAfterPlayers);
		System.out.println("���ڴ� " + winner.getName());
	}
	
	private List<Player> playingPhase(Scanner sc, CardDeck cardDeck, List<Player> players) {
		List<Player> cardReceivedPlayers;
		while(true) {
			cardReceivedPlayers = receiveCardAllPlayers(sc, cardDeck, players);
			
			if(isAllPlayerTurnOff(cardReceivedPlayers)) {
				break;
			}
		}
		return cardReceivedPlayers;
	}
	
	private List<Player> receiveCardAllPlayers(Scanner sc, CardDeck cardDeck, List<Player> players) {
		for(Player player : players) {
			System.out.println(player.getName() + "�� �����Դϴ�.");
			
			if(isReceiveCard(sc)) {
				Card card = cardDeck.draw();
				player.receiveCard(card);
				player.turnOn();
			}else {
				player.turnOff();
			}
		}
		
		return players;
	}
	
	private boolean isAllPlayerTurnOff(List<Player> players) {
		for(Player player : players) {
			if(player.isTurn()) {
				return false;
			}
		}
		return true;
	}	

	private boolean isReceiveCard(Scanner sc) {
		System.out.println("ī�带 �̰ڽ��ϱ�? (����:0)");
		return !STOP_RECEIVE_CARD.equals(sc.nextLine());
	}

	
	private List<Player> initPhase(CardDeck cardDeck, List<Player> players) {
		System.out.println("ó�� 2���� ī�带 ���� �̰ڽ��ϴ�.\n");
		
		for(int i=0; i<INIT_RECEIVE_CARD_COUNT; i++) {
			for(Player player : players) {
				System.out.println(player.getName() + "�� �����Դϴ�.");
				Card card = cardDeck.draw ();
				player.receiveCard(card);
			}
		}
		
		return players;
	}
}
