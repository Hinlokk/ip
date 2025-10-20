package Xiaodavid;

import java.util.ArrayList;
import java.time.format.DateTimeParseException;

public class TaskList {
    private final ArrayList<Task> tasks;

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

    public String addTodo(String desc) {
        Task t = new Todo(desc);
        tasks.add(t);
        return "Added new todo:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.";
    }

    public String addDeadline(String desc, String by) throws XiaodavidException {
    try {Task t = new Deadline(desc, by);
        tasks.add(t);
        return "Added new deadline:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.";
    } catch (DateTimeParseException e) {
        throw new XiaodavidException("date format must be yyyy-mm-dd leh you goooon.");
        }
    }

    public String addEvent(String desc, String from, String to) throws XiaodavidException {
        try {
            Task t = new Event(desc, from, to);
            tasks.add(t);
            return "Added new event:\n  " + t + "\nNow you have " + tasks.size() + " tasks in the list.";
        } catch (DateTimeParseException e) {
            throw new XiaodavidException("date format must be yyyy-mm-dd leh you goooon.");
        }
    }

    public String markTask(int index) throws XiaodavidException {
        if (index < 0 || index >= tasks.size()) {
            throw new XiaodavidException("ehh that task number does not exist la you goooon.");
        }
        Task t = tasks.get(index);
        t.markAsDone();
        return "Shiok, I marked this as done:\n  " + t;
    }

    public String unmarkTask(int index) throws XiaodavidException {
        if (index < 0 || index >= tasks.size()) {
            throw new XiaodavidException("ehh that task number does not exist la you goooon.");
        }
        Task t = tasks.get(index);
        t.markAsUndone();
        return "Ok lor, I unmarked this task:\n  " + t;
    }

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

    // Existing logic (still useful internally)
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
