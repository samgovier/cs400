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
 * hash index is calculated as the absolute value of the key hashcode mod table length. The table is
 * initialized with a capacity and load threshold. Has Get, Insert, Remove and getProperty methods
 * based on HashTableADT.
 * 
 * @param <K> the key is the sorting key of the hash table
 * @param <V> the data value associated with a given key
 */
@SuppressWarnings("unchecked")
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

  /**
   * Hash node inner class storing a key, value, and pointer to the next node in the chain.
   */
  @SuppressWarnings("hiding")
  private class HashNode<K, V> {

    // key is the key value of the node
    private K key;

    // value is the contained value of the node
    private V value;

    // next is the next node in the linked list chain
    private HashNode<K, V> next;

    // this constructor sets key, value, and next node
    private HashNode(K key, V value, HashNode<K, V> next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }

    // hashNodeToString prints the node for the hash table print
    private String hashNodeToString() {
      return key + " : " + value;
    }
  }

  // loadFactorThreshold is the point at which the table should be resized for more elements
  private double loadFactorThreshold;

  // numKeys is the amount of items in the hash table
  private int numKeys;

  // table is the backing array used to represent the hash table
  private HashNode<K, V>[] table;

  /**
   * Default no-arg constructor, sets initial capacity to 5 and threshold to .75
   */
  public HashTable() {
    this(5, 0.75);
  }

  /**
   * Constructor with initial capacity and load factor threshold provided.
   * 
   * @param initialCapacity     is the initial size of the backing array
   * @param loadFactorThreshold is the point when the table should be resized
   */
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    this.loadFactorThreshold = loadFactorThreshold;
    this.numKeys = 0;
    // initialize LinkedList buckets
    table = (HashNode<K, V>[]) new HashNode[initialCapacity];
  }

  /**
   * Adds the key/value pair to the hash table and increases the number of keys. Uses the hashIndex
   * and resizes as per the HashTableADT spec.
   * 
   * @param key   is the key to be inserted
   * @param value is the corresponding value to be inserted
   * @throws DuplicateKeyException if key is already in the data structure
   * @throws NullKeyException      if key is null
   */
  @Override
  public void insert(K key, V value) throws DuplicateKeyException, NullKeyException {

    // if the key is null, throw NullKeyException
    if (null == key) {
      throw new NullKeyException("the passed key is null.");
    }

    // the hashIndex calculation
    int hashIndex = Math.abs(key.hashCode()) % this.table.length;

    // if the value here isn't null, check what exists
    if (null != table[hashIndex]) {

      // current is the existing node
      HashNode<K, V> current = table[hashIndex];

      // if the keys are equal, throw DuplicateKeyException
      if (current.key.equals(key)) {
        throw new DuplicateKeyException("Key already exists in the hash table.");
      }

      // go through the linked list. if a key is equal, throw duplicate key exception
      while (current.next != null) {
        if (current.next.key.equals(key)) {
          throw new DuplicateKeyException("Key already exists in the hash table.");
        }
        current = current.next;
      }

      // since next is now null, set this next item to the new node
      current.next = new HashNode<K, V>(key, value, null);

      // increment numKeys and resize if needed
      numKeys++;
      testResize();
    }

    // else if the value at this hash index is null, insert, increment numKeys and resize if needed
    else {
      table[hashIndex] = new HashNode<K, V>(key, value, null);
      numKeys++;
      testResize();
    }

  }

  /**
   * This method resizes the hash table when the load factor exceeds the threshold.
   * 
   * @throws DuplicateKeyException from insert is the resized key is already in the data structure
   * @throws NullKeyException      from insert if the resized key is null
   */
  private void testResize() throws DuplicateKeyException, NullKeyException {

    // if we've passed the load factor threshold, resize
    if (this.getLoadFactor() >= loadFactorThreshold) {

      // move the now old table to the side for re-insertion
      HashNode<K, V>[] oldTable = table;

      // initialize table with the new larger length
      table = (HashNode<K, V>[]) new HashNode[2 * table.length + 1];

      // set numKeys to zero to accommodate re-insertion
      numKeys = 0;

      // for every node in oldTable, re-insert
      for (HashNode<K, V> arrayNode : oldTable) {

        // the node isn't null, insert
        if (null != arrayNode) {
          insert(arrayNode.key, arrayNode.value);

          // go through the linked list at this node and insert any further nodes
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
   * @param key is the key to be removed
   * @return true is key is found, false if key is not found
   * @throws NullKeyException if key is null
   */
  @Override
  public boolean remove(K key) throws NullKeyException {

    // if the key is null, throw NullKeyException
    if (null == key) {
      throw new NullKeyException("the passed key is null.");
    }

    // the hashIndex calculation
    int hashIndex = Math.abs(key.hashCode()) % this.table.length;

    // current is the table index of hashIndex
    HashNode<K, V> current = table[hashIndex];

    // if the index is null, return false
    if (null == current) {
      return false;
    }

    // if the current node is the matching key, set the current index to the next node,
    // decrement keys and return true
    if (current.key.equals(key)) {
      table[hashIndex] = current.next;
      numKeys--;
      return true;
    }

    // still not found: go through the linked list of this node
    while (current.next != null) {

      // if the current node is the matching key, set the current next to the next node,
      // decrement keys and return true
      if (current.next.key.equals(key)) {
        current.next = current.next.next;
        numKeys--;
        return true;
      }

      // set current to next to continue loop
      current = current.next;
    }

    // if we've made it here, the key wasn't found: return false
    return false;
  }

  /**
   * Returns the value associated with the specified key.
   * 
   * @param key is the key to be got
   * @return value is the corresponding value to key
   * @throws KeyNotFoundException if key is not found
   * @throws NullKeyException     if key is null
   */
  @Override
  public V get(K key) throws KeyNotFoundException, NullKeyException {

    // if the key is null, throw NullKeyException
    if (null == key) {
      throw new NullKeyException("the passed key is null.");
    }

    // the hashIndex calculation
    int hashIndex = Math.abs(key.hashCode()) % this.table.length;

    // current is the table index of hashIndex
    HashNode<K, V> current = table[hashIndex];

    // while current isn't null, if the key matches, return the value, otherwise, continue down list
    while (current != null) {

      if (current.key.equals(key)) {
        return current.value;
      }

      current = current.next;
    }

    // if we've made it here, the key wasn't found: throw KeyNotFoundException
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
   * Returns the current load factor for this hash table.
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
