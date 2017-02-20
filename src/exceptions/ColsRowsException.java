package exceptions;

/**
* The ColsRowsException is used to handle exceptions
* in case number of cols and rows are inappropriate.
* It extends Exception class.
* 
*
* @author  Tiana Dabovic
* @version 1.1
* @since   1.1
*/

public class ColsRowsException extends Exception {
	
	/**
	* Constructor for catching exceptions in number of cols and rows input without custom
	* message. It has not parameters.
	* @author Tiana Dabovic
	*/
    public ColsRowsException() { super("Error in evaluating number of columns and rows!"); }

    /**
	* Constructor for catching exceptions in number of cols and rows input with custom message.
	* This constructor is called when you want to pass custom message to exception.
	* @author Tiana Dabovic
	* @param message Message to be displayed when exception is catched.
	*/
    public ColsRowsException(String message) {  super(message); }
}