import java.util.Comparator;
import java.util.List;

public class SortDate implements Criteria{
    @Override
    public List<Task> meetCriteria(List<Task> taskList) {
        taskList.sort(Comparator.comparing(Task::getDate));
        return taskList;
    }
}
