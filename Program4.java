import java.util.Scanner;
import java.util.*;
/**
 * Write a description of class Program4 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Program4
{
    public static String machine, outputFormat;
    public static int states, inputs, outputs;
    /**
     * This is a new comment.
     */
    public static void main(String[] args) {
        //parsing the command line inputs in the following format:
        //MACHINE #states #inputs #outputs
        machine = args[0];
        states = Integer.parseInt(args[1].substring(1,args[1].length()));
        inputs = Integer.parseInt(args[2].substring(1,args[2].length()));
        outputs = Integer.parseInt(args[3].substring(1,args[3].length()));
        
        //initializing ArrayLists for population
        ArrayList state = new ArrayList();
        ArrayList arc = new ArrayList();
        
        //Prompt user input
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your inputs one by one. Type 'n' when done.");
        String input = "";
        while (!input.equals("n")) {
            input = in.nextLine();
            String[] tokens = input.split(" ");
            if (tokens[1].equalsIgnoreCase("state")) {
                state.add(input.substring(input.indexOf(" ") + 1, input.length()));
            } else if (tokens[1].equalsIgnoreCase("arc")) {
                arc.add(input.substring(input.indexOf(" ") + 1, input.length()));
            }
        }
        System.out.println("What should be the output format? Type 'graph' or 'table'.");
        input = "";
        while (!(input.equalsIgnoreCase("graph") || input.equalsIgnoreCase("table"))) {
            input = in.next();
            if (!(input.equalsIgnoreCase("graph") || input.equalsIgnoreCase("table"))) {
                System.out.println("Incorrect input. Try again.");
            } else {
                outputFormat = input;
            }
        }
    }
}
