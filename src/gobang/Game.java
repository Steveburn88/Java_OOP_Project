package gobang;

import globals.Menu;
import globals.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by stefan on 07.02.17.
 */
public class Game extends five_wins.Game implements ActionListener, Serializable {
    // Components
    /*
    Player p1, p2;
    JButton ng = new JButton("New Game");
    static JButton sg;
    static JButton insertBtn;
    JButton quit = new JButton("Exit to Menu");
    static JLabel coinsStatus;
    static JLabel finishNote;
    static JLabel turn;
    JPanel buttonbox;
    JPanel gamePanel;
    ImageIcon g1 = new ImageIcon(System.getProperty("user.dir")+"/graphics/coins/star_blue.png");
    ImageIcon g2 = new ImageIcon(System.getProperty("user.dir")+"/graphics/coins/star_green.png");
    int pTurn = 0;
    */
    public Field field;
    /*int row, col;
    int rowTiles;
    int colTiles;
    JButton[][] buttons;
    GridLayout myGrid;
    static Container content;
    public String loadedFileName = "";
    */

    // Constructor with buttons:
    public Game(String title, Player p1, Player p2, int pTurn, JButton[][] btns, int col, int row) {
        super(title, p1, p2, pTurn, btns, col, row);
        /*
        sg = new JButton("Save");
        insertBtn = new JButton("Insert new");
        coinsStatus = new JLabel();
        finishNote = new JLabel();
        turn = new JLabel();
        content = new Container();
        */
        field = new Field(col, row);
        rowTiles = field.getRow();
        colTiles = field.getColumn();
        /*
        this.pTurn = pTurn;
        this.p1 = p1;
        this.p2 = p2;
        if (btns != null) {
            buttons = btns;
        } else {
            buttons = new JButton[rowTiles][colTiles];
        }
        myGrid = new GridLayout(rowTiles, colTiles);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new JPanel();
        gamePanel.setLayout(myGrid);

        // Component Attributes
        ng.setToolTipText("Start a new Game");
        coinsStatus.setText("<html>" + p1.getName() + ": " + p1.getCoins() + "coins<br>" + p2.getName() + ": " + p2.getCoins() + "coins</html>");
        if (pTurn % 2 == 0) turn.setText("Current Turn: " + p1.getName());
        if (pTurn % 2 == 1) turn.setText("Current Turn: " + p2.getName());

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
                if (btns == null) {
                    buttons[row][col] = new JButton();
                    buttons[row][col].setBackground(Color.WHITE);
                    buttons[row][col].addActionListener(this);
                }
                gamePanel.add(buttons[row][col]);
            }
        }

        JPanel pan = new JPanel();
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
        setCoinsImageSize(buttons[0][0].getWidth(), buttons[0][0].getHeight());
        */
    }

    // Constructor for a new game:
    public Game(String title, Player p1, Player p2, int pTurn, int col, int row) {
        this(title, p1, p2, pTurn, null, col, row);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void insertCoin(int c, int r) {
        if (pTurn % 2 == 0) {
            int n = p1.getCoins();
            if (n == 0) {
                win(p2, null);
                return;
            }
            p1.setCoins(n - 1);
            if (field.isEmpty(c, r)) {
                field.setCoin(c, r, 1);
                buttons[r][c].setIcon(g1);
                buttons[r][c].setIconTextGap(-10);
                buttons[r][c].setDisabledIcon(g1);
                buttons[r][c].setEnabled(false);
                HashMap<String, Integer> takeaway = field.checkForTakeAway(c, r);
                if (!takeaway.isEmpty()) {
                    int enemyRow1 = takeaway.get("row1");
                    int enemyRow2 = takeaway.get("row2");
                    int enemyCol1 = takeaway.get("col1");
                    int enemyCol2 = takeaway.get("col2");
                    buttons[enemyRow1][enemyCol1].setIcon(null);
                    buttons[enemyRow1][enemyCol1].setEnabled(true);
                    field.setCoin(enemyCol1, enemyRow1, 0);
                    buttons[enemyRow2][enemyCol2].setIcon(null);
                    buttons[enemyRow2][enemyCol2].setEnabled(true);
                    field.setCoin(enemyCol2, enemyRow2, 0);
                    p2.setCoins(p2.getCoins() + 2);
                }
                updateText(p2);
            }
        } else if (pTurn % 2 == 1) {
            int n = p2.getCoins();
            if (n == 0) {
                win(p1, null);
                return;
            }
            p2.setCoins(n - 1);
            if (field.isEmpty(c, r)) {
                field.setCoin(c, r, 2);
                buttons[r][c].setIcon(g2);
                buttons[r][c].setIconTextGap(-10);
                buttons[r][c].setDisabledIcon(g2);
                buttons[r][c].setEnabled(false);
                HashMap<String, Integer> takeaway = field.checkForTakeAway(c, r);
                if (!takeaway.isEmpty()) {
                    int enemyRow1 = takeaway.get("row1");
                    int enemyRow2 = takeaway.get("row2");
                    int enemyCol1 = takeaway.get("col1");
                    int enemyCol2 = takeaway.get("col2");
                    buttons[enemyRow1][enemyCol1].setIcon(null);
                    buttons[enemyRow1][enemyCol1].setEnabled(true);
                    field.setCoin(enemyCol1, enemyRow1, 0);
                    buttons[enemyRow2][enemyCol2].setIcon(null);
                    buttons[enemyRow2][enemyCol2].setEnabled(true);
                    field.setCoin(enemyCol2, enemyRow2, 0);
                    p1.setCoins(p1.getCoins() + 2);
                }
                updateText(p1);
            }
        }
        HashMap<String, Object> scoring = field.checkForWin();
        String noteToShow = scoring.get("note").toString();
        if (noteToShow == "win p1") {
            win(p1, scoring);
        } else if (noteToShow == "win p2") {
            win(p2, scoring);
        } else if (p1.getCoins() == 0 && p2.getCoins() == 0) {
            finishNote.setText("<html><div style='font-size:12px;color:red;font-style:italic;'>Draw!</div></html>");
            setFinishNote();
            disableButtons();
        }
        pTurn += 1;
    }

    @Override
    public void win(Player p, HashMap<String, Object> scoring) {
        finishNote.setText("<html><div style='font-size:12px;color:red;font-style:italic;'>" + p.getName() + " wins!</div></html>");
        setFinishNote();
        if (scoring != null) {
            markWinningStreak(scoring);
        }
        disableButtons();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == ng) {
            // set Coins to Start value
            dispose();
            p1.setCoins((field.getColumn() * field.getRow()) / 2);
            p2.setCoins((field.getColumn() * field.getRow()) / 2);
            Game screen = new Game("Gobang", p1, p2, 0, field.getColumn(), field.getRow());
        } else if (source == sg) {
            String currentTime = Long.toString(System.currentTimeMillis());
            String fileName = title + "_" + currentTime + ".save";
            if (!loadedFileName.isEmpty()) {
                fileName = loadedFileName;
            }
            try {
                saveGame(fileName);
                ImageIcon sgIcon = new ImageIcon(System.getProperty("user.dir") + "/graphics/saveIcon.png");
                String saveMsg = "Succesfully saved to " + fileName + " file!";
                JOptionPane savePane = new JOptionPane(saveMsg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, sgIcon, new Object[]{});
                final JDialog saveDialog = savePane.createDialog(gamePanel, fileName);
                saveDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                Timer timer = new Timer(3000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveDialog.dispose();
                    }
                });
                timer.start();
                saveDialog.setVisible(true);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(gamePanel, "Error while saving game! Technical message: " + ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else if (source == quit) {
            dispose();
            Menu main = new Menu("Hauptmenü");
        } else for (row = 0; row < rowTiles; row++) {
            for (col = 0; col < colTiles; col++) {
                if (source == buttons[row][col]) {
                    System.out.println("ROW: " + row + "\nCOLUMN: " + col + "\n");
                    insertCoin(col, row);
                }
            }
        }
    }

    /**
     * Creates OutputStream for saving game. If there is file with provided filename, game is
     * saved to that file.
     * If not new file is created in folder saves. If error occures it throws ioexception.
     * @author Tiana Dabovic, Stefan Schneider
     * @param fileName Name of file where game should be saved.
     * @throws IOException in case somethings wrong in writing objects to file.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void saveGame(String fileName) throws IOException{
        ObjectOutputStream os=null;
        try{
            os = new ObjectOutputStream(new FileOutputStream("saves/"+fileName));
            os.writeObject(p1);
            os.writeObject(p2);
            os.writeInt(pTurn);
            os.writeObject(buttons);
            os.writeObject(field);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            throw new IOException(ex.getMessage());
        }
        finally{
            if(os!=null){
                os.close();
            }
        }
    }

}
