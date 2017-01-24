package four_wins;

/**
 * Created by stefan on 14.01.17.
 */
public class Field {
    final int column = 7;
    final int row = 6;
    /*
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
        for (int c=0; c<column; c++) {
            for (int r=0; r<row; r++) {
                field[c][r] = 0;
            }
        }

    }

    public boolean isEmpty(int c, int r) {
        if (field[c][r] == 0) return true;
        else return false;
    }

    public int getLowestRow(int c) {
        int lowestRow=0;
        for (int r = row-1; r>=0; r--) {
            if (isEmpty(c, r)) {
                lowestRow = r;
            }
        }
        return lowestRow;
    }

    public void setCoin(int c, int r, int p) {
        field[c][r] = p;
    }
}
