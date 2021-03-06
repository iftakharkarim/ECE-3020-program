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
            if (tokens[0].equalsIgnoreCase("state")) {
                state.add(input.substring(input.indexOf(" ") + 1, input.length()));
            } else if (tokens[0].equalsIgnoreCase("arc")) {
                String invalid = "";
                boolean valid[] ={false, false};
                for (int i = 1; i < 3; i++) {
                    valid[i - 1] = false;
                    for (int j = 0; j < state.size(); j++) {
                        String[] processState = state.get(j).split(" ");
                        if (tokens[i].equalsIgnoreCase(processState[0])) {
                            valid[i - 1] = true;
                            break;
                        }
                    }
                    if (!valid[i-1]) {
                        invalid = tokens[i];
                    }
                }
                if (valid[0] && valid[1]) {
                    arc.add(input.substring(input.indexOf(" ") + 1, input.length()));
                } else {
                    System.out.println("%error: state '" + invalid + "' not defined");
                }
            }
        }
        //Sort the lists in alphabetical order
        Collections.sort(state, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(arc, String.CASE_INSENSITIVE_ORDER);

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
        String[] allInput = stateGenerator(inputs);

        /* extracting all the state and it's arc with output value from 
        *given input list
        */

        for( int i = 0; i < state.size(); i++) {
            String curState ="";
            if(machine.equalsIgnoreCase("moore")) {
                curState = state.get(i);
                curState = curState.substring(0, curState.indexOf(" "));
            } else {
                curState = state.get(i);
            }

            ArrayList<String> tempList = new ArrayList<>();
            for(int j = 0; j < arc.size(); j++) {
                String curArc = arc.get(j);
                String temp = curArc.substring(0, curArc.indexOf(" "));
                if(curState.equalsIgnoreCase(temp)) {
                    tempList.add(curArc);
                }
            }
            extractedState.add(tempList);
        }

        if(outputFormat.equalsIgnoreCase("graph")){
            System.out.println("Output GRAPH: ");
            if(machine.equalsIgnoreCase("mealy")){
                OutPutGraphMelay(extractedState, allInput, state);
            } else {
                OutPutGraphMoore(extractedState, allInput, state);
            }
        } else {
            System.out.println("Output TABLE ("+machine+" FSM) :");
            if(machine.equalsIgnoreCase("mealy")) {
                OutPutTableMelay(extractedState, allInput, state);
            } else{
                OutPutTableMoore(extractedState, allInput, state);
            }
        }
    }
//----------------------------------------------------------------------
    // generats all the possible input comination like 00,01,10.....
    public static String[] stateGenerator(int numOfInputBits) {
        int l = 0;
        int number = (int) Math.pow(2, numOfInputBits);
        String[] returnArray = new String[number];
        for(int i = 0; i < number; i++) {
            String str = new String(Integer.toBinaryString(i));
            if(str.length() < numOfInputBits){
                l = numOfInputBits - str.length();
                while(l != 0){
                    str ="0"+str;
                    l--;
                }
            }
            returnArray[i] = str;
        }
        return returnArray;
    }

//=====================================================================================================================
    // takes the list and output it as a graph
    public static void OutPutGraphMelay(ArrayList<ArrayList<String>> list, String[] allInput, ArrayList<String> state) {
        for(int i = 0; i < state.size(); i++) {
            System.out.println(state.get(i));
            ArrayList<String> allExistenceInput = new ArrayList<>();
            ArrayList<String> EachStateArcs = list.get(i);

            for(int k = 0; k<EachStateArcs.size(); k++){
                String[] array = EachStateArcs.get(k).split(" ");
                allExistenceInput.add(array[2]);
                System.out.println("  "+array[1]+" "+array[2]+"/"+array[3]);
            }

            for(String s: allInput) {
                boolean found = false;
                int index = 0, count = 0;
                for(int j = 0; j < allExistenceInput.size(); j++){
                   if(s.equals(allExistenceInput.get(j))){
                    found = true;
                    count++;
                   }
                }

                if(found && count >1){
                    System.out.println("% warning: input "+s+" specified multiple times %");
                } else if(!found){
                    System.out.println("% warning: input "+s+" not specified %");
                }
            }
        }
                
    }


    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public static void OutPutGraphMoore(ArrayList<ArrayList<String>> list, String[] allInput, ArrayList<String> state) {
        for(int i = 0; i < state.size(); i++) {
            System.out.println(state.get(i));
            ArrayList<String> allExistenceInput = new ArrayList<>();
            ArrayList<String> EachStateArcs = list.get(i);

            for(int k = 0; k<EachStateArcs.size(); k++){
                String[] array = EachStateArcs.get(k).split(" ");
                allExistenceInput.add(array[2]);
                System.out.println("  "+array[1]+" "+array[2]);
            }

            for(String s: allInput) {
                boolean found = false;
                int index = 0, count = 0;
                for(int j = 0; j < allExistenceInput.size(); j++){
                   if(s.equals(allExistenceInput.get(j))){
                    found = true;
                    count++;
                   }
                }
                if(found && count >1){
                    System.out.println("% warning: input "+s+" specified multiple times %");
                } else if(!found){
                    System.out.println("% warning: input "+s+" not specified %");
                }
            }
        }
                
    }

//00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
    //output as Table depends 
    public static void OutPutTableMelay(ArrayList<ArrayList<String>> list, String[] allInput, ArrayList<String> state) {
        String str = "";
        String input ="";
        System.out.println("Current |                       NextState/ Output");
        for(String s: allInput){
            str = str+"X =  "+s+"         ";
        }

        System.out.println("State   |"+str);
        System.out.println("----------------------------------------------------------------------------");

        for(int i = 0; i < state.size(); i++) {
            String outputStr = state.get(i)+"    | ";
            ArrayList<String> allExistenceInput = new ArrayList<>();
            ArrayList<String> allExistenceOutput = new ArrayList<>();
            ArrayList<String> EachStateArcs = list.get(i);
            for(int k = 0; k<EachStateArcs.size(); k++){
                String[] array = EachStateArcs.get(k).split(" ");
                allExistenceInput.add(array[2]);
                allExistenceOutput.add(array[1]+"/"+array[3]);
            }

            for(String s: allInput) {
                boolean found = false;
                int index = 0, count = 0;
                for(int j = 0; j < allExistenceInput.size(); j++){
                   if(s.equals(allExistenceInput.get(j))){
                    found = true;
                    index = j;
                    count++;
                   }
                }
                if(found && count == 1){
                    outputStr = outputStr+allExistenceOutput.get(index)+"           ";
                } else if(found && count>1){
                    outputStr = outputStr+" error           ";
                } else{
                    outputStr = outputStr+" x / x           ";
                }
            }
            System.out.println(outputStr);
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////

    // output table as Moore machine                          \\\\\\\\\\\
    public static void OutPutTableMoore(ArrayList<ArrayList<String>> list, String[] allInput, ArrayList<String> state) {
        String str = "";
        String input ="";
        System.out.println("Current        |                       NextState/ Output");
        for(String s: allInput){
            str = str+"X =  "+s+"         ";
        }
        System.out.println("State(output)  |"+str);
        System.out.println("----------------------------------------------------------------------------");

        for(int i = 0; i < state.size(); i++) {
            String outputStr = state.get(i)+"         | ";
            ArrayList<String> allExistenceInput = new ArrayList<>();
            ArrayList<String> allExistenceOutput = new ArrayList<>();
            ArrayList<String> EachStateArcs = list.get(i);
            for(int k = 0; k<EachStateArcs.size(); k++){
            String[] array = EachStateArcs.get(k).split(" ");
            allExistenceInput.add(array[2]);
            allExistenceOutput.add(array[1]);
            }

            for(String s: allInput) {
                boolean found = false;
                int index = 0, count = 0;
                for(int j = 0; j < allExistenceInput.size(); j++){
                    if(s.equals(allExistenceInput.get(j))){
                        found = true;
                        index = j;
                        count++;
                    }
                }
                if(found && count == 1){
                    outputStr = outputStr+allExistenceOutput.get(index)+"           ";
                } else if(found && count>1){
                    outputStr = outputStr+" error           ";
                } else{
                    outputStr = outputStr+"    xx             ";
                }
            }
            System.out.println(outputStr);
        }
        
    }

}
