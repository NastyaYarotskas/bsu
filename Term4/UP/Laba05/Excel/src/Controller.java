import javax.swing.*;
import java.awt.*;

public class Controller {
    public Controller() {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public void showErrorMessage(String message) {
        view.showErrorMessage(message);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("My Excel");
        Controller controller = new Controller();
        controller.model = new DateTableModel(controller);
        controller.view = new View(controller.model);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(controller.view, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private DateTableModel model;
    private View view;
}
