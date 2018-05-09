public class MovingObject extends GameObject {
    public int speed;
    public MovingObject(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
    }
}
