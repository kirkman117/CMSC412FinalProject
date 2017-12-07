import java.util.LinkedList;

/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective:
 */
public class outputTable {
    public static String[][] createTable(Integer capacity, StringBuffer pages){
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
    public static String[][] printTable(String[][] table, Integer capacity, StringBuffer pages, LinkedList<String> values, int position, boolean fault, String victim){

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
}
