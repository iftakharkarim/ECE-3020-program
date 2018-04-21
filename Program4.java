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
    /*
     *
     * 
     */
    public static void main(String[] args) {
        //parsing the command line inputs in the following format:
        //MACHINE #states #inputs #outputs
        machine = args[0];
        states = Integer.parseInt(args[1].substring(1,args[1].length()));
        inputs = Integer.parseInt(args[2].substring(1,args[2].length()));
        outputs = Integer.parseInt(args[3].substring(1,args[3].length()));
        
        //initializing ArrayLists for population
        ArrayList<String> state = new ArrayList<>();
        ArrayList<String> arc = new ArrayList<>();
        ArrayList <ArrayList<String>>extractedState = new ArrayList<ArrayList<String>>();
        
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
        // generate all the possible input by the given #ofinput bits
        int[] allInput = stateGenerator(inputs);

        // check if there is any invalid state in the arc list
        arc = InvalidStateChecker(arc, state);

        /* extracting all the state and it's arc with output value from 
        *given input list
        */
        for( int i = 0; i < state.size(); i++) {
            String curState = state.get(i);
            ArrayList<String> tempList = new ArrayList<>();
            for(int j = 0; j < arc.size(); j++) {
                String curArc = arc.get(j);
                String temp = curArc.substring(0, curArc.indexOf(" "));
                if(curState.equalsIgnoreCase(temp)) {
                    tempList.add(curArc);
                    arc.remove(j);
                }
            }
            extractedState.add(tempList);
        }
        // output the list according to the user's choice
        if(outputFormat.equalsIgnoreCase("graph")){
            OutPutGraph(extractedState, allInput, state);
        } else {
            OutPutTable(extractedState, allInput, state);
        }
    }

    // generats all the possible input comination like 00,01,10.....
    public static int[] stateGenerator(int numOfInputBits) {
        int number = (int) Math.pow(numOfInputBits, 2);
        int[] returnArray = new int[number];
        for(int i = 0; i < number; i++) {
            returnArray[i] = i;
        }
        return returnArray;
    }

    // this method check if there is any invalid state if so then remove that arc from the arc list
    public static ArrayList<String> InvalidStateChecker(ArrayList<String> arc, ArrayList<String> state) {
        for(int i = 0; i < arc.size(); i++) {
            String[] array = arc.get(i).split(" ");
            for(int j = 0; j <state.size(); j++){
                if(!array[0].equalsIgnoreCase(state.get(j))) {
                    System.out.println("Output: % error state "+array[0]+" is not defined %");
                    arc.remove(i);
                } else if(!array[1].equalsIgnoreCase(state.get(j))){
                    System.out.println("Output: % error state "+array[1]+" is not defined %");
                    arc.remove(i);
                }
            }
        }
        return arc;
    }

    // takes the list and output it as a graph
    public static void OutPutGraph(ArrayList<ArrayList<String>> list, int[] allInput, ArrayList<String> state) {
        for(int i = 0; i < list.size(); i++) {
            int count = 0, n =0, index = 0, uIndex = -1;
            boolean temp = false;
            ArrayList<String> tempList = list.get(i);
            System.out.println(state.get(i));
            //
            for(int j = 0; j < tempList.size(); j++) {
                //take the arc then split in into state, input, output
                String[] array = tempList.get(j).split(" ");
                System.out.println("  "+array[1]+ " "+array[2]+" / "+array[3]);
                n = Integer.parseInt(array[2], 2);
                //check if the input is among the generated input
                for(int k = 0; k < allInput.length; k++) {
                    if(n == allInput[k]){
                        count++;
                    }
                    if(n!= allInput[k]){
                        uIndex = allInput[k];
                    }
                }
                if(count != 1) {
                    index = j;
                }
            }
            if(count > 1) {
                String[] Tarray = tempList.get(index).split(" ");
                System.out.println("%warning: input "+Tarray[2]+" was specified multiple times %");
            }
            if(uIndex >= 0) {
                String s = Integer.toBinaryString(uIndex);
                System.out.println("% warning: input "+s+"not specified %");
            }
        }
    }
    //output as Table depends on machine
    public static void OutPutTable(ArrayList<ArrayList<String>> list, int[] allInput, ArrayList<String> state) {
        System.out.println(list);
    }

}
