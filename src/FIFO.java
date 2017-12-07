import java.util.LinkedList;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective:
 */
public class FIFO {
    public static void FIFOAlgorithm(StringBuffer pages, int pageLength, int capacity, Scanner sc) {
        LinkedList<String> tableValues = new LinkedList<>();
        String[][] table = outputTable.createTable(capacity,pages);
        String type = "FIFO";
        int page_faults = 0, victim_frames = 0;
        String frame,victim;

        table = outputTable.printTable(table, capacity, pages, tableValues, 0, false, "");
        printClass.printSummary(capacity,pages, type);
        if(FinalProject.continueCheck(sc)) {
            for (int i = 0; i < pageLength; i++) {
                frame = String.valueOf(pages.charAt(i));
                if (!tableValues.contains(String.valueOf(pages.charAt(i)))) {
                    if (tableValues.size() < capacity) {
                            page_faults++;
                            tableValues.addFirst(frame);
                            table = outputTable.printTable(table, capacity, pages, tableValues, i + 1, true, "");
                            printClass.printSummary(true, frame, "", capacity, type, tableValues.indexOf(frame));
                    } else {
                            victim_frames++;
                            victim = String.valueOf(tableValues.getLast());
                            tableValues.removeLast();
                            tableValues.addFirst(frame);

                            table = outputTable.printTable(table, capacity, pages, tableValues, i + 1, true, victim);
                            printClass.printSummary(true, frame, victim, capacity, type, tableValues.indexOf(frame));
                            page_faults++;
                    }
                } else{
                    table = outputTable.printTable(table, capacity, pages, tableValues, i + 1, false, "");
                    printClass.printSummary(false, frame, "", capacity, type, tableValues.indexOf(frame));
                }

                if (i == pageLength - 1)
                    break;

                if (!FinalProject.continueCheck(sc)) {
                    break;
                }
            }

            System.out.println("In the end, a total of " +page_faults+ " page faults and "+victim_frames+" victims were generated.");
        }
    }
}
