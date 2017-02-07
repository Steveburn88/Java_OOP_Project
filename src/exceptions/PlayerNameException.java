package exceptions;

/**
 * Created by stefan on 07.02.17.
 */
public class PlayerNameException extends Exception {
    String msg="";

    public PlayerNameException() {msg="Please input a valid Name";}
    public PlayerNameException(String s) {msg=s;}

    public String getMessage() {
        return msg;
    }
}