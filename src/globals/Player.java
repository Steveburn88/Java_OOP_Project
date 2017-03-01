package globals;

import java.io.Serializable;

/**
 * Created by stefan on 14.01.17.
 * The Player class represents a player. As far as we concern, a player has a name,
 * a coin of a certain style and an amount of coins.
 */
public class Player implements Serializable {
    String name;
    String coin;
    int coins;

    /**
     * Generic constructor
     * @param name the player name
     * @param coin the coin style
     * @param coins the amount of coins
     */
    public Player(String name, String coin, int coins) {
        this.name = name;
        this.coin = coin;
        this.coins = coins;
    }

    /**
     * Setters and Getters
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public String getCoin() {
        return coin;
    }

    public int getCoins() {
        return coins;
    }

    /**
     * This method is used to check if a capital letter is placed after blank space or hyphen.
     * If it is, it returns true value and if it is not, it returns false value.
     * @author Tiana Dabovic
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
    	String allowedCharactersRegex="[A-ZÄÖÜ][A-Za-zÄÖÜäöüß -]+";
        //String allowedCharactersRegex="[A-Z][A-Za-z -]+";
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
