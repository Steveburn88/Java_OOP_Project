package globals;

import exceptions.*;
import four_wins.Field;
import four_wins.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by stefan on 14.01.17.
 */
public class Menu extends JFrame implements ActionListener {
    // Components
    JLabel User1 = new JLabel("Player 1:");
    JTextField txtUser1 = new JTextField("");
    JLabel User2 = new JLabel("Player 2:");
    JTextField txtUser2 = new JTextField("");
    JButton ng = new JButton("New Game");
    JButton lg = new JButton("Load");
    JRadioButton fourWinsRBtn = new JRadioButton("Four Wins");
    JRadioButton fiveWinsRBtn = new JRadioButton("Five Wins");
    JRadioButton gobangRBtn = new JRadioButton("Gobang");
    JLabel label = new JLabel();
    JPanel mainPanel;
    JPanel radioPanel;
    JPanel buttonPanel;
    ButtonGroup btngroup;

    // Constructor:
    public Menu(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Component Attributes
        ng.setToolTipText("Start a new Game");
        lg.setToolTipText("Load a saved Game");
        txtUser1.setToolTipText("Enter your name here, please.");
        txtUser2.setToolTipText("Enter your name here, please.");

        // Containers
        mainPanel = new JPanel();
        radioPanel = new JPanel();
        buttonPanel = new JPanel();
        btngroup = new ButtonGroup();
        btngroup.add(fourWinsRBtn);
        btngroup.add(fiveWinsRBtn);
        btngroup.add(gobangRBtn);
        Container content = getContentPane();

        // Layout manager
        content.setLayout(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        // Components -> Container -> main window
        mainPanel.add(User1);
        mainPanel.add(
                Box.createRigidArea(new Dimension(0,5)));
        mainPanel.add(txtUser1);
        mainPanel.add(
                Box.createRigidArea(new Dimension(0,5)));
        mainPanel.add(User2);
        mainPanel.add(
                Box.createRigidArea(new Dimension(0,5)));
        mainPanel.add(txtUser2);

        /*
        * Buttonbereich des JFrame einstellen
         */

        // BoxLayout für das radioPanel einstellen
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.LINE_AXIS));

        // Radio Buttons hinzufügen
        radioPanel.add(fourWinsRBtn);
        radioPanel.add(fiveWinsRBtn);
        radioPanel.add(gobangRBtn);

        //BoxLayout für das buttonPanel einstellen
        buttonPanel.setLayout(
                new BoxLayout(
                        buttonPanel,BoxLayout.LINE_AXIS));

        // Buttons hinzufügen
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

        buttonPanel.add(Box.createHorizontalGlue());

        buttonPanel.add(ng);
        buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
        buttonPanel.add(lg);

        // Hinzufügen der JPanel zum BorderLayout
        content.add(mainPanel, BorderLayout.NORTH);
        content.add(radioPanel, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);

        // Event handling
        ng.addActionListener(this);
        lg.addActionListener(this);


        // display main window
        //pack();
        setSize(300, 180);
        setLocation(200, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == ng) {
            String playerOneName=txtUser1.getText();
            String playerTwoName=txtUser2.getText();
            Player p1 = new Player(playerOneName, 21);
            Player p2 = new Player(playerTwoName, 21);
            try{
                if(!p1.containsAllowedCharacters()||!p2.containsAllowedCharacters()){
            		throw new PlayerNameException("Players name may only contain letters, "
            				+ "hyphens (-) and blanks and begins with a capital letter.");
            	}
            	if(!p1.isCapitalLetterAfterSpace()||!p2.isCapitalLetterAfterSpace()){
            		throw new PlayerNameException("After a blank or a hyphen a capital "
            				+ "letter is required.");
            	}
            	if(p1.areNamesSame(p2.getName())){
            		throw new PlayerNameException("Names can not be same!");
            	}
                int pTurn = 0;
                if (fourWinsRBtn.isSelected()) {
                    this.dispose();
                    four_wins.Game fourwins = new Game("Four Wins", p1, p2, pTurn);
                } else if (fiveWinsRBtn.isSelected()) {
                    this.dispose();
                    //TODO: implement Five Wins
                } else if (gobangRBtn.isSelected()) {
                    this.dispose();
                    gobang.Game gobang = new gobang.Game("Gobang", p1, p2, pTurn);
                } else {
                    throw new NoGameSelectedException("No Game Mode selected. Please choose one Game Mode.");
                }
            }
            catch(PlayerNameException ex){
                JOptionPane.showMessageDialog(mainPanel, ex.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (NoGameSelectedException noGame) {
                JOptionPane.showMessageDialog(mainPanel, noGame.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (source == lg) {
            try {
                if (fourWinsRBtn.isSelected()) {
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream("FourWins.save"));
                    Player p1 = (Player)is.readObject();
                    Player p2 = (Player)is.readObject();
                    int pTurn = is.readInt();
                    Field field = (Field)is.readObject();
                    JButton[][] buttons = (JButton[][])is.readObject();
                    this.dispose();
                    Game loaded = new Game("Four Wins", p1, p2, pTurn, buttons);
                    loaded.field = field;
                } else if (fiveWinsRBtn.isSelected()) {
                    this.dispose();
                    //TODO: implement Five Wins
                } else if (gobangRBtn.isSelected()) {
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream("Gobang.save"));
                    Player p1 = (Player)is.readObject();
                    Player p2 = (Player)is.readObject();
                    int pTurn = is.readInt();
                    gobang.Field field = (gobang.Field)is.readObject();
                    JButton[][] buttons = (JButton[][])is.readObject();
                    this.dispose();
                    gobang.Game loaded = new gobang.Game("Gobang", p1, p2, pTurn, buttons);
                    loaded.field = field;
                } else {
                    throw new NoGameSelectedException("No Game Mode selected. Please choose one Game Mode.");
                }
            } catch (IOException io) {
                System.err.println(io.toString());
            } catch (ClassNotFoundException cnfe) {
                System.err.println(cnfe.toString());
            } catch (NoGameSelectedException noGame) {
                JOptionPane.showMessageDialog(mainPanel, noGame.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }
}