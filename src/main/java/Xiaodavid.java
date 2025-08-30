import java.util.Scanner;
import java.util.ArrayList;


public class Xiaodavid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("Hello! I'm Xiaodavid!");
        System.out.println("What can I do for you?");

        while(true) {
            input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equals("list")){
                System.out.println("------------------------------------");
                System.out.println("Here are the tasks in your list:");
                for(int i = 0; i < tasks.size();i++) {
                    System.out.println((i +1) + ". " + tasks.get(i));

                }
                System.out.println("------------------------------------");
                continue;
            }

            if (input.startsWith("mark")){
                try {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    Task tsk =  tasks.get(index);
                    tsk.markAsDone();
                    System.out.println("------------------------------------");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tsk);
                    System.out.println("------------------------------------");
                } catch (Exception e){
                    System.out.println("Invalid Task number.");
                }

            }
            if (input.startsWith("unmark")){
                try {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    Task tsk =  tasks.get(index);
                    tsk.markAsUndone();
                    System.out.println("------------------------------------");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tsk);
                    System.out.println("------------------------------------");
                } catch (Exception e){
                    System.out.println("Invalid Task number.");
                }
            }
            // Handle todo
            if (input.startsWith("todo ")) {
                String desc = input.substring(5);
                Task newTask = new Todo(desc);
                tasks.add(newTask);
                printAdded(newTask, tasks.size());
                continue;
            }
            //Handle deadline
            if (input.startsWith("deadline ")){
                String[] parts = input.substring(9).split("/by",2);
                String desc = parts[0].trim();
                String by = parts.length > 1 ? parts[1].trim() : "";
                Task newTask = new Deadline(desc, by);
                tasks.add(newTask);
                printAdded(newTask, tasks.size());
                continue;

            }

            //Handle Event
            if (input.startsWith("event ")){
                String[] parts = input.substring(6).split("/from|/to");
                if (parts.length < 3 ){
                    System.out.println("Invalid event format.");
                    continue;
                }
                String desc = parts[0].trim();
                String from = parts[1].trim();
                String to = parts[2].trim();
                Task newTask = new Event(desc, from, to);
                tasks.add(newTask);
                printAdded(newTask, tasks.size());
                continue;
            }

            else {
                String desc = input;
                Task newTask = new Task(desc);
                tasks.add(newTask);
                System.out.println("added: " + desc);
            }

        }
        sc.close();
        }

        private static void printAdded(Task t, int total){
            System.out.println("-----------------------------------");
            System.out.println("Got it. I've added this task: ");
            System.out.println("  " + t);
            System.out.println("Now you have " + total + " tasks in the list.");
            System.out.println("-----------------------------------");
        }
    }

