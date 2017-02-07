package four_wins;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by stefan on 14.01.17.
 */
public class Field implements Serializable {
    final int column = 7;
    final int row = 6;
    /**
     * Integer representation of the field.
     * 0 means empty, 1 and 2 are dedicated to the according player.
     */
	int[][] field = new int[column][row];

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Field() {

    }

    public boolean isEmpty(int c, int r) {
        if (field[c][r] == 0) return true;
        else return false;
    }

	/**
	 * This method is used to check if column is already full. It checks if there is a coin
	 * inserted in first row. If it is, it means that column is full and there is no more space
	 * for new coins. Returns true if it is full or false if not.
	 * @author Tiana Dabovic
	 * @param colNum Number of column which is checked for fullness.
	 * @return boolean Method returns true if column is full or false if is not
	 */
	public boolean isColumnFull(int colNum){
		if(!isEmpty(colNum, 0)) return true;
		else return false;
	}

    public void setCoin(int c, int r, int p) {
        field[c][r] = p;
    }
    
    /**
     * This method is used to create and return a map collection which contains numbers 
     * of columns and rows of winning combination. Map also contains a note which is used 
     * to determine if winner is player one or player two.
     * @author Tiana Dabovic
     * @param row1 Number of row of first coin in winning streak
     * @param row2 Number of row of second coin in winning streak
     * @param row3 Number of row of third coin in winning streak
     * @param row1 Number of row of fourth coin in winning streak
     * @param col1 Number of column of first coin in winning streak
     * @param col2 Number of column of second coin in winning streak
     * @param col3 Number of column of third coin in winning streak
     * @param col4 Number of column of fourth coin in winning streak
     * @return HashMap<String, Object> Method returns map with numbers of rows and 
     * columns of winning combination and note which determine which player created winning comb.
    */
    public HashMap<String, Object> fieldsToHighlight(int row1, int row2, int row3, int row4, int col1, int col2, int col3, int col4){
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	map.put("row1",row1);
    	map.put("row2",row2);
    	map.put("row3",row3);
    	map.put("row4",row4);
    	map.put("col1",col1);
    	map.put("col2",col2);
    	map.put("col3",col3);
    	map.put("col4",col4);
    	return map;
    }
    
    /**
     * This method is used to iterate through field array and check if four coins are
     * put in a row. If winning combination is found, it returns map with numbers of
     * rows and columns of winning combination and note that contains information about
     * winner. If combinations is not found, it returns map with empty note.
     * @author Tiana Dabovic
     * @return HashMap<String, Object> Method returns map with numbers of rows and 
     * columns of winning combination and note which determine which player created winning comb.
    */
    public HashMap<String, Object> checkForWin(){
    	String winningNote="";
    	HashMap<String, Object> mapToReturn = new HashMap<String, Object>();
    	//horizontal checking
    	outerloop:
    	for(int r=0; r<6; r++){
    		for (int c=0; c<7-3; c++){		
    			if(field[c][r]==1&&field[c+1][r]==1&&field[c+2][r]==1&&field[c+3][r]==1){
    				mapToReturn=fieldsToHighlight(r,r,r,r,c,c+1,c+2,c+3);
    				winningNote= "win p1";
    				break outerloop;
    			}
    	    	else if(field[c][r]==2&&field[c+1][r]==2&&field[c+2][r]==2&&field[c+3][r]==2){
    	    		mapToReturn=fieldsToHighlight(r,r,r,r,c,c+1,c+2,c+3);
    	    		winningNote="win p2";
    	    		break outerloop;
    	    	}
    		}
    	}
    	//vertical checking
    	outerloop1:
        	for(int c=0; c<7; c++){
        		for (int r=0; r<6-3; r++){     			
        			if(field[c][r]==1&&field[c][r+1]==1&&field[c][r+2]==1&&field[c][r+3]==1){
        				mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,c,c,c,c);
        				winningNote="win p1";
        				break outerloop1;
        			}
        	    	else if(field[c][r]==2&&field[c][r+1]==2&&field[c][r+2]==2&&field[c][r+3]==2){
        	    		mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,c,c,c,c);
        	    		winningNote="win p2";
        	    		break outerloop1;
        	    	}
        		}
        	}
    	
    	// right up diagonal checking
    	outerloop2:
        for (int c=3; c<7; c++){
            for (int r=0; r<6-3; r++){
            	
                if (field[c][r] == 1 && field[c-1][r+1] == 1 && field[c-2][r+2] == 1 && field[c-3][r+3] == 1){
                	mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,c,c-1,c-2,c-3);
                	winningNote="win p1";
    				break outerloop2;
                }
                else if (field[c][r] == 2 && field[c-1][r+1] == 2 && field[c-2][r+2] == 2 && field[c-3][r+3] == 2){
                	mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,c,c-1,c-2,c-3);
                	winningNote="win p2";
    				break outerloop2;
                }
            }
        }
        //left up diagonal check
        	outerloop3:
            for (int c=0; c<7-3; c++){
                for (int r=0; r<6-3; r++){
                    if (field[c][r] == 1 && field[c+1][r+1] == 1 && field[c+2][r+2] == 1 && field[c+3][r+3] == 1){
                    	mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,c,c+1,c+2,c+3);
                    	winningNote="win p1";
        				break outerloop3;
                    }
                    else if (field[c][r] == 2 && field[c+1][r+1] == 2 && field[c+2][r+2] == 2 && field[c+3][r+3] == 2){
                    	mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,c,c+1,c+2,c+3);
                    	winningNote="win p2";
        				break outerloop3;
                    }
                }
            }
    	mapToReturn.put("note",winningNote);
    	return mapToReturn;
    }
}
