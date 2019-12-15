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

public class MainHelper {

  public static BTree<Integer, hrtoon> parseCSV(String filepath) throws FileNotFoundException {

    BTree hrToonTree = new BTree<Integer, hrtoon>();
    // csvScnr is a scanner to take in the requested file
    Scanner csvScnr = new Scanner(new File(filepath));

    // skip the first line of headers
    csvScnr.nextLine();

    // id is the number of the released toon
    int id = 0;
    // while there is still data, add to the hashTable
    while (csvScnr.hasNextLine()) {

      // row is the next row of data
      String row = csvScnr.nextLine();

      // data is the row split into an array via commas
      String[] data = row.split(",");

      int easterEggs;
      try {
        easterEggs = Integer.parseInt(data[4]);
      } catch (NumberFormatException numEx) {
        easterEggs = 0;
      }
      // try to insert into the hash table using the ID and Airport object.
      // If there's an exception, print it and keep trying
      try {
        hrToonTree.insert(id,
            new hrtoon(id, data[0], data[1], data[2], data[3], easterEggs, data[5]));

        id++;
      } catch (DuplicateKeyException e) {
        e.printStackTrace();
      } catch (NullKeyException e) {
        e.printStackTrace();
      }
    }

    // close the scanner to prevent memory leaks
    csvScnr.close();

    return hrToonTree;
  }

  public static void saveCSV(BTree<Integer, hrtoon> hrtoonsDB, String newPath)
      throws FileNotFoundException {

    ArrayList<Integer> keysToWrite = hrtoonsDB.getAllKeys();

    ArrayList<String> csvLines = new ArrayList<>();
    csvLines.add("Release Date,Day of Week,Type,Toon,Easter Eggs,Runtime");

    for (Integer key : keysToWrite) {
      try {
        hrtoon ct = hrtoonsDB.getValue(key);
        String eggsString = ((Integer) ct.getEasterEggs()).toString();
        String[] csvToon = new String[] {ct.getReleaseDate(), ct.getDow(), ct.getType(),
            ct.getTitle(), eggsString, ct.getRuntime()};

        String csvLine = Stream.of(csvToon).collect(Collectors.joining(","));

        csvLines.add(csvLine);
      } catch (KeyNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NullKeyException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    File csvOutputFile = new File(newPath);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      csvLines.stream().forEach(pw::println);
    }

  }

  public static String runStats(BTree<Integer, hrtoon> hrtoonsDB) {
    // "Number of Toons: \nAverage Number of Easter Eggs: \nMost Popular Upload Date: "

    String newStats = "";
    ArrayList<Integer> hrtoonsKeys = hrtoonsDB.getAllKeys();
    newStats += "Number of Toons: " + hrtoonsKeys.size() + " \n";

    Integer totalEggs = 0;
    for (Integer key : hrtoonsKeys) {
      try {
        totalEggs += hrtoonsDB.getValue(key).getEasterEggs();
      } catch (KeyNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NullKeyException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    newStats += "Average Number of Easter Eggs: " + (totalEggs / hrtoonsKeys.size()) + " \n";

    Integer Monday = 0;
    Integer Tuesday = 0;
    Integer Wednesday = 0;
    Integer Thursday = 0;
    Integer Friday = 0;
    Integer Saturday = 0;
    Integer Sunday = 0;

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
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NullKeyException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    Integer maxOccur = Math.max(Monday, Math.max(Tuesday,
        Math.max(Wednesday, Math.max(Thursday, Math.max(Friday, Math.max(Saturday, Sunday))))));

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
    
    return newStats;
  }
}
