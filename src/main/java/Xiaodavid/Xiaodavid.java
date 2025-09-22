package Xiaodavid;

import java.io.IOException;

/**
 * Main class for the Xiaodavid chatbot.
 * <p>
 * Xiaodavid helps users keep track of tasks by supporting commands such as
 * adding todos, deadlines, and events, marking/unmarking tasks,
 * deleting tasks, searching tasks, and listing all tasks.
 * </p>
 */
public class Xiaodavid {
    private final TaskList tasks;
    private final Storage storage;

    /**
     * Constructs a Xiaodavid chatbot instance.
     * Attempts to load existing tasks from storage; if loading fails,
     * starts with an empty task list.
     *
     * @param filePath the file path to load and save tasks
     */
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

    /**
     * Returns a welcome message with a list of supported commands.
     *
     * @return welcome message as a string
     */
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

    /**
     * Processes user input, executes the corresponding command, and returns the result.
     *
     * @param input the user’s raw input string
     * @return the response message after executing the command
     */
    public String getResponse(String input) {
        try {
            Parser.ParsedCommand parsed = Parser.parse(input);
            switch (parsed.type) {
                case LIST:
                    return tasks.listTasks();
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
                    if (matches.isEmpty()) {
                        return "No matching tasks found.";
                    }
                    StringBuilder sb = new StringBuilder("Here are the matching tasks:\n");
                    for (int i = 0; i < matches.size(); i++) {
                        sb.append((i + 1)).append(". ").append(matches.get(i)).append("\n");
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
