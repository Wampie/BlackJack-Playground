/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

import java.awt.Image;

/**
 * Class represents a single basic playing card with Rank, Suit and Image of the card.
 * @author Wampie
 */
public class Card {
    
    /**
     * Rank of the Card
     */
    private Rank rank;
    /**
     * Suit of the Card
     */
    private Suit suit;
    /**
     * Image of the Card
     */
    private Image image;
    
    /**
     * Creates a new Card Object with given values
     * @param r Given Rank
     * @param s Given Suit
     * @param img Given Image
     */
    public Card(Rank r, Suit s, Image img) {
        this.rank = r;
        this.suit = s;
        this.image = img;
    }
    /**
     * Returns image of the card
     * @return Image object of stored image
     */
    public Image getImage() {
        return image;
    }
    /**
     * Returns the value of the Card from rank
     * @return Value of the Cards Rank to be used in BlackJack
     */
    public int getValue() {
        return rank.value;
    }
    /**
     * Returns String representation of the Card for testing purposes
     * @return String representation of the Card
     */
    public String toString() {
        return rank.symbol + suit.symbol;
    }
}
