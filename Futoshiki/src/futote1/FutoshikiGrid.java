/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futote1;

/**
 *
 * @author 184514
 * @version 07/05/2019
 */
//imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

public class FutoshikiGrid implements Serializable {

    private FutoshikiSquare[][] squares;
    private Constraint[][] colConstraints;
    private Constraint[][] rowConstraints;
    
    private final int gridSize;
    private Random random = new Random();

    public static void main(String[] args) {
        FutoshikiGrid f = new FutoshikiGrid(5);
        f.fillPuzzle(3,3);
        System.out.println(f);
        f.isLegal();
    }

    public FutoshikiGrid(int size) {
        this.gridSize = size;
        squares = new FutoshikiSquare[gridSize][gridSize];
        colConstraints = new Constraint[gridSize - 1][gridSize];
        rowConstraints = new Constraint[gridSize][gridSize - 1];
        clearPuzzle();
    }

    public FutoshikiSquare getSquare(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Invalid co-ordinates entered!");
        }
        return squares[row][col];
    }

    public Constraint getRowConstraint(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Invalid coo-ordinates entered!");
            return null;
        } else 
        {
            return rowConstraints[row][col];
        }
    }

    public int getSize() {
        return gridSize;
    }

    public Constraint getColConstraint(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Invalid coo-ordinates entered!");
            return null;
        } else 
        {
            return colConstraints[row][col];
        }
    }

    public HashSet getNotNumbers(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Invalid co-ordinates entered!");
            return null;
        } else 
        {
            return getSquare(row, col).getCantBeNum();
        }
    }

    public void setSquare(int row, int col, int value) {
        if (value < 0 || value > gridSize) {
            System.err.println("Value entered: " + value + " is out of range!");
            squares[row][col].setNumber(0);
            throw new IllegalArgumentException();
        } else if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Invalid co-ordinates entered!");
            throw new IllegalArgumentException();
        } else {
            squares[row][col].setNumber(value);
        }
    }

     public void setInvalidNumbers(int row, int col, int invalid) {
        if (invalid < 0 || invalid > gridSize) {
            System.err.println("Value enetred is out of range!");
        } else if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Invalid co-ordinates entered!");
        } else {
            squares[row][col].setImpossible(invalid);

        }
     }

    public void setRowConstraint(int row, int col, String constraint) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            System.err.println("Invalid co-ordinates entered!");
        } else if (constraint.equals("<")) {
            rowConstraints[row][col] = new LessThan(getSquare(row, col), getSquare(row, col + 1));
        } else if (constraint.equals(">")) {
            rowConstraints[row][col] = new GreaterThan(getSquare(row, col), getSquare(row, col + 1));
        } else {
            System.out.println("Constraint entered not valid! Please enetr > or <");
        }
    }
    
    public void setColConstraint(int row, int col, String con) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {// equal to 0 so you can make it so a box is empty
            System.err.println("Invalid co-ordinates entered!");
        } else if (con.equals("^")) {
            colConstraints[row][col] = new LessThan(getSquare(row, col), getSquare(row + 1, col));
        } else if (con.equals("V")) {
            colConstraints[row][col] = new GreaterThan(getSquare(row, col), getSquare(row + 1, col));
        } else {
            System.err.println("Constraint entered not valid! Please enter either ^ or v");
        }
    }

    public FutoshikiSquare[][] getGrid() {
        return squares;
    }

    public boolean checkIfFull() {
        boolean t = true;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize - 1; j++) {
                if (squares[i][j].getNumber() == 0) {
                    t = false;
                }
            }
        }
        return t;
    }


    public void clearPuzzle() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                squares[i][j] = new FutoshikiSquare(i, j);
            }
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize - 1; j++) {
                rowConstraints[i][j] = new GreaterThan(squares[0][0], squares[0][0]);
            }
        }
        for (int i = 0; i < gridSize - 1; i++) {
            for (int j = 0; j < gridSize; j++) {
                colConstraints[i][j] = new GreaterThan(squares[0][0], squares[0][0]);
            }
        }
    }

    public void fillPuzzle(int numV, int numRC) {
        clearPuzzle();
        if (numV > gridSize )
        {
            numV = 1;
        }
        for (int i = 0; i < numV; i++) {
            int r1 = random.nextInt(gridSize);
            int r2 = random.nextInt(gridSize);
            int r3 = random.nextInt(gridSize + 1);
            while (r3 == 0) {
                r3 = random.nextInt(gridSize + 1);
            }
            setSquare(r1, r2, r3);
            if (getSquare(r1, r2).getNumber() != 0) {
                getSquare(r1, r2).setInitial();
            }
        }
        if (numRC > gridSize )
        {
            numRC = 1;
        }
        for (int i = 0; i < numRC; i++) {

            int rConRandom = random.nextInt(3);
            if (rConRandom <= 0) {
                setRowConstraint(random.nextInt(gridSize), random.nextInt(gridSize - 1), "<");
            } else {
                setRowConstraint(random.nextInt(gridSize), random.nextInt(gridSize - 1), ">");
            }

            int cConRandom = random.nextInt(3);
            if (cConRandom <= 1) {
                setColConstraint(random.nextInt(gridSize - 1), random.nextInt(gridSize), "V");
            } else {
                setColConstraint(random.nextInt(gridSize - 1), random.nextInt(gridSize), "^");
            }
        }

    }
        public String checkColNum() {
        
        String problems = "";
        FutoshikiSquare[][] tempSquare = new FutoshikiSquare[gridSize][gridSize];
        for (int a = 0; a < gridSize; a++) {
            for (int b = 0; b < gridSize; b++) {
                tempSquare[a][b] = squares[a][b];
            }
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (tempSquare[j][i].getNumber() != 0) {
                    int value = tempSquare[j][i].getNumber();
                    int number = 1;
                    for (int a = j + 1; a < gridSize; a++) {
                        if (value == tempSquare[a][i].getNumber()) {
                            number++;
                            tempSquare[a][i] = new FutoshikiSquare(a, i);
                        }
                    }
                    if (number != 1) {
                        problems += "Column " + (i + 1) + " has " + number + " " + getSquare(j, i).getNumber() + "'s \n";
                    }
                }
            }
        }
        return problems;
    }
    public String checkRowNum() {

        String problems = "";
        FutoshikiSquare[][] tempSquare = new FutoshikiSquare[gridSize][gridSize];  
        for (int a = 0; a < gridSize; a++) {
            for (int b = 0; b < gridSize; b++) {
                tempSquare[a][b] = squares[a][b];
            }
        }
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (tempSquare[i][j].getNumber() != 0) {
                    int value = tempSquare[i][j].getNumber();
                    int number = 1;
                    for (int c = j + 1; c < gridSize; c++) {
                        if (value == tempSquare[i][c].getNumber()) {
                            number++;
                            tempSquare[i][c] = new FutoshikiSquare(i, c);
                        }
                    }
                    if (number != 1) {
                        problems += "Row " + (i + 1) + " has " + number + " " + getSquare(i, j).getNumber() + "'s \n";
                    }
                }
            }
        }
        return problems;
    }

    public String checkRowConstraints() {
        String problems = "";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize - 1; j++) {
                if ((getRowConstraint(i, j).constraintType() == ("<") && getSquare(i, j).getNumber() == gridSize) || (getRowConstraint(i, j).constraintType() == (">") && getSquare(i, j + 1).getNumber() == gridSize)) 
                {
                    problems += "In row " + (i + 1) + ", " + gridSize + " is the greatest number!!\n";
                }
                if (getRowConstraint(i, j).constraintType() == (">") && getSquare(i, j).getNumber() == 1 || getRowConstraint(i, j).constraintType() == ("<") && getSquare(i, j + 1).getNumber() == 1) {
                    problems += "In row " + (i + 1) + ", no number can be less than 1" + "\n";
                }
                if (getRowConstraint(i, j) != null && getSquare(i, j).getNumber() != 0 && getSquare(i, j + 1).getNumber() != 0) 
                {
                    int number = 1;
                    if (!getRowConstraint(i, j).isCorrect()) {
                        number++;
                    }
                    if (number != 1) {
                        problems += "In row " + (i + 1) + ", " + getSquare(i, j).getNumber() + " isn't " + getRowConstraint(i, j).string() + " " + getSquare(i, j + 1).getNumber() + "\n";
                    }
                }
            }
        }
        return problems;
    }

    public String checkColConstraints() {
        String problems = "";
        for (int j = 0; j < gridSize; j++) {
            for (int i = 0; i < gridSize - 1; i++) {
                if ((getColConstraint(i, j).constraintType() == ("^") && getSquare(i, j).getNumber() == gridSize)  || (getColConstraint(i, j).constraintType() == ("V") && getSquare(i + 1, j).getNumber() == gridSize)) {
                    problems += "In column " + (j + 1) + ", no number can be greater than " + gridSize + "\n";
                }
                if (getColConstraint(i, j).constraintType() == ("V") && getSquare(i, j).getNumber() == 1
                        || 
                        getColConstraint(i, j).constraintType() == ("^") && getSquare(i + 1, j).getNumber() == 1) {
                    problems += "In column " + (j + 1) + ", no number can be less than 1" + "\n";
                }
                if (getSquare(i, j).getNumber() != 0 && getSquare(i + 1, j).getNumber() != 0 && getColConstraint(i, j) != null) {
                    int number = 1;
                    if (!(getColConstraint(i, j).isCorrect())) {
                        number++;
                    }
                    if ( number != 1) {
                        problems += "In column " + (j + 1) + ", " + getSquare(i, j).getNumber() + " isn't " + getColConstraint(i, j).string() + " " + getSquare(i + 1, j).getNumber() + "\n"; //sort out signs when printing
                    }
                }
            }
        }
        return problems;
    }

    public String getAllProblems() {
        if ("".equals(checkRowNum() + checkRowConstraints() + checkColNum() + checkColConstraints())) {
            System.out.println("No Probelms, so puzzle is legal! \n");
            return "Legal";
        } else {
            System.out.println(checkRowNum() + checkRowConstraints() + checkColNum() + checkColConstraints());
            return checkRowNum() + checkRowConstraints() + checkColNum() + checkColConstraints();
        }
    }
    public boolean isLegal() {
        return getAllProblems().equals("Legal");
    }
    public FutoshikiSquare getEmptySquares() {
        FutoshikiSquare tempSquare = null;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (getSquare(i, j).getNumber() == 0) {
                    tempSquare = getSquare(i, j);
                }
            }
        }
        return tempSquare;
    }

    @Override
    public String toString() {

        String print = "";
        int n = 0;
        if (gridSize < 10) {
            for (int i = 0; i < gridSize; i++) {
                for (int a = 0; a < gridSize; a++) {
                    print += "--- ";
                }

                print += "\n";
                for (int j = 0; j < gridSize; j++) {
                    if (getSquare(i, j).getNumber() == 0) {
                        print += "| |";
                    } else {
                        print += "|" + getSquare(i, j).getNumber() + "|";
                    }
                    if (j < gridSize - 1) {
                        print += getRowConstraint(i, j).constraintType();
                    }
                }

                print += "\n";
                for (int a = 0; a < gridSize; a++) {
                    print += "--- ";
                }

                print += "\n";

                for (int k = 0; k < gridSize; k++) {

                    if (i < gridSize - 1) {
                        print += " " + getColConstraint(i, k).constraintType() + "  ";
                    }
                }
                print += "\n";
            }
        } else {
            for (int i = 0; i < gridSize; i++) {
                for (int a = 0; a < gridSize; a++) {
                    print += "----- ";
                }

                print += "\n";
                for (int j = 0; j < gridSize; j++) {
                    if (getSquare(i, j).getNumber() == 0) {
                        print += "|   |";
                    } else if (getSquare(i, j).getNumber() < 10) {
                        print += "| " + getSquare(i, j) + " |";
                    } else {
                        print += "| " + getSquare(i, j) + "|";
                    }
                    if (j < gridSize - 1) {
                        print += getRowConstraint(i, j).constraintType();
                    }
                }

                print += "\n";
                for (int a = 0; a < gridSize; a++) {
                    print += "----- ";
                }

                print += "\n";

                for (int k = 0; k < gridSize; k++) {

                    if (i < gridSize - 1) {
                        print += "  " + getColConstraint(i, k).constraintType() + "   ";
                    }
                }
                print += "\n";
            }
        }
        return print;
    }

    public boolean solve() {
        if (checkIfFull() && isLegal()) {
            return true;
        }

        FutoshikiSquare temp = getEmptySquares();

        for (int i = 1; i <= gridSize; i++) {
            temp.setNumber(i);
            if (isLegal()) {
                if (solve()) {
                    return true;
                }
            }
            temp.setNumber(0);
        }
        return false;
    }
    public void print() {
        System.out.println(toString());
    }

    public boolean checkIfSolvable() {
        if (solve()) {
            for (int a = 0; a < gridSize; a++) {
                for (int b = 0; b < gridSize; b++) {
                    if (getSquare(a, b).getInitial() == false) {
                        setSquare(a, b, 0);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
