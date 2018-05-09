import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateTableModel extends DefaultTableModel {
    private final static int COLUMNS_COUNT = 10;
    private final static int ROWS_COUNT = 25;
    private final static String DATE_FORMAT_STRING = "dd.MM.yy";

    private DateCell[][] dates;
    private String[][] formulas;
    private SimpleDateFormat dateFormat;
    private Controller controller;
    //private DateTableCellEditor cellEditor;

    public DateTableModel(Controller controller) {
        this.controller = controller;

        dates = new DateCell[ROWS_COUNT][COLUMNS_COUNT];
        formulas =  new String[ROWS_COUNT][COLUMNS_COUNT];
        for (int i = 0; i < ROWS_COUNT; ++i) {
            for (int j = 0; j < COLUMNS_COUNT; ++j) {
                dates[i][j] = new DateCell(i, j, null);
                formulas[i][j] = "";
            }
        }

        dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
        dateFormat.setLenient(false);

        //cellEditor = new DateTableCellEditor();
    }

    public String getFormulaAt(int row, int column) {
        if (row < 0 || row >= ROWS_COUNT || column < 0 || column >= COLUMNS_COUNT) {
            return "";
        } else {
            return formulas[row][column];
        }
    }

    public class DateTableCellEditor implements TableCellRenderer {
        TableCellRenderer renderer = new DefaultTableCellRenderer();

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (hasFocus) {
                return renderer.getTableCellRendererComponent(
                        table, formulas[row][column], isSelected, hasFocus, row, column);
            } else {
                return renderer.getTableCellRendererComponent(
                        table, getValueAt(row, column), isSelected, hasFocus, row, column);
            }
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public int getColumnCount() {
        return COLUMNS_COUNT;
    }

    @Override
    public int getRowCount() {
        return ROWS_COUNT;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        if (column == 0) {
            return;
        }
        String input = (String) value;
        if (input.equals("")) {
            dates[row][column].setValue(null);
            return;
        }
        try {
            if (!input.startsWith("=")) {
                ConstDate date = parseDate(input);
                dates[row][column].setValue(date);
                formulas[row][column] = input;
            } else if (input.startsWith("=min(") || input.startsWith("=max(")) {
                if (input.charAt(input.length() - 1) != ')') {
                    throw new BadInputFormatException();
                }
                StringTokenizer tokenizer = new StringTokenizer(input.substring(5, input.length() - 1), ",");
                if (!tokenizer.hasMoreTokens()) {
                    throw new BadInputFormatException();
                }
                ArrayList<Date> args = new ArrayList<>();
                while (tokenizer.hasMoreTokens()) {
                    Date current;
                    String token = tokenizer.nextToken();
                    try {
                        current = parseDate(token);
                    } catch (BadDateException e) {
                        current = parseCell(token);
                    }
                    if (current.depended(dates[row][column])) {
                        throw new CircularDependencyException();
                    }
                    args.add(current);
                }
                Date result = input.startsWith("=min") ?
                        new MinDate(args) : new MaxDate(args);
                dates[row][column].setValue(result);
                formulas[row][column] = input;
            } else {
                int operationIndex = input.indexOf('+');
                if (operationIndex == -1) {
                    operationIndex = input.indexOf('-');
                }
                if (operationIndex == -1) {
                    throw new BadInputFormatException();
                }
                Date arg = null;
                try {
                    arg = parseDate(input.substring(1, operationIndex));
                } catch (BadDateException e) {
                    arg = parseCell(input.substring(1, operationIndex));
                }
                if (arg.depended(dates[row][column])) {
                    throw new CircularDependencyException();
                }
                try {
                    int addValue = Integer.parseInt(input.substring(operationIndex));
                    Date date = new AddDate(arg, addValue);
                    dates[row][column].setValue(date);
                    formulas[row][column] = input;
                } catch (NumberFormatException e) {
                    throw new BadInputFormatException();
                }
            }
            //fireTableDataChanged();
        } catch (BadDateException | CircularDependencyException | BadCellException | BadInputFormatException e) {
            controller.showErrorMessage(e.getMessage());
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return row;
            default:
                try {
                    GregorianCalendar calendar = dates[row][column].evaluate();
                    if (calendar == null) {
                        return "";
                    } else {
                        //return "Hello";
                        return dateFormat.format(calendar.getTime());
                    }
                } catch (NullReferenceException e) {
                    return "ERROR";
                }
        }
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "";
        } else if (column < COLUMNS_COUNT) {
            return Character.toString((char) ('A' + (column - 1)));
        }
        return super.getColumnName(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            default:
                return String.class;
        }
    }

    private ConstDate parseDate(String input) throws BadDateException {
        try {
            java.util.Date date = dateFormat.parse(input);
            GregorianCalendar result = new GregorianCalendar();
            result.setTime(date);
            return new ConstDate(result);
        } catch (ParseException e) {
            throw new BadDateException();
        }
    }

    private DateCell parseCell(String input) throws BadCellException {
        try {
            int column = (int) (input.charAt(0) - 'A' + 1);
            int row = Integer.parseInt(input.substring(1));
            if (column < 1 || column >= COLUMNS_COUNT || row < 0 || row >= ROWS_COUNT ||
                    dates[row][column].evaluate() == null) {
                throw new BadCellException();
            }
            return dates[row][column];
        } catch (NullReferenceException e) {
            throw new BadCellException();
        }
    }

}
