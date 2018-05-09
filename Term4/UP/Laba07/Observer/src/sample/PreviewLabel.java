package sample;

import javafx.scene.control.Label;

public class PreviewLabel extends Label implements Observer {

    @Override
    public void update(Object object) {
        setText(object.toString());
    }
}

