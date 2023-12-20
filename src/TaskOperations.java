import java.time.LocalDate;
import java.util.List;

public interface TaskOperations {

    public void removeTask(Task task);
    public List<Task> showList(String text);
    public void createTask(String titel, String description, LocalDate date);
    public Task findTask(String input);
    public List<Task> getTaskList();
}
