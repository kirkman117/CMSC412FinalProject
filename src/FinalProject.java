import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Project: CMSC412FinalProject
 * Created by David on 12/4/2017.
 * Objective: This file is specifically used as the driver for the project.  Each algorithm is a different class.
 */
public class FinalProject {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int physicalFrames = 4;
        StringBuffer referenceString = new StringBuffer();

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

            //Check to make sure the menu selection is an Int
            try {opt = sc.nextInt();}
            catch (InputMismatchException ex) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
            }

            //Loop will continue until 0 is selected.
            switch (opt) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    referenceString.setLength(0);
                    //Clearing the token to make sure handling comma's and spaces correctly
                    sc.nextLine();
                    System.out.println("Please enter the reference string.");
                    System.out.println("You can enter in the string as just a row of numbers, or delimited by commas.");

                    String placeHolder = sc.nextLine();
                    //Calls the checkForDelimiter method to see if a delimiter is used
                    referenceString = (HelperClass.checkForDelimiter(placeHolder,referenceString));

                    if (referenceString.length() > 0){
                        System.out.println("Would you like to set the amount of physical frames, by default it is 4.");
                        //Uses the helper class to make sure to make sure that the next entry is a Y/N, and if if yes will allow user to change default
                        if (HelperClass.continueCheck(sc)) {
                            physicalFrames = HelperClass.setFrame(sc);
                        }
                    }
                    continue;
                case 2:
                    //Sets the reference string to random values
                    referenceString.setLength(0);
                    for (int i = 0; i < 17; i++) {
                        referenceString.append(ThreadLocalRandom.current().nextInt(0, 10));
                    }
                    continue;
                //From here the following cases all make sure you have a reference string otherwise an error is displayed
                case 3:
                    if (referenceString.length() != 0) {
                        //Calls helper to print out reference
                        HelperClass.printReferenceString(referenceString);
                    }else {
                        System.out.println("Please enter a reference string via option 1 or 2.");
                    }
                    continue;

                case 4:
                    if (referenceString.length() != 0) {
                        //Calls the FIFO class to perform algorithm
                        FIFO.FIFOAlgorithm(referenceString, referenceString.length(), physicalFrames, sc);
                        System.out.println("FIFO Simulation complete \n");
                    } else {
                        System.out.println("Please enter a reference string via option 1 or 2.");
                    }

                    continue;
                case 5:
                    if (referenceString.length() != 0) {
                        //Calls the OPT class to perform algorithm
                        OPT.OPTAlgorithm(referenceString, referenceString.length(), physicalFrames, sc);
                        System.out.println("OPT Simulation complete \n");
                    } else {
                        System.out.println("Please enter a reference string via option 1 or 2.");
                    }
                    continue;
                case 6:
                    if (referenceString.length() != 0) {
                        //Calls the LRU class to perform algorithm
                        LRU.LRUAlgorithim(referenceString, referenceString.length(), physicalFrames, sc);
                        System.out.println("LRU Simulation complete \n");
                    } else {
                        System.out.println("Please enter a reference string via option 1 or 2.");
                    }
                    continue;
                case 7:
                    if (referenceString.length() != 0) {
                        //Calls the LFU class to perform algorithm
                        LFU.LFUAlgorithim(referenceString, referenceString.length(), physicalFrames, sc);
                        System.out.println("LFU Simulation complete \n");
                    } else {
                        System.out.println("Please enter a reference string via option 1 or 2.");
                    }
            }
        }
    }
}
