//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: AVL Trees
// Files: TreeADT.java, AVLTree.java, Airport.java, AirportDataReader.java,
// DuplicateKeyException.java, IllegalKeyException.java
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
 * Reads in airport data from a CSV file, constructs an AVL tree, and prompts user to look up an
 * airport. If the user input is parsable and matches an airport code, print it and loop: otherwise,
 * exit the program
 */
public class AirportDataReader {
  /**
   * Main method to do all scanning, inputting and printing of airport codes.
   * 
   * @param args is a string of command-line arguments
   */
  public static void main(String[] args) {

    // tree is the AVL Tree of Airport objects, stored via their ID codes
    AVLTree<String, Airport> tree = new AVLTree<String, Airport>();

    // parse the csv file
    try {

      // csvScnr is a scanner to take in the airportdata.csv
      Scanner csvScnr = new Scanner(new File("airportdata.csv"));

      // skip the first line of headers
      csvScnr.nextLine();

      // while there is still data, add to the AVL tree
      while (csvScnr.hasNextLine()) {

        // row is the next row of data
        String row = csvScnr.nextLine();

        // data is the row split into an array via commas
        String[] data = row.split(",");

        // try to insert into the tree using the ID and Airport object. If there's an exception,
        // print it and keep trying
        try {
          tree.insert(data[3], new Airport(data[3], data[4], data[5]));

        } catch (IllegalKeyException e1) {
          System.out.println(e1.getMessage());
        } catch (DuplicateKeyException e2) {
          System.out.println(e2.getMessage());
        }

      }

      // close the scanner to prevent memory leaks
      csvScnr.close();
    } catch (FileNotFoundException e1) {
      // if the file isn't found, exit
      System.out.println("File not found.");
      return;
    }

    // quit will mark if we got bad input to allow quitting
    boolean quit = false;

    // stdin is the standard input Scanner for user input
    Scanner stdin = new Scanner(System.in);

    // while the input matches an airport ID, continue UI lookup
    do {

      // prompt for an airport id
      System.out.print("Enter 3-digit airport id: ");

      // userInput is the user input string
      String userInput = stdin.nextLine();

      // if the user input is null or isn't a 3 character airport ID, quit
      if ((userInput == null) || (userInput.length() != 3)) {
        quit = true;
      }
      
      // else, try to lookup the airport
      else {

        // airportLookup is the airport we're looking for
        Airport airportLookup = null;
        try {

          // set airportLookup to the matching value from the AVL tree
          airportLookup = tree.get(userInput.toUpperCase());

          // if the key is illegal, print that data
        } catch (IllegalKeyException ie) {
          System.out.println(ie.getMessage());
        }

        // if we couldn't find the airport or the object is somehow null, exit
        if (airportLookup == null) {
          quit = true;
        }

        // otherwise, print the airport data
        else {
          System.out.println(airportLookup.toString());
        }
      }

      // continue looping while airport lookups are valid
    } while (!quit);

    // close the scanner to prevent memory leaks
    stdin.close();
  }
}
