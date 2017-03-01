package globals;

import four_wins.Field;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;


/**
 * Created by stefan on 25.02.17.
 * An abstract game class, containing the attributes and methods used by all game classes.
 */
public abstract class Game extends JFrame implements ActionListener, Serializable {
    // Components
    public String title;
    public Player p1, p2;
    public JButton ng = new JButton("New Game");
    public static JButton sg;
    public static JButton insertBtn;
    public JButton quit = new JButton("Exit to Menu");
    public static JLabel coinsStatus;
    public static JLabel finishNote;
    public static JLabel turn;
    public JPanel buttonbox;
    public JPanel gamePanel;
    public JPanel pan;
    /*
    public ImageIcon g1 = new ImageIcon(System.getProperty("user.dir")+"/graphics/coins/"+p1.getCoin()+".png");
    public ImageIcon g2 = new ImageIcon(System.getProperty("user.dir")+"/graphics/coins/"+p2.getCoin()+".png");
    */
    public ImageIcon g1;
    public ImageIcon g2;
    public int pTurn = 0;
    public Field field;
    public int row, col;
    public int rowTiles;
    public int colTiles;
    public JButton[][] buttons;
    public GridLayout myGrid;
    public static Container content;
    public String loadedFileName="";

    // Constructor with buttons:
    public Game(String title, Player p1, Player p2, int pTurn, JButton[][] btns, int col, int row) {
        super(title);
        sg=new JButton("Save");
        insertBtn= new JButton("Insert new");
        coinsStatus = new JLabel();
        finishNote = new JLabel();
        turn = new JLabel();
        content=new Container();
        field = new Field(col, row);
        rowTiles=field.getRow();
        colTiles=field.getColumn();
        this.title = title;
        this.pTurn = pTurn;
        this.p1 = p1;
        this.p2 = p2;
        this.g1 = new ImageIcon(System.getProperty("user.dir")+"/graphics/coins/"+p1.getCoin()+".png");
        this.g2 = new ImageIcon(System.getProperty("user.dir")+"/graphics/coins/"+p2.getCoin()+".png");
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

                }
                gamePanel.add(buttons[row][col]);
            }
        }

         pan = new JPanel();
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
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
        // pack();
        setSize(720, 576);
        setLocation(100, 50);
        setVisible(true);
        setCoinsImageSize(buttons[0][0].getWidth(), buttons[0][0].getHeight());
    }

    // Constructor for a new game:
    public Game(String title, Player p1, Player p2, int pTurn, int col, int row) {
        this(title, p1, p2, pTurn, null, col, row);
    }

    /**
     * This method is used to resize coins images. Images size is set according to
     * size of a button that represents field of playing board.
     * @author Tiana Dabovic
     * @param width Width of button that represents field.
     * @param height Height of button that represents field.
     */

    public void setCoinsImageSize(int width, int height){
    
      if(width>70) width=70;
        
        if(height>70) height=70;
        Image newImg1 = g1.getImage();
        newImg1 = newImg1.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        g1 = new ImageIcon(newImg1);
        Image newImg2 = g2.getImage();
        newImg2 = newImg2.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        g2 = new ImageIcon(newImg2);
    }

    /**
     * This method is used to check if number of column in which player wants to insert coin is in
     * range of possible values. Possible values are from 0 to number of field columns minus 1.
     * Returns true if inputed number is in allowed range, else returns false
     * @author Tiana Dabovic, Stefan Schneider
     * @param colNum Number of column which is checked for range.
     * @return boolean Returns true if inputed number is in allowed range, else returns false
     */
    public boolean isInputInColumnRange(int colNum){
        if(colNum>=0 && colNum<colTiles) return true;
        else return false;
    }

    /**
     * This method is used to check if number of row in which player wants to insert coin is in
     * range of possible values. Possible values are from 0 to number of field rows minus 1.
     * Returns true if inputed number is in allowed range, else returns false
     * @author Tiana Dabovic, Stefan Schneider
     * @param rowNum Number of row which is checked for range.
     * @return boolean Returns true if inputted number is in allowed range, else returns false
     */
    public boolean isInputInRowRange(int rowNum){
        if(rowNum>=0 && rowNum<rowTiles) return true;
        else return false;
    }

    /**
     * This method uses inputed map to get rows and columns which have to be marked. It sets border
     * of red color on buttons that make winning combination.
     * @author Tiana Dabovic
     * @param scoring Map that contains numbers of rows and columns of win combin.
     */
    public void markWinningStreak(HashMap<String,Object> scoring){
        buttons[(int) scoring.get("row1")][(int) scoring.get("col1")].setBorder(new LineBorder(Color.RED, 5));
        buttons[(int) scoring.get("row2")][(int) scoring.get("col2")].setBorder(new LineBorder(Color.RED, 5));
        buttons[(int) scoring.get("row3")][(int) scoring.get("col3")].setBorder(new LineBorder(Color.RED, 5));
        buttons[(int) scoring.get("row4")][(int) scoring.get("col4")].setBorder(new LineBorder(Color.RED, 5));
    }

    /**
     * This method is used to disable all buttons when game is finished.
     * @author Tiana Dabovic, Stefan Schneider
     */
    public void disableButtons(){
        for (int rowNum = 0; rowNum < field.getRow(); rowNum++) {
            for (int colNum = 0; colNum < field.getColumn(); colNum++){
                buttons[rowNum][colNum].setEnabled(false);
            }
        }
        insertBtn.setEnabled(false);
        sg.setEnabled(false);
    }

    /**
     * This method updates the text in the game screen, eg. the current Player and the coins.
     *
     * @param p The next current Player.
     * @author Stefan Schneider
     */
    public void updateText(Player p) {
        coinsStatus.setText("<html>" + p1.getName() + ": " + p1.getCoins() + "coins<br>" + p2.getName() + ": " + p2.getCoins() + "coins</html>");
        turn.setText("Current Turn: " + p.getName());
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
    public void actionPerformed(ActionEvent e) {}

    /**
     * Displays the winner and disable the Button on the Field.
     * @auth Tiana Dabovic, Stefan Schneider
     * @param p The Player who wins
     * @param scoring The Hashmap contains the winning coins
     */
    public void win(Player p, HashMap<String, Object> scoring) {
        finishNote.setText("<html><div style='font-size:12px;color:red;font-style:italic;'>"+p.getName()+" wins!</div></html>");
        setFinishNote();
        markWinningStreak(scoring);
        disableButtons();
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
