package com.bsu.yarotskas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class AddTour extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField countryTextField;
    private JTextField costTextField;
    private JButton pictureButton;
    ImageIcon icon;
    boolean ok;

    public AddTour() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setMinimumSize(new Dimension(500, 100));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pictureButton.addActionListener(e -> {
            JFileChooser fileopen = new JFileChooser("/Users/nastya/IdeaProjects/EducationPractice-table/plain");
            int ret = fileopen.showDialog(null, "Open file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                icon = new ImageIcon(fileopen.getSelectedFile().getPath());
            }
        });
    }

    private void onOK() {
        ok = true;
        dispose();
    }

    private void onCancel() {
        ok = false;
        dispose();
    }

    public Object[] getData() throws NumberFormatException {
        return new Object[]{icon, countryTextField.getText(), Integer.parseInt(costTextField.getText()), false};
    }

    public boolean okPressed() {
        return ok;
    }

    public static void main(String[] args) {
        AddTour dialog = new AddTour();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
