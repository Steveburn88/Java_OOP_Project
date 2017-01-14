package four_wins;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

/**
 * Created by stefan on 14.01.17.
 */
public class Gui extends JFrame implements ActionListener {
    // Components
    JButton ng = new JButton("New Game");
    JButton lg = new JButton("Load");
    JLabel label = new JLabel();
    JPanel buttonbox;

    // Constructor:
    public Gui(String title) {
        super(title);
        Player p1 = new Player("stefan", 21);
        Player p2 = new Player("tiana", 21);
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
        buttonbox.add(lg);
        content.add(label, BorderLayout.SOUTH);
        content.add(buttonbox, BorderLayout.NORTH);
        //content.add(field, BorderLayout.CENTER);
        // Event handling
        ng.addActionListener(this);
        //addWindowListener(new NewGame());
        // display main window
        pack();
        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO:
        // clear Field
        // set Coins to Start value
    }


}
