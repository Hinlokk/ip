package Xiaodavid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate from;
    private LocalDate to;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Event(String description, LocalDate from, LocalDate to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toSaveFormat() {
        return String.join(" | ",
                type.getSymbol(),
                (isDone ? "1" : "0"),
                description,
                from.format(DATE_FORMAT),  // <-- format LocalDate into String
                to.format(DATE_FORMAT)
        );
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from.format(DATE_FORMAT) + " to: " + to.format(DATE_FORMAT) + ")";
    }
}
