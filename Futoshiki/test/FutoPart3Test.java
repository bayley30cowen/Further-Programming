import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import futote1.FutoshikiGrid;
import futote1.FutoshikiSquare;

/**
 *
 * @author 184514
 * @version 09/05/2019
 */
public class FutoPart3Test {

    public FutoPart3Test() {
    }

    @Test
    public void setSquare() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.setSquare(0, 0, 4);
        int tmp = grid.getSquare(0, 0).getNumber();
        assertEquals(4, tmp);
    }

    @Test
    public void setRowConstraint() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.setRowConstraint(0, 0, "<");
        String tmp = grid.getRowConstraint(0, 0).constraintType();
        assertEquals("<", tmp);
    }

    @Test
    public void setColConstraint() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.setColConstraint(0, 0, "^");
        String tmp = grid.getColConstraint(0, 0).constraintType();
        assertEquals("^", tmp);
    }
    
    @Test
    public void isFull() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        assertEquals(false, grid.checkIfFull());
    }

    @Test
    public void fillPuzzle() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        grid.fillPuzzle(1,1);
        int testNum = 0;
        boolean fillTest = false;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid.getSquare(i, j).getNumber() > 0 && grid.getSquare(i, j).getNumber() <= 5) {
                    testNum++;
                }
                if (testNum > 0) {
                    fillTest = true;
                }
            }
        }
        assertEquals(true, fillTest);

        int testCol = 0;
        boolean fillColTest = false;
        for (int i = 0; i < 5 - 1; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid.getColConstraint(i, j).constraintType().equals("^") || grid.getColConstraint(i, j).constraintType().equals("v")) {
                    testCol++;
                }
                if (testCol > 0) {
                    fillColTest = true;
                }
            }
        }
        assertEquals(true, fillColTest);

        int testRow = 0;
        boolean fillRowTest = false;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5 - 1; j++) {
                if (grid.getRowConstraint(i, j).constraintType().equals("<") || grid.getRowConstraint(i, j).constraintType().equals(">")) {
                    testRow++;
                }
                if (testRow > 0) {
                    fillRowTest = true;
                }
            }
        }
        assertEquals(true, fillRowTest);
    }



    @Test
    public void isLegal() {
        FutoshikiGrid gridCorrect = new FutoshikiGrid(5);
        gridCorrect.setSquare(0, 0, 1);
        gridCorrect.setSquare(0, 1, 3);
        gridCorrect.setRowConstraint(0, 0, "<");
        gridCorrect.setSquare(2, 4, 3);
        gridCorrect.setSquare(3, 4, 1);
        gridCorrect.setColConstraint(2, 4, "V");
        boolean tmp = gridCorrect.isLegal();
        assertEquals(true, tmp);
        FutoshikiGrid gridNotCorrect = new FutoshikiGrid(5);
        gridNotCorrect.setSquare(3, 4, 5);
        gridNotCorrect.setSquare(4, 4, 5);
        gridNotCorrect.setColConstraint(3, 4, "^");
        gridNotCorrect.setSquare(1, 2, 1);
        gridNotCorrect.setSquare(1, 3, 2);
        gridNotCorrect.setRowConstraint(1, 2, ">");
        gridNotCorrect.setSquare(3, 0, 4);
        gridNotCorrect.setSquare(3, 1, 4);
        gridNotCorrect.setSquare(3, 3, 4);
        boolean tmp1 = gridNotCorrect.isLegal();
        assertEquals(false, tmp1);
    }

    @Test
    public void toStringTest() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        String tmp = grid.toString();
        assertEquals("--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n\n", tmp);
        grid.setSquare(3, 4, 5);
        grid.setSquare(4, 4, 5);
        grid.setColConstraint(3, 4, "^");
        grid.setSquare(1, 2, 1);
        grid.setSquare(1, 3, 2);
        grid.setRowConstraint(1, 2, ">");
        grid.setSquare(3, 0, 4);
        grid.setSquare(3, 1, 4);
        grid.setSquare(3, 3, 4);
        String tmp1 = grid.toString();
        assertEquals("--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | |1|>|2| | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n| | | | | | | | | |\n--- --- --- --- --- \n                    \n"
                + "--- --- --- --- --- \n|4| |4| | | |4| |5|\n--- --- --- --- --- \n                 ^  \n"
                + "--- --- --- --- --- \n| | | | | | | | |5|\n--- --- --- --- --- \n\n", tmp1);
    }
    

    @Test
    public void solution() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        boolean tmp = grid.solve();
        assertEquals(true, tmp);
        
        FutoshikiGrid grid1 = new FutoshikiGrid(5);
        grid1.setSquare(3, 0, 4);
        grid1.setSquare(3, 1, 4);
        grid1.setSquare(3, 3, 4);
        boolean tmp1 = grid1.solve();
        assertEquals(false, tmp1);
    }
    
    @Test
    public void solvable() {
        FutoshikiGrid grid = new FutoshikiGrid(5);
        boolean tmp = grid.solve();
        assertEquals(true, tmp);
        grid.print();

        
        FutoshikiGrid grid1 = new FutoshikiGrid(5);
        grid1.setSquare(3, 0, 4);
        grid1.setSquare(3, 1, 4);
        grid1.setSquare(3, 3, 4);
        boolean tmp1 = grid1.solve();
        assertEquals(false, tmp1);
    }
}