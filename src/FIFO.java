import java.util.LinkedList;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective: This program handles the FIFO algorithm and calls the Helper, outputTable, and printClass classes
 */
public class FIFO {
    public static void FIFOAlgorithm(StringBuffer referenceString, int referenceStringLength, int capacity, Scanner sc) {
        //The linked list holds the information for the physical frames
        LinkedList<String> physicalFrames = new LinkedList<>();
        //Calls the outputTable class to create a "table" view
        String[][] table = outputTable.createTable(capacity, referenceString);
        String type = "FIFO";
        int page_faults = 0, victim_frames = 0;
        String frame, victim;

        //Calls the table to display, and passes in the faults and victims.
        table = outputTable.printTable(table, capacity, referenceString, physicalFrames, 0, false, "");
        printClass.printSummary(capacity, referenceString, type);

        //Prompts the user to continue or quit the simulation at the start
        if (HelperClass.continueCheck(sc)) {
            for (int i = 0; i < referenceStringLength; i++) {
                frame = String.valueOf(referenceString.charAt(i));
                //Does a check in the beginning to see if the value is in the physical frame
                if (!physicalFrames.contains(String.valueOf(referenceString.charAt(i)))) {
                    //Checks to see if there is room in the frames, if so it enters without a victim
                    if (physicalFrames.size() < capacity) {
                        page_faults++;
                        physicalFrames.addFirst(frame);

                        //updates the table and prints out the summary.
                        table = outputTable.printTable(table, capacity, referenceString, physicalFrames, i + 1, true, "");
                        printClass.printSummary(true, frame, "", capacity, type, physicalFrames.indexOf(frame));
                    } else {
                        //Since no room, have to remove the last and add value to beginning since FIFO
                        victim = String.valueOf(physicalFrames.getLast());
                        physicalFrames.removeLast();
                        physicalFrames.addFirst(frame);

                        table = outputTable.printTable(table, capacity, referenceString, physicalFrames, i + 1, true, victim);
                        printClass.printSummary(true, frame, victim, capacity, type, physicalFrames.indexOf(frame));
                        page_faults++;
                        victim_frames++;
                    }
                } else {
                    //If value is already in the physical frames, prints out the table without fault
                    table = outputTable.printTable(table, capacity, referenceString, physicalFrames, i + 1, false, "");
                    printClass.printSummary(false, frame, "", capacity, type, physicalFrames.indexOf(frame));
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
