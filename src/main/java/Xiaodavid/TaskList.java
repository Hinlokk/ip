package Xiaodavid;

import java.util.ArrayList;

/**
 * TaskList manages a collection of Task objects.
 * It provides methods to add, remove, and access tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList with the given list of tasks.
     * If the input list is null, an empty list is created instead.
     *
     * @param tasks the initial list of tasks, may be null
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = (tasks == null) ? new ArrayList<>() : tasks;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks whether the task list is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Retrieves the task at the given index.
     *
     * @param index the index of the task to retrieve
     * @return the Task at the specified index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the list.
     *
     * @param t the Task to add
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Removes the task at the given index.
     *
     * @param index the index of the task to remove
     * @return the removed Task
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the underlying list of tasks.
     * Used for saving tasks to storage.
     *
     * @return the list of all tasks
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }
}
