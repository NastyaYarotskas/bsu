package sample;

import java.util.LinkedList;
import java.util.List;

public class Controller implements Observable {

    private List<Observer> observers= new LinkedList<>();

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
        for (Observer observer : observers)
            observer.update(object);
    }
}
