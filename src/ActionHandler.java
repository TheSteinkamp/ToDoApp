import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class ActionHandler implements ActionListener, MouseListener {

    TaskManager taskManager;
    String markedTask = "";
    Task taskMarkedForDeletion = null;
    Task taskToUpdate = null;

    private JLabel lastClickedLabel;

    private GUI gui;

    public ActionHandler(GUI gui, TaskManager taskManager) {
        this.gui = gui;
        this.taskManager = taskManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton buttonClicked) {

            if (Objects.equals(buttonClicked.getText(), "Lägg till uppgift")) {
                gui.addTaskWindow(null);
                System.out.println("Tryckte på lägg till");

            } else if (Objects.equals(buttonClicked.getText(), "Ta bort markerad")) {
                markedTask = lastClickedLabel.getText();
                for (Task task : taskManager.getTaskList()) {
                    if (markedTask.matches(task.getTitle())) {
                        taskMarkedForDeletion = task;
                    }
                }
                taskManager.removeTask(taskMarkedForDeletion);
                System.out.println("Removed task: " + taskMarkedForDeletion);

                try {
                    taskManager.updateDatabase();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                gui.createLabels("all");

            } else if ((Objects.equals(buttonClicked.getText(), "Redigera uppgift")) && lastClickedLabel != null) {
                markedTask = lastClickedLabel.getText();

                for (Task task : taskManager.getTaskList()) {
                    if (markedTask.matches(task.getTitle())) {
                        gui.addTaskWindow(task);
                        taskToUpdate = task;
                    }
                }
                System.out.println("Tryckte på Redigera");

            } else if (buttonClicked.getText().equals("Markera som klar") && lastClickedLabel != null) {
                Task t = taskManager.stringToTask(lastClickedLabel.getText());
                t.setStatus(true);
                lastClickedLabel.setBackground(Color.GREEN);
                lastClickedLabel.setOpaque(true);
                gui.getMainPanel().revalidate();
                gui.getMainPanel().repaint();

            } else if (Objects.equals(buttonClicked.getText(), "Kalender")) {
                CalendarGUI g = new CalendarGUI();
                System.out.println("Tryckte på kalender");

            } else if (Objects.equals(buttonClicked.getText(), "Visa klarade uppgifter")) {
                gui.updateGridPane(taskManager.showList("done"));
                gui.createLabels("done");
                buttonClicked.setText("Visa ej klara uppgifter");
                System.out.println("Tryckte på Visa klarade uppgifter");

            } else if (Objects.equals(buttonClicked.getText(), "Visa ej klara uppgifter")) {
                gui.updateGridPane(taskManager.showList("not done"));
                gui.createLabels("not done");
                buttonClicked.setText("Visa klarade uppgifter");
                System.out.println("Tryckte på Visa ej klara uppgifter");

            } else if (Objects.equals(buttonClicked.getText(), "Visa alla uppgifter")) {
                gui.updateGridPane(taskManager.showList("all"));
                gui.createLabels("all");
                System.out.println("Tryckte på Visa alla uppgifter");

            } else if (Objects.equals(buttonClicked.getText(), "Visa i datumordning")) {
                gui.updateGridPane(taskManager.showList("date"));
                gui.createLabels("date");
                System.out.println("Tryckte på datumsortera uppgifter");

            } else if (Objects.equals(buttonClicked.getText(), "Spara")) {

                System.out.println(gui.getTitleField().getText());
                System.out.println(gui.getDescriptionArea().getText());
                System.out.println(gui.getDate().getText());

                try {
                    taskManager.createTask(gui.getTitleField().getText(),
                            gui.getDescriptionArea().getText(),
                            LocalDate.parse(gui.getDate().getText()));
                    taskManager.updateDatabase();
                    gui.resetTextFields();
                    gui.updateGridPane(taskManager.showList("all"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Efter create task" + taskManager.getTaskList().size());
                gui.createLabels("all");
                gui.getAddTaskFrame().dispose();

            } else if (Objects.equals(buttonClicked.getText(), "Uppdatera")) {

                taskToUpdate.setTitle(gui.getTitleField().getText());
                taskToUpdate.setDate(LocalDate.parse(gui.getDate().getText()));
                taskToUpdate.setContent(gui.getDescriptionArea().getText());
                try {
                    taskManager.updateDatabase();
                    gui.resetTextFields();
                    gui.updateGridPane(taskManager.showList("all"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                gui.createLabels("all");
                gui.getAddTaskFrame().dispose();
                System.out.println("Tryckte på uppdatera");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() instanceof JLabel clickedLabel) {

            if (lastClickedLabel != null && lastClickedLabel != clickedLabel && lastClickedLabel.getBackground() != Color.green) {
                lastClickedLabel.setBackground(null);
                lastClickedLabel.setOpaque(false);
            }
            if (clickedLabel.getBackground() != Color.green) {
                clickedLabel.setBackground(Color.GRAY);
                clickedLabel.setOpaque(true);
            }
            lastClickedLabel = clickedLabel;
            gui.getMainPanel().revalidate();
            gui.getMainPanel().repaint();

            gui.getTaskDescription().setText("Deadline: " + taskManager.findTask((clickedLabel.getText())).getDate() + "\n" + taskManager.findTask(clickedLabel.getText()).getContent());
        }

    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
