package Xiaodavid;

import java.util.ArrayList;
import java.time.format.DateTimeParseException;
/**
 * Maintains the in-memory list of tasks and provides high-level operations on it.
 */
public class TaskList {
    private final ArrayList<Task> tasks;
    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    /**
     * Creates a task list backed by the provided tasks, defaulting to empty if {@code null}.
     *
     * @param tasks initial tasks to populate the list
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = (tasks == null) ? new ArrayList<>() : tasks;
    }
    /**
     * Returns the number of tasks in the list.
     *
     * @return current task count
     */
    public int size() {
        return tasks.size();
    }
    /**
     * Indicates whether the list currently has no tasks.
     *
     * @return {@code true} if there are no tasks
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
    /**
     * Retrieves a task by its zero-based index.
     *
     * @param index zero-based position in the list
     * @return the task at the specified index
     */
    public Task get(int index) {
        return tasks.get(index);
    }
    /**
     * Adds a task to the end of the list.
     *
     * @param t task to append
     */
    public void add(Task t) {
        tasks.add(t);
    }
    /**
     * Removes and returns the task at the specified index.
     *
     * @param index zero-based position to remove
     * @return the removed task
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Exposes the underlying list for persisting tasks to storage.
     *
     * @return the backing {@link ArrayList}
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }

    /**
            * Formats all tasks as a numbered list suitable for display.
     *
             * @return string representation of all tasks
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return "You got no tasks now leh!";
        }
        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }
    /**
     * Adds a new to-do task to the list.
     *
     * @param desc description for the to-do
     * @return confirmation message to show the user
     */
    public String addTodo(String desc) {
        Task t = new Todo(desc);
        tasks.add(t);
        return "Added new todo:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
    /**
     * Adds a new deadline task to the list.
     *
     * @param desc description of the task
     * @param by due date string in {@code yyyy-MM-dd} format
     * @return confirmation message describing the added deadline
     * @throws XiaodavidException if the date string cannot be parsed
     */
    public String addDeadline(String desc, String by) throws XiaodavidException {
        try {
            Task t = new Deadline(desc, by);
            tasks.add(t);
            return "Added new deadline:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.";
        } catch (DateTimeParseException e) {
            throw new XiaodavidException("date format must be yyyy-mm-dd leh you goooon.");
            }
        }
    /**
     * Adds a new event task to the list.
     *
     * @param desc description of the event
     * @param from start date string in {@code yyyy-MM-dd} format
     * @param to end date string in {@code yyyy-MM-dd} format
     * @return confirmation message describing the added event
     * @throws XiaodavidException if either date string cannot be parsed
     */
    public String addEvent(String desc, String from, String to) throws XiaodavidException {
        try {
            Task t = new Event(desc, from, to);
            tasks.add(t);
            return "Added new event:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.";
        } catch (DateTimeParseException e) {
            throw new XiaodavidException("date format must be yyyy-mm-dd leh you goooon.");
        }
    }
    /**
     * Marks the task at the given index as not done.
     *
     * @param index zero-based position of the task to unmark
     * @return confirmation message describing the unmarked task
     * @throws XiaodavidException if the index is out of bounds
     */
    public String markTask(int index) throws XiaodavidException {
        if (index < 0 || index >= tasks.size()) {
            throw new XiaodavidException("ehh that task number does not exist la you goooon.");
        }
        Task t = tasks.get(index);
        t.markAsDone();
        return "Shiok, I marked this as done:\n  " + t;
    }
    /**
     * Removes the task at the specified index.
     *
     * @param index zero-based position of the task to delete
     * @return confirmation message describing the removed task
     * @throws XiaodavidException if the index is out of bounds
     */
    public String unmarkTask(int index) throws XiaodavidException {
        if (index < 0 || index >= tasks.size()) {
            throw new XiaodavidException("ehh that task number does not exist la you goooon.");
        }
        Task t = tasks.get(index);
        t.markAsUndone();
        return "Ok lor, I unmarked this task:\n  " + t;
    }
    /**
     * Finds tasks containing the keyword and returns a formatted summary.
     *
     * @param keyword text to search for in each task
     * @return formatted list of matching tasks, or a message if none found
     */
    public String deleteTask(int index) throws XiaodavidException {
        if (index < 0 || index >= tasks.size()) {
            throw new XiaodavidException("ehh that task number does not exist la you goooon.");
        }
        Task t = tasks.remove(index);
        return "Removed this task:\n  " + t + "\nNow you have " + tasks.size() + " tasks left.";
    }

    public String findTasksAsString(String keyword) {
        ArrayList<Task> matches = findTasks(keyword);
        if (matches.isEmpty()) {
            return "Cannot find anything matching \"" + keyword + "\" leh.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append((i + 1)).append(". ").append(matches.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Finds all tasks whose string representation contains the keyword.
     *
     * @param keyword case-insensitive substring to match
     * @return list of matching tasks
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task t : tasks) {
            if (t.toString().toLowerCase().contains(lowerKeyword)) {
                result.add(t);
            }
        }
        return result;
    }
}
