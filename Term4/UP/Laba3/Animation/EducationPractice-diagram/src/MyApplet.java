import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;

public class MyApplet extends Application {
    double values[];
    String names[];

    @Override public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Imported Fruits");
        stage.setWidth(500);
        stage.setHeight(500);

        Scanner sc ;
        int countOfElements = 0;

        try {
            sc = new Scanner(new File("input.txt"));
            countOfElements = sc.nextInt();
            values = new double[countOfElements];
            names = new String[countOfElements];
            int i = 0;
            while (sc.hasNext()) {
                names[i] = sc.next();
                values[i] = sc.nextInt();
                if (values[i] < 0)
                    JOptionPane.showMessageDialog(null, "Incorrect data");
                i++;
            }
            if (i != countOfElements)
                JOptionPane.showMessageDialog(null, "Incorrect data");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for(int i = 0; i < countOfElements; i++){
            pieChartData.add(new PieChart.Data(names[i], values[i]));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}