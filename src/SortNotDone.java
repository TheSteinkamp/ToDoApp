import java.util.ArrayList;
import java.util.List;

public class SortNotDone implements Criteria {
    @Override
    public List<Task> meetCriteria(List<Task> taskList) {
        List<Task> notDone= new ArrayList<>();
        for (Task t: taskList) {
            if(!t.isStatus())
                notDone.add(t);
        }
        return notDone;
    }
}
