package four_wins.gui;

import four_wins.Field;
import four_wins.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

/**
 * Created by stefan on 14.01.17.
 */

public class Game extends JFrame implements ActionListener {
    // Components
    Player p1, p2;
    JButton ng = new JButton("New Game");
    JButton sg = new JButton("Save");
    JButton quit = new JButton("Exit to Menu");
    JLabel label = new JLabel();
    JPanel buttonbox;
    JPanel gamePanel;
    final ImageIcon g1 = new ImageIcon(System.getProperty("user.dir")+"/graphics/star_blue.png");
    final ImageIcon g2 = new ImageIcon(System.getProperty("user.dir")+"/graphics/star_green.png");
    int pTurn = 0;
    Field field = new Field();
    int row, col, rowSelected, colSelected = 0;
    int rowTiles = field.getRow();
    int colTiles = field.getColumn();
    JButton[][] buttons = new JButton[rowTiles][colTiles];
    GridLayout myGrid = new GridLayout(rowTiles, colTiles);

    // Constructor:
    public Game(String title, Player p1, Player p2) {
        super(title);
        this.p1 = p1;
        this.p2 = p2;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new JPanel();
        gamePanel.setLayout(myGrid);

        // Component Attributes
        ng.setToolTipText("Start a new Game");
        label.setText("<html>"+p1.getName()+": "+p1.getCoins()+"coins<br>"+p2.getName()+": "+p2.getCoins()+"coins</html>");


        // Containers
        buttonbox = new JPanel();
        Container content = getContentPane();

        // Layout manager
        content.setLayout(new BorderLayout());
        buttonbox.setLayout(new FlowLayout());

        // Components -> Container -> main window
        buttonbox.add(ng);
        buttonbox.add(sg);
        buttonbox.add(quit);
        for (row = rowTiles-1; row >= 0; row--) {
            for (col = colTiles-1; col >= 0; col--) {
                buttons[row][col] = new JButton();
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].addActionListener(this);
                gamePanel.add(buttons[row][col]);
            }
        }
        content.add(label, BorderLayout.EAST);
        content.add(gamePanel, BorderLayout.CENTER);
        content.add(buttonbox, BorderLayout.NORTH);

        // Event handling
        ng.addActionListener(this);
        sg.addActionListener(this);
        quit.addActionListener(this);

        // display main window
        pack();
        setSize(640, 480);
        setLocation(100, 50);
        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //System.out.println(source.toString());
        if (source == ng) {
            // set Coins to Start value
            dispose();
            p1.setCoins(21);
            p2.setCoins(21);
            Game screen = new Game("Four Wins", p1, p2);
        }
        if (source == sg) {
            // TODO: implement serialization for saving purpose
        }
        if (source == quit) {
            dispose();
            Menu main = new Menu("Hauptmenü");
        }
        for (row = 0; row < rowTiles; row++) {
            for (col = 0; col < colTiles; col++) {
                if (source == buttons[row][col]) {
                    // Reduce coin of current Player
                    if (pTurn % 2 == 0) {
                        int n = p1.getCoins();
                        p1.setCoins(n-1);
                        label.setText("<html>"+p1.getName()+": "+p1.getCoins()+"coins<br>"+p2.getName()+": "+p2.getCoins()+"coins</html>");
                    }
                    else if (pTurn % 2 == 1) {
                        int n = p2.getCoins();
                        p2.setCoins(n-1);
                        label.setText("<html>"+p1.getName()+": "+p1.getCoins()+"coins<br>"+p2.getName()+": "+p2.getCoins()+"coins</html>");
                    }
                    // just a test from here:
                    colSelected = col;
                    rowSelected = row;
                    System.out.println("col: "+col);
                    System.out.println("row: "+row);
                    pTurn += 1;
                }
            }
        }
    }


}

