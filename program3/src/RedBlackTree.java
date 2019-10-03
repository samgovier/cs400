//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Red Black Tree
// Files: SearchTreeADT.java, RedBlackTree.java, IllegalKeyException.java, WordCountRunner.java
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
 * RedBlackTree class contains the object definition for a Red Black Binary Search Tree. The tree is
 * initialized empty, and each node must be inserted. PreOrderTraversal and PrintSideways return a
 * string representation of the tree. Nodes can also be looked up. GetSize and GetHeight get
 * statistics about the tree.
 *
 * @param <K> is the key element type of the tree
 * @param <V> is the value element type of the tree
 */
public class RedBlackTree<K extends Comparable<K>, V> implements SearchTreeADT<K, V> {

  /**
   * Inner class representing a node of the RedBlack tree. getHeight returns the height from this
   * node as the root.
   *
   * @param <K> is the key data type
   * @param <V> is the value data type
   */
  private class RBNode<K extends Comparable<K>, V> {

    // key is the key value of the node
    private K key;

    // value is the contained value of the node
    private V value;

    // left and right children of the node
    private RBNode<K, V> left;
    private RBNode<K, V> right;

    // isRed marks whether the node is red or black
    private boolean isRed;

    // this constructor sets key and value, left and right to null, and the color to red by default
    private RBNode(K key, V value) {
      this.key = key;
      this.value = value;
      this.left = null;
      this.right = null;
      this.isRed = true;
    }

    /**
     * this recursive method finds the height from this node as the root
     * 
     * @return the height of the tree as an integer. If the node has no children, return 1
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

  }

  // root is the top node of the tree
  private RBNode<K, V> root;

  // size is the amount of nodes
  private int size;



  /**
   * This constructor creates the tree with a null root and size 0
   */
  public RedBlackTree() {
    root = null;
    size = 0;
  }

  // makes this nodes right child into its parent
  private RBNode<K, V> leftRotate(RBNode<K, V> current) {

    // if we have a value, store it temporarily. If there is nothing this will simply be null
    RBNode<K, V> temp = current.right.left;

    // move current to the left child of the right child
    current.right.left = current;

    // set the new current as the right child
    current = current.right;

    // if there is any data, store it in the now empty current.left.right
    current.left.right = temp;

    // return the modified current
    return current;
  }

  // makes this nodes left child into its parent
  private RBNode<K, V> rightRotate(RBNode<K, V> current) {

    // if we have a value, store it temporarily. If there is nothing this will simply be null
    RBNode<K, V> temp = current.left.right;

    // move current to the right child of the left child
    current.left.right = current;

    // set the new current as the right child
    current = current.left;

    // if there is any data, store it in the now empty current.right.left
    current.right.left = temp;

    // return the modified current
    return current;
  }

  /**
   * based on RedBlack tree rules, re-balance the right side of the passed root
   * 
   * @param current is the root of the subtree to be rebalanced
   * @return the rebalanced root
   */
  private RBNode<K, V> rebalanceRight(RBNode<K, V> current) {

    // if the height of this node is greater than 2 nodes, check rules, otherwise skip and return
    if (current.getHeight() > 2) {

      // if the right node is black, we can skip and return
      if (!current.right.isRed) {
        return current;
      }

      // if we're here, there may be a red-red violation, check

      // if the sibling is null or black, continue
      if ((current.left == null) || (!current.left.isRed)) {

        // if the new child is the right-right one and it's red, continue
        if ((current.right.right != null) && (current.right.right.isRed)) {

          // set the median to black, and the current to red
          current.right.isRed = false;
          current.isRed = true;

          // rotate so the black node is at the top
          current = leftRotate(current);
        }

        // if the new child is the right-left one and it's red, continue
        else if ((current.right.left != null) && (current.right.left.isRed)) {

          // set the median to black, and the current to red
          current.right.left.isRed = false;
          current.isRed = true;

          // rotate so the black node is at the top
          current.right = rightRotate(current.right);
          current = leftRotate(current);
        }

        // return the modified node
        return current;
      }

      // if the sibling is red, continue
      if ((current.left != null) && (current.left.isRed)) {

        // if we have red-red violation on either child, fix the coloring
        if ((current.right.right != null && current.right.right.isRed)
            || (current.right.left != null && current.right.left.isRed)) {
          current.right.isRed = false;
          current.left.isRed = false;
          current.isRed = true;
        }

        // return the modified node
        return current;
      }

    }

    // return the un-modified node
    return current;
  }

  /**
   * based on RedBlack tree rules, re-balance the left side of the passed root
   * 
   * @param current is the root of the subtree to be rebalanced
   * @return the rebalanced root
   */
  private RBNode<K, V> rebalanceLeft(RBNode<K, V> current) {

    // if the height of this node is greater than 2 nodes, check rules, otherwise skip and return
    if (current.getHeight() > 2) {

      // if the left node is black, we can skip and return
      if (!current.left.isRed) {
        return current;
      }

      // if we're here, there may be red-red violation, check

      // if the sibling is null or black, continue
      if ((current.right == null) || (!current.right.isRed)) {

        // if the new child is the left-left one and it's red, continue
        if ((current.left.left != null) && (current.left.left.isRed)) {

          // set the median to black, and the current to red
          current.left.isRed = false;
          current.isRed = true;

          // rotate so the black node is at the top
          current = rightRotate(current);
        }

        // if the new child is the left-right one and it's red, continue
        else if ((current.left.right != null) && (current.left.right.isRed)) {

          // set the median to black, and the current to red
          current.left.right.isRed = false;
          current.isRed = true;

          // rotate so the black node is at the top
          current.left = leftRotate(current.left);
          current = rightRotate(current);
        }

        // return the modified node
        return current;
      }

      // if the sibling is red, continue
      if ((current.right != null) && (current.right.isRed)) {

        // if we have red-red violation on either child, fix the coloring
        if ((current.left.left != null && current.left.left.isRed)
            || (current.left.right != null && current.left.right.isRed)) {
          current.left.isRed = false;
          current.right.isRed = false;
          current.isRed = true;
        }

        // return the modified node
        return current;
      }
    }

    // return the un-modified node
    return current;
  }

  /**
   * calls a private recursive helper method and stores the result of that method as the new root
   * 
   * @param key   is the lookup key to be inserted
   * @param value is the element to be inserted
   * @throws IllegalKeyException if the key is null
   */
  @Override
  public void insert(K key, V value) throws IllegalKeyException {

    // if the key is null, throw illegal key exception
    if (null == key) {
      throw new IllegalKeyException();
    }

    // else start recursion
    root = insert(root, key, value);

    // reset root if it's shifted to red
    root.isRed = false;
  }

  /**
   * Private recursive helper method adds a node maintaining BST and Red Black rules
   * 
   * @param current the root of this subtree
   * @param key     the lookup key to be added
   * @param value   the element to be added
   * @return the updated reference to current
   */
  private RBNode<K, V> insert(RBNode<K, V> current, K key, V value) {
    if (current == null) {
      // set the current null location to the inserted element, and increase size
      current = new RBNode<K, V>(key, value);
      size++;
    }

    // if the element is smaller than current, go left
    else if (key.compareTo(current.key) < 0) {
      current.left = insert(current.left, key, value);

      // before returning, rebalance left
      current = rebalanceLeft(current);
    }

    // if the element is greater than current, go right
    else if (key.compareTo(current.key) > 0) {
      current.right = insert(current.right, key, value);

      // before returning, rebalance right
      current = rebalanceRight(current);
    }

    // if we're here, the keys match: just update the value
    else {
      current.value = value;
    }

    // return the current node when it's set
    return current;
  }

  /**
   * Calls a private recursive method to search the tree for an element
   * 
   * @param key the key value to be got
   * @return value the object associated with this key
   * @throws IllegalKeyException if the key is null
   */
  @Override
  public V getValue(K key) throws IllegalKeyException {

    // if the key is null, throw illegal key exception
    if (null == key) {
      throw new IllegalKeyException("key is null");
    }

    return getValue(root, key);
  }

  /**
   * Get a value in the tree given the key. If the value isn't in the tree, return null
   * 
   * @param current the root of this subtree
   * @param key     the key value to be got
   * @return value the object associated with this key
   */
  private V getValue(RBNode<K, V> current, K key) {

    // if the current element is null, the tree has ended, return null
    if (current == null) {
      return null;
    }

    // if the element is smaller than current, go left
    if (key.compareTo(current.key) < 0) {
      return getValue(current.left, key);
    }

    // if the element is greater than current, go right
    if (key.compareTo(current.key) > 0) {
      return getValue(current.right, key);
    }

    // if current is not null, more, or less than the passed key, it's the same.
    return current.value;
  }

  /**
   * this method returns whether the tree is empty or not
   * 
   * @return true if the tree has no elements, false otherwise
   */
  @Override
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * this method returns the size of the tree
   * 
   * @return the number of nodes stored in the tree
   */
  @Override
  public int getSize() {
    return size;
  }

  /**
   * method to return the height of the tree
   * 
   * @return the height of the tree
   */
  @Override
  public int getHeight() {
    // if the root is null the tree is empty
    if (root == null) {
      return 0;
    }

    return root.getHeight();
  }

  /**
   * Returns the Red Black tree in pre-order traversal. The string that is returned will have each
   * node's key separated by a space.
   * 
   * @return a string representing preOrder traversal
   */
  @Override
  public String preOrderTraversal() {
    return preOrderTraversal(root).trim();
  }

  /**
   * recursive method for preOrder traversal
   * 
   * @param current the root of the current subtree
   * @return a string representing preOrder traversal
   */
  private String preOrderTraversal(RBNode<K, V> current) {
    // if the current node is null, return an empty string
    if (current == null) {
      return "";
    }

    // otherwise, return this data along with the data of the children
    return current.key.toString() + " " + preOrderTraversal(current.left)
        + preOrderTraversal(current.right);
  }


  /*
   * prints a tree sideways on your screen source: Building Java Programs, 4th Ed., by Reges and
   * Stepp, Ch. 17
   */
  public void printSideways() {
    System.out.println("------------------------------------------");
    printSideways(root, "");
    System.out.println("------------------------------------------");
  }

  // private recursive helper method for printSideways above
  private void printSideways(RBNode<K, V> current, String indent) {
    if (current != null) {
      printSideways(current.right, indent + "    ");
      if (current.isRed) {
        System.out.println(indent + "[" + current.key + "]");
      } else {
        System.out.println(indent + "(" + current.key + ")");
      }
      printSideways(current.left, indent + "    ");
    }
  }
}
