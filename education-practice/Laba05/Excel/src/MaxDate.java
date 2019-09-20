import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MaxDate extends Date {
    MaxDate(ArrayList<Date> values) {
        this.values = values;
    }

    @Override
    public GregorianCalendar evaluate() throws NullReferenceException {
        GregorianCalendar result = null;
        for (Date value: values) {
            GregorianCalendar current = value.evaluate();
            if (result == null || current.compareTo(result) > 0) {
                result = current;
            }
        }
        return result;
    }

    @Override
    public boolean depended(Date expression) {
        for (Date value: values) {
            if (value.depended(expression)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Date> values;
}
