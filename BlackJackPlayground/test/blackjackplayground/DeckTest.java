/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Wampie
 */
public class DeckTest {
    
    public DeckTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of shuffle method, of class Deck.
     * There is a small chance that this test will fail, due to the fact that its 
     * possible to shuffle the deck two times in a row to same order.
     */
    @Test
    public void newDeckHoldsCards() {
        Deck deck = new Deck();
        assertNotNull(deck.drawCard());
    }
    
    @Test
    public void testShuffle() {
        Deck instance = new Deck();
        instance.shuffle();
        Card firstcard1 = instance.drawCard();
        Card firstcard2 = instance.drawCard();
        instance.shuffle();
        Card secondcard1 = instance.drawCard();
        Card secondcard2 = instance.drawCard();
        assertFalse(firstcard1.toString().equals(secondcard1.toString()) && firstcard2.toString().equals(secondcard2.toString()));
    }

    /**
     * Test of drawCard method, of class Deck.
     */
    @Test
    public void testDrawCard() {
        Deck instance = new Deck();
        Card result = instance.drawCard();
        assertNotNull(result);
    }
}
