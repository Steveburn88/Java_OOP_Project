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
 * The Game class provides an interface for the game and the logic for playing it.
 * It implements Serializable class for file I/O operations and ActionListener for buttons logic.
 * It extends the five_wins.Game and adds the coin takeaway functionality.
 *
 *
 * @author  Stefan Schneider
 * @version 1.1
 * @since   1.1
 */
public class Game extends five_wins.Game implements ActionListener, Serializable {
    // Components
    public Field field;

    /**
     * Constructor for a saved game. An array of JButton is passed.
     * @param title the game title
     * @param p1 the first plaer
     * @param p2 the second player
     * @param pTurn turn counter
     * @param btns the board with the putted coins
     * @param col the number of columns on the field
     * @param row the number of rows on the field
     */
    public Game(String title, Player p1, Player p2, int pTurn, JButton[][] btns, int col, int row) {
        super(title, p1, p2, pTurn, btns, col, row);
        // the following lines guarantee, that a gobang.Field is used
        field = new Field(col, row);
        rowTiles = field.getRow();
        colTiles = field.getColumn();
    }

    /**
     * Constructor for a new game. No JButton array is passed.
     * @param title the game title
     * @param p1 the first player
     * @param p2 the second player
     * @param pTurn turn counter
     * @param col the number of columns on the field
     * @param row the number of rows on the field
     */
    public Game(String title, Player p1, Player p2, int pTurn, int col, int row) {
        this(title, p1, p2, pTurn, null, col, row);
    }

    /**
     * The method inserts a coin, if the position is empty, checks for a win, checks for a takeaway
     * and updates the gui.
     * @param c Number of given column.
     * @param r Number of given row
     */
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

    /**
     * Displays the winner and disable the Button on the Field.
     * @param p The Player who wins
     * @param scoring The Hashmap contains the winning coins
     */
    @Override
    public void win(Player p, HashMap<String, Object> scoring) {
        finishNote.setText("<html><div style='font-size:12px;color:red;font-style:italic;'>" + p.getName() + " wins!</div></html>");
        setFinishNote();
        if (scoring != null) {
            markWinningStreak(scoring);
        }
        disableButtons();
    }

    /**
     * * This code is performed every time an ActionEvent is fired.
     * @param e the ActionEvent
     */
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
            else{
        		loadedFileName=fileName;
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
            Menu main = new Menu("Main Menu");
        } else for (row = 0; row < rowTiles; row++) {
            for (col = 0; col < colTiles; col++) {
                if (source == buttons[row][col]) {
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
