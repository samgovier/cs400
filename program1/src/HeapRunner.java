import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: String Heap
// Files: StringHeap.java, HeapRunner.java
// Course: Computer Science 400
//
// Author: Sam Govier
// Email: sgovier@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

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
    StringHeap heap;
    
    String[] testFileNames = new String[] { "words1.txt", "words2.txt" };
    
    for (String fileName : testFileNames) {

      System.out.println("Adding words from " + fileName + " without removing...\n");
      
      // fill the heap with the given file and print heap details
      heap = fillHeap(fileName);
      printFullHeapDetails(heap);
    }
    
    // new line between tests
    System.out.println();
    
    for (String fileName : testFileNames) {
      System.out.println("Adding words from " + fileName + " and calling remove() once...\n");
      
      // fill the heap with the given file
      heap = fillHeap(fileName);

      // calling remove() once
      heap.remove();
      
      // print heap details
      printFullHeapDetails(heap);

    }
  }

  /**
   * @param heap
   */
  private static void printFullHeapDetails(StringHeap heap) {
    // print out heap using printHeap()
    heap.printHeap();

    // print method delimiter
    System.out.println("------------------------------");

    // print out heap using printLevelOrderTraversal()
    heap.printLevelOrderTraversal();
    
    // new line for next output
    System.out.println();
  }

  /**
   * @param fileName
   * @return
   */
  private static StringHeap fillHeap(String fileName) {
    StringHeap heap;
    // instantiate the heap
    heap = new StringHeap(5);

    // add a try/catch block to make the code below compile
    try {
      Scanner scnr = new Scanner(new File(fileName));
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
    return heap;
  }
}
