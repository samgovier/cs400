//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: AVL Trees
// Files: TreeADT.java, AVLTree.java, Airport.java, AirportDataReader.java,
// DuplicateKeyException.java, IllegalKeyException.java
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

// remember that you are not allowed to add any public methods or fields
// but you can add any private methods or fields

public class AVLTree<K extends Comparable<K>, V> implements TreeADT<K, V> {
  public TreeNode<K, V> root = null;

  /**
   * Inner class representing a node of the AVL tree.
   * 
   * @param <K>
   * @param <V>
   */
  private class TreeNode<K, V> {
    private K key;
    private V value;
    private int height;
    private TreeNode<K, V> left, right;

    // TODO: constructor
  }

  /**
   * Checks for an empty tree.
   * 
   * @return true if tree contains 0 items
   */
  public boolean isEmpty();

  /**
   * Adds a key, value pair to the tree.
   * 
   * @param key
   * @param value
   * @throws DuplicateKeyException if the key has already been inserted into the tree
   * @throws IllegalKeyException   if the key is null
   */
  public void insert(K key, V value) throws DuplicateKeyException, IllegalKeyException;

  /**
   * Removes a key from the AVL tree. Returns nothing if the key is not found.
   * 
   * @param key
   * @throws IllegalKeyException if attempt to delete null
   */
  public void delete(K key) throws IllegalKeyException;

  /**
   * Get a value in the tree given the key.
   * 
   * @param key
   * @return value the object associated with this key
   * @throws IllegalArgumentException if the key is null
   */
  public V get(K key) throws IllegalKeyException;

  /**
   * Returns the AVL tree in pre-order traversal. The string that is returned will have each node's
   * key separated by a whitespace. Example: "MKE ATL MSN LAX".
   */
  public String preOrderTraversal();

  /**
   * Prints AVL tree sideways to show structure. This method may help you debug your implementation.
   */
  public void printSideways();

  /**
   * AVLTree rotate left.
   * 
   * @param root an imbalance node
   * @return TreeNode<K, V> the node for which balance has been modified
   */
  private TreeNode<K, V> rotateLeft(TreeNode<K, V> root) {
    // TODO: implement
    return null;
  }

  /**
   * AVLTree rotate right.
   * 
   * @param root an imbalance node
   * @return the node for which balance has been modified
   */
  private TreeNode<K, V> rotateRight(TreeNode<K, V> root) {
    // TODO: implement
    return root;
  }


  /**
   * Print a tree sideways to show structure. This code is completed for you.
   */
  public void printSideways() {
    System.out.println("------------------------------------------");
    recursivePrintSideways(root, "");
    System.out.println("------------------------------------------");
  }

  /**
   * Print nodes in a tree. This code is completed for you. You are allowed to modify this code to
   * include balance factors or heights
   * 
   * @param current
   * @param indent
   */
  private void recursivePrintSideways(TreeNode<K, V> current, String indent) {
    if (current != null) {
      recursivePrintSideways(current.right, indent + "    ");
      System.out.println(indent + current.key);
      recursivePrintSideways(current.left, indent + "    ");
    }
  }
}
