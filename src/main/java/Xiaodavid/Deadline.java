package Xiaodavid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate by;

    // ISO format for saving
    private static final DateTimeFormatter SAVE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Friendly format for displaying
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    // Accept LocalDate
    public Deadline(String description, LocalDate by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    // Accept String input and parse with Parser for validation
    public Deadline(String description, String byStr) throws XiaodavidException {
        super(description, TaskType.DEADLINE);
        this.by = Parser.parseDate(byStr);
    }

    @Override
    public String toSaveFormat() {
        return String.join(" | ",
                type.getSymbol(),
                (isDone ? "1" : "0"),
                description,
                by.format(SAVE_FORMAT)
        );
    }

    @Override
    public String toString() {
        return   super.toString()
                + " (by: " + by.format(DISPLAY_FORMAT) + ")";
    }
}
