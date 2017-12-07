import java.util.LinkedList;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective:
 */
public class OPT {

    public static void OPTAlgorithm(StringBuffer pages, int pageLength, int capacity, Scanner sc) {
        LinkedList<String> frame = new LinkedList<>();
        String[][] table = outputTable.createTable(capacity,pages);
        String frames,victim;
        String type = "OPT";

        table = outputTable.printTable(table, capacity, pages, frame, 0, false, "");
        printClass.printSummary(capacity,pages, type);

        int page_faults = 0, victim_frames = 0;
        if(FinalProject.continueCheck(sc)) {
            for (int i = 0; i < pageLength; i++) {
                frames = String.valueOf(pages.charAt(i));
                if (!frame.contains(frames)) {
                    if (frame.size() < capacity) {
                        frame.add(frames);
                        page_faults++;
                        table = outputTable.printTable(table, capacity, pages, frame, i + 1, true, "");
                        printClass.printSummary(true, frames, "", capacity, type, frame.indexOf(frames));
                    } else {
                        int j = OPTPredict(pages, frame, pageLength, i);
                        victim = frame.get(j);
                        frame.set(j, frames);
                        page_faults++;
                        victim_frames++;
                        table = outputTable.printTable(table, capacity, pages, frame, i + 1, true, victim);
                        printClass.printSummary(true, frames, victim, capacity, type, frame.indexOf(frames));
                    }
                } else {
                    table = outputTable.printTable(table, capacity, pages, frame, i + 1, false, "");
                    printClass.printSummary(false, frames, "", capacity, type, frame.indexOf(frames));
                }
                if (i == pageLength - 1)
                    break;

                if (!FinalProject.continueCheck(sc)) {
                    break;
                }
            }
        }

        System.out.println("In the end, a total of " +page_faults+ " page faults and "+victim_frames+" victims were generated.");
    }

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
