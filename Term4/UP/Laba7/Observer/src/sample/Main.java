package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    Controller controller = new Controller();

    @Override
    public void start(Stage primaryStage){
        LogTextArea area = new LogTextArea();
        PreviewLabel label = new PreviewLabel();

        label.setFont(Font.font(50));
        label.setAlignment(Pos.CENTER);
        area.setEditable(false);

        HBox hBox = new HBox();

        hBox.getChildren().addAll(area, label);

        Scene scene = new Scene(hBox, 1000, 500);

        controller.registerObserver(label);
        controller.registerObserver(area);
        keyPresssed(scene);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void keyPresssed(Scene scene){
        scene.setOnKeyPressed((e)->{
            String str;
            if(e.getText().isEmpty()) str = e.getCode().getName();
            else str = e.getText();
            controller.notifyObservers(str);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
