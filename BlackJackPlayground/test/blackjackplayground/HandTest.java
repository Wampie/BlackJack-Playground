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
public class HandTest {

    private Card card1;
    private Card card2;

    public HandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        card1 = new Card(Rank.ACE, Suit.CLUBS, null);
        card2 = new Card(Rank.KING, Suit.CLUBS, null);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addCard method, of class Hand.
     */
    @Test
    public void testAddCard() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        assertEquals(card1, instance.getCard(0));
    }

    @Test
    public void testAddNullCard() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        instance.addCard(null);
        assertEquals(1, instance.getSize());
    }

    /**
     * Test of blackJack method, of class Hand.
     */
    @Test
    public void testBlackJack() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        instance.addCard(card2);
        boolean expResult = true;
        boolean result = instance.blackJack();
        assertEquals(expResult, result);
    }

    /**
     * Test of blackJack method returning false if not blackjack
     */
    @Test
    public void testBlackJackReturnsFalse() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        instance.addCard(card1);
        boolean expResult = false;
        boolean result = instance.blackJack();
        assertEquals(expResult, result);
    }

    /**
     * Testing that IsSplittable returns false if the hand is not pair
     */
    @Test
    public void testIsSplittableReturnsFalseIfNotPair() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        instance.addCard(card2);
        boolean expResult = false;
        boolean result = instance.isSplittable();
        assertEquals(expResult, result);
    }

    /**
     * Testing that IsSplittable returns false if once split
     */
    @Test
    public void testIsSplittableReturnsFalseIfOnceSplit() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        instance.addCard(card1);
        instance.split();
        instance.addCard(card1);
        boolean expResult = false;
        boolean result = instance.isSplittable();
        assertEquals(expResult, result);
    }

    /**
     * Test of isSplittable method returns true if splittable
     */
    @Test
    public void testIsSplittableReturnsTrue() {
        Hand instance = new Hand("player");
        card2 = new Card(Rank.ACE, Suit.CLUBS, null);
        instance.addCard(card1);
        instance.addCard(card2);
        boolean expResult = true;
        boolean result = instance.isSplittable();
        assertEquals(expResult, result);
    }

    /**
     * Test that split method returns split hand if called on a pair
     */
    @Test
    public void testSplitWorksOnPair() {
        Hand instance = new Hand("player");
        card2 = new Card(Rank.ACE, Suit.CLUBS, null);
        instance.addCard(card1);
        instance.addCard(card2);
        Hand split = instance.split();
        assertEquals(instance.getCard(0).toString(), split.getCard(0).toString());
    }

    /**
     * Test that split method returns null if not called on a pair
     */
    @Test
    public void testSplitReturnsNullIfNotPair() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        instance.addCard(card2);
        Hand split = instance.split();
        assertNull(split);
    }

    /**
     * Testing that split returns null if once split
     */
    @Test
    public void testSplitReturnsNullIfOnceSplit() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        instance.addCard(card1);
        instance.split();
        instance.addCard(card1);
        assertNull(instance.split());
    }

    /**
     * Test of getAcedValue method, of class Hand.
     */
    @Test
    public void testGetAcedValue() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        instance.addCard(card2);
        int expResult = 21;
        int result = instance.getAcedValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAcedValue method, of class Hand.
     */
    @Test
    public void testGetAcedValueReturns0WhenOver21() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        instance.addCard(card2);
        instance.addCard(new Card(Rank.ACE, Suit.CLUBS, null));
        int expResult = 0;
        int result = instance.getAcedValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of addBet method, of class Hand.
     */
    @Test
    public void testAddBet() {
        int b = 100;
        Hand instance = new Hand("player");
        instance.addBet(b);
        assertEquals(b, instance.getBet());
    }

    /**
     * Test of addBet method, of class Hand.
     */
    @Test
    public void testAddTooSmallBet() {
        int b = 0;
        Hand instance = new Hand("player");
        instance.addBet(b);
        assertEquals(10, instance.getBet());
    }

    @Test
    public void testHasAceReturnsTrue() {
        Hand instance = new Hand("player");
        instance.addCard(card1);
        assertEquals(true, instance.hasAce());
    }

    @Test
    public void testHasAceReturnsFalse() {
        Hand instance = new Hand("player");
        instance.addCard(card2);
        assertEquals(false, instance.hasAce());
    }
}
