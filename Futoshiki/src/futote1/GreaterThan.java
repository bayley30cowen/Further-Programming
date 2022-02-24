
package futote1;

/**
 * Class to represent the functionality of a row constraint.
 * @author 184514
 * @version 06/05/2019
 */
public class GreaterThan extends Constraint {

    /**
     * passes to super class
     *
     * @param square1 the square that you want to be apart of the constraint.
     * @param square2 the square that you want to be apart of the constraint.
     */
    public GreaterThan(FutoshikiSquare before, FutoshikiSquare after) {
        super(before, after);
    }

    @Override
    public boolean isCorrect() {
        if ((isRowConstraint() || isColConstraint()) && before.getNumber() != 0 && after.getNumber() != 0) {
            return before.getNumber() > after.getNumber();
        } else {
            return true;
        }
    }
    @Override
    public String constraintType() {
        if (isRowConstraint()) {
            return ">";
        } else if (isColConstraint()) {
            return "v";
        } else {
            return " ";
        }
    }
    @Override
    public String string() {
        return "greater than";
    }
}
