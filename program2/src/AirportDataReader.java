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
 * Reads in airport data from a CSV file, constructs an AVL tree, and prompts user to look up an airport.
 */
public class AirportDataReader {
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		AVLTree<String, Airport> tree = new AVLTree<String, Airport>();

		// parse csv
		try {
			Scanner csvScnr = new Scanner(new File("airportdata.csv"));
			while (csvScnr.hasNextLine()) {
				String row = csvScnr.nextLine();
				String[] data = row.split(",");

					// TODO: extract airport data: ID, city, name
				Airport insert = new Airport(data[0], data[1], data[2]);
				tree.insert(data[0], insert);
					// TODO: create airport object
					// TODO: insert airport into tree
					// TODO: catch exceptions

			}
			csvScnr.close();
		} catch (FileNotFoundException e1) {
			System.out.println("File not found.");
		}

		boolean quit = false;
		
		do {
		  Scanner stdin = new Scanner(System.in);
		  String nextInput = stdin.next();
		  
		} while (!quit);
		// TODO: prompt user to look up airport
		// TODO: accept user input of 3-digit code
		// TODO: get airport object, print to standard output
		// TODO: exit when a user enters an airport code that is not found
	}
}