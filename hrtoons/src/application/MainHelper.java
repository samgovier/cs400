//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Final Project
// Files: BTree.java, BtreeADT.java, BTreeTests.java,
// DuplicateKeyException.java, KeyNotFoundException.java, NullKeyException.java,
// Main.java, MainHelper.java, hrtoon.java
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
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tree.BTree;
import tree.DuplicateKeyException;
import tree.KeyNotFoundException;
import tree.NullKeyException;

/**
 * MainHelper has functions that Main can use to help clean up the JavaFX view class.
 *
 */
public class MainHelper {

  /**
   * parseCSV takes in a csv file and parses it
   * 
   * @param filepath is the path to the file to be parsed
   * @return a btree of the parsed file
   * @throws FileNotFoundException if the file can't be found
   */
  public static BTree<Integer, hrtoon> parseCSV(String filepath) throws FileNotFoundException {

    // hrToonTree is the tree to be filled and returned
    BTree<Integer, hrtoon> hrToonTree = new BTree<Integer, hrtoon>();

    // csvScnr is a scanner to take in the requested file
    Scanner csvScnr = new Scanner(new File(filepath));

    // skip the first line of headers
    csvScnr.nextLine();

    // id is the number of the released toon
    int id = 0;

    // while there is still data, add to the b-tree
    while (csvScnr.hasNextLine()) {

      // row is the next row of data
      String row = csvScnr.nextLine();

      // data is the row split into an array via commas
      String[] data = row.split(",");

      // easterEggs is the amount of easter Eggs. if we can parse cell 5 set it to easterEggs
      int easterEggs;
      try {
        easterEggs = Integer.parseInt(data[4]);
      } catch (NumberFormatException numEx) {
        easterEggs = 0;
      }

      // try to insert into the b-tree using the ID and hrtoon object.
      // If there's an exception, skip and keep trying
      try {
        hrToonTree.insert(id,
            new hrtoon(id, data[0], data[1], data[2], data[3], easterEggs, data[5]));

        id++;
      } catch (DuplicateKeyException e) {
        // skip
      } catch (NullKeyException e) {
        // skip
      }
    }

    // close the scanner to prevent memory leaks
    csvScnr.close();

    return hrToonTree;
  }

  /**
   * saveCSV saves a CSV to the defined path
   * 
   * @param hrtoonsDB is the database to be saved
   * @param newPath   is the path to the file to be saved
   * @throws FileNotFoundException if the path can't be resolved
   */
  public static void saveCSV(BTree<Integer, hrtoon> hrtoonsDB, String newPath)
      throws FileNotFoundException {

    // keysToWrite is all the keys from the BTree
    ArrayList<Integer> keysToWrite = hrtoonsDB.getAllKeys();

    // csvLines is all lines to be written to the csv
    ArrayList<String> csvLines = new ArrayList<>();
    csvLines.add("Release Date,Day of Week,Type,Toon,Easter Eggs,Runtime");

    // for each key, add to csvLines
    for (Integer key : keysToWrite) {
      try {

        // try to get the value. This should work since it's immediate
        // If there's an exception, skip and keep trying
        hrtoon ct = hrtoonsDB.getValue(key);

        // convert the easter eggs integer to string
        String eggsString = ((Integer) ct.getEasterEggs()).toString();

        // create a string array of the hrtoon object
        String[] csvToon = new String[] {ct.getReleaseDate(), ct.getDow(), ct.getType(),
            ct.getTitle(), eggsString, ct.getRuntime()};

        // use a stream to turn the array into an actual csv string
        String csvLine = Stream.of(csvToon).collect(Collectors.joining(","));

        // add the csv line to csvLines
        csvLines.add(csvLine);
      } catch (KeyNotFoundException e) {
        // skip
      } catch (NullKeyException e) {
        // skip
      }
    }

    // create a file object to the new path
    File csvOutputFile = new File(newPath);

    // try to stream the csv file to the file. If there's an exception, pass it up
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      csvLines.stream().forEach(pw::println);
    }

  }

  /**
   * Run stats gets stats on the passed tree
   * 
   * @param hrtoonsDB is the tree to run stats against
   * @return the stats string to be printed
   */
  public static String runStats(BTree<Integer, hrtoon> hrtoonsDB) {

    // instantiate the stats string
    String newStats = "";

    // these are all the keys from the database
    ArrayList<Integer> hrtoonsKeys = hrtoonsDB.getAllKeys();

    // add number of keys
    newStats += "Number of Toons: " + hrtoonsKeys.size() + " \n";

    // get the total amount of easter eggs
    Integer totalEggs = 0;
    for (Integer key : hrtoonsKeys) {
      try {
        // try to get the number of easter eggs. This should work since it's immediate
        // If there's an exception, skip and keep trying
        totalEggs += hrtoonsDB.getValue(key).getEasterEggs();
      } catch (KeyNotFoundException e) {
        // skip
      } catch (NullKeyException e) {
        // skip
      }
    }

    // add average easter eggs to stats
    newStats += "Average Number of Easter Eggs: " + (totalEggs / hrtoonsKeys.size()) + " \n";

    // create an integer variable for each day of week
    Integer Monday = 0;
    Integer Tuesday = 0;
    Integer Wednesday = 0;
    Integer Thursday = 0;
    Integer Friday = 0;
    Integer Saturday = 0;
    Integer Sunday = 0;

    // check the day of week for each toon: if it matches, add to above variables
    for (Integer key : hrtoonsKeys) {
      try {
        switch (hrtoonsDB.getValue(key).getDow().toUpperCase()) {
          case "MONDAY":
            Monday++;
            break;
          case "TUESDAY":
            Tuesday++;
            break;
          case "WEDNESDAY":
            Wednesday++;
            break;
          case "THURSDAY":
            Thursday++;
            break;
          case "FRIDAY":
            Friday++;
            break;
          case "SATURDAY":
            Saturday++;
            break;
          case "SUNDAY":
            Sunday++;
            break;
          default:
            break;
        }
      } catch (KeyNotFoundException e) {
        // skip
      } catch (NullKeyException e) {
        // skip
      }
    }

    // maxOccur is the value of the date that has the most uploads
    Integer maxOccur = Math.max(Monday, Math.max(Tuesday,
        Math.max(Wednesday, Math.max(Thursday, Math.max(Friday, Math.max(Saturday, Sunday))))));

    // add the most popular date to the stats
    newStats += "Most Popular Upload Date:";

    if (maxOccur.equals(Monday)) {
      newStats += " Monday";
    }
    if (maxOccur.equals(Tuesday)) {
      newStats += " Tuesday";
    }
    if (maxOccur.equals(Wednesday)) {
      newStats += " Wednesday";
    }
    if (maxOccur.equals(Thursday)) {
      newStats += " Thursday";
    }
    if (maxOccur.equals(Friday)) {
      newStats += " Friday";
    }
    if (maxOccur.equals(Saturday)) {
      newStats += " Saturday";
    }
    if (maxOccur.equals(Sunday)) {
      newStats += " Sunday";
    }

    // return the stats
    return newStats;
  }
}
