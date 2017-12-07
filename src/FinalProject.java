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
                            LRU.LRUAlgorithim(parsedPages, parsedPages.length(), physicalFrames,sc);
                            System.out.println("LRU Simulation complete \n");
                        }else {
                            System.out.println("Please enter a reference string via option 1 or 2.");
                        }
                        continue;
                    case 7:
                        if(parsedPages.length() != 0){
                            LFU.LFUAlgorithim(parsedPages, parsedPages.length(), physicalFrames, sc);
                            System.out.println("LFU Simulation complete \n");
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

}
