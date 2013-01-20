/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

/**
 * Class represents a deck of basic playing cards that can be shuffled, and from witch cards can be dealt from
 * @author Wampie
 */
public class Deck {

    private ArrayList<Card> cardStorage = new ArrayList<>();
    private ArrayList<Card> deck = new ArrayList<>();
    
    /**
     * Creates a new Deck object that stores the 52 different playing cards in order
     */
    public Deck() {
        int i = 1;
        for (Rank r : Rank.values()) {
            for (Suit s : Suit.values()) {
                try {
                Image temp = ImageIO.read(new File("cards/" + i + ".png"));
                cardStorage.add(new Card(r, s, temp));
                i ++;
                } catch (IOException ex) {
                    System.out.println("You seem to miss the card files");
                    System.exit(666);
                }
            }
        }
    }
    /**
     * Uses the card storage to shuffle a new 52 card deck of playing cards.
     */
    public void shuffle() {
        deck = new ArrayList<>(cardStorage);
        Collections.shuffle(deck);
    }
    /**
     * Returns the first Card of the deck.
     * If the deck is empty (all the cards have been drawn or the Deck object was just created),
     * uses shuffle() to refill it.
     * @return first Card of the deck
     */
    public Card drawCard() {
        if (deck.isEmpty()) {
            shuffle();
        }
        return deck.remove(0);
    }
}
