import java.util.Scanner;
import java.util.ArrayList;


public class Xiaodavid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        ArrayList<String> list = new ArrayList<>();

        System.out.println("Hello! I'm Xiaodavid!");
        System.out.println("What can I do for you?");

        while(true) {
            input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equals("list")){
                for(int i = 0; i < list.size();i++) {
                    System.out.println((i +1) + ". " + list.get(i));

                }
                continue;
            }

            list.add(input);

            System.out.println("added: " + input);

        }
        sc.close();
        }
    }

