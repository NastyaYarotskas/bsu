import java.util.GregorianCalendar;

abstract class Date {
    abstract GregorianCalendar evaluate() throws NullReferenceException;
    abstract boolean depended(Date expression);
}

