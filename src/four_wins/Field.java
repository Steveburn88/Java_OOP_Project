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

    }

    public boolean isEmpty(int c, int r) {
        if (field[c][r] == 0) return true;
        else return false;
    }

    public void setCoin(int c, int r, int p) {
        field[c][r] = p;
    }
}
