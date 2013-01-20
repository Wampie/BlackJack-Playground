/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

import javax.swing.event.ChangeEvent;
import junit.framework.Assert;
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
public class CoinPurseTest {

    public CoinPurseTest() {
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
     * Test of betAcceptable method, of class CoinPurse.
     */
    @Test
    public void testBetAcceptable() {
        CoinPurse instance = new CoinPurse(10);
        boolean expResult = true;
        boolean result = instance.betAcceptable();
        assertEquals(expResult, result);
    }

    @Test
    public void testBetAcceptableNotAcceptable() {
        CoinPurse instance = new CoinPurse(10);
        instance.bet();
        boolean expResult = false;
        boolean result = instance.betAcceptable();
        assertEquals(expResult, result);
    }

    /**
     * Test of bet method, of class CoinPurse.
     */
    @Test
    public void testBetDecreasesWealth() {
        CoinPurse instance = new CoinPurse();
        instance.bet();
        int expResult = 990;
        int result = instance.getMoney();
        assertEquals(expResult, result);
    }

    /**
     * Test of bet method, of class CoinPurse.
     */
    @Test
    public void testBetReturnsBetsize() {
        CoinPurse instance = new CoinPurse();
        int expResult = 10;
        int result = instance.bet();
        assertEquals(expResult, result);
    }

    /**
     * Test of bet method, of class CoinPurse.
     */
    @Test
    public void testBetDoesNotSetWealthToNegative() {
        CoinPurse instance = new CoinPurse(10);
        int expResult = 0;
        instance.bet();
        instance.bet();
        assertEquals(expResult, 0);
//        fail("The betting set wealth to negative");
    }

    /**
     * Test of getMoney method, of class CoinPurse.
     */
    @Test
    public void testGetMoney() {
        CoinPurse instance = new CoinPurse();
        int expResult = 1000;
        int result = instance.getMoney();
        assertEquals(expResult, result);
    }

    /**
     * Test of addMoney method, of class CoinPurse.
     */
    @Test
    public void testAddMoney() {
        int m = 1100;
        CoinPurse instance = new CoinPurse();
        instance.addMoney(m);
        assertEquals(instance.getMoney(), 2100);
    }

    /**
     * Test of addMoney method, of class CoinPurse with entering negative amount
     */
    @Test
    public void testAddMoneyNegativeAmount() {
        int m = -10;
        CoinPurse instance = new CoinPurse();
        instance.addMoney(m);
        assertEquals(instance.getMoney(), 1000);
    }

    /**
     * Test of setWealth method, of class CoinPurse.
     */
    @Test
    public void testSetWealth() {
        int m = 10;
        CoinPurse instance = new CoinPurse();
        instance.setWealth(m);
        assertEquals(instance.getMoney(), 10);
    }

    /**
     * Test of setWealth method, of class CoinPurse with entering negative amount.
     */
    @Test
    public void testSetWealthNegativeAmount() {
        int m = -10;
        CoinPurse instance = new CoinPurse();
        instance.setWealth(m);
        Assert.assertEquals(instance.getMoney(), 1000);
    }
}
