import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class GUI extends JFrame {
    private JFrame addTaskFrame = new JFrame();
    private JPanel mainPanel = new JPanel();
    private JPanel sidePanel = new JPanel();
    private JPanel menuPanel = new JPanel(new GridLayout(2,4));
    private JPanel addTaskMenuPanel = new JPanel(new GridLayout(4,1));
    private JPanel addTaskMainPanel = new JPanel(new GridLayout(4,1));
    private JButton addButton = new JButton("Lägg till uppgift");
    private JButton editButton = new JButton("Redigera uppgift");
    private JButton removeButton = new JButton("Ta bort markerad");
    private JButton markAsDoneButton = new JButton("Markera som klar");
    private JButton doneTaskButton = new JButton("Visa klarade uppgifter");
    //private JButton undoneTaskButton = new JButton("Visa ej klarade uppgifter");
    private JButton calendarButton = new JButton("Kalender");
    private JButton allTaskButton = new JButton("Visa alla uppgifter");
    private JButton dateTaskButton = new JButton("Visa i datumordning");
    private JTextArea taskDescription = new JTextArea(20, 40);
    private JTextField titleField = new JTextField();
    private JTextArea descriptionArea = new JTextArea(20, 40);
    private JTextField date = new JTextField();
    private JButton save = new JButton("Spara");
    private JButton updateButton = new JButton("Uppdatera");
    private JPanel gridPane = new JPanel(new GridLayout(15,1,15,15));
    private JScrollPane sideScrollPanel = new JScrollPane(gridPane);
    TaskManager taskManager = new TaskManager();
    ActionHandler actionListener = new ActionHandler(this,taskManager);
    private JLabel titel = new JLabel("Titel");
    private JLabel beskrivning = new JLabel("Beskrivning");
    private JLabel datum = new JLabel("Datum");


    public GUI() throws IOException {
        taskManager.dbToList();
        System.out.println("Task-list init size: " + taskManager.getTaskList().size());
        setTitle("ToDo Applikation");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        mainPanel.add(taskDescription);

        menuPanel.add(addButton);
        menuPanel.add(editButton);
        menuPanel.add(removeButton);
        menuPanel.add(markAsDoneButton);
        menuPanel.add(calendarButton);
        menuPanel.add(doneTaskButton);
        menuPanel.add(allTaskButton);
        menuPanel.add(dateTaskButton);

        addButton.addActionListener(actionListener);
        editButton.addActionListener(actionListener);
        removeButton.addActionListener(actionListener);
        doneTaskButton.addActionListener(actionListener);
        calendarButton.addActionListener(actionListener);
        allTaskButton.addActionListener(actionListener);
        dateTaskButton.addActionListener(actionListener);
        save.addActionListener(actionListener);
        updateButton.addActionListener(actionListener);
        markAsDoneButton.addActionListener(actionListener);

        createLabels("all");

        mainPanel.setPreferredSize(new Dimension(500, getHeight()));
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));
        menuPanel.setPreferredSize(new Dimension(getWidth(), 50));
        add(menuPanel, BorderLayout.NORTH);
        add(sideScrollPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.EAST);

        setVisible(true);
    }


    public Task addTaskWindow(Task task){
        addTaskFrame.dispose();
        addTaskFrame.add(addTaskMainPanel, BorderLayout.EAST);
        addTaskFrame.add(addTaskMenuPanel, BorderLayout.WEST);

        if(task != null) {
            titleField.setText(task.getTitle());
            descriptionArea.setText(task.getContent());
            date.setText(task.getDate().toString());
        }
        addTaskMenuPanel.add(titel);
        addTaskMainPanel.add(titleField);
        addTaskMenuPanel.add(beskrivning);
        addTaskMainPanel.add(descriptionArea);
        addTaskMenuPanel.add(datum);
        addTaskMainPanel.add(date);
        revalidate();
        repaint();

        if(task != null){
            addTaskMenuPanel.add(updateButton);
            System.out.println("I task != null");
        }else if (task == null) {
            addTaskMenuPanel.add(save);
            System.out.println("I task == null");
        }
        addTaskMenuPanel.setPreferredSize(new Dimension(100, 500));
        addTaskMainPanel.setPreferredSize(new Dimension(400, 500));
        addTaskFrame.setSize(800,500);
        addTaskFrame.setVisible(true);
        addTaskFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        return null;
    }

    public void updateGridPane(List<Task> taskList) {
        gridPane.removeAll();

        for (Task task : taskList) {
            JLabel label = new JLabel(task.getTitle());
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel clickedLabel = (JLabel) e.getSource();
                    clickedLabel.setBackground(Color.GRAY);
                    Component[] labels = gridPane.getComponents();
                    for (Component comp : labels) {
                        if (comp instanceof JLabel && comp != clickedLabel) {
                            comp.setBackground(null);
                        }
                    }
                }
            });
            label.setPreferredSize(new Dimension(300, label.getPreferredSize().height));
            label.setOpaque(true);
            gridPane.add(label);
        }

        revalidate();
        repaint();
    }


    public void createLabels(String text){
        gridPane.removeAll();
        for (Task task:taskManager.showList(text)
        ) {
            JLabel label = new JLabel(task.getTitle());
            label.addMouseListener(actionListener);
            label.setPreferredSize(new Dimension(300, label.getPreferredSize().height));
            label.setOpaque(true);
            gridPane.add(label);
        }

        gridPane.repaint();
        gridPane.revalidate();
    }

    public void resetTextFields(){
        date.setText(null);
        titleField.setText(null);
        descriptionArea.setText(null);
    }

    public void resetFrame(JFrame panel){
        this.remove(panel);
        this.validate();
        this.repaint();
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JTextArea getDescriptionArea() {
        return descriptionArea;
    }

    public JTextField getDate() {
        return date;
    }

    public JFrame getAddTaskFrame() {
        return addTaskFrame;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextArea getTaskDescription() {
        return taskDescription;
    }
}
