import java.util.Scanner;
import java.util.ArrayList;

public class Xiaodavid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        ArrayList<Task> tasks = new ArrayList<>();

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
                    System.out.println("------------------------------------");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tsk);
                    System.out.println("------------------------------------");
                }

                else if (input.startsWith("todo")) {
                    if (input.length() <= 5) {
                        throw new XiaodavidException("the description of todo cannot be empty lehh you goooon.");
                    }
                    String desc = input.substring(5);
                    Task newTask = new Todo(desc);
                    tasks.add(newTask);
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
                    printAdded(newTask, tasks.size());
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
}
