/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * CoinPurse stores information about the amount of money the player can use in the game.
 * @author Wampie
 */
public class CoinPurse implements ChangeListener {

    /**
     * Amount of money the player currently has
     */
    private int wealth;
    /**
     * Size of the bet the player uses, in range 10-150
     */
    private int betSize = 10;

    /**
     * Creates new CoinPurse object with 1000€ wealth;
     */
    public CoinPurse() {
        wealth = 1000;
    }

    /**
     * Creates new CoinPurse object with selected amount of wealth.
     * If given less than zero, creates object with 1000€ wealth
     * @param m 
     */
    public CoinPurse(int m) {
        wealth = m > 0 ? m : 1000;
    }

    /**
     * Checks if the CoinPurse object has enough money to bet the sum selected by player
     * @return true if there is enough money, else false
     */
    public boolean betAcceptable() {
        return wealth - betSize >= 0;
    }

    /**
     * Makes a bet for the selected sum and returns the amount bet.
     * If there is not enough money in CoinPurse object, returns zero
     * @return Amount of money bet
     */
    public int bet() {
        if (!betAcceptable()) {
            return 0;
        }
        wealth -= betSize;
        return betSize;
    }
    /**
     * Amount of money in the CoinPurse
     * @return Amount of money in the CoinPurse
     */
    public int getMoney() {
        return wealth;
    }

    /**
     * Adds the specified amount of money to the CoinPurse object
     * If given negative amount, adds nothing
     * @param m Amount of money to be added
     */
    public void addMoney(int m) {
        wealth += m > 0 ? m : 0;
    }
    /**
     * Sets the current wealth in the CoinPurse to given amount
     * if given less than 10, keeps the current amount
     * @param m Amount of money to be set as wealth
     */
    public void setWealth(int m) {
        wealth = m >= 10 ? m : wealth;
    }
    /**
     * Sets the current betSize in the CoinPurse to given amount
     * if given less than 10, keeps the current amount
     * @param m Amount of money to be set as betSize
     */
    private void setBetSize(int m) {
        betSize = m >= 10 ? m : 0;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            setBetSize(source.getValue());
        }
    }
}
