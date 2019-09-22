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
 * airport.
 */
public class AirportDataReader {
  /**
   * Main method.
   * 
   * @param args
   */
  public static void main(String[] args) {
    AVLTree<String, Airport> tree = new AVLTree<String, Airport>();

    // parse csv
    try {
      Scanner csvScnr = new Scanner(new File("airportdata.csv"));
      
      // skip the first line of headers
      csvScnr.nextLine();
      while (csvScnr.hasNextLine()) {
        String row = csvScnr.nextLine();
        String[] data = row.split(",");
        try {
          tree.insert(data[3], new Airport(data[3], data[4], data[5]));

        } catch (IllegalKeyException e1) {
          System.out.println(e1.getMessage());
        } catch (DuplicateKeyException e2) {
          System.out.println(e2.getMessage());
        }

      }
      csvScnr.close();
    } catch (FileNotFoundException e1) {
      System.out.println("File not found.");
    }

    boolean quit = false;
    Scanner stdin = new Scanner(System.in);
    do {
      System.out.print("Enter 3-digit airport id: ");
      String userInput = stdin.nextLine();

      if (userInput.toLowerCase() == "quit") {
        quit = true;
      }
      if ((userInput == null) || (userInput.length() != 3)) {
        System.out.println("Please enter a 3-digit airport code.");
      } else {
        Airport airportLookup = null;
        try {
          airportLookup = tree.get(userInput);
        } catch (IllegalKeyException ie) {
          System.out.println(ie.getMessage());
        }
        
        if (airportLookup == null) {
          System.out.println("Airport code not found.");
          quit = true;
        }
        else {
          System.out.println(airportLookup.toString());
        }
      }

    } while (!quit);

    stdin.close();
  }
}
