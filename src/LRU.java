import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective: This program handles the LRU algorithm and calls the Helper, outputTable, and printClass classes
 */
public class LRU {
    public static void LRUAlgorithim(StringBuffer referenceString, int referenceStringLength, int capacity, Scanner sc) {
        int page_faults = 0, victim_frames = 0;
        //Calls the outputTable class to create a "table" view
        String[][] table = outputTable.createTable(capacity, referenceString);
        String frames, victim, type = "LRU";

        //The linked list holds the information for the physical frames
        LinkedList<String> physicalFrames = new LinkedList<>();
        //Hash is to keep a record of the the position where the value was last found
        HashMap<String, Integer> indexes = new HashMap<>();

        //Calls the table to display, and passes in the faults and victims.
        table = outputTable.printTable(table, capacity, referenceString, physicalFrames, 0, false, "");
        printClass.printSummary(capacity, referenceString, type);

        //Prompts the user to continue or quit the simulation at the start
        if (HelperClass.continueCheck(sc)) {
            for (int i = 0; i < referenceStringLength; i++) {
                frames = String.valueOf(referenceString.charAt(i));
                //Does a check in the beginning to see if the value is in the physical frame
                if (!physicalFrames.contains(frames)) {
                    if (physicalFrames.size() < capacity) {
                        physicalFrames.add(frames);
                        page_faults++;

                        //Calls the table to display, and passes in the faults and victims
                        table = outputTable.printTable(table, capacity, referenceString, physicalFrames, i + 1, true, "");
                        printClass.printSummary(true, frames, "", capacity, type, physicalFrames.indexOf(frames));
                    } else {
                        //Compares the values from the hash to see where the last use was
                        int maxValue = Integer.MAX_VALUE, minValue = Integer.MIN_VALUE;

                        for (String temp : physicalFrames) {
                            if (indexes.get(temp) < maxValue) {
                                maxValue = indexes.get(temp);
                                minValue = Integer.parseInt(temp);
                            }
                        }
                        victim = String.valueOf(minValue);
                        //Replaces the victim with the new value
                        physicalFrames.set(physicalFrames.indexOf(victim), frames);
                        page_faults++;
                        victim_frames++;
                        //Passes fault, the victim and the frame being added to the table
                        table = outputTable.printTable(table, capacity, referenceString, physicalFrames, i + 1, true, victim);
                        printClass.printSummary(true, frames, victim, capacity, type, physicalFrames.indexOf(frames));
                    }
                } else {
                    //If value is already in the physical frames, prints out the table without fault
                    table = outputTable.printTable(table, capacity, referenceString, physicalFrames, i + 1, false, "");
                    printClass.printSummary(false, frames, "", capacity, type, physicalFrames.indexOf(frames));
                }
                //Updates the hash with the last position for the frame
                indexes.put(frames, i);

                //Even though in a loop, added this to exit out before the prompt to continue when at the end
                if (i == referenceStringLength - 1)
                    break;

                //Prompts to continue
                if (!HelperClass.continueCheck(sc)) {
                    break;
                }
            }
            System.out.println("In the end, a total of " + page_faults + " page faults and " + victim_frames + " victims were generated.\n");

        }
    }
}
