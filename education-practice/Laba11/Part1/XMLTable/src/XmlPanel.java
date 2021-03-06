import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class XmlPanel extends JPanel {

    private JMenuBar menuBar;
    private JTable table;
    private DefaultTableModel data;
    private int count = 0;

    public XmlPanel() {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openDomMenuItem = new JMenuItem("Open XML");
        openDomMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openDom();
            }
        });
        fileMenu.add(openDomMenuItem);

        JMenuItem saveMenuItem = new JMenuItem("Save as XML");
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveToXml();
            }
        });
        fileMenu.add(saveMenuItem);

        JMenu tableMenu = new JMenu("Table");

        JMenuItem addRowMenuItem = new JMenuItem("Add Row");
        addRowMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addRow();
            }
        });
        tableMenu.add(addRowMenuItem);

        JMenuItem deleteRowMenuItem = new JMenuItem("Delete Row");
        deleteRowMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteRow();
            }
        });
        tableMenu.add(deleteRowMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(tableMenu);


        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        data = new DefaultTableModel(new String[]{"ID", "Name", "Faculty", "Year", "Mean Grade"}, 0){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                } else if (columnIndex == 1) {
                    return String.class;
                } else if (columnIndex == 2) {
                    return String.class;
                } else if (columnIndex == 3) {
                    return Integer.class;
                } else if (columnIndex == 4) {
                    return Integer.class;
                } else {
                    return Boolean.class;
                }
            }

            @Override
            public void rowsRemoved(TableModelEvent event) {
                super.rowsRemoved(event);
            }

            @Override
            public int getRowCount() {
                return super.dataVector.size();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public void addRow(Object[] rowData) {
                super.addRow(rowData);
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
            }

            @Override
            public Object getValueAt(int row, int column) {
                return super.getValueAt(row, column);
            }
        };

        table.setModel(data);

    }

    private void openDom() {
        try {
            JFileChooser jFileChooser = new JFileChooser("/Users/nastya/IdeaProjects/XMLTable");
            int r = jFileChooser.showDialog(null, "Open file");
            if (r == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                ArrayList<Student> students = readXML(file);
                removeDataFromTableModel();
                addStudentsToTable(students);
            }
        } catch (DomParserException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void addStudentsToTable(ArrayList<Student> list){
        for (Student student : list) {
            count++;
            data.addRow(new Object[]{
                    student.getId(), student.getName(), student.getFaculty(), student.getYear(), student.getMeanGrade()} );
        }
    }

    public static ArrayList<Student> readXML(File file) throws DomParserException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = (Document) builder.parse(file);
            ArrayList<Student> list = new ArrayList<>();
            NodeList nodeList = document.getElementsByTagName("student");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                int id = Integer.parseInt(element.getAttribute("id"));
                String name = element.getAttribute("name");
                String faculty = element.getElementsByTagName("faculty").item(0).getTextContent();
                int year = Integer.parseInt(element.getElementsByTagName("year").item(0).getTextContent());
                double meanGrade = Double.parseDouble(element.getElementsByTagName("grade").item(0).getTextContent());
                list.add(new Student(id, name, faculty, year, meanGrade));
            }
            return list;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new DomParserException(e);
        }
    }

    private void saveToXml() {
        try {
            JFileChooser jFileChooser = new JFileChooser("/Users/nastya/IdeaProjects/XMLTable");
            int r = jFileChooser.showDialog(null, "Save file");
            if (r == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                writeXML(file);
            }
        } catch (XmlSaveException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void writeXML(File file) throws XmlSaveException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement("bsu");
            document.appendChild(rootElement);

            for (int i = 0; i < data.getRowCount(); i++) {
                Element elementStudent = document.createElement("student");
                rootElement.appendChild(elementStudent);

                Attr attr1 = document.createAttribute("name");
                attr1.setValue((String) data.getValueAt(i, 1));

                Attr attr = document.createAttribute("id");
                attr.setValue(Integer.toString(((Integer) data.getValueAt(i, 0))));


                elementStudent.setAttributeNode(attr);
                elementStudent.setAttributeNode(attr1);

                Element elementFaculty = document.createElement("faculty");
                elementFaculty.appendChild(document.createTextNode((String) data.getValueAt(i, 2)));
                elementStudent.appendChild(elementFaculty);

                Element elementYear = document.createElement("year");
                elementYear.appendChild(document.createTextNode(Integer.toString(((Integer) data.getValueAt(i, 3)))));
                elementStudent.appendChild(elementYear);

                Element elementGrade = document.createElement("grade");
                elementGrade.appendChild(document.createTextNode(Double.toString(((Double) data.getValueAt(i, 4)))));
                elementStudent.appendChild((elementGrade));

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(source, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            throw new XmlSaveException(e);
        }
    }

    private void addRow() {
        Dialog d = new Dialog();
        d.setVisible(true);
        if(d.isOK()){
            data.addRow(d.getData());
        }

    }

    private void deleteRow() {
        int row = table.getSelectedRow();
        if (row == -1){
            JOptionPane.showMessageDialog(null,"Choose a student.");
        }
        else {
            data.removeRow(row);
        }
    }

    private void removeDataFromTableModel(){
        int rowCount = data.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            data.removeRow(i);
        }
    }

    public static void main(String[] args) {
        XmlPanel xmlPanel = new XmlPanel();

        JFrame frame = new JFrame("XML");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(xmlPanel, BorderLayout.CENTER);
        frame.setJMenuBar(xmlPanel.menuBar);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
