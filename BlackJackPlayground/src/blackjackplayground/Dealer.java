/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Just like in the game of blackjack, the Dealer controls the flow of the game.
 * Player may choose actions, and Dealer 
 * @author Wampie
 */
public class Dealer implements ActionListener {

    /**
     * The deck of playing cards used by dealer
     */
    private Deck deck;
    /**
     * The User Interface of the game
     */
    private GameUI ui = null;
    /**
     * Hand dealt to player
     */
    private Hand playerHand;
    /**
     * Hand dealt to dealer
     */
    private Hand dealerHand;
    /**
     * Hand used to store the other hand in split game
     */
    private Hand splitHand = null;
    /**
     * CoinPurse used by player
     */
    private CoinPurse playerPurse;
    /**
     * Stores the information if player is currently playing his main hand, or split hand
     */
    private boolean playingSplitHand = false;

    public Dealer() {
        deck = new Deck();
        ui = new GameUI(this);
        playerPurse = new CoinPurse();
        ui.betslider.addChangeListener(playerPurse);

    }

    /**
     * Method used to update the UI for changes in hand values
     * @param hand Hand that value needs to be updated in the UI
     */
    private void updateHandValue(Hand hand) {
        String update = hand.getValue() + "";
        if (hand.hasAce() && hand.getAcedValue() > 0) {
            update += "/" + hand.getAcedValue();
        }
        if (hand.blackJack()) {
            update = "BLACKJACK!";
        }
        ui.UpdateHandValue(hand.getOwner(), update);

    }

    /**
     * Deals one Card from Deck to target hand
     * @param target The hand that the card is dealt to
     */
    private void dealCard(Hand target) {
        Card temp = deck.drawCard();
        ui.drawCard(temp, target.getOwner());
        target.addCard(temp);
    }

    /**
     * Check if the hand is bust (value over 21)
     * If the hand is bust, calls method to check if bust leads to Game Over. 
     * @param hand hand that is checked
     * @return true if the hand is bust
     */
    private boolean checkBust(Hand hand) {
        if (hand.getValue() <= 21) {
            return false;
        }
        if (gameoverBust(hand)) {
            ui.setEnableButton("bust");
        }
        return true;
    }

    /**
     * Checks if the bust lead to Game Over and acts accordingly.
     * If splitHand busts, game continues, if dealer busts game is over and its time to declareWinner(),
     * if playerHand busts, game continues only if splitHand is still in game
     * @param hand Hand to be checked
     * @return true if Game is over.
     */
    public boolean gameoverBust(Hand hand) {
        boolean gameOver = true;
        switch (hand.getOwner()) {
            case "split":
                setEnableSplitHand(false);
                gameOver = false;
                break;
            case "dealer":
                declareWinner();
                break;
            case "player":
                if (splitHand != null && splitHand.getValue() <= 21) {
                    ui.stayButton.doClick();
                    gameOver = false;
                    break;
                }
                ui.gameOver("PLAYER BUSTS");
                break;
        }
        return gameOver;
    }

    /**
     * Updates the playingSplitHand variable, and updates UI to highlight the hand being currently played by player.
     * @param enable Value to be set in playingSplitHand
     */
    private void setEnableSplitHand(boolean enable) {
        playingSplitHand = enable;
        if (enable) {
            ui.enableHand("split");
            ui.disableHand("player");
        } else {
            ui.enableHand("player");
            ui.disableHand("split");
        }
    }

    /**
     * Doubles the amount of bet in a hand, then deals one Card and checks if the hand busts.
     * After the Card is dealt, the turn is passed to dealer by forcing click on stayButton.
     * @param hand 
     */
    public void doubleHand(Hand hand) {
        hand.addBet(playerPurse.bet());
        dealCard(hand);
        checkBust(hand);
        updateHandValue(hand);
        ui.stayButton.doClick();
    }

    /**
     * Pays the winnings to playerPurse and updates UI.
     * @param amount Amount to be paid to playerPurse
     */
    private void payWinnings(int amount) {
        playerPurse.addMoney(amount);
        ui.updateBets(playerPurse.getMoney(), playerHand.getBet());
    }

    /**
     * Declares how much the player won with a split game.
     * Compares both of the players hand against dealers and counts how much the player won,
     * then declares the winnings/loss and updates UI.
     * @param dealer value of the dealers hand
     * @param player value of the players hand
     * @param split value of the split hand
     */
    public void declareSplitWinnings(int dealer, int player, int split) {
        int winnings = 0;
        if (playerHand.blackJack()) {
            winnings += playerHand.getBet() + (playerHand.getBet() * 3) / 2;
        } else if (player > dealer) {
            winnings += playerHand.getBet() * 2;
        } else if (player == dealer) {
            winnings += playerHand.getBet();
        }
        if (splitHand.blackJack()) {
            winnings += splitHand.getBet() + (splitHand.getBet() * 3) / 2;
        } else if (split > dealer) {
            winnings += splitHand.getBet() * 2;
        } else if (split == dealer) {
            winnings += splitHand.getBet();
        }
        if (winnings > 0) {
            payWinnings(winnings);
            ui.gameOver("YOU WON " + winnings + "€");
        } else {
            ui.gameOver("DEALER WINS");
        }
    }

    /**
     * Declares the winner of the deal
     * First checks the values of the hands in question. Then updates values to 0 if those hands are bust.
     * After that, if game was played as split, calls declareSplitWinnings(), else checks who won, updates UI
     * and calls payWinnings() if needed
     */
    public void declareWinner() {
        int dealer = Math.max(dealerHand.getAcedValue(), dealerHand.getValue());
        int player = Math.max(playerHand.getValue(), playerHand.getAcedValue());
        int split = 0;
        dealer = dealer > 21 ? 0 : dealer;
        player = player > 21 ? 0 : player;

        if (splitHand != null) {
            split = Math.max(splitHand.getValue(), splitHand.getAcedValue());
            split = split > 21 ? 0 : split;
            declareSplitWinnings(dealer, player, split);
            return;
        }
        if (dealer > player) {
            ui.gameOver("DEALER WINS");
        } else if (dealer == player) {
            ui.gameOver("GAME IS SPLIT");
            payWinnings((playerHand.getBet()));
        } else {
            ui.gameOver("YOU WIN " + (playerHand.getBet() * 2) + "€");
            payWinnings((playerHand.getBet() * 2));
        }
    }

    /**
     * Checks player or dealer holds blackjack after the the first cards are dealt.
     * If either of the Hands hold blackjack, the deal is over, and the method declares winner,
     * updates UI and pays winnings.
     * @return true if either hands hold blackjack
     */
    public boolean checkBlackJack() {
        if (playerHand.blackJack() && dealerHand.blackJack()) {
            ui.gameOver("GAME IS SPLIT");
            payWinnings((playerHand.getBet()));
        } else if (dealerHand.blackJack()) {
            ui.gameOver("DEALER WINS");
        } else if (playerHand.blackJack()) {
            ui.gameOver("YOU WIN " + (playerHand.getBet() + (playerHand.getBet() * 3) / 2) + "€");
            payWinnings(playerHand.getBet() + (playerHand.getBet() * 3) / 2);
        } else {
            return false;
        }
        ui.revealDealerCard();
        updateHandValue(dealerHand);
        return true;
    }

    /**
     * Method thats called when player starts a new deal by pressing the deal button.
     * Creates clean objects to playerHand and dealerHand and deals 2 cards to both of them.
     * Adds the chosen bet to playerHand and updates UI.
     */
    public void dealPressed() {
        ui.clear();
        playerHand = new Hand("player");
        dealerHand = new Hand("dealer");
        splitHand = null;
        dealCard(dealerHand);
        dealCard(dealerHand);
        dealCard(playerHand);
        dealCard(playerHand);
        playerHand.addBet(playerPurse.bet());
        updateHandValue(playerHand);
        ui.updateBets(playerPurse.getMoney(), playerHand.getBet());
    }

    /**
     * Method that called after player chooses to hit.
     * Checks witch hand the player is playing, deals one Card to that hand and checks if the Hand goes bust before updating UI.
     */
    public void hitPressed() {
        Hand temp;
        if (playingSplitHand) {
            temp = splitHand;
        } else {
            temp = playerHand;
        }
        dealCard(temp);
        checkBust(temp);
        updateHandValue(temp);
    }

    /**
     * Method that is called after player decides he wants to stay with his current hand.
     * Highlights possible split hand, reveals dealers hole card and deals cards to dealer as long as its hands value is less than 17.
     * Declares winner and updates UI
     */
    public void stayPressed() {
        ui.enableHand("split");
        ui.revealDealerCard();
        while (dealerHand.getValue() < 17) {
            dealCard(dealerHand);
        }
        updateHandValue(dealerHand);
        if (!checkBust(dealerHand)) {
            declareWinner();
        }
    }

    /**
     * Method that is called if the player decides to split his hand
     * Calls split() on the hand, then deals one more card to each hand,
     * enables the split hand as the hand played and checks if it was blackjack
     * before updating UI. If both hands are blackjack after split, the deal is over.
     */
    public void splitPressed() {
        ui.clearPlayer();
        splitHand = playerHand.split();
        splitHand.addBet(playerPurse.bet());
        ui.drawCard(playerHand.getCard(0), "player");
        ui.drawCard(splitHand.getCard(0), "split");
        dealCard(playerHand);
        dealCard(splitHand);
        setEnableSplitHand(true);
        updateHandValue(playerHand);
        updateHandValue(splitHand);
        if (splitHand.blackJack()) {
            ui.stayButton.doClick();
            if (playerHand.blackJack()) {
                ui.stayButton.doClick();
            }
        }
    }

    /**
     * Overrides the actionPerformed() method of ActionListener and listens to buttons player can press.
     * After deal, checks for blackjacks and if found, ends the deal
     * After stay, checks if the player was playing split hand, if so, deal continues, if not, calls stayPressed()
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        switch (command) {
            case "deal":
                if (!playerPurse.betAcceptable()) {
                    ui.gameOver("Choose smaller bet!");
                    return;
                }
                dealPressed();
                if (checkBlackJack()) {
                    command = "bust";
                }
                ui.splitButton.setEnabled(playerHand.isSplittable());
                break;
            case "hit":
                hitPressed();
                break;
            case "stay":
                if (playingSplitHand) {
                    setEnableSplitHand(false);
                    // if the playerHand is blackjack, the game ends.
                    if (playerHand.blackJack()) {
                        ui.stayButton.doClick();
                        return;
                    }
                    return;
                }
                stayPressed();
                break;
            case "split":
                splitPressed();
                break;
            case "double":
                doubleHand(playerHand);
        }
        ui.setEnableButton(command);
    }

    /**
     * Setter for dealerHand used for testing
     * @param hand 
     */
    public void setDealerHand(Hand hand) {
        this.dealerHand = hand;
    }

    /**
     * Setter for playerHand used for testing
     * @param hand 
     */
    public void setPlayerHand(Hand hand) {
        this.playerHand = hand;
    }

    /**
     * Setter for splitHand used for testing
     * @param hand 
     */
    public void setSplitHand(Hand hand) {
        this.splitHand = hand;
    }

    /**
     * Getter for dealerHand used for testing
     */
    public Hand getDealerHand() {
        return dealerHand;
    }

    /**
     * Getter for playerHand used for testing
     */
    public Hand getPlayerHand() {
        return playerHand;
    }

    /**
     * Getter for splitHand used for testing 
     */
    public Hand getSplitHand() {
        return splitHand;
    }

    /**
     * Getter for playerPurse for testing
     */
    public CoinPurse getPurse() {
        return playerPurse;
    }
}
