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
 */
public class Menu extends JFrame implements ActionListener {
    // Components
    JLabel User1 = new JLabel("Player 1:");
    JTextField txtUser1 = new JTextField("");
    JLabel User2 = new JLabel("Player 2:");
    JTextField txtUser2 = new JTextField("");
    JButton ng = new JButton("New Game");
    JButton lg = new JButton("Load");
    JRadioButton fourWinsRBtn = new JRadioButton("Four Wins");
    JRadioButton fiveWinsRBtn = new JRadioButton("Five Wins");
    JRadioButton gobangRBtn = new JRadioButton("Gobang");
    JLabel label = new JLabel();
    JPanel mainPanel;
    JPanel radioPanel;
    JPanel buttonPanel;
    JPanel settingsPanel;
    JSpinner rowNumberSpinner;
    JSpinner colNumberSpinner;
    ButtonGroup btngroup;

    // Constructor:
    public Menu(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Component Attributes
        ng.setToolTipText("Start a new Game");
        lg.setToolTipText("Load a saved Game");
        txtUser1.setToolTipText("Enter your name here, please.");
        txtUser2.setToolTipText("Enter your name here, please.");

        // Containers
        mainPanel = new JPanel();
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
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new TitledBorder("Players"));
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS));

        // Components -> Container -> main window
        mainPanel.add(User1);
        mainPanel.add(
                Box.createRigidArea(new Dimension(0,5)));
        mainPanel.add(txtUser1);
        mainPanel.add(
                Box.createRigidArea(new Dimension(0,5)));
        mainPanel.add(User2);
        mainPanel.add(
                Box.createRigidArea(new Dimension(0,5)));
        mainPanel.add(txtUser2);

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
        content.add(mainPanel, BorderLayout.NORTH);
        content.add(radioPanel, BorderLayout.CENTER);
        content.add(settingsPanel, BorderLayout.SOUTH);

        // Event handling
        ng.addActionListener(this);
        lg.addActionListener(this);


        // display main window
        pack();
        //setSize(300, 180);
        setLocation(200, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == ng) {
        	int numberOfRows=(int) rowNumberSpinner.getValue();
        	int numberOfCols=(int) colNumberSpinner.getValue();
            String playerOneName=txtUser1.getText();
            String playerTwoName=txtUser2.getText();
            Player p1 = new Player(playerOneName, (numberOfRows*numberOfCols)/2);
            Player p2 = new Player(playerTwoName, (numberOfRows*numberOfCols)/2);
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
            	if(!isColsRowsProductEven(numberOfCols, numberOfRows)){
            		throw new ColsRowsException("Product of number of rows and columns must be even!");
            	}
                int pTurn = 0;
                if (fourWinsRBtn.isSelected()) {
                    this.dispose();
                    Game fourwins = new Game("Four Wins", p1, p2, pTurn, numberOfCols, numberOfRows);
                } else if (fiveWinsRBtn.isSelected()) {
                    this.dispose();
                    five_wins.Game fivewins = new five_wins.Game("Five Wins", p1, p2, pTurn, numberOfCols, numberOfRows);
                } else if (gobangRBtn.isSelected()) {
                    this.dispose();
                    gobang.Game gobang = new gobang.Game("Gobang", p1, p2, pTurn, numberOfCols, numberOfRows);
                } else {
                    throw new NoGameSelectedException("No Game Mode selected. Please choose one Game Mode.");
                }
            }
            catch(PlayerNameException ex){
                JOptionPane.showMessageDialog(mainPanel, ex.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (NoGameSelectedException noGame) {
                JOptionPane.showMessageDialog(mainPanel, noGame.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (ColsRowsException ex) {
                JOptionPane.showMessageDialog(mainPanel, ex.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (source == lg) {
        	JFileChooser loadFileChooser = new JFileChooser(System.getProperty("user.dir")+"/saves");
        	loadFileChooser.setAcceptAllFileFilterUsed(false);
        	FileNameExtensionFilter filter = new FileNameExtensionFilter("Saved games", "save");
        	loadFileChooser.addChoosableFileFilter(filter);
    		int returnChooserValue = loadFileChooser.showOpenDialog(mainPanel);
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
                    JOptionPane.showMessageDialog(mainPanel, noGame.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(mainPanel, "Error while loading game! Technical message: "+ex.getMessage(),  "Info", JOptionPane.INFORMATION_MESSAGE);
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