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
package tree;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;
import java.util.ArrayList;
import java.util.Random;

/**
 * BTreeTests tests the BTree.java class to make sure all methods work properly in all cases
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BTreeTests {

  // btree is the tree used for testing
  private BTree<Integer, String> btree;

  // rnd is used to generate random key/value pairs for insertion
  private Random rnd;

  /**
   * setUp initializes a new btree and rnd for each test
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    this.btree = new BTree<Integer, String>();

    // keep a consistent seed for proper testing
    this.rnd = new Random(313);
  }

  /**
   * tearDown sets the current btree to null
   * 
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    this.btree = null;
    this.rnd = null;
  }

  /**
   * Test that exceptions are thrown on null
   */
  @Test
  public void test01_NullTesting() {
    try {
      try {
        btree.insert(null, "null");
        fail("Exception Expected");

      } catch (NullKeyException e) {
        // pass
      }
      try {
        btree.remove(null);
        fail("Exception Expected");

      } catch (NullKeyException e) {
        // pass
      }
      try {
        btree.contains(null);
        fail("Exception Expected");

      } catch (NullKeyException e) {
        // pass
      }
      try {
        btree.getValue(null);
        fail("Exception Expected");

      } catch (NullKeyException e) {
        // pass
      }
    } catch (DuplicateKeyException e) {
      fail("Duplicate Key Exception");
      e.printStackTrace();
    } catch (KeyNotFoundException e) {
      fail("Key Not Found Exception");
      e.printStackTrace();
    }
  }

  /**
   * Test inserting many items: 1000 is double the needed keys for project spec
   */
  @Test
  public void test02_InsertMany() {
    for (int i = 0; i < 1000; i++) {
      Integer intSert = rnd.nextInt();
      try {
        btree.insert(intSert, intSert.toString());
        assertTrue("insert failed at test " + i, btree.contains(intSert));
      } catch (DuplicateKeyException e) {
        fail("Duplicate Key Exception");
        e.printStackTrace();
      } catch (NullKeyException e) {
        fail("Null Key Exception");
        e.printStackTrace();
      } catch (Exception e) {
        fail("Other Exception\n" + e.getMessage());
      }
    }
  }

  /**
   * Test removing many items: 1000 is double the needed keys for project spec
   */
  @Test
  public void test03_RemoveMany() {
    ArrayList<Integer> intSerted = new ArrayList<Integer>();
    for (int i = 0; i < 1000; i++) {
      Integer intSert = rnd.nextInt();
      try {
        btree.insert(intSert, intSert.toString());
        assertTrue(btree.contains(intSert));
        intSerted.add(intSert);
      } catch (DuplicateKeyException e) {
        fail("Duplicate Key Exception");
        e.printStackTrace();
      } catch (NullKeyException e) {
        fail("Null Key Exception");
        e.printStackTrace();
      } catch (Exception e) {
        fail("Other Exception");
        e.printStackTrace();
      }
    }

    for (int i = 0; i < intSerted.size(); i++) {
      Integer intRemove = intSerted.get(i);
      try {
        btree.remove(intRemove);
        assertFalse(btree.contains(intRemove));
      } catch (NullKeyException e) {
        fail("Null Key Exception");
        e.printStackTrace();
      } catch (KeyNotFoundException e) {
        fail("Key Not Found Exception");
        e.printStackTrace();
      } catch (Exception e) {
        fail("Other Exception\n" + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  /**
   * Test getting many items: 1000 is double the needed keys for project spec
   */
  @Test
  public void test04_getValueMany() {
    for (int i = 0; i < 1000; i++) {
      Integer intSert = rnd.nextInt();
      try {
        btree.insert(intSert, intSert.toString());
        assertTrue(btree.getValue(intSert).equals(intSert.toString()));
      } catch (DuplicateKeyException e) {
        fail("Duplicate Key Exception");
        e.printStackTrace();
      } catch (NullKeyException e) {
        fail("Null Key Exception");
        e.printStackTrace();
      } catch (Exception e) {
        fail("Other Exception");
        e.printStackTrace();
      }
    }
  }

  /**
   * Test a tree that has a single value
   */
  @Test
  public void test05_singleTesting() {
    try {
      btree.insert(4, "4");
      assertTrue(btree.contains(4));
      btree.remove(4);
    } catch (DuplicateKeyException e) {
      fail("Duplicate Key Exception");
      e.printStackTrace();
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    } catch (Exception e) {
      fail("Other Exception");
      e.printStackTrace();
    }
  }

  /**
   * Test that height is proper on different levels
   */
  @Test
  public void test06_height() {
    assertTrue(btree.height() == 0);
    try {
      btree.insert(1, "1");
    } catch (DuplicateKeyException e) {
      fail("Duplicate Key Exception");
      e.printStackTrace();
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    }
    assertTrue(btree.height() == 1);
    try {
      for (Integer i = 2; i <= 10; i++) {
        btree.insert(i, i.toString());
      }
    } catch (DuplicateKeyException e) {
      fail("Duplicate Key Exception");
      e.printStackTrace();
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    }
    assertTrue(btree.height() == 3);
    try {
      for (Integer i = 11; i <= 20; i++) {
        btree.insert(i, i.toString());
      }
    } catch (DuplicateKeyException e) {
      fail("Duplicate Key Exception");
      e.printStackTrace();
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    }
    assertTrue(btree.height() == 4);
    try {
      for (Integer i = 1; i <= 10; i++) {
        btree.remove(i);
      }
    } catch (KeyNotFoundException e) {
      fail("KeyNotFoundException");
      e.printStackTrace();
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    }
    assertTrue(btree.height() == 3);
  }

  /**
   * Test that size is proper after many different iterations
   */
  @Test
  public void test07_size() {
    assertTrue(btree.size() == 0);
    try {
      btree.insert(1, "1");
    } catch (DuplicateKeyException e) {
      fail("Duplicate Key Exception");
      e.printStackTrace();
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    }
    assertTrue(btree.size() == 1);
    try {
      for (Integer i = 2; i <= 10; i++) {
        btree.insert(i, i.toString());
      }
    } catch (DuplicateKeyException e) {
      fail("Duplicate Key Exception");
      e.printStackTrace();
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    }
    assertTrue(btree.size() == 10);
    try {
      for (Integer i = 11; i <= 20; i++) {
        btree.insert(i, i.toString());
      }
    } catch (DuplicateKeyException e) {
      fail("Duplicate Key Exception");
      e.printStackTrace();
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    }
    assertTrue(btree.size() == 20);
    try {
      for (Integer i = 1; i <= 10; i++) {
        btree.remove(i);
      }
    } catch (KeyNotFoundException e) {
      fail("KeyNotFoundException");
      e.printStackTrace();
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    }
    assertTrue(btree.size() == 10);
  }

  /**
   * Test that getAllKeys returns all keys in many iterations
   */
  @Test
  public void test08_getAllKeys() {
    assertTrue(btree.getAllKeys().size() == 0);

    try {
      for (Integer i = 0; i <= 20; i++) {
        btree.insert(i, i.toString());
      }
    } catch (DuplicateKeyException e) {
      fail("Duplicate Key Exception");
      e.printStackTrace();
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    }
    assertTrue(btree.getAllKeys().size() == 21);
  }

  /**
   * Test contains on many items: 1000 is double the needed keys for project spec
   */
  @Test
  public void test09_containsMany() {
    for (int i = 0; i < 1000; i++) {
      Integer intSert = rnd.nextInt();
      try {
        btree.insert(intSert, intSert.toString());
        assertTrue(btree.contains(intSert));
        btree.remove(intSert);
        assertFalse(btree.contains(intSert));
      } catch (DuplicateKeyException e) {
        fail("Duplicate Key Exception");
        e.printStackTrace();
      } catch (NullKeyException e) {
        fail("Null Key Exception");
        e.printStackTrace();
      } catch (Exception e) {
        fail("Other Exception");
        e.printStackTrace();
      }
    }
  }

  /**
   * Test that exceptions are thrown at the proper locations
   */
  @Test
  public void test10_exceptionTesting() {
    try {
      btree.insert(4, "4");
      btree.insert(4, "4");
      fail("no fail on dup");
    } catch (DuplicateKeyException e) {
      // pass
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    } catch (Exception e) {
      fail("Other Exception");
      e.printStackTrace();
    }

    try {
      btree.remove(5);
      fail("no fail on not found");
    } catch (KeyNotFoundException e) {
      // pass
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    } catch (Exception e) {
      fail("Other Exception");
      e.printStackTrace();
    }

    try {
      btree.getValue(5);
      fail("no fail on not found");
    } catch (KeyNotFoundException e) {
      // pass
    } catch (NullKeyException e) {
      fail("Null Key Exception");
      e.printStackTrace();
    } catch (Exception e) {
      fail("Other Exception");
      e.printStackTrace();
    }
  }
}
