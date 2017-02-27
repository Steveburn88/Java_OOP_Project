package five_wins;

import globals.Menu;
import globals.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
* The Game is used for making interface for game and provides logic for playing game.
* It implements Serializable class for I/O operations and ActionListener for buttons logic.
* It extends JFrame for using GUI components.
* 
*
* @author  Tiana Dabovic
* @version 1.1
* @since   1.1
*/

public class Game extends globals.Game implements ActionListener, Serializable {
    // Components
    /*Player p1, p2;
    JButton ng = new JButton("New Game");
    static JButton sg;
    static JButton insertBtn;
    JButton quit = new JButton("Exit to Menu");
    static JLabel finishNote;
    static JLabel turn;
    */
    JSpinner rowNumberSpinner;
    JSpinner colNumberSpinner;
    /*
    JPanel buttonbox;
    JPanel gamePanel;
    ImageIcon g1 = new ImageIcon(System.getProperty("user.dir")+"/graphics/coins/star_blue.png");
    ImageIcon g2 = new ImageIcon(System.getProperty("user.dir")+"/graphics/coins/star_green.png");
    int pTurn = 0;
    */
    public Field field;
    /*
    int row, col;
    int rowTiles;
    int colTiles;
    JButton[][] buttons;
    GridLayout myGrid;
    static Container content;
    public String loadedFileName="";
    */

    /**
     * Constructor for a saved game. An array of JButton is passed.
     * @param title the game title
     * @param p1 the first player
     * @param p2 the second player
     * @param pTurn turn counter
     * @param btns the board with the putted coins
     * @param col number of columns on the board
     * @param row number of rows on the board
     */
    public Game(String title, Player p1, Player p2, int pTurn, JButton[][] btns, int col, int row) {
        super(title, p1, p2, pTurn, btns, col, row);
        /*
        sg=new JButton("Save");
        insertBtn= new JButton("Insert new");
        finishNote = new JLabel();
        turn = new JLabel();
        content=new Container();
        */
        field = new Field(col, row);
        rowTiles=field.getRow();
        colTiles=field.getColumn();
        SpinnerNumberModel rowNumberModel = new SpinnerNumberModel(1, 1, field.getRow(), 1);
        rowNumberSpinner = new JSpinner(rowNumberModel);
        SpinnerNumberModel colNumberModel = new SpinnerNumberModel(1, 1, field.getColumn(), 1);
        colNumberSpinner = new JSpinner(colNumberModel);
        /*
        this.pTurn = pTurn;
        this.p1 = p1;
        this.p2 = p2;
        if (btns!=null) {
            buttons = btns;
        }
        else{
        	buttons = new JButton[rowTiles][colTiles];
        }
        myGrid = new GridLayout(rowTiles, colTiles);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new JPanel();
        gamePanel.setLayout(myGrid);

        // Component Attributes
        ng.setToolTipText("Start a new Game");
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
                }
                gamePanel.add(buttons[row][col]);
            }
        }

        JPanel pan=new JPanel();
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        pan.setBorder(new TitledBorder("Coins"));
        pan.add(insertBtn);
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
       // pack();
        setSize(720, 576);
        setLocation(100, 50);
        setVisible(true);
        setCoinsImageSize(buttons[0][0].getWidth(), buttons[0][0].getHeight());
        */
    }

    /**
     * Constructor for a new game. No JButton array is passed.
     * @param title the game title
     * @param p1 the first player
     * @param p2 the second player
     * @param pTurn turn counter
     * @param col number of columns on the board
     * @param row number of rows on the board
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
    public void findWhereToInsertCoin(int rowNum, int colNum, int playerNum, ImageIcon img){
        if(field.isEmpty(colNum, rowNum)){
            field.setCoin(colNum, rowNum, playerNum);
            buttons[rowNum][colNum].setIcon(img);
            buttons[rowNum][colNum].setIconTextGap(-10);
            buttons[rowNum][colNum].setDisabledIcon(img);
            buttons[rowNum][colNum].setEnabled(false);
        }
    }

    /**
    * This method uses inputed map to get rows and columns which have to be marked. It sets border
    * of red color on buttons that make winning combination.
    * @author Tiana Dabovic
    * @param scoring Map that contains numbers of rows and columns of win combin.
    */
    @Override
    public void markWinningStreak(HashMap<String,Object> scoring){
        buttons[(int) scoring.get("row1")][(int) scoring.get("col1")].setBorder(new LineBorder(Color.RED, 5));
        buttons[(int) scoring.get("row2")][(int) scoring.get("col2")].setBorder(new LineBorder(Color.RED, 5));
        buttons[(int) scoring.get("row3")][(int) scoring.get("col3")].setBorder(new LineBorder(Color.RED, 5));
        buttons[(int) scoring.get("row4")][(int) scoring.get("col4")].setBorder(new LineBorder(Color.RED, 5));
        buttons[(int) scoring.get("row5")][(int) scoring.get("col5")].setBorder(new LineBorder(Color.RED, 5));
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == ng) {
            // set Coins to Start value
            dispose();
            p1.setCoins((field.getColumn()*field.getRow())/2);
            p2.setCoins((field.getColumn()*field.getRow())/2);
            Game screen = new Game("Four Wins", p1, p2, 0, field.getColumn(), field.getRow());
        }
        else if (source == sg) {
        	String currentTime=Long.toString(System.currentTimeMillis());
        	String fileName=title + "_"+currentTime+".save";
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
            Menu main = new Menu("Main Menu");
        }
        else if (source == insertBtn) {
        	JPanel insertPanel = new JPanel();
        	insertPanel.add(new JLabel("Row:"));
        	insertPanel.add(rowNumberSpinner);
        	insertPanel.add(Box.createHorizontalStrut(15)); // a spacer
        	insertPanel.add(new JLabel("Column:"));
        	insertPanel.add(colNumberSpinner);

            int result = JOptionPane.showConfirmDialog(gamePanel, insertPanel,
                     "Insert new coin", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
               int numOfRow=(int) rowNumberSpinner.getValue();
               int numOfCol=(int) colNumberSpinner.getValue();
               if(field.isEmpty(numOfCol-1, numOfRow-1)){
            	   insertCoin(numOfRow-1,numOfCol-1);
               }
               else{
            	   JOptionPane.showMessageDialog(gamePanel, "Field is not empty!", "Warning", JOptionPane.WARNING_MESSAGE);
               }
            }
        }
        else for (row = 0; row < rowTiles; row++) {
            for (col = 0; col < colTiles; col++) {
                if (source == buttons[row][col]) {
                    insertCoin(col, row);
                }
            }
        }
    }


    /**
     * This method is used to insert a coin in given column. It sets the value of the field
     * to the player value and sets the appropriate coin image. It also checks for winning combination
     * and if one is found, the game ends. At the end the player turn counter is increased by one,
     * so the next player will get his turn when next input is made.
     * @auth Stefan Schneider
     * @param c Number of given column.
     * @param r Number of given row
     */
    public void insertCoin(int c, int r) {
        if (pTurn % 2 == 0) {
            int n = p1.getCoins();
            p1.setCoins(n - 1);
            findWhereToInsertCoin(r, c, 1, g1);
            updateText(p2);

        } else if (pTurn % 2 == 1) {
            int n = p2.getCoins();
            p2.setCoins(n - 1);
            findWhereToInsertCoin(r, c, 2, g2);
            updateText(p1);
        }
        HashMap<String, Object> scoring=field.checkForWin();
        String noteToShow=scoring.get("note").toString();
        if(noteToShow=="win p1"){
        	win(p1, scoring);
        } else if(noteToShow=="win p2"){
        	win(p2, scoring);
        } else if(field.isBoardFull()){
            finishNote.setText("<html><div style='font-size:12px;color:red;font-style:italic;'>Draw!</div></html>");
            setFinishNote();
            disableButtons();
        }
        pTurn += 1;
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