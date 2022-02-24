
package futote1;

/**
 *
 * @author 184514
 * @version 05/05/2019
 */
public class LessThan extends Constraint {

    /**
     * @param square1 the square that you want to be apart of the constraint
     * @param square2 the square that you want to be apart of the constraint
     */
    public LessThan(FutoshikiSquare before, FutoshikiSquare after) {
        super(before, after);
    }

    @Override
    public boolean isCorrect() {
        if ((isRowConstraint() || isColConstraint()) && before.getNumber() != 0 && after.getNumber() != 0) {
            return before.getNumber() < after.getNumber();
        } else {
            return true;
        }
    }

    @Override
    public String constraintType() {
        if (isRowConstraint()) {
            return "<";
        } else if (isColConstraint()) {
            return "^";
        } else {
            return " ";
        }
    }

    @Override
    public String string() {
        return "less than";
    }
}
