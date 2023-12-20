import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {

    private String title;
    private String content;
    private LocalDate date;
    private boolean status;

    public Task(String title, String content, LocalDate date){
        this.title = title;
        this.content = content;
        this.date = date;
        status = false;
    }

    public Task(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
