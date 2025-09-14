package Xiaodavid;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Xiaodavid {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Xiaodavid(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
            assert loaded != null : "Loaded tasks should not be null";
        } catch (IOException e) {
            ui.showLoadingError(e.getMessage());
            loaded = new TaskList();
        }
        this.tasks = loaded;
        assert this.tasks != null : "Tasks should never be null";
    }

    private void ensureValidIndex(int idx) throws XiaodavidException {
        if (idx < 0 || idx >= tasks.size()) {
            throw new XiaodavidException("That task number doesn't exist, you goooon.");
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            if (input == null || input.isBlank()) continue;

            try {
                Parser.ParsedCommand pc = Parser.parse(input);
                isExit = dispatchCommand(pc);
            } catch (XiaodavidException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showError("Invalid number input, you goooon.");
            } catch (IOException e) {
                ui.showError("Error saving tasks: " + e.getMessage());
            }
        }
    }

    private boolean dispatchCommand(Parser.ParsedCommand pc) throws IOException, XiaodavidException {
        switch (pc.type) {
            case BYE -> {
                ui.showBye();
                return true;
            }
            case LIST -> {
                ui.showList(tasks);
            }
            case MARK -> handleMark(pc.args[0]);
            case UNMARK -> handleUnmark(pc.args[0]);
            case DELETE -> handleDelete(pc.args[0]);
            case TODO -> handleTodo(pc.args[0]);
            case DEADLINE -> handleDeadline(pc.args[0], pc.args[1]);
            case EVENT -> handleEvent(pc.args[0], pc.args[1], pc.args[2]);
            case FIND -> handleFind(pc.args[0]);
            default -> throw new XiaodavidException("Unknown command, I don't understand leh you goooon.");
        }
        return false;
    }

    private void handleMark(String arg) throws XiaodavidException, IOException {
        int idx = Parser.parseIndex(arg);
        ensureValidIndex(idx);
        Task t = tasks.get(idx);
        t.markAsDone();
        saveAndShowMarked(t);
    }

    private void handleUnmark(String arg) throws XiaodavidException, IOException {
        int idx = Parser.parseIndex(arg);
        ensureValidIndex(idx);
        Task t = tasks.get(idx);
        t.markAsUndone();
        saveAndShowUnmarked(t);
    }

    private void handleDelete(String arg) throws XiaodavidException, IOException {
        int idx = Parser.parseIndex(arg);
        ensureValidIndex(idx);
        Task removed = tasks.remove(idx);
        saveTasks();
        ui.showDeleted(removed, tasks.size());
    }

    private void handleTodo(String desc) throws IOException {
        assert desc != null && !desc.isBlank() : "Todo description should not be empty";
        Task newTask = new Todo(desc);
        tasks.add(newTask);
        saveAndShowAdded(newTask);
    }

    private void handleDeadline(String desc, String byStr) throws IOException, XiaodavidException {
        assert desc != null && !desc.isBlank() : "Deadline description cannot be empty";
        LocalDate by = Parser.parseDate(byStr);
        Task newTask = new Deadline(desc, by);
        tasks.add(newTask);
        saveAndShowAdded(newTask);
    }

    private void handleEvent(String desc, String fromStr, String toStr) throws IOException, XiaodavidException {
        assert desc != null && !desc.isBlank() : "Event description cannot be empty";
        LocalDate from = Parser.parseDate(fromStr);
        LocalDate to = Parser.parseDate(toStr);
        Task newTask = new Event(desc, from, to);
        tasks.add(newTask);
        saveAndShowAdded(newTask);
    }

    private void handleFind(String keyword) {
        assert keyword != null && !keyword.isBlank() : "Keyword cannot be empty";
        List<Task> matches = tasks.findTasks(keyword);

        ui.showLine();
        if (matches.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + ". " + matches.get(i));
            }
        }
        ui.showLine();
    }

    private void saveTasks() throws IOException {
        storage.save(tasks.getAll());
    }

    private void saveAndShowAdded(Task task) throws IOException {
        saveTasks();
        ui.showAdded(task, tasks.size());
    }

    private void saveAndShowMarked(Task task) throws IOException {
        saveTasks();
        ui.showMarked(task);
    }

    private void saveAndShowUnmarked(Task task) throws IOException {
        saveTasks();
        ui.showUnmarked(task);
    }

    public static void main(String[] args) {
        new Xiaodavid("tasks.txt").run();
    }
}
