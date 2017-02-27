package gobang;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by stefan on 07.02.17.
 * The Field class contains the number of columns and rows of a game and which positions
 * are already used by which player.
 */
public class Field implements Serializable {
    int row;
    int column;

    /**
     * Integer representation of the field.
     * 0 means empty, 1 and 2 are dedicated to the according player.
     */
    int[][] field;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Field(int column, int row) {
    	this.column=column;
    	this.row=row;
    	field = new int[column][row];
    }

    public boolean isEmpty(int c, int r) {
        if (field[c][r] == 0) return true;
        else return false;
    }

    public void setCoin(int c, int r, int p) {
        field[c][r] = p;
    }

    /**
     * This method checks, if the last placed coin beats two opponent coins. If so, a HashMap with the according
     * positions is returned. The HashMap is empty otherwise.
     * @author Stefan Schneider
     * @param colSelected The column of the placed coin.
     * @param rowSelected The row of the placed coin.
     * @return A HashMap containing the positions of the coins to take away.
     */
    public HashMap<String, Integer> checkForTakeAway(int colSelected, int rowSelected) {
        HashMap<String, Integer> result = new HashMap();
        int p = field[colSelected][rowSelected];
        int q=0;
        if (p==1) {
            q = 2;
        } else if (p==2) {
            q=1;
        }
        // horizontal check to left
        if (colSelected>2 && field[colSelected-3][rowSelected]==p && field[colSelected-2][rowSelected]==q && field[colSelected-1][rowSelected]==q) {
            result.put("row1", rowSelected);
            result.put("row2", rowSelected);
            result.put("col1", colSelected-2);
            result.put("col2", colSelected-1);
        }

        // horizontel check to right
        else if (colSelected<column-3 && field[colSelected+3][rowSelected]==p && field[colSelected+2][rowSelected]==q && field[colSelected+1][rowSelected]==q) {
            result.put("row1", rowSelected);
            result.put("row2", rowSelected);
            result.put("col1", colSelected+2);
            result.put("col2", colSelected+1);
        }

        // vertical check up
        else if (rowSelected>2 && field[colSelected][rowSelected-3]==p && field[colSelected][rowSelected-2]==q && field[colSelected][rowSelected-1]==q) {
            result.put("row1", rowSelected-2);
            result.put("row2", rowSelected-1);
            result.put("col1", colSelected);
            result.put("col2", colSelected);
        }

        // vertical check down
        else if (rowSelected<row-3 && field[colSelected][rowSelected+3]==p && field[colSelected][rowSelected+2]==q && field[colSelected][rowSelected+1]==q) {
            result.put("row1", rowSelected+2);
            result.put("row2", rowSelected+1);
            result.put("col1", colSelected);
            result.put("col2", colSelected);
        }

        // diagonal left & up
        else if (colSelected>2 && rowSelected>2 && field[colSelected-3][rowSelected-3]==p && field[colSelected-2][rowSelected-2]==q && field[colSelected-1][rowSelected-1]==q) {
            result.put("row1", rowSelected-2);
            result.put("row2", rowSelected-1);
            result.put("col1", colSelected-2);
            result.put("col2", colSelected-1);
        }

        // diagonal left & down
        else if (colSelected>2 && rowSelected<row-3 && field[colSelected-3][rowSelected+3]==p && field[colSelected-2][rowSelected+2]==q && field[colSelected-1][rowSelected+1]==q) {
            result.put("row1", rowSelected+2);
            result.put("row2", rowSelected+1);
            result.put("col1", colSelected-2);
            result.put("col2", colSelected-1);
        }

        // diagonal right & down
        else if (colSelected<column-3 && rowSelected<row-3 && field[colSelected+3][rowSelected+3]==p && field[colSelected+2][rowSelected+2]==q && field[colSelected+1][rowSelected+1]==q) {
            result.put("row1", rowSelected+2);
            result.put("row2", rowSelected+1);
            result.put("col1", colSelected+2);
            result.put("col2", colSelected+1);
        }

        // diagonal right & up
        else if (colSelected<column-3 && rowSelected>2 && field[colSelected+3][rowSelected-3]==p && field[colSelected+2][rowSelected-2]==q && field[colSelected+1][rowSelected-1]==q) {
            result.put("row1", rowSelected-2);
            result.put("row2", rowSelected-1);
            result.put("col1", colSelected+2);
            result.put("col2", colSelected+1);
        }
        return result;
    }

    /**
     * This method is used to create and return a map collection which contains numbers
     * of columns and rows of winning combination. Map also contains a note which is used
     * to determine if winner is player one or player two.
     * @author Tiana Dabovic, Stefan Schneider
     * @param row1 Number of row of first coin in winning streak
     * @param row2 Number of row of second coin in winning streak
     * @param row3 Number of row of third coin in winning streak
     * @param row4 Number of row of fourth coin in winning streak
     * @param row5 Number of row of fifth coin in winning streak
     * @param col1 Number of column of first coin in winning streak
     * @param col2 Number of column of second coin in winning streak
     * @param col3 Number of column of third coin in winning streak
     * @param col4 Number of column of fourth coin in winning streak
     * @param col5 Number of column of the fifth coin in winning streak
     * @return HashMap<String, Object> Method returns map with numbers of rows and
     * columns of winning combination and note which determine which player created winning comb.
     */
    public HashMap<String, Object> fieldsToHighlight(int row1, int row2, int row3, int row4, int row5, int col1, int col2, int col3, int col4, int col5) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("row1",row1);
        map.put("row2",row2);
        map.put("row3",row3);
        map.put("row4",row4);
        map.put("row5",row5);
        map.put("col1",col1);
        map.put("col2",col2);
        map.put("col3",col3);
        map.put("col4",col4);
        map.put("col5",col5);
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
        for(int r=0; r<row; r++){
            for (int c=0; c<column-4; c++){
                if(field[c][r]==1 && field[c+1][r]==1 && field[c+2][r]==1 && field[c+3][r]==1 && field[c+4][r]==1){
                    mapToReturn=fieldsToHighlight(r,r,r,r,r,c,c+1,c+2,c+3,c+4);
                    winningNote= "win p1";
                    break outerloop;
                }
                else if(field[c][r]==2 && field[c+1][r]==2 && field[c+2][r]==2 && field[c+3][r]==2 && field[c+4][r]==2){
                    mapToReturn=fieldsToHighlight(r,r,r,r,r,c,c+1,c+2,c+3,c+4);
                    winningNote="win p2";
                    break outerloop;
                }
            }
        }
        //vertical checking
        outerloop1:
        for(int c=0; c<column; c++){
            for (int r=0; r<row-4; r++){
                if(field[c][r]==1 && field[c][r+1]==1 && field[c][r+2]==1 && field[c][r+3]==1 && field[c][r+4]==1){
                    mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,r+4,c,c,c,c,c);
                    winningNote="win p1";
                    break outerloop1;
                }
                else if(field[c][r]==2 && field[c][r+1]==2 && field[c][r+2]==2 && field[c][r+3]==2 && field[c][r+4]==2){
                    mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,r+4,c,c,c,c,c);
                    winningNote="win p2";
                    break outerloop1;
                }
            }
        }

        // right up diagonal checking
        outerloop2:
        for (int c=4; c<column; c++){
            for (int r=0; r<row-4; r++){

                if (field[c][r] == 1 && field[c-1][r+1] == 1 && field[c-2][r+2] == 1 && field[c-3][r+3] == 1 && field[c-4][r+4] == 1){
                    mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,r+4,c,c-1,c-2,c-3,c-4);
                    winningNote="win p1";
                    break outerloop2;
                }
                else if (field[c][r] == 2 && field[c-1][r+1] == 2 && field[c-2][r+2] == 2 && field[c-3][r+3] == 2 && field[c-4][r+4] == 2){
                    mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,r+4,c,c-1,c-2,c-3,c-4);
                    winningNote="win p2";
                    break outerloop2;
                }
            }
        }
        //left up diagonal check
        outerloop3:
        for (int c=0; c<column-4; c++){
            for (int r=0; r<row-4; r++){
                if (field[c][r] == 1 && field[c+1][r+1] == 1 && field[c+2][r+2] == 1 && field[c+3][r+3] == 1 && field[c+4][r+4] == 1){
                    mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,r+4,c,c+1,c+2,c+3,c+4);
                    winningNote="win p1";
                    break outerloop3;
                }
                else if (field[c][r] == 2 && field[c+1][r+1] == 2 && field[c+2][r+2] == 2 && field[c+3][r+3] == 2 && field[c+4][r+4] == 2){
                    mapToReturn=fieldsToHighlight(r,r+1,r+2,r+3,r+4,c,c+1,c+2,c+3,c+4);
                    winningNote="win p2";
                    break outerloop3;
                }
            }
        }
        mapToReturn.put("note",winningNote);
        return mapToReturn;
    }
}