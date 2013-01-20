/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

/**
 * Enumerator representing the Suits of the basic playing cards used in BlackJack
 * @author Wampie
 */
public enum Suit {
    CLUBS('\u2663', 'c'), SPADES('\u2660', 's'), HEARTS('\u2665', 'h'), DIAMONDS('\u2666', 'd');
    public final char symbol;
    public final char letter;

    Suit(char c, char l) {
        symbol = c;
        letter = l;
    }
}
