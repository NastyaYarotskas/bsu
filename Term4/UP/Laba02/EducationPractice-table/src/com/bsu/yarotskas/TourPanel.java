package com.bsu.yarotskas;

import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TourPanel extends JPanel {
    private HashMap<String, Pair<String, ImageIcon>> map;
    private JTable table;
    private DefaultTableModel data;

    public TourPanel(HashMap<String, Pair<String, ImageIcon>> newMap) {
        super();
        map = newMap;
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(700, 500));
        table.setRowHeight(40);
        add(new JScrollPane(table));
        data = new DefaultTableModel(new String[]{"Flag", "Country", "Cost", "Trip"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class;
                } else if (columnIndex == 1) {
                    return String.class;
                } else if (columnIndex == 2) {
                    return Integer.class;
                } else {
                    return Boolean.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                if(row == 0) return false;
                return super.isCellEditable(row, column);
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {

                if(column == 2) {//summ
                    int oldVal = Integer.valueOf((Integer) super.getValueAt(row, column));
                    int newVal = (Integer)aValue;
                    int oldSumm = Integer.valueOf((Integer) super.getValueAt(0, 2));
                    int newSumm = oldSumm - oldVal + newVal;
                    super.setValueAt(newSumm, 0, 2);
                }
                else if(column == 3){//check
                    boolean f = (Boolean) aValue;
                    if(f == false){
                        int oldSumm = Integer.valueOf((Integer) super.getValueAt(0, 2));
                        int newVal = Integer.valueOf((Integer) super.getValueAt(row, column - 1));
                        int newSumm = oldSumm - newVal;
                        super.setValueAt(newSumm, 0, 2);
                    }
                    else if(f == true){
                        int oldSumm = Integer.valueOf((Integer) super.getValueAt(0, 2));
                        int newVal = Integer.valueOf((Integer) super.getValueAt(row, column - 1));
                        int newSumm = oldSumm + newVal;
                        super.setValueAt(newSumm, 0, 2);
                    }
                }

                super.setValueAt(aValue, row, column);
            }

            @Override
            public Object getValueAt(int row, int column) {
                return super.getValueAt(row, column);
            }
        };
        table.setModel(data);
        int cost = 1000;

        data.addRow(new Object[]{"", "Summ", 0, false} );

        for (Map.Entry<String, Pair<String, ImageIcon>> element : map.entrySet()) {
            data.addRow(new Object[]{
                    map.get(element.getKey()).getValue(), element.getKey(), cost++, false} );
        }

        setPreferredSize(new Dimension(720, 600));
        setMinimumSize(new Dimension(700, 500));
        setVisible(true);
    }

    public void getCost() {
        int cost = 0;
        for (int row = 0; row < data.getRowCount(); ++row) {
            if ((Boolean) data.getValueAt(row, 3)) {
                cost += (Integer) data.getValueAt(row, 2);
            }
        }

        data.setValueAt(cost, 0, 2);
    }

    public void addNewTour() {
        AddTour dialog = new AddTour();
        dialog.setVisible(true);
        if (dialog.okPressed()) {
            try {
                data.addRow(dialog.getData());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Exception");
            }
        }
    }
}
