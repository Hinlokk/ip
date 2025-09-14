package Xiaodavid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate from;
    private LocalDate to;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Accept LocalDate
    public Event(String description, LocalDate from, LocalDate to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    // New constructor: accept Strings
    public Event(String description, String fromStr, String toStr) {
        super(description, TaskType.EVENT);
        this.from = LocalDate.parse(fromStr);
        this.to = LocalDate.parse(toStr);
    }

    @Override
    public String toSaveFormat() {
        return String.join(" | ",
                type.getSymbol(),
                (isDone ? "1" : "0"),
                description,
                from.format(DATE_FORMAT),
                to.format(DATE_FORMAT)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;

        Event other = (Event) obj;
        return this.from.equals(other.from) && this.to.equals(other.to);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + from.hashCode() + to.hashCode();
    }


    @Override
    public String toString() {
        return super.toString() + " (from: " + from.format(DATE_FORMAT) + " to: " + to.format(DATE_FORMAT) + ")";
    }
}
