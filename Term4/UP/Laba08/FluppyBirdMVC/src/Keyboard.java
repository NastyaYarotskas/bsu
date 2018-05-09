import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class Keyboard implements KeyListener, Observable {

	private static Keyboard instance;
	private LinkedList<Observer> observers = new LinkedList<>();

	private boolean[] keys;
	
	private Keyboard() {
		keys = new boolean[256];
	}

	public static Keyboard getInstance() {

		if (instance == null) {
			instance = new Keyboard();
		}
		
		return instance;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = false;
		}
	}

	public void keyTyped(KeyEvent e) {}

	public boolean isDown(int key) {

		if (key >= 0 && key < keys.length) {
			return keys[key];
		}
		
		return false;
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Object object) {
		for(Observer observer : observers){
			observer.update(object);
		}
	}
}
