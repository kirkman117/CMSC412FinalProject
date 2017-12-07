/**
 * Project: CMSC412FinalProject
 * Created by David on 12/6/2017.
 * Objective:
 */
public class printClass {

    public static void printSummary(int capacity, StringBuffer frames, String type) {
        switch (type){
            case "FIFO":
                System.out.println("\nIn the FIFO page-replacement algorithm, the victim frame is the oldest frame. \n"
                        + "The simulation of the FIFO page-replacement algorithm assumes a hypothetical computer having " + capacity + "\n"
                        + "physical frames numbered 0 to " + (capacity - 1) +", which form a FIFO queue.  It assumes that the single process that is \n"
                        + "running has a virtual memory of 10 frames numbered 0 to 9.  The reference string is:\n");
                break;
            case "OPT":
                System.out.println("\nThe optimal page-replacement algorithm is an idealized algorithm in which the victim frame is the one \n"
                        + "that will not be accessed for the longest period of time.\n"
                        + "This simulation assumes a hypothetical computer having "+capacity+" physical frames numbered 0 to "+(capacity -1)+". It\n"
                        + "assumes that the single process that is running has a virtual memory of 10 frames numbered 0 to 9. \n"
                        + " The reference string is:\n");
                break;
            case "LRU":
                System.out.println("\nThe LRU page-replacement algorithm is an approximation of the optimal page-replacement algorithm. \n"
                        + "The victim frame is the one that has not been accessed for the longest period of time.\n"
                        + "This simulation assumes a hypothetical computer having "+capacity+" physical frames numbered 0 to "+(capacity -1)+". It\n"
                        + "assumes that the single process that is running has a virtual memory of 10 frames numbered 0 to 9. \n"
                        + " The reference string is:\n");
                break;
            case "LFU":
                System.out.println("\nIn the LFU page-replacement algorithm, the victim frame is the one that has the smallest number of references. \n"
                        + "This simulation assumes a hypothetical computer having "+capacity+" physical frames numbered 0 to "+(capacity -1)+". It\n"
                        + "assumes that the single process that is running has a virtual memory of 10 frames numbered 0 to 9. \n"
                        + " The reference string is:\n");
                break;
        }

        FinalProject.printReferenceString(frames);
    }


    public static void printSummary(boolean fault, String frame, String victim, int capacity, String type, int frameLocation){
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
            case "OPT":
                if(fault && !victim.equals("")){
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "Because virtual frame "+ frame +" is not present in physical memory, a page fault is generated.\n"
                            + "Because there is no more room in the physical memory, a frame must be replaced.\n"
                            + "The victim frame is virtual frame " +victim +" which is the farthest (or does not occur again) in the reference string. \n"
                            + "Virtual frame "+victim + " is swapped out, and virtual frame "+ frame +" is swapped in.\n");
                }else if(fault){
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "Because virtual frame "+ frame +" is not present in physical memory, a page fault is generated.\n"
                            + "Virtual frame "+ frame+" is loaded into physical frame "+frameLocation +".\n"
                            + "Because there was room in the physical memory, we have no victim frame.\n");
                }else {
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "Because virtual frame "+ frame +" is already present in the physical memory, in physical frame "+frameLocation+", \n"
                            + "nothing else needs to be done.\n"
                            + "No page fault is generated. \nWe have no victim frame.\n");
                }
                break;
            case "LRU":
                if(fault && !victim.equals("")){
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "Because virtual frame "+ frame +" is not present in physical memory, a page fault is generated.\n"
                            + "Because there is no more room in the physical memory, a frame must be replaced.\n"
                            + "The victim frame is virtual frame " +victim +" which was not used (referenced) for the longest period of time. \n"
                            + "Virtual frame "+victim + " is swapped out, and virtual frame "+ frame +" is swapped in.\n");
                }else if(fault){
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "Because virtual frame "+ frame +" is not present in physical memory, a page fault is generated.\n"
                            + "Virtual frame "+ frame+" is loaded into physical frame "+frameLocation +".\n"
                            + "Because there was room in the physical memory, we have no victim frame.\n");
                }else {
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "Because virtual frame "+ frame +" is already present in the physical memory, in physical frame "+frameLocation+", \n"
                            + "nothing else needs to be done.\n"
                            + "No page fault is generated. \nWe have no victim frame.\n");
                }
                break;
            case "LFU":
                if(fault && !victim.equals("")){
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "Because virtual frame "+ frame +" is not present in physical memory, a page fault is generated.\n"
                            + "Because there is no more room in the physical memory, a frame must be replaced.\n"
                            + "The victim frame is virtual frame " +victim +". \n"
                            + "Virtual frame "+victim + " is swapped out, and virtual frame "+ frame +" is swapped in.\n");
                }else if(fault){
                    System.out.println("\nVirtual frame "+ frame+ " is referenced. \n"
                            + "ecause virtual frame "+ frame +" is not present in physical memory, a page fault is generated.\n"
                            + "Virtual frame "+ frame+" is loaded into physical frame "+frameLocation +".\n"
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
}
