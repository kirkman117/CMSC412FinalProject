import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Project: CMSC412FinalProject
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
                            FIFO.FIFOAlgorithm(parsedPages,parsedPages.length(),physicalFrames,sc);
                            System.out.println("FIFO Simulation complete \n");
                        }else {
                            System.out.println("Please enter a reference string via option 1 or 2.");
                        }

                        continue;
                    case 5:
                        if(parsedPages.length() != 0){
                            OPT.OPTAlgorithm(parsedPages, parsedPages.length(), physicalFrames, sc);
                            System.out.println("OPT Simulation complete \n");
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
    public static void  printReferenceString(StringBuffer parsedPages){
        for (int i = 0; i < parsedPages.length(); i++) {
            if(i == parsedPages.length() - 1){
                System.out.print(parsedPages.charAt(i) + "\n\n");

            }else
                System.out.print(parsedPages.charAt(i)+ ", ");
        }
    }
    public static boolean continueCheck(Scanner sc){
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
