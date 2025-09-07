package Xiaodavid;

import java.io.IOException;
import java.time.LocalDate;

public class Xiaodavid {
    private final TaskList tasks;
    private final Storage storage;

    public Xiaodavid(String filePath) {
        storage = new Storage(filePath);
        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (IOException e) {
            loadedTasks = new TaskList(); // fallback empty list
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        tasks = loadedTasks;
    }

    public String getWelcomeMessage() {
        return "Hello! I am Xiaodavid 😎\n" +
                "Here are some commands you can use:\n" +
                "- todo <description>\n" +
                "- deadline <description> /by <yyyy-mm-dd>\n" +
                "- event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>\n" +
                "- list\n" +
                "- mark <task number>\n" +
                "- unmark <task number>\n" +
                "- delete <task number>\n" +
                "- find <keyword>\n" +
                "- bye";
    }

    public String getResponse(String input) {
        try {
            Parser.ParsedCommand parsed = Parser.parse(input);
            switch (parsed.type) {
                case LIST:
                    return tasks.listTasks();   // returns string now
                case TODO:
                    return tasks.addTodo(parsed.args[0]);
                case DEADLINE:
                    return tasks.addDeadline(parsed.args[0], parsed.args[1]);
                case EVENT:
                    return tasks.addEvent(parsed.args[0], parsed.args[1], parsed.args[2]);
                case MARK:
                    return tasks.markTask(Parser.parseIndex(parsed.args[0]));
                case UNMARK:
                    return tasks.unmarkTask(Parser.parseIndex(parsed.args[0]));
                case DELETE:
                    return tasks.deleteTask(Parser.parseIndex(parsed.args[0]));
                case FIND:
                    var matches = tasks.findTasks(parsed.args[0]);
                    if (matches.isEmpty()) return "No matching tasks found.";
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < matches.size(); i++) {
                        sb.append((i + 1) + ". " + matches.get(i) + "\n");
                    }
                    return sb.toString();

                case BYE:
                    return "Bye goooooooooooon, see you again never!";
                default:
                    return "ehh you gooooon, I don’t understand your command leh.";
            }
        } catch (XiaodavidException e) {
            return e.getMessage();
        }
    }
}
