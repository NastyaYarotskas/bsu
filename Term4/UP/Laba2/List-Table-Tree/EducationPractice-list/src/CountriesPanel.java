import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CountriesPanel extends JPanel{

    private Map<String, Pair<String, ImageIcon>> map;
    private JLabel label;

    CountriesPanel(Map<String, Pair<String, ImageIcon>> newMap) {
        super();
        map = newMap;
        setLayout(new BorderLayout());
        JList<String> list = new JList();
        add(new JScrollPane(list), BorderLayout.CENTER);
        DefaultListModel data = new DefaultListModel();

        for (Map.Entry temp : map.entrySet()) {
            data.addElement(temp.getKey());
        }
        label = new JLabel();
        add(label, BorderLayout.NORTH);

        list.setModel(data);
        list.setCellRenderer(new MyCellRenderer());
        list.addListSelectionListener(e->{
            String country = list.getSelectedValue();
            label.setIcon(map.get(country).getValue());
            label.setText(country + "   " + map.get(country).getKey());
        });
        setPreferredSize(new Dimension(600, 550));
        setMinimumSize(new Dimension(600, 400));
        setVisible(true);
    }

    class MyCellRenderer extends DefaultListCellRenderer {
        public MyCellRenderer() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList<?> list,
                                                      Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            setIcon(map.get(value.toString()).getValue());

            return this;
        }
    }

}
