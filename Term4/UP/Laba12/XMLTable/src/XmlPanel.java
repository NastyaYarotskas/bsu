import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class XmlPanel extends JPanel{

    private JMenuBar menuBar;
    private JTable table;
    private DefaultTableModel data;

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

        JMenuItem saveBinFileMenuItem = new JMenuItem("Save as Bin file");
        saveBinFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBinary();
            }
        });
        fileMenu.add(saveBinFileMenuItem);

        JMenuItem checkXMLFileMenuItem = new JMenuItem("Check XML file");
        checkXMLFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkXMLFile();
            }
        });
        fileMenu.add(checkXMLFileMenuItem);

        JMenu tableMenu = new JMenu("Table");

        JMenuItem readBinFileMenuItem = new JMenuItem("Open Bin file");
        readBinFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBinary();
            }
        });
        fileMenu.add(readBinFileMenuItem);

        JMenuItem saxMenuItem = new JMenuItem("Statistics");
        saxMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openSax();
            }
        });
        fileMenu.add(saxMenuItem);

        JMenuItem htmlMenuItem = new JMenuItem("XML to HTML");
        htmlMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    xmltoHTML();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }
        });
        fileMenu.add(htmlMenuItem);

        JMenuItem txtMenuItem = new JMenuItem("XML to TXT");
        txtMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    xmltoTXT();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
            }
        });
        fileMenu.add(txtMenuItem);


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

    private void checkXMLFile(){
        try {
            JFileChooser jFileChooser = new JFileChooser(".");
            int r = jFileChooser.showDialog(null, "Open file");
            if (r == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                try {
                    String xsd_path = "schema.xsd";
                    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                    Schema schema = factory.newSchema(new File(xsd_path));
                    Validator validator = schema.newValidator();
                    validator.validate(new StreamSource(file));
                    JOptionPane.showMessageDialog(this, "File  " + file.getName() + ".xml  is correct");
                } catch (IOException | SAXException e) {
                    JOptionPane.showMessageDialog(this,"Exception: " + e.getMessage());
                }
            }
        } catch (Exception i) {
            JOptionPane.showMessageDialog(null, i.getMessage());
        }

    }

    private void  xmltoHTML() throws TransformerException{
        String xslt_path = "toHTML.xslt";
        JFileChooser fileopen = new JFileChooser(".");
        if (fileopen.showDialog(null, "Choose file") == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            TransformerFactory factory = TransformerFactory.newInstance();
            StreamSource xslStream = new StreamSource(new File(xslt_path));
            Transformer transformer = factory.newTransformer(xslStream);
            StreamSource in = new StreamSource(file);
            StreamResult out = new StreamResult(new File(file.getPath() + ".html"));
            transformer.transform(in, out);
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            try {
                desktop.open(new File(file.getPath() + ".html"));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private void  xmltoTXT()throws TransformerException{
        String  xslt_path = "toTXT.xslt";
        JFileChooser fileopen = new JFileChooser(".");
        if (fileopen.showDialog(null, "Choose file") == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            TransformerFactory factory = TransformerFactory.newInstance();
            StreamSource xslStream = new StreamSource(new File(xslt_path));
            Transformer transformer = factory.newTransformer(xslStream);
            StreamSource in = new StreamSource(file);
            StreamResult out = new StreamResult(new File(file.getPath() + ".txt"));
            transformer.transform(in, out);
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            try {
                desktop.open(new File(file.getPath() + ".txt"));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private void openBinary() {
        try {
            JFileChooser jFileChooser = new JFileChooser(".");
            int r = jFileChooser.showDialog(null, "Open file");
            if (r == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                ArrayList<Student> list = new ArrayList<Student>();
                Object obj = null;

                try{
                    while(true){
                        obj = objectInputStream.readObject();
                        list.add((Student)obj);
                        System.out.println(obj);
                    }
                } catch(EOFException e){

                }

               objectInputStream.close();
                removeDataFromTableModel();
                addStudentsToTable(list);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveBinary() {
        try {
            JFileChooser jFileChooser = new JFileChooser(".");
            int r = jFileChooser.showDialog(null, "Save file");
            if (r == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ArrayList<Student> list = makeStudentList();
                ObjectOutputStream objectOutputStream =new ObjectOutputStream(fileOutputStream);
                for(int i = 0; i < list.size(); i++){
                    objectOutputStream.writeObject(list.get(i));
                    System.out.println(list.get(i));
                }
                //objectOutputStream.flush();
                objectOutputStream.close();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void openSax() {
        try {
            JFileChooser jFileChooser = new JFileChooser(".");
            int r = jFileChooser.showDialog(null, "Open file");
            if (r == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                SAXPars saxp = new SAXPars();
                saxParser.parse(file, saxp);
                JOptionPane.showMessageDialog(this, saxp.getResult());
            }
        } catch (Exception i) {
            JOptionPane.showMessageDialog(null, i.getMessage());
        }
    }

    private void openDom() {
        try {
            JFileChooser jFileChooser = new JFileChooser(".");
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

    private ArrayList<Student> makeStudentList(){
        ArrayList<Student> list = new ArrayList<>();
        Vector<Vector> vec = data.getDataVector();
        for(int i = 0; i < vec.size(); i++){
            int id = (int) vec.elementAt(i).elementAt(0);
            String name = (String) vec.elementAt(i).elementAt(1);
            String faculty = (String) vec.elementAt(i).elementAt(2);
            int year = (int) vec.elementAt(i).elementAt(3);
            double grade = (double) vec.elementAt(i).elementAt(4);
            list.add(new Student(id, name, faculty, year, grade));
        }

        return list;
    }

    public void addStudentsToTable(ArrayList<Student> list){
        for (Student student : list) {
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
            JFileChooser jFileChooser = new JFileChooser(".");
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
