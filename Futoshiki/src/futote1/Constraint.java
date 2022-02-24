
package futote1;

/**
 *
 * @author 184514
 * @version 06/05/2019
 */
//Telling the Java serializer that it is OK to serialize objects of this type.
//This mechanism is used to persist the object.
import java.io.Serializable;
public abstract class Constraint implements Serializable{
 //coordinates of squares involved in this constraint
    protected FutoshikiSquare before;
    protected FutoshikiSquare after;
    private boolean rowConstraint;
    private boolean colConstraint;

    /**
     * Constructor which will initialise the fields. It also checks to see whether 
     * you are making a row constraint or column constraint.
     * 
     * @param first the first futoshiki square of the constraint.
     * @param second the second futoshiki square of the constraint.
     */
    public Constraint(FutoshikiSquare first, FutoshikiSquare second) {
        this.before = first;
        this.after = second;

        if ((first.getRow() == second.getRow()) && (second.getCol() == first.getCol()+1)) {
            rowConstraint = true;
        } else if ((second.getRow() == first.getRow()+1) && (first.getCol() == second.getCol())) {
            colConstraint = true;
        } else {
            rowConstraint = false;
            colConstraint = false;
        }
    }

    public boolean isRowConstraint() {
        return rowConstraint;
    }
    
    public boolean isColConstraint() {
        return colConstraint;
    }
    public abstract boolean isCorrect();

    public abstract String constraintType();

    public abstract String string();
}
