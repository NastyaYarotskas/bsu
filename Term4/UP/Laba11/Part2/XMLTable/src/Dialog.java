import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField idField;
    private JTextField nameField;
    private JTextField facultyField;
    private JTextField yearField;
    private JTextField gradeField;
    private boolean ok;
    private Student student;

    public Dialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setMinimumSize(new Dimension(500, 300));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() throws BadInputException {
        // add your code here
        if (nameField.equals("") || idField.equals("") || facultyField.equals("")||
                yearField.equals("") || gradeField.equals("")){
            throw new BadInputException("Fill all the fields.");
        }
        student = new Student(Integer.parseInt(idField.getText()), nameField.getText(),
                facultyField.getText(), Integer.parseInt(yearField.getText()),
                Double.parseDouble(gradeField.getText()));
        ok = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        ok = false;
        dispose();
    }

    public boolean isOK(){
        return ok;
    }

    public Student getStudent(){
        return student;
    }

    public Object[] getData() throws NumberFormatException {
        return new Object[]{Integer.parseInt(idField.getText()), nameField.getText(),
                facultyField.getText(), Integer.parseInt(yearField.getText()),
                Double.parseDouble(gradeField.getText())};
    }

    public static void main(String[] args) {
        Dialog dialog = new Dialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
