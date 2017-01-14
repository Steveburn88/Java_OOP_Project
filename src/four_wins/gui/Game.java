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
    JButton ng = new JButton("New Game");
    JButton sg = new JButton("Save");
    JButton quit = new JButton("Exit to Menu");
    JLabel label = new JLabel();
    JPanel buttonbox;
    JPanel gamePanel;
    int pTurn = 0;
    Field field = new Field();
    int row, col = 0;
    int rowTiles = field.getRow();
    int colTiles = field.getColumn();
    JButton[][] buttons = new JButton[rowTiles][colTiles];
    GridLayout myGrid = new GridLayout(rowTiles, colTiles);

    // Constructor:
    public Game(String title, Player p1, Player p2) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new JPanel();
        gamePanel.setLayout(myGrid);

        // Component Attributes
        ng.setToolTipText("Start a new Game");
        label.setText(p1.getName()+": "+p1.getCoins()+"coins,   "+p2.getName()+": "+p2.getCoins()+"coins");

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
        content.add(label, BorderLayout.SOUTH);
        content.add(gamePanel, BorderLayout.CENTER);
        content.add(buttonbox, BorderLayout.NORTH);
        //content.add(field, BorderLayout.CENTER);

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
        if (source == ng) {
            // TODO:
            // clear Field
            // set Coins to Start value
        }
        if (source == sg) {
            // TODO: implement serialization for saving purpose
        }
        if (source == quit) {
            dispose();
            Menu main = new Menu("Hauptmenü");
        }

    }


}
