package sample;

import javafx.scene.control.ListView;

public class LogTextArea extends ListView implements Observer<String>{
    @Override
    public void update(String object) {
        getItems().add(object);
    }
}
