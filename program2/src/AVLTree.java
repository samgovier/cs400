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
    //TODO implement (?) private int height;
    private TreeNode<K, V> left, right;

    private TreeNode(K key, V value) {
      this.key = key;
      this.value = value;
      this.left = null;
      this.right = null;
      //TODO implement (?) this.height = 1;
    }
    
    /**
     * this recursive method which finds the height from this node as the root
     * 
     * @return the height of the tree as an integer If the node has no children, return 1
     */
    private int getHeight() {
      // if there are no children, return 1, as defined
      if (this.left == null && this.right == null) {
        return 1;
      }

      // otherwise, if there is no left child, return 1 + the right height
      if (this.left == null) {
        return 1 + this.right.getHeight();
      }

      // otherwise, if there is no right child, return 1 + the left height
      if (this.right == null) {
        return 1 + this.left.getHeight();
      }

      // if there are 2 children, return the max height between the two
      return 1 + Math.max(this.left.getHeight(), this.right.getHeight());
    }
    
    /**
     * this method finds the balance factor of the node. negative means left is bigger, positive
     * means right is bigger. 0 is perfect balance.
     * 
     * @return an integer representing the balance of this node per its children
     */
    private int getBalance() {
      if (this.left == null && this.right == null) {
        return 0;
      } else if (this.left == null) {
        return right.getHeight();
      } else if (this.right == null) {
        return left.getHeight();
      }

      return left.getHeight() - right.getHeight();
    }
  }

  /**
   * Checks for an empty tree.
   * 
   * @return true if tree contains 0 items
   */
  public boolean isEmpty() {
    return null == root;
  }

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
  public String preOrderTraversal() {
    return preOrderTraversal(root);
  }

  private String preOrderTraversal(TreeNode<K, V> current) {
    if (current == null) {
      return "";
    }

    // otherwise
    return current.key.toString() + " " + preOrderTraversal(current.left)
        + preOrderTraversal(current.left);
  }

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
