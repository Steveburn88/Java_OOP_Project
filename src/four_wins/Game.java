package four_wins;

import globals.Menu;
import globals.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by stefan on 14.01.17.
 */

public class Game extends globals.Game implements ActionListener, Serializable {


    // Constructor with buttons:
    public Game(String title, Player p1, Player p2, int pTurn, JButton[][] btns, int col, int row) {
        super(title, p1, p2, pTurn, btns, col, row);
        for (int r = 0; r < rowTiles; r++) {
            for (int c = 0; c < colTiles; c++) {
                if (r != 0) {
                    buttons[r][c].setEnabled(false);
                }
            }
        }
        //setCoinsImageSize(buttons[0][0].getWidth(), buttons[0][0].getHeight());

    }

    // Constructor for a new game:
    public Game(String title, Player p1, Player p2, int pTurn, int col, int row) {
        this(title, p1, p2, pTurn, null, col, row);
    }

    /**
    * This method finds first empty field to insert coin in given column. It sets value of player
    * to the field and sets icon of appropriate player coin. If row where coin is inserted is making
    * the column full of coins, first button of column is disabled for click.
    * @author Tiana Dabovic
    * @param colNum Number of column that is clicked.
    * @param playerNum Number of player whose turn is.
    * @param img Icon of coin that has to be set on appropriate button where coin is inserted.
    */
    public void findWhereToInsertCoin(int colNum, int playerNum, ImageIcon img){
        for(int rowNum=field.getRow()-1;rowNum>=0;rowNum--){
            if(field.isEmpty(colNum, rowNum)){
                field.setCoin(colNum, rowNum, playerNum);
                buttons[rowNum][colNum].setIcon(img);
                buttons[rowNum][colNum].setIconTextGap(-10);
                buttons[rowNum][colNum].setDisabledIcon(img);
                if(rowNum==0){
                    buttons[rowNum][colNum].setEnabled(false);
                }
                break;
            }
        }
    }


    /**
    * This method is used to insert coin in given column. It sets the value of field to player value
    * and sets appropriate coin image. It also checks for winning combination and if one is found
    * game is finished. At the end player turn counter is increased by one, so the next player will
    * get his turn when next input is made.
    * @author Tiana Dabovic, Stefan Schneider
    * @param col Number of column that is clicked or passed by input dialog.
    */
    @SuppressWarnings("Duplicates")
    public void insertCoin(int col){
        if (pTurn % 2 == 0) {
            findWhereToInsertCoin(col, 1, g1);
            int n = p1.getCoins();
            p1.setCoins(n-1);
            updateText(p2);
        }
        else if (pTurn % 2 == 1) {
            findWhereToInsertCoin(col, 2, g2);
            int n = p2.getCoins();
            p2.setCoins(n-1);
            updateText(p1);
        }
        HashMap<String, Object> scoring=field.checkForWin();
        String noteToShow=scoring.get("note").toString();
        if(noteToShow=="win p1") {
            win(p1, scoring);
        }
        else if(noteToShow=="win p2") {
            win(p2, scoring);
        }
        else if(p1.getCoins()==0 && p2.getCoins()==0) {
            finishNote.setText("<html><div style='font-size:12px;color:red;font-style:italic;'>Draw!</div></html>");
            setFinishNote();
            disableButtons();
        }

        pTurn += 1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == ng) {
            // set Coins to Start value
            dispose();
            p1.setCoins((field.getColumn()*field.getRow())/2);
            p2.setCoins((field.getColumn()*field.getRow())/2);
            if (title.equals("Four Wins")) {
                four_wins.Game screen = new four_wins.Game(title, p1, p2, 0, field.getColumn(), field.getRow());
            } else if (title.equals("Five Wins")) {
                five_wins.Game screen = new five_wins.Game(title, p1, p2, 0, field.getColumn(), field.getRow());
            }else if (title.equals("Gobang")) {
                gobang.Game screen = new gobang.Game(title, p1, p2, 0, field.getColumn(), field.getRow());
            }
        }
        else if (source == sg) {
            String currentTime=Long.toString(System.currentTimeMillis());
            String fileName=title+"_"+currentTime+".save";
            if(!loadedFileName.isEmpty()){
                fileName=loadedFileName;
            }
            guiSaved(fileName);
        }
        else if (source == quit) {
            dispose();
            Menu main = new Menu("Hauptmenü");
        }
        else if (source == insertBtn) {
            String colNumberInput= (String) JOptionPane.showInputDialog(gamePanel, "Please enter the number of column where you want to put your coin.",  "Coin input", JOptionPane.INFORMATION_MESSAGE);
            try{
                int colNumber= Integer.parseInt(colNumberInput)-1;
                if(isInputInColumnRange(colNumber)&&!field.isColumnFull(colNumber)){
                    insertCoin(colNumber);
                }
                else{
                    JOptionPane.showMessageDialog(gamePanel, "Number must be in range 1-"+Integer.toString(field.getColumn())
                            +", or column is already full and you should pick another column!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
            catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(gamePanel, "Input is not a number!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
        else for (row = 0; row < rowTiles; row++) {
                for (col = 0; col < colTiles; col++) {
                    if (source == buttons[row][col]) {
                        insertCoin(col);
                    }
                }
            }
    }
}
