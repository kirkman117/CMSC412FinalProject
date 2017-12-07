import java.util.LinkedList;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective: This file builds and populates the array and then prints it out in a table format for easy viewing
 */
public class outputTable {
    //Loops through and builds the table based on the capcity, and populates the reference string piece
    public static String[][] createTable(Integer capacity, StringBuffer referenceString) {
        //Capacity + 3 to handle the additional rows
        String[][] table = new String[capacity + 3][referenceString.length() + 1];
        table[0][0] = "\nReference String";

        //Loops through the capacity to to build the physical frame rows
        for (int i = 0; i < capacity; i++) {
            table[i + 1][0] = "Physical Frame " + i;
        }
        //Adds final 2 rows
        table[capacity + 1][0] = "Page Faults\t\t";
        table[capacity + 2][0] = "Victim Frames\t";

        //Loops through reference string to populate first row
        for (int i = 0; i < referenceString.length(); i++) {
            table[0][i + 1] = String.valueOf(referenceString.charAt(i));
        }
        return table;
    }

    //Populates the reference string value and victim if one and prints out the table
    public static String[][] printTable(String[][] table, Integer capacity, StringBuffer referenceString, LinkedList<String> values, int position, boolean fault, String victim) {
        //Gets the position and the correct row for the values to add
        for (int i = 0; i < values.size(); i++) {
            table[i + 1][position] = values.get(i);
        }
        //Adds the fault, and victim if one to the final rows
        if (fault) {
            table[capacity + 1][position] = "F";
            table[capacity + 2][position] = victim;
        }
        //Loops through and prints out the array in a table format
        for (String[] aTable : table) {
            System.out.print(aTable[0]);
            for (int j = 1; j < referenceString.length() + 1; j++) {
                if (aTable[j] == null)
                    System.out.print("\t" + " ");
                else
                    System.out.print("\t" + aTable[j] + " ");
            }
            System.out.println("");
        }
        return table;
    }
}
