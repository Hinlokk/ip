package Xiaodavid;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

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

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = (tasks == null) ? new ArrayList<>() : tasks;
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    /** Expose the underlying list for Xiaodavid.Storage.save(...) */
    public ArrayList<Task> getAll() {
        return tasks;
    }
}



