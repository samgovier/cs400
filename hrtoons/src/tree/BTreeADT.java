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

import java.util.ArrayList;

public interface BTreeADT<K extends Comparable<K>, V> {

  public void insert(K key, V value) throws DuplicateKeyException, NullKeyException;
  
  public void remove(K key) throws KeyNotFoundException, NullKeyException;
  
  public V getValue(K key) throws KeyNotFoundException, NullKeyException;
  
  public boolean contains(K key) throws NullKeyException;
  
  public int height();
  
  public int size();
  
  public ArrayList<K> getAllKeys();
}
