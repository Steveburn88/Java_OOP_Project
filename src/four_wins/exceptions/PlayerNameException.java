package four_wins.exceptions;

/**
 * Created by stefan on 07.02.17.
 */
public class PlayerNameException extends Exception {
    public PlayerNameException() {System.out.println("Please input a valid Name");}
    public PlayerNameException(String s) {System.out.println(s);}
}