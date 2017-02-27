package globals;

import exceptions.*;
import four_wins.Field;
import four_wins.Game;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by stefan on 14.01.17.
 * The Menu class represents the Main Menu and is the first thing the players see,
 * when starting the program.
 */
public class Menu extends JFrame implements ActionListener {
    // Components
    JPanel user1;
    JTextField txtUser1 = new JTextField("");
    JPanel user2;
    JTextField txtUser2 = new JTextField("");
    JButton ng = new JButton("New Game");
    JButton lg = new JButton("Load");
    JRadioButton fourWinsRBtn = new JRadioButton("Four Wins");
    JRadioButton fiveWinsRBtn = new JRadioButton("Five Wins");
    JRadioButton gobangRBtn = new JRadioButton("Gobang");
    JPanel userPanel;
    JPanel theme1;
    JPanel theme2;
    String[] shapes = {"Heart", "Hexagon", "Star"};
    String[] colors = {"Black", "Blue", "Green", "Red", "Transparent", "Yellow"};
    JComboBox shape1 = new JComboBox(shapes);
    JComboBox color1 = new JComboBox(colors);
    JComboBox shape2 = new JComboBox(shapes);
    JComboBox color2 = new JComboBox(colors);
    JPanel radioPanel;
    JPanel buttonPanel;
    JPanel settingsPanel;
    JSpinner rowNumberSpinner;
    JSpinner colNumberSpinner;
    ButtonGroup btngroup;

    /**
     * Constructor
     * @param title The Title will be seen in the window
     * @author Tiana Dabovic, Stefan Schneider
     */
    public Menu(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Component Attributes
        ng.setToolTipText("Start a new Game");
        lg.setToolTipText("Load a saved Game");
        txtUser1.setToolTipText("Enter your name here, please.");
        txtUser2.setToolTipText("Enter your name here, please.");

        // Containers
        userPanel = new JPanel();
        user1 = new JPanel();
        user2 =  new JPanel();
        theme1 = new JPanel();
        theme2 = new JPanel();
        radioPanel = new JPanel();
        buttonPanel = new JPanel();
        settingsPanel = new JPanel();
        btngroup = new ButtonGroup();
        btngroup.add(fourWinsRBtn);
        btngroup.add(fiveWinsRBtn);
        btngroup.add(gobangRBtn);
        Container content = getContentPane();

        // Layout manager
        content.setLayout(new BorderLayout());
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.PAGE_AXIS));
        userPanel.setBorder(new TitledBorder("Players"));
        user1.setLayout(new BoxLayout(user1, BoxLayout.PAGE_AXIS));
        user1.setBorder(new TitledBorder("Player 1"));
        user2.setLayout(new BoxLayout(user2, BoxLayout.PAGE_AXIS));
        user2.setBorder(new TitledBorder("Player 2"));
        theme1.setLayout(new BoxLayout(theme1, BoxLayout.LINE_AXIS));
        //theme1.setBorder(new TitledBorder("Theme"));
        theme2.setLayout(new BoxLayout(theme2, BoxLayout.LINE_AXIS));
        //theme2.setBorder(new TitledBorder("Theme"));
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS));

        // Components -> Container -> main window
        theme1.add(shape1);
        theme1.add(Box.createRigidArea(new Dimension(5,0)));
        theme1.add(color1);
        theme2.add(shape2);
        theme2.add(Box.createRigidArea(new Dimension(5,0)));
        theme2.add(color2);
        user1.add(txtUser1);
        user1.add(
                Box.createRigidArea(new Dimension(0,5)));
        user1.add(theme1);
        userPanel.add(user1);
        userPanel.add(
                Box.createRigidArea(new Dimension(0,15)));
        user2.add(txtUser2);
        user2.add(Box.createRigidArea(new Dimension(0,5)));
        user2.add(theme2);
        userPanel.add(user2);

        /*
        * Buttonbereich des JFrame einstellen
         */

        // BoxLayout für das radioPanel einstellen
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.LINE_AXIS));
        radioPanel.setBorder(new TitledBorder("Type"));
        
        // Radio Buttons hinzufügen
        radioPanel.add(fourWinsRBtn);
        radioPanel.add(fiveWinsRBtn);
        radioPanel.add(gobangRBtn);

        //BoxLayout für das buttonPanel einstellen
        buttonPanel.setLayout(
                new BoxLayout(
                        buttonPanel,BoxLayout.LINE_AXIS));

        // Buttons hinzufügen
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

        buttonPanel.add(Box.createHorizontalGlue());

        buttonPanel.add(ng);
        buttonPanel.add(Box.createRigidArea(new Dimension(5,0)));
        buttonPanel.add(lg);
        
        JPanel rowPanel=new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.LINE_AXIS));
        rowPanel.setBorder(new TitledBorder("Rows"));
        JPanel colPanel=new JPanel();
        colPanel.setLayout(new BoxLayout(colPanel, BoxLayout.LINE_AXIS));
        colPanel.setBorder(new TitledBorder("Columns"));
        SpinnerNumberModel rowNumberModel = new SpinnerNumberModel(6, 6, 20, 1);  
        rowNumberSpinner = new JSpinner(rowNumberModel);
        rowNumberSpinner.setToolTipText("Input number of rows. Min. 6, max. 20");
        SpinnerNumberModel colNumberModel = new SpinnerNumberModel(7, 7, 20, 1);  
        colNumberSpinner = new JSpinner(colNumberModel);
        colNumberSpinner.setToolTipText("Input number of columns. Min. 7, max. 20");
        rowPanel.add(rowNumberSpinner);
        colPanel.add(colNumberSpinner);
        JPanel rowColPanel=new JPanel();
        rowColPanel.setLayout(new BoxLayout(rowColPanel, BoxLayout.LINE_AXIS));
        rowColPanel.setBorder(new TitledBorder("Settings"));
        rowColPanel.add(rowPanel);
        rowColPanel.add(colPanel);
        settingsPanel.add(rowColPanel);
        settingsPanel.add(buttonPanel);
        
        // Hinzufügen der JPanel zum BorderLayout
        content.add(userPanel, BorderLayout.NORTH);
        content.add(radioPanel, BorderLayout.CENTER);
        content.add(settingsPanel, BorderLayout.SOUTH);

        // Event handling
        shape1.addActionListener(this);
        color1.addActionListener(this);
        shape2.addActionListener(this);
        color2.addActionListener(this);
        ng.addActionListener(this);
        lg.addActionListener(this);


        // display main window
        pack();
        //setSize(300, 180);
        setLocation(200, 100);
        setVisible(true);
    }

    /**
     * This code is performed every time an ActionEvent is fired.
     * @param e the ActionEvent
     * @throws PlayerNameException if the name does not fit the requirements.
     * @throws CoinColorException if both players choose the same coin color.
     * @throws ColsRowsException if both, columns and rows are odd when four wins is selected or
     *                           if board is not squared when five wins or gobang is selected.
     * @throws NoGameSelectedException if no game mode is selected.
     * @author Tiana Dabovic, Stefan Schneider
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == ng) {
            //JComboBox cb = (JComboBox)source;
            String coinShape1 = (String)shape1.getSelectedItem();
            String coinColor1 = (String)color1.getSelectedItem();
            String playerOneCoin = coinShape1.toLowerCase() + "_" + coinColor1.toLowerCase();
            String coinShape2 = (String)shape2.getSelectedItem();
            String coinColor2 = (String)color2.getSelectedItem();
            String playerTwoCoin = coinShape2.toLowerCase() + "_" + coinColor2.toLowerCase();
        	int numberOfRows=(int) rowNumberSpinner.getValue();
        	int numberOfCols=(int) colNumberSpinner.getValue();
            String playerOneName=txtUser1.getText();
            String playerTwoName=txtUser2.getText();
            Player p1 = new Player(playerOneName, playerOneCoin, (numberOfRows*numberOfCols)/2);
            Player p2 = new Player(playerTwoName, playerTwoCoin, (numberOfRows*numberOfCols)/2);
            try{
                if(!p1.containsAllowedCharacters()||!p2.containsAllowedCharacters()){
            		throw new PlayerNameException("Players name may only contain letters, "
            				+ "hyphens (-) and blanks and begins with a capital letter.");
            	}
            	if(!p1.isCapitalLetterAfterSpace()||!p2.isCapitalLetterAfterSpace()){
            		throw new PlayerNameException("After a blank or a hyphen a capital "
            				+ "letter is required.");
            	}
            	if(p1.areNamesSame(p2.getName())){
            		throw new PlayerNameException("Names can not be same!");
            	}
                if (coinColor1 == coinColor2) {
                    throw new CoinColorException("Colors cannot be the same");
                }
            	int pTurn = 0;
                if (fourWinsRBtn.isSelected()) {
                	if(!isColsRowsProductEven(numberOfCols, numberOfRows)){
                		throw new ColsRowsException("Product of number of rows and columns must be even!");
                	}
                    this.dispose();
                    Game fourwins = new Game("Four Wins", p1, p2, pTurn, numberOfCols, numberOfRows);
                } else if (fiveWinsRBtn.isSelected()) {
                	if(numberOfCols!=numberOfRows){
                		throw new ColsRowsException("Playing board must be square!");
                	}
                    this.dispose();
                    five_wins.Game fivewins = new five_wins.Game("Five Wins", p1, p2, pTurn, numberOfCols, numberOfRows);
                } else if (gobangRBtn.isSelected()) {
                	if(numberOfCols!=numberOfRows){
                		throw new ColsRowsException("Playing board must be square!");
                	}
                    this.dispose();
                    gobang.Game gobang = new gobang.Game("Gobang", p1, p2, pTurn, numberOfCols, numberOfRows);
                } else {
                    throw new NoGameSelectedException("No Game Mode selected. Please choose one Game Mode.");
                }
            }
            catch(PlayerNameException ex){
                JOptionPane.showMessageDialog(userPanel, ex.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (CoinColorException ex) {
                JOptionPane.showMessageDialog(userPanel, ex.getMessage(), "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (NoGameSelectedException noGame) {
                JOptionPane.showMessageDialog(userPanel, noGame.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (ColsRowsException ex) {
                JOptionPane.showMessageDialog(userPanel, ex.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (source == lg) {
        	JFileChooser loadFileChooser = new JFileChooser(System.getProperty("user.dir")+"/saves");
        	loadFileChooser.setAcceptAllFileFilterUsed(false);
        	FileNameExtensionFilter filter = new FileNameExtensionFilter("Saved games", "save");
        	loadFileChooser.addChoosableFileFilter(filter);
    		int returnChooserValue = loadFileChooser.showOpenDialog(userPanel);
    		if (returnChooserValue == JFileChooser.APPROVE_OPTION) {
    			File fileToLoad = loadFileChooser.getSelectedFile();
    			try {
                    if (fourWinsRBtn.isSelected()) {
                        loadGame(fileToLoad, "Four Wins");
                        this.dispose();
                    } else if (fiveWinsRBtn.isSelected()) {
                        loadGame(fileToLoad, "Five Wins");
                        this.dispose();
                    } else if (gobangRBtn.isSelected()) {
                    	loadGame(fileToLoad, "Gobang");
                        this.dispose();
                    } else {
                        throw new NoGameSelectedException("No Game Mode selected. Please choose one Game Mode.");
                    }
                } catch (NoGameSelectedException noGame) {
                    JOptionPane.showMessageDialog(userPanel, noGame.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(userPanel, "Error while loading game! Technical message: "+ex.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
                }
    		}
           

        }

    }
    
    /**
     * Creates InputStream for loading game. Loads file that is provided and creates 
     * appropriate Game object according to typeOfGame that is selected.
     *  If error occures it throws exception.
     * @author Tiana Dabovic, Stefan Schneider
     * @param fileToLoad File wich is chosen with file chooser to load.
     * @param typeOfGame String that determins wich type of game is chosen.
     * @throws IOException in case somethings wrong in reading objects from file.
     * @throws FileNotFoundException in case file could not be loaded because its not found.
     * @throws ClassNotFoundException in case there is no class for provided object.
     * @throws ClassCastException in case trying to cast different types of objects.
     */
    public void loadGame(File fileToLoad, String typeOfGame) throws IOException,FileNotFoundException,ClassNotFoundException,ClassCastException{
    	ObjectInputStream is=null;
    	try{
    		is = new ObjectInputStream(new FileInputStream(fileToLoad));
    		Player p1 = (Player)is.readObject();
    		Player p2 = (Player)is.readObject();
    		int pTurn = is.readInt();
    		JButton[][] buttons = (JButton[][])is.readObject();
    		if(typeOfGame.equals("Four Wins")){
    			Field field = (Field)is.readObject();
    			Game loaded = new Game(typeOfGame, p1, p2, pTurn, buttons, field.getColumn(), field.getRow());
                loaded.field = field;
                loaded.loadedFileName=fileToLoad.getName();
    		}
    		else if(typeOfGame.equals("Five Wins")){
    			five_wins.Field field = (five_wins.Field)is.readObject();
    			five_wins.Game loaded = new five_wins.Game(typeOfGame, p1, p2, pTurn, buttons, field.getColumn(), field.getRow());
                loaded.field = field;
                loaded.loadedFileName=fileToLoad.getName();
    		}
    		else if(typeOfGame.equals("Gobang")){
    			gobang.Field field = (gobang.Field)is.readObject();
    			gobang.Game loaded = new gobang.Game(typeOfGame, p1, p2, pTurn, buttons, field.getColumn(), field.getRow());
                loaded.field = field;
                loaded.loadedFileName=fileToLoad.getName();
    		}
    	}
    	catch(IOException ex){
    		System.out.println(ex.getMessage());
    		throw new IOException(ex.getMessage());
    	}
    	catch(ClassNotFoundException ex){
    		System.out.println(ex.getMessage());
    		throw new ClassNotFoundException(ex.getMessage());
    	}
    	catch(ClassCastException ex){
    		System.out.println(ex.getMessage());
    		throw new ClassCastException(ex.getMessage());
    	}
    	finally{
    		if(is!=null){
    			is.close();
    		}
    	}

    }
    
    /**
     * This method is used to check if product of number of columns and rows is even number.
     * Check is performed so coins could be equally set to both players.
     * @author Tiana Dabovic
     * @param cols Inputed number of columns
     * @param rows Inputed number of rows
     * @return boolean Method returns boolean value true if product is even number or false if is not
    */
    
    public boolean isColsRowsProductEven(int cols, int rows){
    	if((cols*rows)%2==0) return true;
    	else return false;
    }
}