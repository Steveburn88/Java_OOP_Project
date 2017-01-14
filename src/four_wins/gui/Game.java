package four_wins.gui;

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

    // Constructor:
    public Game(String title, Player p1, Player p2) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Player p1 = new Player("stefan", 21);
        //Player p2 = new Player("tiana", 21);
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
        content.add(label, BorderLayout.SOUTH);
        content.add(buttonbox, BorderLayout.NORTH);
        //content.add(field, BorderLayout.CENTER);
        // Event handling
        ng.addActionListener(this);
        sg.addActionListener(this);
        quit.addActionListener(this);
        // display main window
        pack();
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
