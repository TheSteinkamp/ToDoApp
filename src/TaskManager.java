import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskManager implements TaskOperations {
    List<Task> taskList = new ArrayList<>();
    File dbFile = new File("src/taskDatabase");

    @Override
    public List<Task> showList(String text) {
        List<Task> listToReturn = new ArrayList<>();
        if (Objects.equals(text, "all")) {
            Criteria all = new SortAll();
            listToReturn = all.meetCriteria(taskList);
        } else if (Objects.equals(text, "done")) {
            Criteria done = new SortDone();
            listToReturn = done.meetCriteria(taskList);
        } else if (Objects.equals(text, "not done")) {
            Criteria notDone = new SortNotDone();
            listToReturn = notDone.meetCriteria(taskList);
        }else if (Objects.equals(text, "date")) {
            Criteria date = new SortDate();
            listToReturn = date.meetCriteria(taskList);
        }
        return listToReturn;
    }

    public void dbToList() throws IOException {
        taskList.clear();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dbFile))) {
            String tempLine;
            while ((tempLine = bufferedReader.readLine()) != null) {
                Task task = new Task();
                task.setTitle(tempLine.substring(0, tempLine.indexOf(",")));
                task.setContent(tempLine.substring(tempLine.indexOf(",") + 2, tempLine.lastIndexOf(",")));
                task.setDate(LocalDate.parse(tempLine.substring(tempLine.lastIndexOf(",") + 2)));
                taskList.add(task);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
    }

    public void updateDatabase() throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dbFile))) {
            for (Task task : taskList) {
                bufferedWriter.write(task.getTitle() + ", " + task.getContent() + ", " + task.getDate());
                bufferedWriter.newLine();
            }
        }
    }

    @Override
    public void createTask(String titel, String description, LocalDate date) {
        Task task = new Task(titel, description, date);
        this.taskList.add(task);

    }

    @Override
    public Task findTask(String input) {
        for (Task task : taskList
        ) {
            if (input.matches(task.getTitle())) {
                return task;
            }
        }
        return null;
    }


    @Override
    public void removeTask(Task task) {
        taskList.remove(task);
    }

    @Override
    public List<Task> getTaskList() {
        return taskList;
    }

    public Task stringToTask(String text) {
        Task task = null;
        for (Task t : taskList) {
            if (Objects.equals(t.getTitle(), text)) {
                task = t;
            }
        }
        return task;
    }


}