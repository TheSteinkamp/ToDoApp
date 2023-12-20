import java.util.ArrayList;
import java.util.List;

public class SortDone implements Criteria {
    @Override
    public List<Task> meetCriteria(List<Task> taskList) {
        List<Task> done= new ArrayList<>();
        for (Task t: taskList) {
            if(t.isStatus())
                done.add(t);
        }
        return done;
    }
}
