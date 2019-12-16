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

/**
 * BTreeADT is the ADT for a 2-3 B-Tree. It includes options for inserting, removing, getting the
 * value and other data on the tree.
 *
 * @param <K> is the key value that items are sorted according to
 * @param <V> is the value stored with a corresponding key
 */
public interface BTreeADT<K extends Comparable<K>, V> {

  /**
   * insert the key/value pair into the BTree, following 2-3 B-Tree rules.
   * 
   * @param key   is the key for inserting and searching the B-Tree
   * @param value is the value to match with the key
   * @throws DuplicateKeyException if the key already exists
   * @throws NullKeyException      if the key is null
   */
  public void insert(K key, V value) throws DuplicateKeyException, NullKeyException;

  /**
   * remove the key/value pair from the BTree, following 2-3 B-Tree rules.
   * 
   * @param key is the key of the object to remove
   * @throws KeyNotFoundException if the key can't be found
   * @throws NullKeyException     if the key is null
   */
  public void remove(K key) throws KeyNotFoundException, NullKeyException;

  /**
   * get the value of the corresponding key from the B-Tree
   * 
   * @param key is the key of the object to get
   * @return the value associated with the passed key
   * @throws KeyNotFoundException if the key isn't found
   * @throws NullKeyException     if the key is null
   */
  public V getValue(K key) throws KeyNotFoundException, NullKeyException;

  /**
   * check to see if the passed key exists in the tree
   * 
   * @param key is the key to check for
   * @return boolean of whether the key exists or not
   * @throws NullKeyException if the key is null
   */
  public boolean contains(K key) throws NullKeyException;

  /**
   * Get the height of the tree, 0 if there are no keys, 1 if there is only one node, otherwise
   * return the number of tree levels.
   * 
   * @return the height of the tree from root to leaf
   */
  public int height();

  /**
   * Get the amount of keys in the tree
   * 
   * @return the size of the tree, which is the amount of keys
   */
  public int size();

  /**
   * Get all keys in the tree. If the tree is empty, return an empty list
   * 
   * @return an arraylist of all keys in the tree
   */
  public ArrayList<K> getAllKeys();
}
