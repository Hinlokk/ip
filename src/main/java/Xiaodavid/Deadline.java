package Xiaodavid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents a task with a due date.
 */
public class Deadline extends Task {
    private LocalDate by;

    // ISO format for saving
    private static final DateTimeFormatter SAVE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Friendly format for displaying
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Creates a deadline task with a parsed {@link LocalDate}.
     *
     * @param description description of the deadline task
     * @param by due date of the task
     */
    public Deadline(String description, LocalDate by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    /**
     * Creates a deadline task using a string representation of the due date.
     *
     * @param description description of the deadline task
     * @param byStr date string in {@code yyyy-MM-dd} format
     * @throws XiaodavidException if the date cannot be parsed
     */
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
