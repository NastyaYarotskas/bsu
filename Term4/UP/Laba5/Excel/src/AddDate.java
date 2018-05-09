import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddDate extends Date {
    AddDate(Date date, int addValue) {
        this.date = date;
        this.addValue = addValue;
    }

    @Override
    GregorianCalendar evaluate() throws NullReferenceException {
        GregorianCalendar result = date.evaluate();
        if (result == null) {
            throw new NullReferenceException();
        }
        result.add(Calendar.DAY_OF_MONTH, addValue);
        return result;
    }

    @Override
    boolean depended(Date expression) {
        return date.depended(expression);
    }

    private Date date;
    private int addValue;
}
