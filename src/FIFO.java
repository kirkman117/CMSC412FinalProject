import java.util.LinkedList;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective:
 */
public class FIFO {
    public static void FIFOAlgorithm(StringBuffer pages, int pageLength, int capacity, Scanner sc) {
        LinkedList<String> physicalFrames = new LinkedList<>();
        String[][] table = outputTable.createTable(capacity,pages);
        String type = "FIFO";
        int page_faults = 0, victim_frames = 0;
        String frame,victim;

        table = outputTable.printTable(table, capacity, pages, physicalFrames, 0, false, "");
        printClass.printSummary(capacity,pages, type);
        if(FinalProject.continueCheck(sc)) {
            for (int i = 0; i < pageLength; i++) {
                frame = String.valueOf(pages.charAt(i));
                if (!physicalFrames.contains(String.valueOf(pages.charAt(i)))) {
                    if (physicalFrames.size() < capacity) {
                            page_faults++;
                            physicalFrames.addFirst(frame);
                            table = outputTable.printTable(table, capacity, pages, physicalFrames, i + 1, true, "");
                            printClass.printSummary(true, frame, "", capacity, type, physicalFrames.indexOf(frame));
                    } else {
                            victim_frames++;
                            victim = String.valueOf(physicalFrames.getLast());
                            physicalFrames.removeLast();
                            physicalFrames.addFirst(frame);

                            table = outputTable.printTable(table, capacity, pages, physicalFrames, i + 1, true, victim);
                            printClass.printSummary(true, frame, victim, capacity, type, physicalFrames.indexOf(frame));
                            page_faults++;
                    }
                } else{
                    table = outputTable.printTable(table, capacity, pages, physicalFrames, i + 1, false, "");
                    printClass.printSummary(false, frame, "", capacity, type, physicalFrames.indexOf(frame));
                }

                if (i == pageLength - 1)
                    break;

                if (!FinalProject.continueCheck(sc)) {
                    break;
                }
            }

            System.out.println("In the end, a total of " +page_faults+ " page faults and "+victim_frames+" victims were generated.\n");
        }
    }
}
