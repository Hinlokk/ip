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

            else if (input.equals("list")){
                System.out.println("------------------------------------");
                System.out.println("Here are the tasks in your list:");
                for(int i = 0; i < tasks.size();i++) {
                    System.out.println((i +1) + ". " + tasks.get(i));

                }
                System.out.println("------------------------------------");
                continue;
            }

            else if (input.startsWith("mark")){
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
            else if (input.startsWith("unmark")){
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

            else {
                String desc = input;
                Task newTask = new Task(desc);
                tasks.add(newTask);
                System.out.println("added: " + desc);
            }

        }
        sc.close();
        }
    }

