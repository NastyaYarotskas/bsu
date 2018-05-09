import java.util.GregorianCalendar;

public class ConstDate extends Date {
    ConstDate(GregorianCalendar value) {
        this.value = value;
    }

    @Override
    GregorianCalendar evaluate() {
        GregorianCalendar result = new GregorianCalendar();
        result.setTime(value.getTime());
        return result;
    }

    @Override
    boolean depended(Date expression) {
        return false;
    }

    private GregorianCalendar value;
}