package Xiaodavid;
/**
 * Represents a basic to-do task that only stores a description.
 */
public class Todo extends Task {
    /**
     * Creates a new to-do task with the specified description.
     *
     * @param description details of the to-do task
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toSaveFormat() {
        return String.join(" | ",
                type.getSymbol(),              // "T"
                (isDone ? "1" : "0"),          // done flag
                description
        );
    }
}
