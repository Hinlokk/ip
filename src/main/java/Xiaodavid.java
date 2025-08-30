import java.util.Scanner;


public class Xiaodavid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;

        System.out.println("Hello! I'm Xiaodavid!");
        System.out.println("What can I do for you?");

        while(true) {
            input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            System.out.println(input);

        }
        }
    }

