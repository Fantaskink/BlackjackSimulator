package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Game {
    private final Dealer dealer;
    private final Player player;
    private Shoe shoe;
    private int bet;

    public Game(int numberOfDecks, int chips) {
        this.dealer = new Dealer(numberOfDecks);
        this.player = new Player(chips);
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void startRound() {
        promptPlayerBet();

        dealPlayerMainHand();
        dealDealer();
        //dealPlayerMainHand();

        //Debugging
        Card card = player.getMainHand().getCards().get(0);
        player.getMainHand().getCards().add(card);
        player.getMainHand().updateValue();


        dealDealer();

        printHands();

        if(playerCanSplit()) { promptPlayerSplit(); }

    }

    public void continueRound() {
        checkForBust(); //TODO implement
        checkForBlackjack(); //TODO implement
    }

    public void endRound() {
        List<Card> cardsToAddToShoe = new ArrayList<>();
        cardsToAddToShoe.addAll(player.getMainHand().getCards());
        cardsToAddToShoe.addAll(player.getSplitHand().getCards());
        cardsToAddToShoe.addAll(dealer.getHand().getCards());

        dealer.addCardsToShoe(cardsToAddToShoe);

        dealer.getHand().clearHand();
        player.getMainHand().clearHand();
        player.getSplitHand().clearHand();
    }

    public void checkForBust() {
    }

    public void checkForBlackjack() {
        if (player.getMainHand().hasBlackjack()) {
            System.out.println("You have blackjack!");
            player.addChips(bet * 2);
            endRound();
        } else if (player.getSplitHand().hasBlackjack()) {
            System.out.println("You have blackjack!");
            player.addChips(bet * 2);
            endRound();
        } else if (dealer.getHand().hasBlackjack()) {
            System.out.println("Dealer has blackjack!");
            endRound();
        }

    }

    public Boolean playerCanSplit() {
        if (player.getChips() < bet) {
            System.out.println("You don't have enough chips to split!");
            return false;
        }
        // True if player's first and second card are the same rank
        return player.getMainHand().getCards().get(0).getRank().getValue() == player.getMainHand().getCards().get(1).getRank().getValue();
    }

    public void promptPlayerSplit() {
        Scanner scanner = new Scanner(System.in);
        printHands();
        System.out.println("Would you like to split? (y/n)");
        String input = "";

        if (scanner.hasNextLine()) {
            input = scanner.nextLine().toLowerCase();
        } else {
            System.out.println("No input found.");
            return;
        }

        if (input.equals("y")) {
            System.out.println("You chose yes!");
            player.splitHand();
            printHands();
        } else if (input.equals("n")) {
            System.out.println("You chose no!");
            printHands();

        } else {
            System.out.println("Invalid input!");
        }
        scanner.close();
    }
    public void promptPlayerBet() {
        Scanner scanner = new Scanner(System.in);
        printPlayerChips();
        System.out.println("How much would you like to bet?");
        int bet = scanner.nextInt();
        int playerChips = player.getChips();
        while (bet > playerChips) {
            System.out.println("You don't have enough chips to bet that much!");
            System.out.println("How much would you like to bet?");
            bet = scanner.nextInt();
        }
        player.removeChips(bet);
        scanner.nextLine();
        //scanner.close();
        setBet(bet);
    }

    public void dealPlayerMainHand() {
        player.addCardToMainHand(dealer.drawCardFromShoe());
        player.getMainHand().updateValue();
    }

    public void dealPlayerSplitHand() {
        player.addCardToSplitHand(dealer.drawCardFromShoe());
        player.getSplitHand().updateValue();
    }

    public void dealDealer() {
        dealer.addCardToHand(dealer.drawCardFromShoe());
        dealer.getHand().updateValue();
    }

    public void printHands() {
        System.out.println("\n*---------------------------------------------------*\n");
        printPlayerChips();
        if (player.getHasSplit()) {
            System.out.println("Your hands:");
            player.getMainHand().printHand();
            System.out.println("Your hand value: " + player.getMainHandValue());
            player.getSplitHand().printHand();
            System.out.println("Your hand value: " + player.getSplitHandValue() + "\n");
        } else {
            System.out.println("Your hand:");
            player.getMainHand().printHand();
            System.out.println("Your hand value: " + player.getMainHandValue() + "\n");
        }

        System.out.println("Dealer's hand:");

        if (dealer.getHand().getCards().size() == 2) {
            dealer.getHand().printHandWithHiddenCard();
        } else {
            dealer.getHand().printHand();
            System.out.println("Dealer's hand value: " + dealer.getHandValue() + "\n");
        }
    }
    public void printPlayerChips() {
        System.out.println("You have " + player.getChips() + " chips.");
    }
}
