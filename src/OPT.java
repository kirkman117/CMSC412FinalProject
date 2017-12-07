import java.util.LinkedList;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective: This program handles the OPT algorithm and calls the Helper, outputTable, and printClass classes
 */
public class OPT {

    public static void OPTAlgorithm(StringBuffer referenceString, int referenceStringLength, int capacity, Scanner sc) {
        //The linked list holds the information for the physical frames
        LinkedList<String> physicalFrames = new LinkedList<>();
        //Calls the outputTable class to create a "table" view
        String[][] table = outputTable.createTable(capacity, referenceString);
        String frames, victim, type = "OPT";

        //Calls the table to display, and passes in the faults and victims.
        table = outputTable.printTable(table, capacity, referenceString, physicalFrames, 0, false, "");
        printClass.printSummary(capacity, referenceString, type);

        int page_faults = 0, victim_frames = 0;
        //Prompts the user to continue or quit the simulation at the start
        if (HelperClass.continueCheck(sc)) {
            for (int i = 0; i < referenceStringLength; i++) {
                frames = String.valueOf(referenceString.charAt(i));
                //Does a check in the beginning to see if the value is in the physical frame
                if (!physicalFrames.contains(frames)) {
                    if (physicalFrames.size() < capacity) {
                        //Since within the first 4, no victims, and values are just added to the physical frames
                        physicalFrames.add(frames);
                        page_faults++;
                        //updates the table and prints out the summary.
                        table = outputTable.printTable(table, capacity, referenceString, physicalFrames, i + 1, true, "");
                        printClass.printSummary(true, frames, "", capacity, type, physicalFrames.indexOf(frames));
                    } else {
                        //Finds the victim
                        int j = OPTPredict(referenceString, physicalFrames, referenceStringLength, i);
                        victim = physicalFrames.get(j);
                        physicalFrames.set(j, frames);

                        //Passes fault, the victim and the frame being added to the table
                        table = outputTable.printTable(table, capacity, referenceString, physicalFrames, i + 1, true, victim);
                        printClass.printSummary(true, frames, victim, capacity, type, physicalFrames.indexOf(frames));

                        page_faults++;
                        victim_frames++;
                    }
                } else {
                    //If value is already in the physical frames, prints out the table without fault
                    table = outputTable.printTable(table, capacity, referenceString, physicalFrames, i + 1, false, "");
                    printClass.printSummary(false, frames, "", capacity, type, physicalFrames.indexOf(frames));
                }
                //Even though in a loop, added this to exit out before the prompt to continue when at the end
                if (i == referenceStringLength - 1)
                    break;

                //Prompts to continue
                if (!HelperClass.continueCheck(sc)) {
                    break;
                }
            }
        }

        System.out.println("In the end, a total of " + page_faults + " page faults and " + victim_frames + " victims were generated.\n");
    }

    //Loops through the reference string to find out which one is the is the farthest to be the victim
    private static Integer OPTPredict(StringBuffer pages, LinkedList<String> frame, int pageLength, int index) {

        int res = -1;
        int farthest = index;
        for (int i = 0; i < frame.size(); i++) {
            int j;
            for (j = index; j < pageLength; j++) {

                if (frame.get(i).equals(String.valueOf(pages.charAt(j)))) {
                    if (j > farthest) {
                        farthest = j;
                        res = i;
                    }
                    break;
                }
                if (j == pageLength - 1)
                    return i;
            }
        }
        return res;
    }
}
