//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Hash Table
// Files: Airport.java, AirportDataReader.java, HashTable.java, HashTaleADT.java,
// DuplicateKeyException.java, KeyNotFoundException.java, NullKeyException.java
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
import HashTable.HashNode;

/**
 * Hash table data structure. This hash table is an array of linked lists with key/value pairs. The
 * hash index will be calculated as follows: int hashIndex = Math.abs(key.hashCode()) %
 * this.table.length; Your class may not use any import statements May not add any public members to
 * ADT or implementation.
 * 
 * @param <K> the key must not be null and must be Comparable
 * @param <V> the data value associated with a given key
 * 
 * @param <K> key
 * @param <V> value
 */
// @SuppressWarnings("unchecked")
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

  /**
   * Hash node inner class storing a key, value, and pointer to the next node in the chain.
   */
  @SuppressWarnings("hiding")
  private class HashNode<K, V> {
    private K key;
    private V value;
    private HashNode<K, V> next;

    private HashNode(K key, V value, HashNode<K, V> next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }

    private String hashNodeToString() {
      return key + " : " + value;
    }
  }

  // instance variables for hash table
  private double loadFactorThreshold;
  private int numKeys;
  private HashNode<K, V>[] table; // array of header nodes

  /**
   * Default no-arg constructor.
   */
  public HashTable() {
    this(5, 0.75);
  }

  /**
   * Constructor with initial capacity and load factor threshold provided.
   * 
   * @param initialCapacity
   * @param loadFactorThreshold
   */
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    this.loadFactorThreshold = loadFactorThreshold;
    this.numKeys = 0;
    // initialize buckets
    table = (HashNode<K, V>[]) new HashNode[initialCapacity];
  }

  /**
   * Adds the key/value pair to the hash table and increases the number of keys. This method resizes
   * the hash table when load factor >= threshold Capacity must increase to: 2 * capacity + 1. Once
   * increased, capacity never decreases.
   * 
   * @param key
   * @param value
   * @throws DuplicateKeyException if key is already in the data structure
   * @throws NullKeyException      if key is null
   */
  @Override
  public void insert(K key, V value) throws DuplicateKeyException, NullKeyException {
    // TODO Auto-generated method stub
    // int hashIndex = Math.abs(key.hashCode()) % this.table.length;


  }

  /**
   * Removes the key/value pair from the hash table and decreases the number of keys.
   * 
   * @param key
   * @return true is key is found, false if key is not found
   * @throws NullKeyException if key is null
   */
  @Override
  public boolean remove(K key) throws NullKeyException {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * Returns the value associated with the specified key.
   * 
   * @param key
   * @return value
   * @throws KeyNotFoundException if key is not found
   * @throws NullKeyException     if key is null
   */
  @Override
  public V get(K key) throws KeyNotFoundException, NullKeyException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Returns the number of keys in the hash table.
   * 
   * @return number of keys
   */
  @Override
  public int numKeys() {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Returns the current load factor for this hash table. load factor = number of items / current
   * table size
   * 
   * @return load factor (as a double)
   */
  @Override
  public double getLoadFactor() {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Returns the current capacity (table size) of the hash table array. The initial capacity must be
   * a positive integer, 1 or greater and is specified in the constructor. When the load factor >=
   * threshold, the capacity must increase to: 2 * capacity + 1.
   * 
   * @return capacity
   */
  @Override
  public int getCapacity() {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Prints the HashTable to standard output.
   */
  public void printHashTable() {
    System.out.println("HASH TABLE:");
    // print out an array of BucketList<KeyValuePair>
    for (int i = 0; i < table.length; i++) {
      System.out.print(i + ": ");
      HashNode<K, V> currNode = table[i];
      while (currNode != null) {
        System.out.print("(" + currNode.hashNodeToString() + "), ");
        currNode = currNode.next;
      }
      System.out.println();
    }
    System.out.println(
        "numKeys: " + numKeys + "\tload factor: " + 1.0 * this.numKeys / this.table.length);
    System.out.println("--------------------------------------------------");
  }
}
