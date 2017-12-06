import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Project: CMSC412Homework6
 * Created by David on 11/27/2017.
 * Objective:
 */
public class FinalProject {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int physicalFrames = 4;
        String results[];
        StringBuffer parsedPages = new StringBuffer();
        int pages[] = {1, 7, 5, 4, 0, 1, 4, 7, 3, 6, 5, 0, 4, 7, 3, 2, 1};
        //int pages[] = {4, 1, 2,	3, 5, 7, 8,	9, 4, 7, 0,	3, 6, 0, 5, 2, 8};



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
                int opt = -1;
                try {
                   opt = sc.nextInt();
                } catch (NumberFormatException ex){
                    System.out.println("Please enter a valid number.");
                }
                switch (opt) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        parsedPages.setLength(0);
                        System.out.println("Please enter the reference string. (No spaces or comma's needed, each character is a frame).");
                        parsedPages.append(sc.next());
                        readInputs(parsedPages);
                        continue;
                    case 2:
                        parsedPages.setLength(0);
                        for (int i = 0; i < 10; i++) {
                            parsedPages.append(ThreadLocalRandom.current().nextInt(0, 10));
                        }
                        continue;
                    case 3:
                        printReferenceString(parsedPages);
                        continue;
                    case 4:
                        if(parsedPages.length() != 0){
                            FIFO(parsedPages,parsedPages.length(),physicalFrames,sc);
                            System.out.println("FIFO Simulation complete \n");
                        }else {
                            System.out.println("Please enter a reference string via option 1 or 2.");
                        }

                        continue;
                    case 5:
                        if(parsedPages.length() != 0){
                            results = OPT(pages, pages.length, physicalFrames);
                            System.out.println("OPT Page faults: " + results[0]);
                            System.out.println("OPT Victim Frames: " + results[1]);
                        }else {
                            System.out.println("Please enter a reference string via option 1 or 2.");
                        }
                        continue;
                    case 6:
                        if(parsedPages.length() != 0){
                            results = LRU(pages, pages.length, physicalFrames);
                            System.out.println("LRU Page faults: " + results[0]);
                            System.out.println("LRU Victim Frames: " + results[1]);
                        }else {
                            System.out.println("Please enter a reference string via option 1 or 2.");
                        }
                        continue;
                    case 7:
                        if(parsedPages.length() != 0){
                            results = LFU(pages, pages.length, physicalFrames);
                            System.out.println("LFU Page faults: " + results[0]);
                            System.out.println("LFU Victim Frames: " + results[1]);
                        }else {
                            System.out.println("Please enter a reference string via option 1 or 2.");
                        }
                }
            }
        }



    private static void readInputs(StringBuffer parsedPages){
        for (int i = 0; i < parsedPages.length(); i++) {
            if(!Character.isDigit(parsedPages.charAt(i))){
                System.out.println("All characters must be a number, please try again.");
                parsedPages.setLength(0);
                break;
            }
        }
    }
    private static void  printReferenceString(StringBuffer parsedPages){
        for (int i = 0; i < parsedPages.length(); i++) {
            if(i == parsedPages.length() - 1){
                System.out.print(parsedPages.charAt(i) + "\n\n");

            }else
                System.out.print(parsedPages.charAt(i)+ ", ");
        }
    }
    private static String[][] createTable(Integer capacity, StringBuffer pages){
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
    private static String[][] printTable(String[][] table, Integer capacity, StringBuffer pages, LinkedList<String> values, int position, boolean fault, String victim){

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
    private static boolean continueCheck(Scanner sc){
        boolean isContinuing = true;
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
        return  isContinuing;
    }
    private static void printSummary(int capacity, StringBuffer frames, String type) {
        switch (type){
            case "FIFO":
                System.out.println("\nIn the FIFO page-replacement algorithm, the victim frame is the oldest frame. \n"
                        + "The simulation of the FIFO page-replacement algorithm assumes a hypothetical computer having " + capacity + "\n"
                        + "physical frames numbered 0 to " + (capacity - 1) +", which form a FIFO queue.  It assumes that the sing process that is \n"
                        + "running has a virtual memory of 10 frames numbered 0 to 9.  The reference string is:\n");
                break;
        }

        printReferenceString(frames);
    }
    private static void printSummary(boolean fault, String frame,String victim, int capacity, String type, int frameLocation){
        switch (type){
            case "FIFO":
                if(fault && !victim.equals("")){
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "Because virtual frame "+ frame +" is not present in physical memory, a page fault is generated.\n"
                            + "Because there is no more room in the physical memory, a frame must be replaced.\n"
                            + "The victim frame is virtual frame " +victim +", according to the FIFO strategy used in this algorithm \n"
                            + "Virtual frame "+victim + " is swapped out, and virtual frame "+ frame +" is swapped in.\n");
                }else if(fault){
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "Because virtual frame "+ frame +" is not present in physical memory, a page fault is generated.\n"
                            + "Virtual frame "+ frame+" is loaded into the FIFO queue formed by the physical frames 0 to "+(capacity - 1) +".\n"
                            + "Because there was room in the physical memory, we have no victim frame.\n");
                }else {
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "Because virtual frame "+ frame +" is already present in the physical memory, in physical frame "+frameLocation+", \n"
                            + "nothing else needs to be done.\n"
                            + "No page fault is generated. \nWe have no victim frame.\n");
                }
                break;
        }

    }
    private static void FIFO(StringBuffer pages, int pageLength, int capacity, Scanner sc) {
        LinkedList<String> tableValues = new LinkedList<>();
        String[][] table = createTable(capacity,pages);
        String type = "FIFO";
        int page_faults = 0, victim_frames = 0;
        String frame,victim;

        table = printTable(table, capacity, pages, tableValues, 0, false, "");
        printSummary(capacity,pages, type);
        if(continueCheck(sc)) {
            for (int i = 0; i < pageLength; i++) {
                frame = String.valueOf(pages.charAt(i));
                if (tableValues.size() < capacity) {
                    if (!tableValues.contains(String.valueOf(pages.charAt(i)))) {
                        page_faults++;
                        tableValues.addFirst(frame);
                        table = printTable(table, capacity, pages, tableValues, i + 1, true, "");
                        printSummary(true, frame,"",capacity,type, tableValues.indexOf(frame));
                    } else {
                        table = printTable(table, capacity, pages, tableValues, i + 1, false, "");
                        printSummary(false, frame,"",capacity,type, tableValues.indexOf(frame));
                    }
                } else {
                    if (!tableValues.contains((String.valueOf(pages.charAt(i))))) {
                        victim_frames++;
                        victim = String.valueOf(tableValues.getLast());
                        tableValues.removeLast();
                        tableValues.addFirst(frame);

                        table = printTable(table, capacity, pages, tableValues, i + 1, true, victim);
                        printSummary(true, frame,victim,capacity,type, tableValues.indexOf(frame));
                        page_faults++;
                    } else {
                        table = printTable(table, capacity, pages, tableValues, i + 1, false, "");
                        printSummary(false, frame,"",capacity,type, tableValues.indexOf(frame));
                    }
                }

                if (i == pageLength - 1)
                    break;

                if (!continueCheck(sc)) {
                    break;
                }
            }

            System.out.println("In the end, a total of " +page_faults+ " page faults and "+victim_frames+" victims were generated.");
        }
    }

    private static String[] OPT(int[] pages, int pageLength, int capacity) {
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

    private static Integer OPTPredict(int[] pages, LinkedList<Integer> frame, int pageLength, int index) {

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

    private static String[] LRU(int pages[], int pageLength, int capacity) {
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

    private static String[] LFU(int pages[], int pageLength, int capacity) {
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
