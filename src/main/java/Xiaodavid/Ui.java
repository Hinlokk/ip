package Xiaodavid;

import java.util.Scanner;

public class Ui {
    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm Xiaodavid.Xiaodavid!");
        System.out.println("What can I do for you?");
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void showLine() {
        System.out.println("------------------------------------");
    }

    public void showError(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }

    public void showLoadingError(String msg) {
        showLine();
        System.out.println("Error loading tasks: " + msg);
        showLine();
    }

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

    public void showAdded(Task t, int total) {
        System.out.println("-----------------------------------");
        System.out.println("Got it. I've added this task: ");
        System.out.println("  " + t);
        System.out.println("Now you have " + total + " tasks in the list.");
        System.out.println("-----------------------------------");
    }

    public void showMarked(Task t) {
        showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + t);
        showLine();
    }

    public void showUnmarked(Task t) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + t);
        showLine();
    }

    public void showDeleted(Task removed, int total) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + total + " tasks in the list.");
        showLine();
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
