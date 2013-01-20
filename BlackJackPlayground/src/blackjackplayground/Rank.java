/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

/**
 * Enumerator representing the Ranks of basic playing cards used in BlackJack
 * Holds the Ranks name, Symbol and Value used in BlackJack.
 * @author Wampie
 */
public enum Rank {

    ACE("A", 1), KING("K", 10), QUEEN("Q", 10), JACK("J", 10), TEN("10", 10), NINE("9", 9), EIGHT("8", 8), SEVEN("7", 7), SIX("6", 6), FIVE("5", 5), FOUR("4", 4), THREE("3", 3), TWO("2", 2);
    public final String symbol;
    public final int value;

    Rank(String sym, int value) {
        symbol = sym;
        this.value = value;
    }
}
