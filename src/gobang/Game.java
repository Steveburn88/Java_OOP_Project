package gobang;

import globals.Player;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Created by stefan on 07.02.17.
 */
public class Game extends JFrame implements ActionListener, Serializable {
    // Components
    Player p1, p2;
    JButton ng = new JButton("New Game");
    JButton sg = new JButton("Save");
    JButton insertBtn= new JButton("Insert new");
    JButton quit = new JButton("Exit to Menu");
    JLabel coinsStatus = new JLabel();
    JLabel finishNote= new JLabel();
    JLabel turn = new JLabel();
    JPanel buttonbox;
    JPanel gamePanel;
    final ImageIcon g1 = new ImageIcon(System.getProperty("user.dir")+"/graphics/star_blue.png");
    final ImageIcon g2 = new ImageIcon(System.getProperty("user.dir")+"/graphics/star_green.png");
    int pTurn = 0;
    public Field field = new Field();
    int row, col;
    int rowTiles = field.getRow();
    int colTiles = field.getColumn();
    JButton[][] buttons = new JButton[rowTiles][colTiles];
    GridLayout myGrid = new GridLayout(rowTiles, colTiles);
    Container content=new Container();

    // Constructor with buttons:
    public Game(String title, Player p1, Player p2, int pTurn, JButton[][] btns) {
        super(title);
        this.pTurn = pTurn;
        this.p1 = p1;
        this.p2 = p2;
        if (btns!=null) {
            buttons = btns;
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new JPanel();
        gamePanel.setLayout(myGrid);

        // Component Attributes
        ng.setToolTipText("Start a new Game");
        coinsStatus.setText("<html>"+p1.getName()+": "+p1.getCoins()+"coins<br>"+p2.getName()+": "+p2.getCoins()+"coins</html>");
        if (pTurn % 2 == 0) turn.setText("Current Turn: "+p1.getName());
        if (pTurn % 2 == 1) turn.setText("Current Turn: "+p2.getName());

        // Containers
        buttonbox = new JPanel();
        content = getContentPane();

        // Layout manager
        content.setLayout(new BorderLayout());
        buttonbox.setLayout(new FlowLayout());

        // Components -> Container -> main window
        buttonbox.add(ng);
        buttonbox.add(sg);
        buttonbox.add(quit);
        for (row = 0; row < rowTiles; row++) {
            for (col = 0; col < colTiles; col++) {
                if (btns==null) {
                    buttons[row][col] = new JButton();
                    buttons[row][col].setBackground(Color.WHITE);
                    buttons[row][col].addActionListener(this);
                    if(row!=0){
                        buttons[row][col].setEnabled(false);
                    }
                }
                gamePanel.add(buttons[row][col]);
            }
        }

        JPanel pan=new JPanel();
        pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
        pan.setBorder(new TitledBorder("Coins"));
        pan.add(insertBtn);
        pan.add(coinsStatus);
        //with this we enable to hit button insert coin with enter
        getRootPane().setDefaultButton(insertBtn);
        content.add(pan, BorderLayout.EAST);
        content.add(gamePanel, BorderLayout.CENTER);
        content.add(buttonbox, BorderLayout.NORTH);
        content.add(turn, BorderLayout.SOUTH);

        // Event handling
        ng.addActionListener(this);
        sg.addActionListener(this);
        quit.addActionListener(this);
        insertBtn.addActionListener(this);

        // display main window
        pack();
        setSize(720, 576);
        setLocation(100, 50);
        setVisible(true);
    }

    // Constructor for a new game:
    public Game(String title, Player p1, Player p2, int pTurn) {
        this(title, p1, p2, pTurn, null);
    }


    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == ng) {
            // set Coins to Start value
            dispose();
            p1.setCoins(21);
            p2.setCoins(21);
            Game screen = new Game("Gobang", p1, p2, 0);
        }
        else if (source == quit) {
            dispose();
            Menu main = new Menu("Hauptmenü");
        }
    }
}
