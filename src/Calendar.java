import javax.swing.table.AbstractTableModel;

public class Calendar extends AbstractTableModel {
    private String[] days = { "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"  };

    private int[] numberOfDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private String[][] calendar = new String[7][7];

    public Calendar() {
        for (int i = 0; i < days.length; ++i)
            calendar[0][i] = days[i];
        for (int i = 1; i < 7; ++i)
            for (int j = 0; j < 7; ++j)
                calendar[i][j] = " ";
    }
    
    public void setMonth(int year, int month) {
        for (int i = 1; i < 7; ++i)
            for (int j = 0; j < 7; ++j)
                calendar[i][j] = " ";

        java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
        cal.set(year, month, 1);
        int offset = cal.get(java.util.GregorianCalendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
        offset = (offset + 7) % 7;

        int num = daysInMonth(year, month);

        int currentDay = 1;
        for (int i = 1; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (i == 1 && j < offset) {
                    // Tomma celler före första dagen
                    calendar[i][j] = " ";
                } else if (currentDay <= num) {
                    calendar[i][j] = Integer.toString(currentDay++);
                }
            }
        }
    }
    public int daysInMonth(int year, int month) {
        int days = numberOfDays[month];
        if (month == 1 && (year % 4 == 0)) // kolla om det är skottår eller inte
            ++days;
        return days;
    }


    public int getRowCount() {
        return 7;
    }

    public int getColumnCount() {
        return 7;
    }

    public Object getValueAt(int row, int column) {
        return calendar[row][column];
    }

    public void setValueAt(Object value, int row, int column) {
        calendar[row][column] = (String) value;
    }
}

