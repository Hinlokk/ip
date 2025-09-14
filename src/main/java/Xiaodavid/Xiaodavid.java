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
            assert loadedTasks != null : "Task list should be be null after loading";
        } catch (IOException e) {
            loadedTasks = new TaskList(); // fallback empty list
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        tasks = loadedTasks;

        //Check: After initialization, tasks should never be null
        assert tasks != null : "Task must be initialized";
    }

    public String getWelcomeMessage() {
        String msg = "Hello! I am Xiaodavid 😎\n" +
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
        assert msg != null && !msg.isEmpty() : "Welcome message should be not be empty";
        return msg;
    }

    public String getResponse(String input) {
        assert input != null : "Input should not be null";
        assert !input.trim().isEmpty() : "Input should not be empty";

        try {
            Parser.ParsedCommand parsed = Parser.parse(input);
            assert parsed != null : "Parsed command should not be null";

            switch (parsed.type) {
                case LIST:
                    String listResult = tasks.listTasks();
                    assert listResult != null : "List result should not be null";
                    return listResult;   // returns string now
                case TODO:
                    assert parsed.args.length > 0 : "TODO requires description";
                    return tasks.addTodo(parsed.args[0]);
                case DEADLINE:
                    assert parsed.args.length > 1 : "Deadline requires description and date";
                    return tasks.addDeadline(parsed.args[0], parsed.args[1]);
                case EVENT:
                    assert parsed.args.length > 2 : "Event requires description, start and end";
                    return tasks.addEvent(parsed.args[0], parsed.args[1], parsed.args[2]);
                case MARK:
                    return tasks.markTask(Parser.parseIndex(parsed.args[0]));
                case UNMARK:
                    return tasks.unmarkTask(Parser.parseIndex(parsed.args[0]));
                case DELETE:
                    return tasks.deleteTask(Parser.parseIndex(parsed.args[0]));
                case FIND:
                    assert parsed.args.length > 0 : "Find requires a keyword";
                    var matches = tasks.findTasks(parsed.args[0]);
                    assert matches != null : "Find should return a non-null list";

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
