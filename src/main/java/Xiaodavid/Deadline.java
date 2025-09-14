package Xiaodavid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate by;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Accept LocalDate
    public Deadline(String description, LocalDate by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    // New constructor: accept String input and parse to LocalDate
    public Deadline(String description, String byStr) {
        super(description, TaskType.DEADLINE);
        this.by = LocalDate.parse(byStr); // throws exception if format wrong
    }

    @Override
    public String toSaveFormat() {
        return String.join(" | ",
                type.getSymbol(),
                (isDone ? "1" : "0"),
                description,
                by.format(DATE_FORMAT)
        );
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by.format(DATE_FORMAT) + ")";
    }
}
