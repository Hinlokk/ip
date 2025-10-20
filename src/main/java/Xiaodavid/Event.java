package Xiaodavid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents an event task that spans a start and end date.
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    // For saving back to file (keep ISO)
    private static final DateTimeFormatter SAVE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // For displaying to user (pretty)
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Creates an event task using already parsed dates.
     *
     * @param description description of the event
     * @param from start date of the event
     * @param to end date of the event
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates an event task from string representations of the dates.
     *
     * @param description description of the event
     * @param fromStr start date in {@code yyyy-MM-dd} format
     * @param toStr end date in {@code yyyy-MM-dd} format
     * @throws XiaodavidException if either date cannot be parsed
     */
    public Event(String description, String fromStr, String toStr) throws XiaodavidException {
        super(description, TaskType.EVENT);
        this.from = Parser.parseDate(fromStr);
        this.to = Parser.parseDate(toStr);
    }

    @Override
    public String toSaveFormat() {
        return String.join(" | ",
                type.getSymbol(),
                (isDone ? "1" : "0"),
                description,
                from.format(SAVE_FORMAT),
                to.format(SAVE_FORMAT)
        );
    }

    @Override
    public String toString() {
        return super.toString()
                + " (from: " + from.format(DISPLAY_FORMAT)
                + " to: " + to.format(DISPLAY_FORMAT) + ")";
    }
}
