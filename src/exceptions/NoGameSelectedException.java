package exceptions;

/**
 * Created by stefan on 08.02.17.
 */
public class NoGameSelectedException extends Exception {
    String msg="";

    public NoGameSelectedException() {msg="Please choose a Game Mode.";}
    public NoGameSelectedException(String s) {msg=s;}

    public String getMessage() {
        return msg;
    }
}
