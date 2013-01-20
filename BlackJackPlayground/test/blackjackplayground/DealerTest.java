/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
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
public class DealerTest {

    Card card1;
    Card card2;
    Hand dealer;
    Hand player;
    Hand split;
    static Image temp;

    public DealerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        try {
            temp = ImageIO.read(new File("cards/1.png"));

        } catch (IOException ex) {
            System.out.println("You seem to miss the card files");
        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {

        card1 = new Card(Rank.ACE, Suit.CLUBS, temp);
        card2 = new Card(Rank.KING, Suit.CLUBS, temp);
        dealer = new Hand("dealer");
        dealer.addCard(card1);
        dealer.addCard(card1);
        player = new Hand("player");
        player.addCard(card1);
        player.addCard(card1);
        split = new Hand("split");
        split.addCard(card1);
        split.addCard(card1);

    }

    @After
    public void tearDown() {
    }

    public Dealer newInstance(Hand d, Hand p, Hand s) {
        Dealer deal = new Dealer();
        deal.setDealerHand(d);
        deal.setPlayerHand(p);
        deal.setSplitHand(s);
        return deal;
    }

    /**
     * Test of gameoverBust method, of class Dealer.
     */
    @Test
    public void testGameoverBustReturnsFalseIfSplitBusts() {
        Dealer instance = new Dealer();
        boolean expResult = false;
        // value 22
        split.addCard(card1);
        split.addCard(card2);
        split.addCard(card2);

        boolean result = instance.gameoverBust(split);
        assertEquals(expResult, result);
    }

    /**
     * Test of gameoverBust method, of class Dealer.
     */
    @Test
    public void testGameoverBustReturnsTrueIfPlayerBusts() {
        Dealer instance = new Dealer();
        boolean expResult = true;
        // value 22
        player.addCard(card1);
        player.addCard(card2);
        player.addCard(card2);
        boolean result = instance.gameoverBust(player);
        assertEquals(expResult, result);
    }

    /**
     * Test of gameoverBust method, of class Dealer.
     */
    @Test
    public void testGameoverBustReturnsTrueIfDealerBusts() {
        Dealer instance = new Dealer();
        boolean expResult = true;
        // value 22
        dealer = new Hand("dealer");
        dealer.addCard(card1);
        dealer.addCard(card2);
        dealer.addCard(card2);
        instance.setDealerHand(dealer);
        instance.setPlayerHand(player);
        boolean result = instance.gameoverBust(dealer);
        assertEquals(expResult, result);
    }

    /**
     * Test of doubleHand method, of class Dealer.
     */
    @Test
    public void testDoubleHandDoublesBet() {
        Dealer instance = new Dealer();
        player.addBet(10);
        instance.doubleHand(player);
        assertEquals(player.getBet(), 20);
    }

    /**
     * Test of declareSplitWinnings method, of class Dealer.
     */
    @Test
    public void testDeclareSplitWinningsDealerWon() {

        int d = 21;
        int p = 5;
        int s = 5;
        Dealer instance = newInstance(dealer, player, split);
        int startamount = instance.getPurse().getMoney();
        player.addBet(10);
        split.addBet(10);

        instance.declareSplitWinnings(d, p, s);
        assertEquals(startamount, instance.getPurse().getMoney());
    }

    /**
     * Test of declareSplitWinnings method, of class Dealer.
     */
    @Test
    public void testDeclareSplitWinningsPush() {

        int d = 20;
        int p = 20;
        int s = 20;
        Dealer instance = newInstance(dealer, player, split);
        int startamount = instance.getPurse().getMoney();
        player.addBet(10);
        split.addBet(10);

        instance.declareSplitWinnings(d, p, s);
        assertEquals(startamount + 20, instance.getPurse().getMoney());
    }

    /**
     * Test of declareSplitWinnings method, of class Dealer.
     */
    @Test
    public void testDeclareSplitWinningsPlayerAndSplitWon() {

        int d = 20;
        int p = 21;
        int s = 21;
        Dealer instance = newInstance(dealer, player, split);
        int startamount = instance.getPurse().getMoney();
        player.addBet(10);
        split.addBet(10);

        instance.declareSplitWinnings(d, p, s);
        assertEquals(startamount + 40, instance.getPurse().getMoney());
    }

    /**
     * Test of declareSplitWinnings method, of class Dealer.
     */
    @Test
    public void testDeclareSplitWinningsPlayerhandWinsSplithandLoses() {

        int d = 20;
        int p = 21;
        int s = 19;
        Dealer instance = newInstance(dealer, player, split);
        int startamount = instance.getPurse().getMoney();
        player.addBet(10);
        split.addBet(10);

        instance.declareSplitWinnings(d, p, s);
        assertEquals(startamount + 20, instance.getPurse().getMoney());
    }
    
        /**
     * Test of declareSplitWinnings method, of class Dealer.
     */
    @Test
    public void testDeclareSplitWinningsPlayerhandWinsSplithandPush() {

        int d = 20;
        int p = 21;
        int s = 20;
        Dealer instance = newInstance(dealer, player, split);
        int startamount = instance.getPurse().getMoney();
        player.addBet(10);
        split.addBet(10);

        instance.declareSplitWinnings(d, p, s);
        assertEquals(startamount + 30, instance.getPurse().getMoney());
    }

    /**
     * Test of declareWinner method, of class Dealer.
     */
    @Test
    public void testDeclareWinnerDealerWins() {
        dealer.addCard(card1);
        Dealer instance = newInstance(dealer, player, null);
        player.addBet(10);
        int startamount = instance.getPurse().getMoney();
        instance.declareWinner();
        assertEquals(startamount, instance.getPurse().getMoney());
    }

    /**
     * Test of declareWinner method, of class Dealer.
     */
    @Test
    public void testDeclareWinnerPush() {
        Dealer instance = newInstance(dealer, player, null);
        player.addBet(10);
        int startamount = instance.getPurse().getMoney();
        instance.declareWinner();
        assertEquals(startamount + 10, instance.getPurse().getMoney());
    }

    /**
     * Test of declareWinner method, of class Dealer.
     */
    @Test
    public void testDeclareWinnerPlayerWins() {
        player.addCard(card1);
        Dealer instance = newInstance(dealer, player, null);
        player.addBet(10);
        int startamount = instance.getPurse().getMoney();
        instance.declareWinner();
        assertEquals(startamount + 20, instance.getPurse().getMoney());
    }

    /**
     * Test of checkBlackJack method, of class Dealer.
     */
    @Test
    public void testCheckBlackJackDealer() {
        dealer = new Hand("dealer");
        dealer.addCard(card1);
        dealer.addCard(card2);
        Dealer instance = newInstance(dealer, player, null);
        boolean expResult = true;
        boolean result = instance.checkBlackJack();
        assertEquals(expResult, result);
    }

    /**
     * Test of checkBlackJack method, of class Dealer.
     */
    @Test
    public void testCheckBlackJackPlayer() {
        player = new Hand("player");
        player.addCard(card1);
        player.addCard(card2);
        Dealer instance = newInstance(dealer, player, null);
        boolean expResult = true;
        boolean result = instance.checkBlackJack();
        assertEquals(expResult, result);
    }

    /**
     * Test of checkBlackJack method, of class Dealer.
     */
    @Test
    public void testCheckBlackJackNoBlackJack() {
        Dealer instance = newInstance(dealer, player, null);
        boolean expResult = false;
        boolean result = instance.checkBlackJack();
        assertEquals(expResult, result);
    }

    /**
     * Test of dealPressed method, of class Dealer.
     */
    @Test
    public void testDealPressedDealsCardsToDealer() {
        Dealer instance = new Dealer();
        instance.dealPressed();
        assertEquals(instance.getDealerHand().getSize(), 2);
    }

    /**
     * Test of dealPressed method, of class Dealer.
     */
    @Test
    public void testDealPressedDealsCardsToPlayer() {
        Dealer instance = new Dealer();
        instance.dealPressed();
        assertEquals(instance.getPlayerHand().getSize(), 2);
    }

    /**
     * Test of hitPressed method, of class Dealer.
     */
    @Test
    public void testHitPressedAddsCardsToPlayerHand() {
        Dealer instance = newInstance(dealer, player, null);
        instance.hitPressed();
        assertEquals(instance.getPlayerHand().getSize(), 3);
    }

    /**
     * Test of hitPressed method, of class Dealer.
     */
    @Test
    public void testHitPressedAddsCardsToSplitHand() {
        Dealer instance = newInstance(dealer, player, null);
        instance.splitPressed();
        instance.hitPressed();
        assertEquals(instance.getSplitHand().getSize(), 3);
    }

    /**
     * Test of hitPressed method, of class Dealer.
     */
    @Test
    public void testHitPressedAddsCardsToPlayerHandAfterSplitHand() {
        Dealer instance = newInstance(dealer, player, null);
        instance.splitPressed();
        instance.hitPressed();
        instance.hitPressed();
        instance.hitPressed();
        instance.hitPressed();

        assertTrue(instance.getPlayerHand().getSize() > 2);
    }

    /**
     * Test of stayPressed method, of class Dealer.
     */
    @Test
    public void testStayPressedDealsCardsToDealer() {
        Dealer instance = newInstance(dealer, player, null);
        instance.stayPressed();
        assertTrue(instance.getDealerHand().getSize() > 2);
    }

    /**
     * Test of splitPressed method, of class Dealer.
     */
    @Test
    public void testSplitPressedSplitsHand() {
        Dealer instance = newInstance(dealer, player, null);
        instance.splitPressed();
        assertEquals(instance.getSplitHand().getSize(), 2);
    }
}
