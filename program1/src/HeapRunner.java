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
 * HeapRunner takes in words1.txt and words2.txt, creates heaps out of the content and prints them
 * using the defined printing methods of StringHeap.java with proper spacing.
 */
public class HeapRunner {
  /**
   * main is the main printing method. It reads words from a hard-coded local file and add them to
   * the heap. It will also remove some elements from the Heap.
   * 
   * @param args is command-line arguments
   */
  public static void main(String[] args) {
    
    // heap is the heap for testing
    StringHeap heap;

    // testFileNames is a collection to loop over for printing file details
    String[] testFileNames = new String[] {"words1.txt", "words2.txt"};

    // for both files, print the heaps without removing
    for (String fileName : testFileNames) {

      // print details of the action
      System.out.println("Adding words from " + fileName + " without removing...\n");

      // fill the heap with the given file and print heap details
      heap = fillHeap(fileName);
      printFullHeapDetails(heap);
    }

    // new line between tests
    System.out.println();

    // for both files, print the heaps with one removal
    for (String fileName : testFileNames) {
      
      // print details of the action
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
   * this method prints the full heap details to the console
   * @param heap is the heap to be printed
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
   * this method fills the heap with the contents of the given file name
   * @param fileName is the local file to be scanned for input strings
   * @return a StringHeap representing the strings from the file
   */
  private static StringHeap fillHeap(String fileName) {

    // instantiate the heap. Start small to test expansion
    StringHeap heap = new StringHeap(5);

    // scnr is the Scanner used for file scanning
    Scanner scnr = null;
    
    // try accessing the given filename
    try {
      
      // initialize scnr with the given fileName
      scnr = new Scanner(new File(fileName));
      
      // while there is still a string, add that string to the heap
      while (scnr.hasNextLine()) {
        String word = scnr.nextLine();
        heap.add(word);
      }
      
    }
    catch (FileNotFoundException ex) {

      // print the error message
      System.out.println(ex.getMessage());
    }
    finally {
      
      // close the scanner if it exists
      if (scnr != null) {
        scnr.close();
      }
    }
    
    // return the heap
    return heap;
  }
}
