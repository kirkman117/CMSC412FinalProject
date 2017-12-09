import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective: This class handles some error checking and looping that is used across all classes in this project
 */
public class HelperClass {
    //Ensures that each character is a digit
    private static void readInputs(StringBuffer parsedPages) {
        for (int i = 0; i < parsedPages.length(); i++) {
            if (!Character.isDigit(parsedPages.charAt(i))) {
                System.out.println("All characters must be a number, please try again.");
                parsedPages.setLength(0);
                break;
            }
        }
    }

    //Loops through and prints out the buffer in an easy to read format
    public static void printReferenceString(StringBuffer parsedPages) {
        for (int i = 0; i < parsedPages.length(); i++) {
            if (i == parsedPages.length() - 1) {
                System.out.print(parsedPages.charAt(i) + "\n\n");

            } else
                System.out.print(parsedPages.charAt(i) + ", ");
        }
    }

    //Prompt for user if they want to continue, keeps the user inside the loop until a valid option is selected
    public static boolean continueCheck(Scanner sc) {
        boolean isContinuing = true;
        boolean inputIsInvalid = true;
        while (inputIsInvalid) {
            System.out.println("Continue? (y/n): ");

            String choice = sc.next();

            if ("y".equalsIgnoreCase(choice)) {
                inputIsInvalid = false;
            } else if ("n".equalsIgnoreCase(choice)) {
                inputIsInvalid = false;
                isContinuing = false;
            } else {
                System.out.print("Error: Only valid answers are Y/N.\n");
            }
        }
        return isContinuing;
    }

    //Lets the user set the amount of Physical frames, with some limitations
    public static int setFrame(Scanner sc) {
        int numberOfFrames = -1;
        while (numberOfFrames == -1) {
            try {
                System.out.print("Enter value:");
                numberOfFrames = sc.nextInt();
                if (numberOfFrames < 1 || numberOfFrames > 7) {
                    System.out.println("Value must be between 0 and 8 (non inclusive).");
                    numberOfFrames = -1;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Value must be a number between 0 and 8 (non inclusive).");
                sc.next();
            }
        }
        return numberOfFrames;
    }

    //Checks to see if a delimiter is used, and handles correctly
    public static StringBuffer checkForDelimiter(String referenceString, StringBuffer bufferedReferenceString){
        String[] splitReferenceString;
        //Removes any spaces
        referenceString = referenceString.replace(" ","");
        //Using the delimiter as a comma, otherwise it will go into the readInputs method to make sure each character
        //entered is an int (will also handle incorrect delimiters.)
        if(referenceString.contains(",")){
            splitReferenceString = referenceString.split(",");
            for (String aSplitReferenceString : splitReferenceString) {
                try {
                    Integer referenceStringItem = Integer.parseInt(aSplitReferenceString);
                    if (referenceStringItem < 0 || referenceStringItem > 9) {
                        System.out.println("All values must be between 0 and 9, please try again.");
                        bufferedReferenceString.setLength(0);
                        break;
                    } else
                        bufferedReferenceString.append(referenceStringItem);
                } catch (NumberFormatException ex) {
                    System.out.println("All values must be integers between 0 and 9, please try again.");
                    bufferedReferenceString.setLength(0);
                    break;
                }
            }
        }else{
            //Calls this to make sure every character is an integer
            readInputs(bufferedReferenceString.append(referenceString));
        }

        return  bufferedReferenceString;
    }
}
