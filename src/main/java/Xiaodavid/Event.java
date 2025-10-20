package Xiaodavid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    // For saving back to file (keep ISO)
    private static final DateTimeFormatter SAVE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // For displaying to user (pretty)
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    // Accept LocalDate directly
    public Event(String description, LocalDate from, LocalDate to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    // Accept String dates, use Parser.parseDate for validation
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
