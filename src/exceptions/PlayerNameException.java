package exceptions;

/**
* The PlayerNameException is used to handle exceptions
* in case if users input inappropriate player names.
* It extends Exception class.
* 
*
* @author  Tiana Dabovic, Stefan Schneider
* @version 1.0
* @since   1.0
*/

public class PlayerNameException extends Exception {
	
	/**
	* Constructor for catching exceptions in player name input without custom
	* message. It has not parameters.
	* @author Tiana Dabovic
	*/
    public PlayerNameException() { super("Please input a valid name"); }

    /**
	* Constructor for catching exceptions in player name input with custom message.
	* This constructor is called when you want to pass custom message to exception.
	* @author Tiana Dabovic
	* @param message Message to be displayed when exception is catched.
	*/
    public PlayerNameException(String message) {  super(message); }
}