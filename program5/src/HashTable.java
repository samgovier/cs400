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

/**
 * Hash table data structure. This hash table is an array of linked lists with key/value pairs. The
 * hash index is calculated as the absolute value of the key hashcode mod table length.
 * 
 * @param <K> the key must not be null and must be Comparable
 * @param <V> the data value associated with a given key
 * 
 * @param <K> key
 * @param <V> value
 */
@SuppressWarnings("unchecked")
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
    // initialize LinkedList buckets
    table = (HashNode<K, V>[]) new HashNode[initialCapacity];
  }

  /**
   * Adds the key/value pair to the hash table and increases the number of keys. The table is
   * resized when load factor >= threshold Capacity must increase to: 2 * capacity + 1. Once
   * increased, capacity never decreases.
   * 
   * @param key
   * @param value
   * @throws DuplicateKeyException if key is already in the data structure
   * @throws NullKeyException      if key is null
   */
  @Override
  public void insert(K key, V value) throws DuplicateKeyException, NullKeyException {

    if (null == key) {
      throw new NullKeyException("the passed key is null.");
    }

    // the hashIndex calculation
    int hashIndex = Math.abs(key.hashCode()) % this.table.length;

    if (null != table[hashIndex]) {
      HashNode<K, V> current = table[hashIndex];
      if (current.key.equals(key)) {
        throw new DuplicateKeyException("Key already exists in the hash table.");
      }
      while (current.next != null) {
        if (current.next.key.equals(key)) {
          throw new DuplicateKeyException("Key already exists in the hash table.");
        }
        current = current.next;
      }
      current.next = new HashNode(key, value, null);
      numKeys++;
      testResize();
    } else {
      table[hashIndex] = new HashNode(key, value, null);
      numKeys++;
      testResize();
    }

  }

  /**
   * This method resizes the hash table when the load factor exceeds the threshold
   * 
   * @throws DuplicateKeyException from insert is the resized key is already in the data structure
   * @throws NullKeyException      from insert if the resized key is null
   */
  private void testResize() throws DuplicateKeyException, NullKeyException {
    if (this.getLoadFactor() >= loadFactorThreshold) {
      HashNode<K, V>[] oldTable = table;
      table = (HashNode<K, V>[]) new HashNode[2 * table.length + 1];
      numKeys = 0;

      for (HashNode<K, V> arrayNode : oldTable) {
        if (null != arrayNode) {

          insert(arrayNode.key, arrayNode.value);
          HashNode<K, V> current = arrayNode;
          while (null != current.next) {
            current = current.next;
            insert(current.key, current.value);
          }
        }
      }
    }
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

    if (null == key) {
      throw new NullKeyException("the passed key is null.");
    }

    // the hashIndex calculation
    int hashIndex = Math.abs(key.hashCode()) % this.table.length;

    HashNode<K, V> current = table[hashIndex];

    if (null == current) {
      return false;
    }

    if (current.key.equals(key)) {
      table[hashIndex] = current.next;
      numKeys--;
      return true;
    }

    while (current.next != null) {

      if (current.next.key.equals(key)) {
        current.next = current.next.next;
        numKeys--;
        return true;
      }

      current = current.next;
    }

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

    if (null == key) {
      throw new NullKeyException("the passed key is null.");
    }

    // the hashIndex calculation
    int hashIndex = Math.abs(key.hashCode()) % this.table.length;

    HashNode<K, V> current = table[hashIndex];

    while (current != null) {

      if (current.key.equals(key)) {
        return current.value;
      }

      current = current.next;
    }

    throw new KeyNotFoundException("the passed key was not found.");
  }

  /**
   * Returns the number of keys in the hash table.
   * 
   * @return number of keys
   */
  @Override
  public int numKeys() {
    return numKeys;
  }

  /**
   * Returns the current load factor for this hash table. load factor = number of items / current
   * table size
   * 
   * @return load factor (as a double)
   */
  @Override
  public double getLoadFactor() {
    return (double) numKeys / (double) table.length;
  }

  /**
   * Returns the current capacity (table size) of the hash table array.
   * 
   * @return capacity
   */
  @Override
  public int getCapacity() {
    return table.length;
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
