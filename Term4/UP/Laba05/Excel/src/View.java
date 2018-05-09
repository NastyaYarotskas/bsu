import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class View extends  JPanel {
    JTextField editTextField;
    int row = -1;
    int column = -1;

    public View(DateTableModel tableModel) {
        super(new BorderLayout());

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                editCell(table, tableModel);
            }
        });
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT ||  keyEvent.getKeyCode() == KeyEvent.VK_RIGHT ||
                        keyEvent.getKeyCode() == KeyEvent.VK_DOWN || keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                    editCell(table, tableModel);
                }
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setShowGrid(true);
        table.setGridColor(Color.lightGray);

        table.setCellSelectionEnabled(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        editTextField = new JTextField("");
        add(editTextField, BorderLayout.NORTH);
        editTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(row == -1 || column == -1){
                    showErrorMessage("Please, choose cell");
                    return;
                }
                tableModel.setValueAt(editTextField.getText(), row, column);
            }
        });
    }

    private void editCell(JTable table, DateTableModel tableModel) {
        row = table.getSelectedRow();
        column = table.getSelectedColumn();
        editTextField.setText(tableModel.getFormulaAt(row, column));
    }

    public Dimension getPreferredSize() {
        return new Dimension(900, 400);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }


}
