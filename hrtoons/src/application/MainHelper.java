package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import tree.BTree;

public class MainHelper {

  private static BTree<Integer, hrtoon> parseCSV(String filename) throws FileNotFoundException {
    
    BTree hrToonTree = new BTree<Integer, hrtoon>();
    // csvScnr is a scanner to take in the requested file
    Scanner csvScnr = new Scanner(new File(filename));

    // skip the first line of headers
    csvScnr.nextLine();

    // while there is still data, add to the hashTable
    while (csvScnr.hasNextLine()) {

      // row is te next row of data
      String row = csvScnr.nextLine();

      // data is the row split into an array via commas
      String[] data = row.split(",");

      // try to insert into the hash table using the ID and Airport object.
      // If there's an exception, print it and keep trying
      try {
        hrToonTree.insert(data[3],
            new Airport(data[3], data[4], data[5], Integer.parseInt(data[0])));
      } catch (NullKeyException e1) {
        System.out.println(e1.getMessage());
      } catch (DuplicateKeyException e2) {
        System.out.println(e2.getMessage());
      }
    }

    // close the scanner to prevent memory leaks
    csvScnr.close();
  }
}
