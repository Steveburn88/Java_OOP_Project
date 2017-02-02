package four_wins.gui;

import four_wins.Field;
import four_wins.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Created by stefan on 14.01.17.
 */

public class Game extends JFrame implements ActionListener {
    // Components
    Player p1, p2;
    JButton ng = new JButton("New Game");
    JButton sg = new JButton("Save");
    JButton insertBtn= new JButton("Insert new");
    JButton quit = new JButton("Exit to Menu");
    JLabel coinsStatus = new JLabel();
    JLabel finishNote= new JLabel();
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
    Container content=new Container();

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
        coinsStatus.setText("<html>"+p1.getName()+": "+p1.getCoins()+"coins<br>"+p2.getName()+": "+p2.getCoins()+"coins</html>");


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
                buttons[row][col] = new JButton();
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].addActionListener(this);
                if(row!=0){
                	buttons[row][col].setEnabled(false);
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

        // Event handling
        ng.addActionListener(this);
        sg.addActionListener(this);
        quit.addActionListener(this);
        insertBtn.addActionListener(new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) {
      		String colNumberInput= (String) JOptionPane.showInputDialog(gamePanel, "Please enter the number of column where you want to put your coin.",  "Coin input", JOptionPane.INFORMATION_MESSAGE);
      		try{
      			int colNumber= Integer.parseInt(colNumberInput)-1;
      			if(isInputInColumnRange(colNumber)&&!isColumnFull(colNumber)){
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
        } );

        // display main window
        pack();
        setSize(720, 576);
        setLocation(100, 50);
        setVisible(true);
    }

/**
* This method is used to check if column is already full. It checks if there is a coin
* inserted in first row. If it is, it means that column is full and there is no more space
* for new coins. Returns true if it is full or false if not.
* @author Tiana Dabovic
* @param colNum Number of column which is checked for fullness.
* @return boolean Method returns true if column is full or false if is not
*/

public boolean isColumnFull(int colNum){
    if(!field.isEmpty(colNum, 0)) return true;
    return false;
}

/**
* This method is used to check if number of column in which player wants to insert coin is in
* range of possible values. Possible values are from 0 to number of field columns minus 1.
* Returns true if inputed number is in allowed range, else returns false
* @author Tiana Dabovic
* @param colNum Number of column which is checked for range.
* @return boolean Returns true if inputed number is in allowed range, else returns false
*/

public boolean isInputInColumnRange(int colNum){
	if(colNum>=0&&colNum<field.getColumn()) return true;
    return false;
}

/**
* This method finds first empty field to insert coin in given column. It sets value of player
* to the field and sets icon of appropriate player coin. If row where coin is inserted is making
* the column full of coins, first button of column is disabled for click.
* @author Tiana Dabovic
* @param colNum Number of column that is clicked.
* @param playerNum Number of player whose turn is.
* @param ImageIcon Icon of coin that has to be set on appropriate button where coin is inserted.
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
* This method uses inputed map to get rows and columns which have to be marked. It sets border
* of red color on buttons that make winning combination.
* @author Tiana Dabovic
* @param HashMap<String,Object> Map that contains numbers of rows and columns of win combin.
*/

public void markWinningStreak(HashMap<String,Object> scoring){
	buttons[(int) scoring.get("row1")][(int) scoring.get("col1")].setBorder(new LineBorder(Color.RED, 5));
    buttons[(int) scoring.get("row2")][(int) scoring.get("col2")].setBorder(new LineBorder(Color.RED, 5));
    buttons[(int) scoring.get("row3")][(int) scoring.get("col3")].setBorder(new LineBorder(Color.RED, 5));
    buttons[(int) scoring.get("row4")][(int) scoring.get("col4")].setBorder(new LineBorder(Color.RED, 5));
}

/**
* This method is used to disable all buttons when game is finished.
* @author Tiana Dabovic
*/

public void disableButtons(){
    for(int colNum=0;colNum<field.getColumn();colNum++){
    	buttons[0][colNum].setEnabled(false);
    }
    	insertBtn.setEnabled(false);
}
   
/**
* This method is used to display winners name in south region of frame.
* @author Tiana Dabovic
*/

public void setFinishNote(){
    finishNote.setHorizontalAlignment(SwingConstants.CENTER);
    content.add(finishNote, BorderLayout.SOUTH);
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
                    insertCoin(col);
                }
            }
        }
    }
 
/**
* This method is used to insert coin in given column. It sets the value of field to player value
* and sets appropriate coin image. It also checks for winning combination and if one is found
* game is finished. At the end player turn counter is increased by one, so the next player will
* get his turn when next input is made. 
* @author Tiana Dabovic
* @param col Number of column that is clicked or passed by input dialog.
*/
    
public void insertCoin(int col){
	// Reduce coin of current Player
    if (pTurn % 2 == 0) {
    	findWhereToInsertCoin(col, 1, g1);
        int n = p1.getCoins();
        p1.setCoins(n-1);
        coinsStatus.setText("<html>"+p1.getName()+": "+p1.getCoins()+"coins<br>"+p2.getName()+": "+p2.getCoins()+"coins</html>");
    }
    else if (pTurn % 2 == 1) {
    	findWhereToInsertCoin(col, 2, g2);
        int n = p2.getCoins();
        p2.setCoins(n-1);
        coinsStatus.setText("<html>"+p1.getName()+": "+p1.getCoins()+"coins<br>"+p2.getName()+": "+p2.getCoins()+"coins</html>");
    }
    HashMap<String, Object> scoring=field.checkForWin();
    String noteToShow=scoring.get("note").toString();
    if(noteToShow=="win p1"){
    	finishNote.setText("<html><div style='font-size:12px;color:red;font-style:italic;'>"+p1.getName()+" wins!</div></html>");
    	setFinishNote();
    	markWinningStreak(scoring);
    	disableButtons();    	
    }
    else if(noteToShow=="win p2"){
    	finishNote.setText("<html><div style='font-size:12px;color:red;font-style:italic;'>"+p2.getName()+" wins!</div></html>");
    	setFinishNote();
    	markWinningStreak(scoring);
    	disableButtons();
    }
    else if(p1.getCoins()==0&&p2.getCoins()==0){
    	finishNote.setText("<html><div style='font-size:12px;color:red;font-style:italic;'>No winner!</div></html>");
    	setFinishNote();
    	disableButtons();
    }
    // just a test from here:
    colSelected = col;
    rowSelected = row;
    System.out.println("col: "+col);
    System.out.println("row: "+row);
    pTurn += 1;
}

}

