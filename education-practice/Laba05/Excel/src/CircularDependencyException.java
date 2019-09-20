public class CircularDependencyException extends Exception {
    public CircularDependencyException() {
        super("Circular dependency occured");
    }
}
