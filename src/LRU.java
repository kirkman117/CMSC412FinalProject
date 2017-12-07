import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective:
 */
public class LRU {
    public static void LRUAlgorithim(StringBuffer pages, int pageLength, int capacity,Scanner sc) {
        int page_faults = 0, victim_frames = 0;
        String[][] table = outputTable.createTable(capacity, pages);
        String frames, victim;
        String type = "LRU";

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
                        int maxValue = Integer.MAX_VALUE, minValue = Integer.MIN_VALUE;

                        for (String temp : physicalFrames) {
                            if (indexes.get(temp) < maxValue) {
                                maxValue = indexes.get(temp);
                                minValue = Integer.parseInt(temp);
                            }
                        }
                        victim = String.valueOf(minValue);
                        physicalFrames.set(physicalFrames.indexOf(victim), frames);
                        page_faults++;
                        victim_frames++;
                        table = outputTable.printTable(table, capacity, pages, physicalFrames, i + 1, true, victim);
                        printClass.printSummary(true, frames, victim, capacity, type, physicalFrames.indexOf(frames));
                    }
                } else {
                    table = outputTable.printTable(table, capacity, pages, physicalFrames, i + 1, false, "");
                    printClass.printSummary(false, frames, "", capacity, type, physicalFrames.indexOf(frames));
                }
                indexes.put(frames, i);

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
