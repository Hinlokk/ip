import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Xiaodavid {
    private static final String FILE_PATH = "tasks.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        ArrayList<Task> tasks = loadTasks(); // load tasks at start

        System.out.println("Hello! I'm Xiaodavid!");
        System.out.println("What can I do for you?");

        while (true) {
            input = sc.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }

                else if (input.equals("list")) {
                    System.out.println("------------------------------------");
                    if (tasks.isEmpty()) {
                        System.out.println("Your task list is empty leh...");
                    } else {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                    }
                    System.out.println("------------------------------------");
                }

                else if (input.startsWith("mark")) {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new XiaodavidException("that task number dont exist la you goooon.");
                    }
                    Task tsk = tasks.get(index);
                    tsk.markAsDone();
                    saveTasks(tasks); // save after change
                    System.out.println("------------------------------------");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tsk);
                    System.out.println("------------------------------------");
                }

                else if (input.startsWith("unmark")) {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new XiaodavidException("that task number dont exist la you goooon.");
                    }
                    Task tsk = tasks.get(index);
                    tsk.markAsUndone();
                    saveTasks(tasks); // save after change
                    System.out.println("------------------------------------");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tsk);
                    System.out.println("------------------------------------");
                }

                else if (input.startsWith("todo")) {
                    if (input.length() <= 5) {
                        throw new XiaodavidException("the description of todo cannot be empty lehh you goooon.");
                    }
                    String desc = input.substring(5).trim();
                    Task newTask = new Todo(desc);
                    tasks.add(newTask);
                    saveTasks(tasks);
                    printAdded(newTask, tasks.size());
                }

                else if (input.startsWith("deadline")) {
                    if (input.length() <= 9 || !input.contains("/by")) {
                        throw new XiaodavidException("ehh deadline must have description and a /by time you goooon.");
                    }
                    String[] parts = input.substring(9).split("/by", 2);
                    String desc = parts[0].trim();
                    String by = parts[1].trim();
                    if (desc.isEmpty() || by.isEmpty()) {
                        throw new XiaodavidException("ehh deadline description or /by cannot be empty you goooon.");
                    }
                    Task newTask = new Deadline(desc, by);
                    tasks.add(newTask);
                    saveTasks(tasks);
                    printAdded(newTask, tasks.size());
                }

                else if (input.startsWith("event")) {
                    if (input.length() <= 6 || !input.contains("/from") || !input.contains("/to")) {
                        throw new XiaodavidException("ehh an event must have description, /from and /to you goooon.");
                    }
                    String[] parts = input.substring(6).split("/from|/to");
                    if (parts.length < 3) {
                        throw new XiaodavidException("ehh invalid event description laaa you goooon.");
                    }
                    String desc = parts[0].trim();
                    String from = parts[1].trim();
                    String to = parts[2].trim();
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        throw new XiaodavidException("ehh an event's description, /from and /to cannot be empty you goooon.");
                    }
                    Task newTask = new Event(desc, from, to);
                    tasks.add(newTask);
                    saveTasks(tasks);
                    printAdded(newTask, tasks.size());
                }

                else if (input.startsWith("delete")) {
                    if (input.length() <= 7) {
                        throw new XiaodavidException("ehh must specify which task number to delete la you goooon.");
                    }
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= tasks.size()) {
                        throw new XiaodavidException("that task number dont exist la you goooon.");
                    }
                    Task removed = tasks.remove(index);
                    saveTasks(tasks);
                    System.out.println("------------------------------------");
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removed);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("------------------------------------");
                }

                else {
                    throw new XiaodavidException("ehh what are you saying i dun understand leh you goooon.");
                }

            } catch (XiaodavidException e) {
                System.out.println("------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("------------------------------------");
            } catch (NumberFormatException e) {
                System.out.println("------------------------------------");
                System.out.println("ehh that number input is invalid la you goooon.");
                System.out.println("------------------------------------");
            }
        }
        sc.close();
    }

    private static void printAdded(Task t, int total) {
        System.out.println("-----------------------------------");
        System.out.println("Got it. I've added this task: ");
        System.out.println("  " + t);
        System.out.println("Now you have " + total + " tasks in the list.");
        System.out.println("-----------------------------------");
    }

    // === SAVE TASKS ===
    private static void saveTasks(List<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Task t : tasks) {
                writer.write(t.toSaveFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    // === LOAD TASKS ===
    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return tasks; // return empty list if no file
        }

        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String desc = parts[2];

                switch (type) {
                    case "T":
                        Todo todo = new Todo(desc);
                        if (isDone) todo.markAsDone();
                        tasks.add(todo);
                        break;
                    case "D":
                        String by = parts[3];
                        Deadline deadline = new Deadline(desc, by);
                        if (isDone) deadline.markAsDone();
                        tasks.add(deadline);
                        break;
                    case "E":
                        String from = parts[3];
                        String to = parts[4];
                        Event event = new Event(desc, from, to);
                        if (isDone) event.markAsDone();
                        tasks.add(event);
                        break;
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }
}
