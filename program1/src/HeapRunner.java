import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * HeapRunner
 *
 */
public class HeapRunner {
  /**
   * main is the main printing method. It reads words from a hard-coded local file and add them to
   * the heap. It will also remove some elements from the Heap.
   * 
   * @param args is command-line arguments
   */
  public static void main(String[] args) {
    // instantiate your heap
    StringHeap heap = new StringHeap(5);

    // add a try/catch block to make the code below compile
    try {
      Scanner scnr = new Scanner(new File("words2.txt"));
      while (scnr.hasNextLine()) {
        String word = scnr.nextLine();
        heap.add(word);
      }

      // close the unmanaged Scanner scnr
      scnr.close();
    } catch (FileNotFoundException ex) {

      // print the error message
      System.out.println(ex.getMessage());
    }

    // print out heap using printHeap()
    heap.printHeap();

    // print method delimiter
    System.out.println("------------------------------");

    // print out heap using printLevelOrderTraversal()
    heap.printLevelOrderTraversal();
  }
}
