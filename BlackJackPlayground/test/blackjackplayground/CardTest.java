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
public class CardTest {
    private Image temp;
    
    public CardTest() {
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

    @Test
    public void testConstrutorWorksOnKing() {
        Card lol = new Card(Rank.KING, null, null);
        assertEquals(10, lol.getValue());
    }
    @Test
    public void testConstrutorWorksOnAce() {
        Card lol = new Card(Rank.ACE, null, null);
        assertEquals(1, lol.getValue());
    }
    @Test
    public void testConstrutorWorksOnImage() {
        try {
            temp = ImageIO.read(new File("cards/1.png"));

        } catch (IOException ex) {
            System.out.println("You seem to miss the card files");
        }
        Card lol = new Card(null, null, temp);
        assertEquals(temp, lol.getImage());
    }
}
