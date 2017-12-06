import sun.awt.image.ImageWatched;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Project: CMSC412Homework6
 * Created by David on 11/27/2017.
 * Objective:
 */
public class FinalProject {
    public static void main(String[] args) {
        BufferedReader object = new BufferedReader(new InputStreamReader(System.in));
        int frames = 4;
        String results[];
        StringBuffer parsedPages = new StringBuffer();
        int pages[] = {1, 7, 5, 4, 0, 1, 4, 7, 3, 6, 5, 0, 4, 7, 3, 2, 1};
        //int pages[] = {4, 1, 2,	3, 5, 7, 8,	9, 4, 7, 0,	3, 6, 0, 5, 2, 8};


        try {
            System.out.println("Please select from the below options: ");
            while (true) {

                System.out.println("0 - Exit \n"
                        + "1 - Read Reference String \n"
                        + "2 - Generate Reference String \n"
                        + "3 - Display current reference string \n"
                        + "4 - FIFO Simulation \n"
                        + "5 - OPT Simulation \n"
                        + "6 - LRU Simulation \n"
                        + "7 - LFU Simulation");

                System.out.println("\nChoose next option: ");
                int opt = Integer.parseInt(object.readLine());
                switch (opt) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        parsedPages.setLength(0);
                        System.out.println("Please enter the reference string. (No spaces or comma's needed, each character is a frame).");
                        parsedPages.append(object.readLine());
                        readInputs(parsedPages);
                        continue;
                    case 2:
                        parsedPages.setLength(0);
                        for (int i = 0; i < 10; i++) {
                            parsedPages.append(ThreadLocalRandom.current().nextInt(0, 10));
                        }
                        continue;
                    case 3:
                        System.out.println(parsedPages.toString());
                        continue;
                    case 4:
                        if(parsedPages.length() != 0){
                            results = FIFO(parsedPages,parsedPages.length(), frames);
                            System.out.println("FIFO Simulation complete \n"
                                    + "FIFO Page faults: " + results[0] + "\n"
                                    + "FIFO Victim Frames: " + results[1] + "\n");
                        }else {
                            System.out.println("Please enter a reference string via option 1 or 2.");
                        }

                        continue;
                    case 5:
                        if(parsedPages.length() != 0){
                            results = OPT(pages, pages.length, frames);
                            System.out.println("OPT Page faults: " + results[0]);
                            System.out.println("OPT Victim Frames: " + results[1]);
                        }else {
                            System.out.println("Please enter a reference string via option 1 or 2.");
                        }
                        continue;
                    case 6:
                        if(parsedPages.length() != 0){
                            results = LRU(pages, pages.length, frames);
                            System.out.println("LRU Page faults: " + results[0]);
                            System.out.println("LRU Victim Frames: " + results[1]);
                        }else {
                            System.out.println("Please enter a reference string via option 1 or 2.");
                        }
                        continue;
                    case 7:
                        if(parsedPages.length() != 0){
                            results = LFU(pages, pages.length, frames);
                            System.out.println("LFU Page faults: " + results[0]);
                            System.out.println("LFU Victim Frames: " + results[1]);
                        }else {
                            System.out.println("Please enter a reference string via option 1 or 2.");
                        }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static StringBuffer readInputs(StringBuffer parsedPages){
        for (int i = 0; i < parsedPages.length(); i++) {
            if(!Character.isDigit(parsedPages.charAt(i))){
                System.out.println("All characters must be a number, please try again.");
                parsedPages.setLength(0);
                break;
            }
        }

        return parsedPages;
    }
    static String[][] createTable(Integer capacity, StringBuffer pages){
        String[][] table = new String[capacity + 3][pages.length() + 1];
        table[0][0] = "\nReference String";
        for(int i = 0; i < capacity; i ++){
            table[i +1][0] = "Physical Frame " + i;
        }
        table[capacity + 1][0] = "Page Faults\t\t";
        table[capacity + 2][0] = "Victim Frames\t";
        for (int i = 0; i < pages.length(); i++) {
            table[0][i+1] = String.valueOf(pages.charAt(i));
        }
        return table;
    }

    static String[][] printTable(String[][] table, Integer capacity, StringBuffer pages, LinkedList<String> values, int position, boolean fault, String victim){

        for (int i = 0; i < values.size(); i++) {
            table[i + 1][position] = values.get(i);
        }
        if(fault) {
            table[capacity + 1][position] = "F";
            table[capacity + 2][position] = victim;
        }
        for (String[] aTable : table) {
            System.out.print(aTable[0]);
            for (int j = 1; j < pages.length() + 1; j++) {
                if (aTable[j] == null)
                    System.out.print("\t" + " ");
                else
                    System.out.print("\t" + aTable[j] + " ");
            }
            System.out.println("");
        }
        return table;
    }

    static String[] FIFO(StringBuffer pages, int pageLength, int capacity) {
        String result[] = new String[2];
        Scanner sc = new Scanner(System.in);
        LinkedList<String> tableValues = new LinkedList<>();
        String[][] table = createTable(capacity,pages);

        int page_faults = 0, victim_frames = 0;
        boolean isContinuing = true;

        for (int i = 0; i < pageLength; i++) {

            if (tableValues.size() < capacity) {
                if (!tableValues.contains(String.valueOf(pages.charAt(i)))) {
                    page_faults++;
                    tableValues.addFirst(String.valueOf(pages.charAt(i)));
                    table = printTable(table, capacity, pages, tableValues, i + 1, true, "");
                }
                else {
                    table = printTable(table, capacity, pages, tableValues, i + 1, false, "");
                }
            } else {
                if (!tableValues.contains((String.valueOf(pages.charAt(i))))) {
                    victim_frames++;
                    int val = Integer.parseInt(tableValues.getLast());
                    tableValues.removeLast();
                    tableValues.addFirst(String.valueOf(pages.charAt(i)));

                    table = printTable(table, capacity, pages, tableValues, i + 1, true, String.valueOf(val));
                    page_faults++;
                } else {
                    table = printTable(table, capacity, pages, tableValues, i + 1, false, "");
                }
            }

            if(i == pageLength - 1)
                break;

            boolean inputIsInvalid = true;
            while (inputIsInvalid) {
                System.out.print("Continue? (y/n): ");

                String choice = sc.next();

                if ("y".equalsIgnoreCase(choice)) {
                    inputIsInvalid = false;
                }
                else if ("n".equalsIgnoreCase(choice)) {
                    inputIsInvalid = false;
                    isContinuing = false;
                }
                else {
                    System.out.print("Error: Only valid answers are Y/N.\n");

                }
            }
            if(!isContinuing){
                break;
            }
        }

        result[0] = String.valueOf(page_faults);
        result[1] = String.valueOf(victim_frames);
        return result;
    }

    static String[] OPT(int[] pages, int pageLength, int capacity) {
        String result[] = new String[2];
        LinkedList<Integer> frame = new LinkedList<>();

        int page_faults = 0, victim_frames = 0;
        for (int i = 0; i < pageLength; i++) {
            if (frame.size() < capacity) {
                frame.add(pages[i]);
                page_faults++;
            } else {
                if (!frame.contains(pages[i])) {
                    int j = OPTPredict(pages, frame, pageLength, i);
                    frame.set(j, pages[i]);
                    page_faults++;
                    victim_frames++;
                }

            }
        }
        result[0] = String.valueOf(page_faults);
        result[1] = String.valueOf(victim_frames);
        return result;
    }

    static Integer OPTPredict(int[] pages, LinkedList<Integer> frame, int pageLength, int index) {

        int res = -1;
        int farthest = index;
        for (int i = 0; i < frame.size(); i++) {
            int j;
            for (j = index; j < pageLength; j++) {

                if (frame.get(i) == pages[j]) {
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

    static String[] LRU(int pages[], int pageLength, int capacity) {
        String[] results = new String[2];
        int page_faults = 0;
        int victim_frames = 0;

        LinkedList<Integer> frames = new LinkedList<>();
        HashMap<Integer, Integer> indexes = new HashMap<>();
        for (int i = 0; i < pageLength; i++) {
            if (frames.size() < capacity) {
                if (!frames.contains(pages[i])) {
                    frames.add(pages[i]);
                    page_faults++;
                }
                indexes.put(pages[i], i);
            } else {
                if (!frames.contains(pages[i])) {
                    int maxValue = Integer.MAX_VALUE, minValue = Integer.MIN_VALUE;

                    for (Integer temp : frames) {
                        if (indexes.get(temp) < maxValue) {
                            maxValue = indexes.get(temp);
                            minValue = temp;
                        }
                    }
                    frames.set(frames.indexOf(minValue), pages[i]);
                    page_faults++;
                    victim_frames++;
                }
                indexes.put(pages[i], i);
            }
        }
        results[0] = String.valueOf(page_faults);
        results[1] = String.valueOf(victim_frames);
        return results;
    }

    static String[] LFU(int pages[], int pageLength, int capacity) {
        String[] results = new String[2];
        int page_faults = 0, victim_frames = 0;


        LinkedList<Integer> frames = new LinkedList<>();
        HashMap<Integer, Integer> indexes = new HashMap<>();

        for (int i = 0; i < pageLength; i++) {
            if (frames.size() < capacity) {
                if (!frames.contains(pages[i])) {
                    frames.add(pages[i]);
                    page_faults++;
                }
                int count = 0;
                count++;
                indexes.put(pages[i], count);
            } else if (!frames.contains(pages[i])) {
                int compare = 0, remove = 0;
                for (int j = 0; j < frames.size(); j++) {
                    int test = indexes.get(frames.get(j));
                    if (compare == 0) {
                        compare = test;
                        remove = j;
                    }
                    if (test < compare) {
                        compare = test;
                        remove = j;
                    }
                }
                int count = 0;
                count++;
                if (indexes.containsKey(pages[i])) {
                    indexes.put(pages[i], indexes.get(pages[i]) + 1);
                } else {
                    indexes.put(pages[i], count);
                }

                frames.set(remove, pages[i]);
                page_faults++;
                victim_frames++;
            } else {
                indexes.put(pages[i], indexes.get(pages[i]) + 1);
            }
        }

        results[0] = String.valueOf(page_faults);
        results[1] = String.valueOf(victim_frames);

        return results;
    }
}
