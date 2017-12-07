import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective:
 */
public class LFU {

    public static void LFUAlgorithim(StringBuffer pages, int pageLength, int capacity, Scanner sc) {
        int page_faults = 0, victim_frames = 0;
        String[][] table = outputTable.createTable(capacity, pages);
        String frames, victim, type = "LFU";

        LinkedList<String> physicalFrames = new LinkedList<>();
        HashMap<String, Integer> indexes = new HashMap<>();

        table = outputTable.printTable(table, capacity, pages, physicalFrames, 0, false, "");
        printClass.printSummary(capacity, pages, type);

        if (FinalProject.continueCheck(sc)) {
            for (int i = 0; i < pageLength; i++) {
                frames = String.valueOf(pages.charAt(i));
                if (!physicalFrames.contains(frames)) {
                    if (physicalFrames.size() < capacity) {
                        physicalFrames.add(frames);
                        page_faults++;
                        table = outputTable.printTable(table, capacity, pages, physicalFrames, i + 1, true, "");
                        printClass.printSummary(true, frames, "", capacity, type, physicalFrames.indexOf(frames));
                    } else {
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
                        table = outputTable.printTable(table, capacity, pages, physicalFrames, i + 1, true, victim);
                        printClass.printSummary(true, frames, victim, capacity, type, physicalFrames.indexOf(frames));
                    }
                } else {
                    table = outputTable.printTable(table, capacity, pages, physicalFrames, i + 1, false, "");
                    printClass.printSummary(false, frames, "", capacity, type, physicalFrames.indexOf(frames));
                }
                if (indexes.containsKey(frames)) {
                    indexes.put(frames, indexes.get(frames) + 1);
                } else {
                    int count = 0;
                    count++;
                    indexes.put(frames, count);
                }
                if (i == pageLength - 1)
                    break;

                if (!FinalProject.continueCheck(sc)) {
                    break;
                }

            }
            System.out.println("In the end, a total of " + page_faults + " page faults and " + victim_frames + " victims were generated.\n");
        }
    }
}
