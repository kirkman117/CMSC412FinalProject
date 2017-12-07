import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective: This program handles the LFU algorithm and calls the Helper, outputTable, and printClass classes
 */
public class LFU {

    public static void LFUAlgorithim(StringBuffer referenceString, int referenceStringLength, int capacity, Scanner sc) {
        int page_faults = 0, victim_frames = 0;
        //Calls the outputTable class to create a "table" view
        String[][] table = outputTable.createTable(capacity, referenceString);
        String frames, victim, type = "LFU";

        //The linked list holds the information for the physical frames
        LinkedList<String> physicalFrames = new LinkedList<>();
        //Hash is to keep a count of the each value in the reference string and how many times used
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
                    //Checks to see if there is room in the frames, if so it enters without a victim
                    if (physicalFrames.size() < capacity) {
                        physicalFrames.add(frames);
                        page_faults++;

                        //updates the table and prints out the summary.
                        table = outputTable.printTable(table, capacity, referenceString, physicalFrames, i + 1, true, "");
                        printClass.printSummary(true, frames, "", capacity, type, physicalFrames.indexOf(frames));
                    } else {
                        //Compare the values using the hash to see how many times ued
                        int compare = 0, remove = 0;
                        for (int j = 0; j < physicalFrames.size(); j++) {
                            int test = indexes.get(physicalFrames.get(j));
                            if (compare == 0) {
                                compare = test;
                                remove = j;
                            }
                            if (test < compare) {
                                compare = test;
                                remove = j;
                            }
                        }

                        victim = physicalFrames.get(remove);
                        physicalFrames.set(remove, frames);
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

                //This is an if check to increment or add the value so the count can be kept
                if (indexes.containsKey(frames)) {
                    indexes.put(frames, indexes.get(frames) + 1);
                } else {
                    int count = 0;
                    count++;
                    indexes.put(frames, count);
                }
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
