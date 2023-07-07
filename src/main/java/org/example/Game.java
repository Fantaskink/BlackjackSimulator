package org.example;

import java.util.Scanner;

public class Game {
    private Dealer dealer;
    private Player player;

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

        dealPlayer();
        dealDealer();
        dealPlayer();
        dealDealer();

        printHands();

        checkIfPlayerCanSplit();
    }

    public void continueRound() {
        dealPlayer();
        printHands();
    }

    public Boolean checkIfPlayerCanSplit() {
        if (player.getHand().getCards().get(0).getRank() == player.getHand().getCards().get(1).getRank()) {
            System.out.println("Would you like to split? (y/n)");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            scanner.close();
            if (answer.equals("y") || answer.equals("Y")) {
                return true;
            }
        }
        return false;
    }
    public void promptPlayerBet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How much would you like to bet?");
        int bet = scanner.nextInt();
        scanner.close();
        setBet(bet);
    }

    public void dealPlayer() {
        player.addCardToHand(dealer.drawCardFromShoe());
    }

    public void dealDealer() {
        dealer.addCardToHand(dealer.drawCardFromShoe());
    }

    public void printHands() {
        System.out.println("Your hand:");
        player.getHand().printHand();
        System.out.println("Your hand value: " + player.getHandValue());

        System.out.println("Dealer's hand:");
        dealer.getHand().printHand();
    }
}
