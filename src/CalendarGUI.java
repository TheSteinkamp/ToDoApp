import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalendarGUI extends JFrame implements ActionListener, ListSelectionListener{
    Calendar calendar = new Calendar();
    private String[] years = { "2023", "2024", "2025" };
    private JComboBox comboBox = new JComboBox(years);
    private String[] months = { "Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti",
            "September", "Oktober", "November", "December" };
    private JList list = new JList(months);
    private JScrollPane scrollPane = new JScrollPane(list);
    private JTable table = new JTable(calendar);

    public CalendarGUI() {

        getContentPane().setLayout(null);
        comboBox.setBounds(10, 10, 100, 30);
        comboBox.setSelectedIndex(0);
        comboBox.addActionListener(this);
        scrollPane.setBounds(200, 10, 150, 100);
        list.setSelectedIndex(11);
        list.addListSelectionListener(this);
        table.setBounds(10, 150, 550, 200);
        calendar.setMonth(comboBox.getSelectedIndex() + 2023, list.getSelectedIndex());

        getContentPane().add(comboBox);
        getContentPane().add(scrollPane);
        table.setGridColor(Color.black);
        table.setShowGrid(true);
        getContentPane().add(table);

        setSize(800, 500);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        calendar.setMonth(comboBox.getSelectedIndex() + 2023, list.getSelectedIndex());
        table.repaint();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        calendar.setMonth(comboBox.getSelectedIndex() + 2023, list.getSelectedIndex());
        table.repaint();
    }

}
