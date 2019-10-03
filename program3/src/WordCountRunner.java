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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Reads in string data from a TXT file, constructs a Red Black Tree, and prompts the user to look
 * up a word to gather statistics on it. It also prints the tree. It will keep accepting user input
 * until we get 'qqq'.
 */
public class WordCountRunner {

  // INPUT_FILE is the static variable with the input file name
  private static final String INPUT_FILE = "test.txt";

  /**
   * Main method to do all scanning, inputting and printing of word lookup.
   * 
   * @param args is a string of command-line arguments
   */
  public static void main(String[] args) {

    // tree is the RedBlack Tree of word objects, and the number of times they appear in the text
    RedBlackTree<String, Integer> tree = new RedBlackTree<String, Integer>();

    // scnr is a scanner to take in the INPUT_FILE
    Scanner scnr;

    try {

      // take in the INPUT_FILE
      scnr = new Scanner(new File(INPUT_FILE));

      // while there is still data, add to the RedBlack Tree
      while (scnr.hasNextLine()) {

        // loop through all lines
        String line = scnr.nextLine();

        // clean a line with regex
        // .replaceAll("[^a-zA-Z ]", "") removes all non-alphabetic characters (replaces them with
        // the empty string) and .split("\\s+") separates out the words using whitespace as a
        // delimiter
        String[] words = line.trim().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        // for every word in the words array, add them to the tree
        for (String word : words) {

          // if there is data in the string, add it
          if (word.length() > 0) {

            // try adding to the tree. If there's an exception, print and keep trying
            try {

              // value is the amount the word shows up in the text, null if it hasn't shown yet
              Integer value = tree.getValue(word);

              // if the word hasn't shown yet, add it and mark it's appearance once
              if (value == null) {
                tree.insert(word, 1);
              }

              // otherwise re-enter the word and increment the amount it appears
              else {
                tree.insert(word, ++value);
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

    // print the tree to show the build
    tree.printSideways();

    // after all words are read in, print out this message about tree statistics
    System.out.println(
        "The tree has a size of " + tree.getSize() + " and a height of " + tree.getHeight());

    // input is the user System.in input
    Scanner input = new Scanner(System.in);

    // request will be the modified user input
    String request = "";
    
    // while the input doesn't match 'qqq', continue word lookup
    do {
      
      // prompt for a word to search for
      System.out.print("Enter a word to search for in \"" + INPUT_FILE + "\": ");
      
      // filter the user input
      request = input.next().replaceAll("[^a-zA-Z ]", "").toLowerCase();

      // if the request is 'qqq', exit
      if (request.equalsIgnoreCase("qqq")) {
        System.out.println("Exiting.");
      }
      
      // otherwise lookup the string
      else {
        try {
          
          // wordTimes is the amount of times the word shows up
          Integer wordTimes = tree.getValue(request);

          // if wordTiems is null, print that the word doesn't exist in the tree
          if (null == wordTimes) {
            System.out.println("\"" + request + "\" was not found in the given file.");
          }
          
          // otherwise print the amount of times the word shows up in the file
          else {
            System.out
                .println("\"" + request + "\" was found in the file " + wordTimes + " times.");
          }
        } catch (IllegalKeyException e1) {
          // if null is somehow input, print that
          System.out.println("Request was null.");
        }
      }
      
      // continue looping until 'qqq' is entered
    } while (!request.equalsIgnoreCase("qqq"));

    // close the scanner to prevent memory leaks
    input.close();
  }
}
