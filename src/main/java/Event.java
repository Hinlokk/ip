import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = LocalDate.parse(from); // expects yyyy-mm-dd
        this.to = LocalDate.parse(to);     // expects yyyy-mm-dd
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (isDone ? 1 : 0) + " | " + description + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM d yyyy");
        return super.toString() + " (from: " + from.format(fmt)
                + " to: " + to.format(fmt) + ")";
    }
}
