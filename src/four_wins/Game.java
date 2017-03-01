package four_wins;

import globals.Menu;
import globals.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by stefan on 14.01.17.
 * This class implements the game four wins.
 * @author Tiana Dabovic, Stefan Schneider
 */
public class Game extends globals.Game implements ActionListener, Serializable {


    /**
     * Constructor for a saved game. An array of JButton is passed.
     * @param title The game title.
     * @param p1 the first player.
     * @param p2 the second player.
     * @param pTurn turn counter
     * @param btns the board with the putted coins.
     * @param col number of columns on the board.
     * @param row number of rows on the board.
     */
    public Game(String title, Player p1, Player p2, int pTurn, JButton[][] btns, int col, int row) {
        super(title, p1, p2, pTurn, btns, col, row);
        for (int r = 0; r < rowTiles; r++) {
            for (int c = 0; c < colTiles; c++) {
                if (r != 0) {
                    buttons[r][c].setEnabled(false);
                }
            }
        }
    }

    /**
     * Constructor for a new game. No JButton array is passed.
     * @param title The game title.
     * @param p1 the first player.
     * @param p2 the second player.
     * @param pTurn turn counter
     * @param col number of columns on the board.
     * @param row number of rows on the board.
     */
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
        if(noteToShow.equals("win p1")) {
            win(p1, scoring);
        }
        else if(noteToShow.equals("win p2")) {
            win(p2, scoring);
        }
        else if(p1.getCoins()==0 && p2.getCoins()==0) {
            finishNote.setText("<html><div style='font-size:12px;color:red;font-style:italic;'>Draw!</div></html>");
            setFinishNote();
            disableButtons();
        }

        pTurn += 1;
    }

    /**
     * This code is performed every time an ActionEvent is fired.
     * @param e the ActionEvent
     * @throws NumberFormatException if column input is not a number.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void actionPerformed(ActionEvent e) {
    	 
        Object source = e.getSource();
        if (source == ng) {
            // set Coins to Start value
            dispose();
            p1.setCoins((field.getColumn()*field.getRow())/2);
            p2.setCoins((field.getColumn()*field.getRow())/2);
            if (title.equals("Four Wins")) {
               new four_wins.Game(title, p1, p2, 0, field.getColumn(), field.getRow());
            } else if (title.equals("Five Wins")) {
                 new five_wins.Game(title, p1, p2, 0, field.getColumn(), field.getRow());
            }else if (title.equals("Gobang")) {
               new gobang.Game(title, p1, p2, 0, field.getColumn(), field.getRow());
            }
        }
        else if (source == sg) {
            String currentTime=Long.toString(System.currentTimeMillis());
            String fileName=title+"_"+currentTime+".save";
            if(!loadedFileName.isEmpty()){
                fileName=loadedFileName;
            }
            else{
        		loadedFileName=fileName;
        	}
            try {
                saveGame(fileName);
                ImageIcon sgIcon = new ImageIcon(System.getProperty("user.dir") + "/graphics/saveIcon.png");
                String saveMsg = "Succesfully saved to " + fileName + " file!";
                JOptionPane savePane = new JOptionPane(saveMsg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, sgIcon, new Object[]{});
                //kad se jednom kreira da se ne mjenja
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
        }
        else if (source == quit) {
        	
            dispose();
            new Menu("Main Menu");
        }
        else if (source == insertBtn) {
            String colNumberInput=  JOptionPane.showInputDialog(gamePanel, "Please enter the number of column where you want to put your coin.",  "Coin input", JOptionPane.INFORMATION_MESSAGE);
           if (colNumberInput != null ){
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
