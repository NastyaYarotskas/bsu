import java.util.GregorianCalendar;

public class DateCell extends Date {
    DateCell(int row, int column, Date value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    void setValue(Date value) {
        this.value = value;
    }

    @Override
    GregorianCalendar evaluate() throws NullReferenceException {
        return value == null ? null : value.evaluate();
    }

    @Override
    boolean depended(Date expression) {
        if (expression instanceof ConstDate) {
            return false;
        }
        DateCell cell = (DateCell) expression;
        if (row == cell.row && column == cell.column) {
            return true;
        }
        return value.depended(expression);
    }

    private Date value;
    private int row;
    private int column;
}