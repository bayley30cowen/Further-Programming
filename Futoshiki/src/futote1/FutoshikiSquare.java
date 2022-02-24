
package futote1;

/**
 *
 * @author 184514
 * @version 05/05/2019
 */
import java.io.Serializable;
import java.util.HashSet;
public class FutoshikiSquare implements Serializable {

    private int row;
    private int col;
    private int value;
    private HashSet<Integer> cantbeNum;
    private boolean startSquare = false;

    /**
     * @param row row of square
     * @param col column of square
     */
    public FutoshikiSquare(int row, int col) {
        this.row = row;
        this.col = col;
        value= 0;
        cantbeNum = new HashSet<>();
    }

    /**
     * Returns the row of the square.
     *
     * @return row of the square.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns column of the square.
     *
     * @return column of the square
     */
    public int getCol() {
        return col;
    }

    public void setNumber(int val) {
        this.value = val;
    }

    public int getNumber() {
        return value;
    }

    public void setImpossible(int notNum) {
        cantbeNum.add(notNum);
    }
    
    public boolean getInitial () {
        return startSquare ;
    }
    
    public void setInitial () {
        startSquare = true;
    }
    public void removeNotNumber(int canBe) {
        try {
            cantbeNum.remove(canBe);
        } catch (Exception e) {
            System.err.println(canBe + " is not in the list");
        }
    }
    public HashSet getCantBeNum() {
        return cantbeNum;
    }

}
