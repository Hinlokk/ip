package Xiaodavid;

import java.util.Scanner;
/**
 * Handles all console-based user interactions, including reading input and displaying messages.
 */
public class Ui {
    private final Scanner sc;
    /**
     * Creates a UI helper that reads from {@link System#in}.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }
    /**
     * Prints the welcome banner shown when the application starts.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Xiaodavid.Xiaodavid!");
        System.out.println("What can I do for you?");
    }
    /**
     * Reads a full command line entered by the user.
     *
     * @return the next line from standard input
     */
    public String readCommand() {
        return sc.nextLine();
    }
    /**
     * Prints a divider line used to frame sections of console output.
     */
    public void showLine() {
        System.out.println("------------------------------------");
    }
    /**
     * Shows a general error message framed by divider lines.
     *
     * @param msg message describing the error condition
     */
    public void showError(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }
    /**
     * Shows an error message encountered while loading saved tasks.
     *
     * @param msg message describing the loading issue
     */
    public void showLoadingError(String msg) {
        showLine();
        System.out.println("Error loading tasks: " + msg);
        showLine();
    }
    /**
     * Displays all tasks in the given list to the console.
     *
     * @param tasks list of tasks to print
     */
    public void showList(TaskList tasks) {
        showLine();
        if (tasks.isEmpty()) {
            System.out.println("Your task list is empty leh...");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
        showLine();
    }
    /**
     * Prints a confirmation that a task was added along with the updated task count.
     *
     * @param t the task that was added
     * @param total the new total number of tasks
     */
    public void showAdded(Task t, int total) {
        System.out.println("-----------------------------------");
        System.out.println("Got it. I've added this task: ");
        System.out.println("  " + t);
        System.out.println("Now you have " + total + " tasks in the list.");
        System.out.println("-----------------------------------");
    }
    /**
     * Prints a confirmation that a task has been marked as done.
     *
     * @param t the task that was marked done
     */
    public void showMarked(Task t) {
        showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + t);
        showLine();
    }
    /**
     * Prints a confirmation that a task has been marked as not done.
     *
     * @param t the task that was unmarked
     */
    public void showUnmarked(Task t) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + t);
        showLine();
    }
    /**
     * Prints a confirmation that a task was deleted along with the updated task count.
     *
     * @param removed the task that was removed
     * @param total the remaining number of tasks
     */
    public void showDeleted(Task removed, int total) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + total + " tasks in the list.");
        showLine();
    }
    /**
     * Prints the goodbye message shown when exiting the application.
     */
    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
