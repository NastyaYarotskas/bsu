public class BadCellException extends Exception {
    public BadCellException() {
        super("Bad cell indices or referencing to empty cell.");
    }
}
