package exceptions;

/**
 * The CoinColorException is used to handle exceptions
 * in case if users choose the same Colors.
 * It extends Exception class.
 *
 * @author stefan
 */
public class CoinColorException extends Exception {

    /**
     * Constructor for catching exceptions in players coin color without custom message.
     */
    public CoinColorException() { super("Please choose different coin colors"); }

    /**
     * Constructor for catching exceptions in players coin color with custom message.
     * This constructor is called when you want to pass custom message to exception.
     * @param message Message to be displayed when exception is catched.
     */
    public CoinColorException(String message) { super(message); }

}
