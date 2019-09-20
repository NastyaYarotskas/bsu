package com.bsu.yarotskas;

import javax.swing.*;
import java.awt.*;

public class MyJFrame extends JFrame {

    public MyJFrame(){
        super("Tree");

        JPanel panel = new JPanel();
        add(panel);

        StudentsPanel studentsPanel = new StudentsPanel();
        panel.add(studentsPanel);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu student = new JMenu("Options");
        menuBar.add(student);

        JMenuItem addStudent = new JMenuItem("Add");
        addStudent.addActionListener(e->{
            studentsPanel.addStudent();
        });
        student.add(addStudent);

        JMenuItem deleteStudent = new JMenuItem("Delete");
        deleteStudent.addActionListener(e->{
            studentsPanel.deleteStudent();
        });
        student.add(deleteStudent);

        JMenuItem refactorStudent = new JMenuItem("Refactor");
        refactorStudent.addActionListener(e->{
            studentsPanel.refactorStudent();
        });
        student.add(refactorStudent);

        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
