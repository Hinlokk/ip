package Xiaodavid;

/**
 * Represents a generic task with a description and completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    /**
     * Creates a Task with the given description.
     *
     * @param description description of the task
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }
    /**
     * Marks the task as undone.
     */
    public void markAsUndone() {this.isDone = false; }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if done, " " if not done
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }
    /**
     * Returns the task's description.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }


    /**
     * Returns a string suitable for saving the task to file.
     *
     * @return formatted string for save file
     */
    public abstract String toSaveFormat();

    @Override
    public String toString() {
        return "[" + type.getSymbol() + "][" + getStatusIcon() + "]" + description;
    }
}
