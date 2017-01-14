package four_wins;

/**
 * Created by stefan on 14.01.17.
 */
public class Field {
    final int column = 7;
    final int row = 6;
    // Boolean representation of the field. True means empty, false means occupied.
    boolean[][] field = new boolean[column][row];

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Field() {
        
    }

    public boolean isEmpty(int c, int r) {
        return field[c][r];
    }
}
