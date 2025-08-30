package Xiaodavid;

import java.io.IOException;
import java.time.LocalDate;

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
        } catch (IOException e) {
            ui.showLoadingError(e.getMessage());
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    private void ensureValidIndex(int idx) throws XiaodavidException {
        if (idx < 0 || idx >= tasks.size()) {
            throw new XiaodavidException("that task number dont exist la you goooon.");
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            try {
                Parser.ParsedCommand pc = Parser.parse(input);

                switch (pc.type) {
                    case BYE:
                        ui.showBye();
                        isExit = true;
                        break;

                    case LIST:
                        ui.showList(tasks);
                        break;

                    case MARK: {
                        int idx = Parser.parseIndex(pc.args[0]);
                        ensureValidIndex(idx);
                        Task t = tasks.get(idx);
                        t.markAsDone();
                        storage.save(tasks.getAll());
                        ui.showMarked(t);
                        break;
                    }

                    case UNMARK: {
                        int idx = Parser.parseIndex(pc.args[0]);
                        ensureValidIndex(idx);
                        Task t = tasks.get(idx);
                        t.markAsUndone();
                        storage.save(tasks.getAll());
                        ui.showUnmarked(t);
                        break;
                    }

                    case DELETE: {
                        int idx = Parser.parseIndex(pc.args[0]);
                        ensureValidIndex(idx);
                        Task removed = tasks.remove(idx);
                        storage.save(tasks.getAll());
                        ui.showDeleted(removed, tasks.size());
                        break;
                    }

                    case TODO: {
                        Task newTask = new Todo(pc.args[0]);
                        tasks.add(newTask);
                        storage.save(tasks.getAll());
                        ui.showAdded(newTask, tasks.size());
                        break;
                    }

                    case DEADLINE: {
                        String desc = pc.args[0];
                        LocalDate by = Parser.parseDate(pc.args[1]);
                        Task newTask = new Deadline(desc, by);
                        tasks.add(newTask);
                        storage.save(tasks.getAll());
                        ui.showAdded(newTask, tasks.size());
                        break;
                    }

                    case EVENT: {
                        String desc = pc.args[0];
                        LocalDate from = Parser.parseDate(pc.args[1]);
                        LocalDate to   = Parser.parseDate(pc.args[2]);
                        Task newTask = new Event(desc, from, to);
                        tasks.add(newTask);
                        storage.save(tasks.getAll());
                        ui.showAdded(newTask, tasks.size());
                        break;
                    }

                    default:
                        throw new XiaodavidException("ehh what are you saying i dun understand leh you goooon.");
                }

            } catch (XiaodavidException e) {
                ui.showError(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showError("ehh that number input is invalid la you goooon.");
            } catch (IOException e) {
                ui.showError("Error saving tasks: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Xiaodavid("tasks.txt").run();
    }
}
