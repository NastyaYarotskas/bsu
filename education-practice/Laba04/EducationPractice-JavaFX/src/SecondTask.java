import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SecondTask extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public static final ObservableList data =
            FXCollections.observableArrayList();

    final TextArea text = new TextArea ("");
    final Button button = new Button ("Go!");

    @Override public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("TextArea and ListView");
        Scene scene = new Scene(new Group(), 700, 400);

        final ListView listView = new ListView(data);
        listView.setPrefSize(200, 100);

        listView.setItems(data);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.clear();
                String val = text.getText();
                Matcher m = Pattern.compile("(\\s|^)((([1-9]|1[0-9]|2[0-8])/2)|([1-9]|[1-2][0-9]|30)/(4|6|9|11)|([1-9]|[1-2][0-9]|30|31)/(1|3|5|7|10|12))/([1-9][0-9]*)(?!\\S)($?)").matcher(val);
                while (m.find()){
                    data.add(m.group(0));
                }
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Text: "), 2, 0);
        grid.add(listView, 2, 3);
        grid.add(text, 2, 2);
        grid.add(button, 3, 2);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
    }
}