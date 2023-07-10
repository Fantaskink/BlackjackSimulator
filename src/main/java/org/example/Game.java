package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Dealer dealer;
    private final Player player;
    private Hand activeHand;
    private int bet;

    public Hand getActiveHand() {
        return activeHand;
    }

    public void setActiveHand(Hand activeHand) {
        this.activeHand = activeHand;
    }

    public Game(int numberOfDecks, int chips) {
        this.dealer = new Dealer(numberOfDecks);
        this.player = new Player(chips);
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void startRound() {
        setActiveHand(player.getMainHand());
        player.setHasSplit(false);
        promptPlayerBet();

        dealPlayerActiveHand();
        dealDealer();
        //dealPlayerMainHand();

        //Debugging
        Card card = player.getMainHand().getCards().get(0);
        player.getMainHand().getCards().add(card);
        player.getMainHand().updateValue();

        dealDealer();

        checkForBlackjack();

        printHands();
        promptNextPlayerAction();
    }

    public void continueRound() {
        printHands();

        if (player.getMainHand().getStanded() && player.getSplitHand().getStanded()) {
            dealerTurn();
        }

        if (getActiveHand().getValue() == 21) {getActiveHand().setStanded(true);}

        checkForBlackjack();
        checkForBust();

        if (!getActiveHand().getStanded()) {
            promptNextPlayerAction();
        } else {dealerTurn();}

    }

    public void endRound() {
        List<Card> cardsToAddToShoe = new ArrayList<>();
        cardsToAddToShoe.addAll(player.getMainHand().getCards());

        if (player.getHasSplit()) { cardsToAddToShoe.addAll(player.getSplitHand().getCards()); }

        cardsToAddToShoe.addAll(dealer.getHand().getCards());

        dealer.addCardsToShoe(cardsToAddToShoe);

        dealer.getHand().clearHand();
        player.getMainHand().clearHand();
        player.getSplitHand().clearHand();
    }

    public void dealerTurn(){
        if (dealer.getHand().hasSoftSeventeen()) {dealer.getHand().printHand();}
        while (!dealer.getHand().hasSoftSeventeen() && dealer.getHandValue() < 17) {
            dealDealer();
            printHands();
        }
        compareHands();
    }

    public void compareHands() {
        if (dealer.getHandValue() > 21) {
            System.out.println("Dealer has busted");
        }

        if (player.getMainHand().getValue() > dealer.getHandValue()) {
            player.addChips(bet*2);
        }

        if (player.getMainHand().getValue() == dealer.getHandValue()) {
            player.addChips(bet);
        }

        if (player.getHasSplit()) {
            if (player.getSplitHand().getValue() > dealer.getHandValue()) {
                player.addChips(bet*2);
            }

            if (player.getSplitHand().getValue() == dealer.getHandValue()) {
                player.addChips(bet);
            }
        }
    }

    public void checkForBust() {
        if (player.getHasSplit()) {
            if (player.getMainHand().hasBust() && player.getSplitHand().hasBust()) {
                System.out.println("Both hands have busted");
                dealerTurn();
            } else if (player.getMainHand().hasBust()) {
                System.out.println("Main hand has busted");
                setActiveHand(player.getSplitHand());
                continueRound();
            } else if (player.getSplitHand().hasBust()) {
                System.out.println("Split hand has busted");
                dealerTurn();
            }
        } else {
            if (player.getMainHand().hasBust()) {
                System.out.println("Main hand has busted");
                dealerTurn();
            }
        }
    }

    public void promptNextPlayerAction() {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("Would you like to:\n");
            System.out.println("Hit (h)");
            System.out.println("Stand (s)");

            if (playerCanSplit()) {
                System.out.println("Split (sp)");
            }
            if (playerCanDoubleDown()) {
                System.out.println("Double Down (d)");
            }

            input = scanner.nextLine();

            if (input.equals("h")) {
                hit();
                break;
            } else if (input.equals("s")) {
                stand();
                break;
            } else if (input.equals("sp") && playerCanSplit()) {
                split();
                break;
            } else if (input.equals("d") && playerCanDoubleDown()) {
                doubleDown();
                break;
            } else {
                System.out.println("Invalid input");
            }
        } while (true);

        scanner.close();
    }

    public void hit() {
        dealPlayerActiveHand();
        continueRound();
    }


    public void stand() {
        getActiveHand().setStanded(true);

        if (player.getHasSplit()) {
            if (getActiveHand().equals(player.getMainHand())) {
                setActiveHand(player.getSplitHand());
            } else {
                getActiveHand().setStanded(true);
            }
        }
        continueRound();
    }

    public void split() {
        player.removeChips(bet);
        player.splitHand();
        player.addCardToMainHand(dealer.drawCardFromShoe());
        player.addCardToSplitHand(dealer.drawCardFromShoe());
        player.getMainHand().updateValue();
        player.getSplitHand().updateValue();

        continueRound();
    }

    //TODO: Implement separate bets for each hand
    public void doubleDown() {
        dealPlayerActiveHand();
        getActiveHand().setStanded(true);
        if (player.getHasSplit()) {setActiveHand(player.getSplitHand());}
        player.removeChips(bet);
        continueRound();
    }

    public void checkForBlackjack() {
        if (getActiveHand().hasBlackjack()) {
            System.out.println("You have blackjack!");
            player.addChips(bet * 2);
            continueRound();
        } else if (dealer.getHand().hasBlackjack()) {
            System.out.println("Dealer has blackjack!");
            dealer.getHand().printHand();
            endRound();
        }

    }

    public Boolean playerCanSplit() {
        if (player.getHasSplit()) {
            return false;
        }

        if (player.getChips() < bet) {
            System.out.println("You don't have enough chips to split!");
            return false;
        }
        // True if player's first and second card of main hand are the same rank
        return player.getMainHand().getCards().get(0).getRank().getValue() == player.getMainHand().getCards().get(1).getRank().getValue();
    }

    public Boolean playerCanDoubleDown() {
        if (player.getChips() < bet || getActiveHand().getNumberOfCards() != 2) {
            System.out.println("You don't have enough chips to double down!");
            return false;
        } else {
            return true;
        }
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

    public void dealPlayerActiveHand() {
        activeHand.addCard(dealer.drawCardFromShoe());
        activeHand.updateValue();
    }

    public void dealDealer() {
        dealer.addCardToHand(dealer.drawCardFromShoe());
        dealer.getHand().updateValue();
    }

    public void printHands() {
        System.out.println("\n*---------------------------------------------------*\n");
        printPlayerChips();
        printPlayerBet();
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

        if (dealer.getHand().getNumberOfCards() == 2) {
            dealer.getHand().printHandWithHiddenCard();
        } else {
            dealer.getHand().printHand();
            System.out.println("Dealer's hand value: " + dealer.getHandValue() + "\n");
        }
    }
    public void printPlayerChips() {
        System.out.println("You have " + player.getChips() + " chips.");
    }
    public void printPlayerBet() {
        System.out.println("Your bet: " + bet + " chips.");
    }
}
