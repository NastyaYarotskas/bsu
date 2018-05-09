

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FirstTask extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    final Button button = new Button ("Go!");
    final Label notification = new Label ();
    final TextField subject = new TextField("");
    final TextArea text = new TextArea ("");

    String address = " ";
    int index;

    String[] regex = {"[1-9][0-9]*",
            "([+-]?[1-9][0-9]*)|[+-]?0",
            "(([-+]?([0-9]|[1-9][0-9]*)((\\.[0-9]*)|(\\.[0-9]+)(e([+-]?[1-9][0-9]*|0))))?|" +
                    "([-+]?([0-9]?|[1-9][0-9]*)((\\.[0-9]+)|(\\.[0-9]+)?e([+-]?[1-9][0-9]*|0))))",
            "((([1-9]|1[0-9]|2[0-8])/2)|([1-9]|[1-2][0-9]|30)/(4|6|9|11)|([1-9]|[1-2][0-9]|30|31)/(1|3|5|7|8|10|12))/([1-9][0-9]*)",
            "([0-9]|[0-1][0-9]|2[0-3]):[0-5][0-9]",
            "[a-z](\\w*)@[a-zA-Z]{2,}(\\.(ru|by|com))",

    };

    Label label = new Label();
    final ComboBox comboBox = new ComboBox();

    InputStream input= getClass().getResourceAsStream("ok.png");
    Image image = new Image(input);
    ImageView imageView = new ImageView(image);

    InputStream input1 = getClass().getResourceAsStream("wrong.png");
    Image image1 = new Image(input1);
    ImageView imageView1 = new ImageView(image1);

    @Override public void start(Stage stage) {
        stage.setTitle("ComboBoxSample");
        Scene scene = new Scene(new Group(), 450, 250);



        // Image Source




        // Set Image
        label.setGraphic(imageView);

        comboBox.getItems().addAll(
                "натуральное число",
                "целое число",
                "число с плавающей запятой",
                "дата",
                "время",
                "e-mail"
        );

        //comboBox.setValue("Format");

        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                change();
            }
        });

        subject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                change();
            }
        });

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                change();
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Choose: "), 0, 0);
        grid.add(comboBox, 1, 0);
        grid.add(label, 0, 4);
        grid.add(subject, 1, 4, 3, 1);
        grid.add(button, 0, 6);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();
    }

    public void change(){
        String val = subject.getText();
        int index = comboBox.getSelectionModel().getSelectedIndex();
        Pattern p = Pattern.compile(regex[index]);
        Matcher m = p.matcher(val);
        boolean flag = m.matches();
        if (flag) {
            label.setGraphic(imageView);
        }
        else {
            label.setGraphic(imageView1);
        }
    }
}