package application;

import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import tree.BTree;
import tree.DuplicateKeyException;
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
      }
      catch (NumberFormatException numEx) {
        easterEggs = 0;
      }
      // try to insert into the hash table using the ID and Airport object.
      // If there's an exception, print it and keep trying
      try {
        hrToonTree.insert(id, new hrtoon(id, data[0], data[1], data[2], data[3], easterEggs, data[5]));
        
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
}
