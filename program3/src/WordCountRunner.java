import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Red Black Tree
// Files: SearchTreeADT.java, RedBlackTree.java, IllegalKeyException.java, WordCountRunner.java
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


public class WordCountRunner {
  private static final String INPUT_FILE = "test.txt";

  public static void main(String[] args) {
    RedBlackTree<String, Integer> tree = new RedBlackTree<String, Integer>();
    Scanner scnr;

    try {
      scnr = new Scanner(new File(INPUT_FILE));

      while (scnr.hasNextLine()) {
        // loop through all lines
        String line = scnr.nextLine();

        // clean a line with regex
        // .replaceAll("[^a-zA-Z ]", "") removes all non-alphabetic characters (replaces them with
        // the
        // empty string) and .split("\\s+") separates out the words using whitespace as a delimiter
        String[] words = line.trim().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        for (String word : words) {
          if (word.length() > 0) {
            try {
              Integer value = tree.getValue(word);
              if (value == null) {
                tree.insert(word, 1);
              } else {
                tree.insert(word, value++);
              }

            } catch (IllegalKeyException e1) {
              System.out.println("null word on the following line: " + line);
            }
          }
        }

      }

      // close the scanner to prevent memory leaks
      scnr.close();
    } catch (FileNotFoundException e1) {
      // if the file isn't found, exit
      System.out.println("File not found.");
      return;
    }
    tree.printSideways();
    // after all words are read in, print out this message
    System.out.println(
        "The tree has a size of " + tree.getSize() + " and a height of " + tree.getHeight());
    
    Scanner input = new Scanner(System.in);
    String request = "";
    // while the input doesn't match 'qqq', continue word lookup
    do {
      System.out.print("Enter a word to search for in \"" + INPUT_FILE + "\": ");
      request = input.next().replaceAll("[^a-zA-Z ]", "").toLowerCase();
      
      if (request == "qqq") {
        System.out.println("Exiting.");
      }
      else {
        try {
          Integer wordTimes = tree.getValue(request);
          
          if (null == wordTimes) {
            System.out.println("\"" + request + "\" was not found in the given file.");
          }
          else {
            System.out.println("\"" + request + "\" was found in the file " + wordTimes + " times.");
          }
        } catch (IllegalKeyException e1) {
          System.out.println("Request was null.");
        }
      }
    } while (request != "qqq");

    // close the scanner to prevent memory leaks
    input.close();
  }
}
