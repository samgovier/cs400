import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HeapRunner {
  public static void main(String[] args) {
    
    StringHeap heap = new StringHeap(30);
    // TODO: add a try/catch block to make the code below compile

    Scanner scnr = new Scanner(new File("words2.txt"));
    while (scnr.hasNextLine()) {
      String word = scnr.nextLine();
      heap.add(word);
    }

    // TODO: print out heap using printHeap()

    System.out.println("------------------------------");

    // TODO: print out heap using printLevelOrderTraversal()
  }
}
