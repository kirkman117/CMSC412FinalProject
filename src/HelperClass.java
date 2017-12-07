import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective: This class handles some error checking and looping that is used across all classes in this project
 */
public class HelperClass {
    //Ensures that each character is a digit
    public static void readInputs(StringBuffer parsedPages) {
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
            System.out.print("Continue? (y/n): ");

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
}
