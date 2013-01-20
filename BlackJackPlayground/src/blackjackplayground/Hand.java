/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

import java.util.ArrayList;

/**
 * Represents a single hand in the game of blackjack. 
 * @author Wampie
 */
public class Hand {

    /**
     * list that holds all the cards dealt to the Hand
     */
    private ArrayList<Card> cards;
    /**
     * Value of the hand according BlackJack rules
     */
    private int value = 0;
    /**
     * If hand holds ace, in BlackJack it can have two values, this variable takes care of that
     */
    private int acedValue = 0;
    /**
     * Amount of money the player has bet to the hand
     */
    private int bet = 0;
    /**
     * Since Ace can be 1 or 11 in BlackJack, we need to know if the Hand holds one
     */
    private boolean hasAce = false;
    /**
     * According to most BlackJack rules, you can only split once in single deal.
     * If the hand has been split once, it cant be split again.
     */
    private boolean isSplitted = false;
    /**
     * in BlackJack, hand can belong to player or dealer, in this program, we also make the difference between player and split
     */
    private String owner;

    /**
     * Creates a new Hand object and specifies the owner
     * @param owner owner of the hand
     */
    public Hand(String owner) {
        cards = new ArrayList<>();
        this.owner = owner;
    }

    /**
     * Adds a given card to the hand. If card is null, returns without doing anything.
     * @param c card dealt to hand
     */
    public void addCard(Card c) {
        if (c == null) {
            return;
        }
        cards.add(c);
        value += c.getValue();
        acedValue += c.getValue();
        if (c.getValue() == 1 && !hasAce) {
            hasAce = true;
            acedValue = value + 10;
        }
    }

    /**
     * Checks if the hand holds a Blackjack (21 value with first two cards, ACE and 10 or J or Q or K)
     * @return true if the hand holds blackjack
     */
    public boolean blackJack() {
        if (!hasAce || cards.size() != 2) {
            return false;
        }
        return acedValue == 21;
    }

    /**
     * In BlackJack, you can split a hand that has pairs to two hands that are both dealt one new card
     * Method checks that the owner of the Hand is player and it has not been split before (no re-splitting allowed) and the both of the hands cards have the same value
     * @return true if the hand has pair with 2 first cards.
     */
    public boolean isSplittable() {
        return owner.equalsIgnoreCase("player") && cards.size() == 2 && cards.get(0).getValue() == cards.get(1).getValue() && !isSplitted;
    }

    /**
     * Creates a new hand using one card from the orginal hand.
     * If Hand is not splittable, returns null
     * @return Split hand
     */
    public Hand split() {
        if (!isSplittable()) {
            return null;
        } else {
            Hand split = new Hand("split");
            split.addCard(cards.remove(1));
            value = cards.get(0).getValue();
            isSplitted = true;
            return split;
        }
    }

    /**
     * Returns the value of the hand when Ace is considered 11
     * If value goes over 21, returns 0;
     * @return Value of the hand with Ace considered 11
     */
    public int getAcedValue() {
        if (acedValue > 21) {
            return 0;
        }
        return acedValue;
    }

    /**
     * Adds given amount to the amount bet in the hand. If less than 10, add 10
     * @param b given bet
     */
    public void addBet(int b) {
        this.bet += b < 10 ? 10 : b;
    }

    /**
     * Returns the card dealt to given index
     * @param index The index of Card wanted
     * @return Card in the given index
     */
    public Card getCard(int index) {
        return cards.get(index);
    }

    /**
     * Returns the value of the hand, Aces are considered 1
     * @return Value of the hand.
     */
    public int getValue() {
        return value;
    }

    /**
     * Owner set to the hand
     * @return owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Returns true if the hand holds Ace
     * @return true if the hand holds Ace
     */
    public boolean hasAce() {
        return hasAce;
    }

    /**
     * Returns the amount bet to the hand
     * @return amount bet to the hand
     */
    public int getBet() {
        return bet;
    }

    /**
     * Returns the size of the hand for testing.
     * @return 
     */
    public int getSize() {
        return cards.size();
    }
}
