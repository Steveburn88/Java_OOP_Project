package globals;

import java.io.Serializable;

/**
 * Created by stefan on 14.01.17.
 */
public class Player implements Serializable {
    String name;
    int coins;

    public Player(String name, int coins) {
        this.name = name;
        this.coins = coins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }
    /**
     * This method is used to check if a capital letter is placed after blank space or hyphen.
     * If it is, it returns true value and if it is not, it returns false value.
     * @author Tiana Dabovic
     * @param playerName Player name which user inputed
     * @return boolean Method returns boolean value true if capital letter is after all
     * blank spaces and hyphens or false if is not
    */
    
    public boolean isCapitalLetterAfterSpace(){
    	boolean isCapital=true;
    	for(int i=0;i<name.length()-1;i++){
    		char currentChar=name.charAt(i);
    		char nextChar=name.charAt(i+1);
    		if((currentChar==' '||currentChar=='-')&&!Character.isUpperCase(nextChar)){
    			isCapital=false;
    			break;
    		}
    	}
    	if(name.charAt(name.length()-1)==' '||name.charAt(name.length()-1)=='-'){
    		isCapital=false;
    	}
    	return isCapital;
    }
    
    /**
     * This method is used to check if player name contains only allowed characters.
     * @author Tiana Dabovic
     * @return boolean Method returns boolean value true if name contains only allowed characters.
     *  or false if not.
    */
    
    public boolean containsAllowedCharacters(){
    	String allowedCharactersRegex="[A-ZÄÖÜ][A-ZÄÖÜa-zäöüß -]+";
    	if(name.matches(allowedCharactersRegex)) return true;
    	else return false;
    }
    
    /**
     * This method checks if player name is same with other player name.
     * @author Tiana Dabovic
     * @param otherName Name of other player
     * @return boolean Method returns boolean value true if names are same, or false if not
    */
    
    public boolean areNamesSame(String otherName){
    	if(name.equals(otherName)) return true;
    	else return false;
    }
}
