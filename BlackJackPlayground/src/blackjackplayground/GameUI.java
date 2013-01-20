/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackplayground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Wampie
 */
public class GameUI extends JFrame {

    private Card hidden = null;
    public JButton hitButton = new JButton("Hit Me");
    public JButton stayButton = new JButton("Stay");
    public JButton dealButton = new JButton("Deal");
    public JButton doubleButton = new JButton("Double");
    public JButton splitButton = new JButton("Split");
    private JLayeredPane layeredPane;
    private JPanel buttonPanel;
    private JPanel panel = new JPanel();
    private JPanel controls = new JPanel();
    private JPanel bets = new JPanel();
    public JSlider betslider = new JSlider();
    private ArrayList<JLabel> dealerHand = new ArrayList<>();
    private ArrayList<JLabel> playerHand = new ArrayList<>();
    private ArrayList<JLabel> splitHand = new ArrayList<>();
    private JLabel playerAmount = new JLabel();
    private JLabel dealerAmount = new JLabel();
    private JLabel splitAmount = new JLabel();
    private JLabel gameOver = new JLabel();
    private JLabel moneyLabel = new JLabel();
    private JLabel betLabel = new JLabel();
    private String bet = "Bet:        ";
    private String wealth = "Wealth: ";
    

    /**
     * Creates a new GameUI object and builds the UI
     */
    public GameUI(ActionListener list) {

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 300));
        layeredPane.setBorder(BorderFactory.createTitledBorder("Gametable"));

        hitButton.addActionListener(list);
        hitButton.setActionCommand("hit");
        hitButton.setEnabled(false);

        stayButton.addActionListener(list);
        stayButton.setActionCommand("stay");
        stayButton.setEnabled(false);

        dealButton.addActionListener(list);
        dealButton.setActionCommand("deal");

        doubleButton.addActionListener(list);
        doubleButton.setActionCommand("double");
        doubleButton.setEnabled(false);

        splitButton.addActionListener(list);
        splitButton.setActionCommand("split");
        splitButton.setEnabled(false);

        buttonPanel = new JPanel();
        controls.setPreferredSize(new Dimension(50, 50));
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);
        buttonPanel.add(doubleButton);
        buttonPanel.add(splitButton);
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        betslider.setMajorTickSpacing(20);
        betslider.setMinorTickSpacing(10);
        betslider.setPaintTicks(true);
        betslider.setPaintLabels(true);
        betslider.setSnapToTicks(true);
        betslider.setMinimum(10);
        betslider.setMaximum(150);
        betslider.setValue(10);
        
        bets.setLayout(new GridLayout(2, 0));
        bets.setBorder(BorderFactory.createTitledBorder("Bets"));
        bets.add(betslider);
        bets.add(dealButton);

        dealerAmount.setBounds(15, 120, 75, 25);
        layeredPane.add(dealerAmount);
        playerAmount.setBounds(15, 370, 75, 25);
        layeredPane.add(playerAmount);
        splitAmount.setBounds(230, 370, 75, 25);
        layeredPane.add(splitAmount);
        
        moneyLabel.setBounds(330, 15, 100, 20);
        layeredPane.add(moneyLabel);
        
        betLabel.setBounds(330, 30, 125, 20);
        layeredPane.add(betLabel);
        
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));

        controls.setLayout(new GridLayout(0, 2));
        controls.add(buttonPanel);
        controls.add(bets);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(layeredPane);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(controls);
        
        gameOver.setBounds(100, 150, 300, 75);
        gameOver.setBorder(BorderFactory.createTitledBorder("Game Over:"));
        gameOver.setFont(new Font(gameOver.getFont().getFontName(), gameOver.getFont().getStyle(), 25));
        gameOver.setVisible(false);
        layeredPane.add(gameOver);

        this.add(panel);
        this.setTitle("BlackJack Playground v1.0");
        this.setSize(450, 600);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    /**
     * Draws a new Card on gametable layeredPanel from cards Image file
     * @param c card to be drawn
     * @param dest hand the card belongs to
     */
    public void drawCard(Card c, String dest) {
        ImageIcon card;
        JLabel cardLabel;
        switch (dest) {
            case "dealer":
                if (dealerHand.size() != 1) {
                    card = new ImageIcon(c.getImage());
                } else {
                    hidden = c;
                    card = new ImageIcon("cards/b1fv.png");
                }
                cardLabel = new JLabel(card);
                cardLabel.setBounds(10 + 15 * dealerHand.size(), 20, card.getIconWidth(), card.getIconHeight());
                dealerHand.add(cardLabel);
                layeredPane.add(cardLabel, new Integer(dealerHand.size()));
                break;
            case "player":
                card = new ImageIcon(c.getImage());
                cardLabel = new JLabel(card);
                cardLabel.setBounds(10 + 13 * playerHand.size(), 270, card.getIconWidth(), card.getIconHeight());
                layeredPane.add(cardLabel, new Integer(playerHand.size()));
                playerHand.add(cardLabel);
                break;
            case "split":
                card = new ImageIcon(c.getImage());
                cardLabel = new JLabel(card);
                cardLabel.setBounds(230 + 13 * splitHand.size(), 270, card.getIconWidth(), card.getIconHeight());
                layeredPane.add(cardLabel, new Integer(splitHand.size()));
                splitHand.add(cardLabel);
                break;
            default:
        }
    }
    /**
     * Enables the buttons of the UI depending on command from Dealer
     * @param command command to enable/disable certain buttons
     */
    public void setEnableButton(String command) {
        switch (command) {
            case "deal":
                this.betslider.setEnabled(false);
                this.dealButton.setEnabled(false);
                this.stayButton.setEnabled(true);
                this.hitButton.setEnabled(true);
                this.doubleButton.setEnabled(true);
                break;
            case "stay":
                this.splitButton.setEnabled(false);
                this.hitButton.setEnabled(false);
                this.stayButton.setEnabled(false);
                this.dealButton.setEnabled(true);
                this.betslider.setEnabled(true);
                this.doubleButton.setEnabled(false);
                break;
            case "split":
                this.splitButton.setEnabled(false);
                this.doubleButton.setEnabled(false);
                break;
            case "bust":
                this.hitButton.setEnabled(false);
                this.stayButton.setEnabled(false);
                this.dealButton.setEnabled(true);
                this.betslider.setEnabled(true);
            case "hit":
                this.doubleButton.setEnabled(false);
                this.splitButton.setEnabled(false);
                break;
        }
    }
    /**
     * Updates the value of a hand in the JLabel below it
     * @param owner the hand witch value needs updating
     * @param value value to be updated
     */
    public void UpdateHandValue(String owner, String value) {
        switch (owner) {
            case "dealer":
                dealerAmount.setText(value);
                break;
            case "player":
                playerAmount.setText(value);
                break;
            case "split":
                splitAmount.setText(value);
                break;
        }
    }
    /**
     * Clears the panels from cards and value texts
     */
    public void clear() {
        for (JLabel jLabel : dealerHand) {
            layeredPane.remove(jLabel);
        }
        for (JLabel jLabel : playerHand) {
            layeredPane.remove(jLabel);
        }
        for (JLabel jLabel : splitHand) {
            layeredPane.remove(jLabel);
        }
        dealerHand = new ArrayList<>();
        playerHand = new ArrayList<>();
        splitHand = new ArrayList<>();
        dealerAmount.setText("");
	splitAmount.setText("");
        gameOver.setVisible(false);
        this.repaint();
    }
    /**
     * Clears the cards drawn to player, used after split
     */
    public void clearPlayer() {
        for (JLabel jLabel : playerHand) {
            layeredPane.remove(jLabel);
        }
        playerHand = new ArrayList<>();
        this.repaint();
    }
    /**
     * Disables either split or player hand, so the other seems like highlighted.
     * Used to tell player witch one of his two hands is active at the moment
     * @param owner 
     */
    public void disableHand(String owner) {
        switch (owner) {
            case "player":
                for (JLabel jLabel : playerHand) {
                    jLabel.disable();
                }
                this.repaint();
                break;
            case "split":
                for (JLabel jLabel : splitHand) {
                    jLabel.disable();
                }
                this.repaint();
                break;
            default:
                return;
        }
    }
    /**
     * Enables a Hand so player knows its active
     * @param owner 
     */
    public void enableHand(String owner) {
        switch (owner) {
            case "player":
                for (JLabel jLabel : playerHand) {
                    jLabel.enable();
                }
                this.repaint();
                break;
            case "split":
                for (JLabel jLabel : splitHand) {
                    jLabel.enable();
                }
                this.repaint();
                break;
            default:
                return;
        }
    }
    /**
     * In the game of blackjack, the second card of the dealer is only shown after
     * player has played his hands. This method takes care of that.
     */
    public void revealDealerCard() {
        if (dealerHand.size() == 0) {
            return;
        }
        layeredPane.remove(dealerHand.get(1));
        dealerHand.remove(1);
        ImageIcon card = new ImageIcon(hidden.getImage());
        JLabel cardLabel = new JLabel(card);
        cardLabel.setBounds(25, 20, card.getIconWidth(), card.getIconHeight());
        dealerHand.add(1, cardLabel);
        layeredPane.add(cardLabel, new Integer(2));
    }
    /**
     * Shows a Game Over notification tezt to player
     * @param text notification
     */
    public void gameOver(String text) {
        gameOver.setText(text);
        gameOver.setVisible(true);
    }
    /**
     * Updates the amount of player wealth and betSize shown on the upper right corner of screen.
     * @param wealthAmount
     * @param betAmount 
     */
    public void updateBets(int wealthAmount, int betAmount) {
        moneyLabel.setText(this.wealth + wealthAmount + "€");
        betLabel.setText(bet + betAmount + "€");
    }
}
