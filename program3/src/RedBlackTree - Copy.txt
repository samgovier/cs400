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
 * 
 * @author Sam
 *
 * @param <K>
 * @param <V>
 */
public class RedBlackTree<K extends Comparable<K>, V> implements SearchTreeADT<K, V> {
  // Note: your RedBlackTree implementation must be consistent with the class notes and
  // specifications.
  // When in doubt, check your results with
  // https://www.cs.usfca.edu/~galles/visualization/RedBlack.html

  // private inner class that stores Key, Value pairs
  private class RBNode<K extends Comparable<K>, V> {
    private K key;
    private V value;
    private RBNode<K, V> left; // left child
    private RBNode<K, V> right; // right child
    private boolean isRed; // red/black property


    public RBNode(K key, V value) {
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
  
  private RBNode<K, V> rebalanceRight(RBNode<K, V> current) {

    if (!current.right.isRed) {
      return current;
    }

    // case 2
    if ((current.left == null) || (!current.left.isRed)) {
      if ((current.right.right != null) && (current.right.right.isRed)) {
        current.right.isRed = false;
        current.isRed = true;
        current = leftRotate(current);
      }
      return current;
    }

    // case 1a
    if ((current.left != null) && current.left.isRed) {
      if (current.right.right != null && current.right.right.isRed) {
        current.right.isRed = false;
        current.left.isRed = false;
        current.isRed = true;
      }
      return current;
    }

    return current;
  }

  private RBNode<K, V> rebalanceLeft(RBNode<K, V> current) {
    // TODO Auto-generated method stub
    return current;
  }

  // makes this nodes right child into its parent
  private RBNode<K, V> leftRotate(RBNode<K, V> current) {

    RBNode<K, V> temp = current.right;
    current.right = temp.left;
    temp.left = current;
    return current;
  }

  // makes this nodes left child into its parent
  private RBNode<K, V> rightRotate(RBNode<K, V> current) {
    
    RBNode<K, V> temp = current.left;
    current.left = temp.right;
    temp.right = current;
    return current;
  }

  
  @Override
  public void insert(K key, V value) {
    root = insert(root, key, value);
    root.isRed = false;
  }

  private RBNode<K, V> insert(RBNode<K, V> current, K key, V value) {
    if (current == null) { // base case
      current = new RBNode(key, value); // isRed = true
    } else if (key.compareTo(current.key) < 0) { // key is less, go left
      current.left = insert(current.left, key, value);
      current = rebalanceLeft(current);
    } else if (key.compareTo(current.key) > 0) { // key is more, go right
      current.right = insert(current.right, key, value);
      current = rebalanceRight(current);
    } else {
      // equals ..depends on your implementation
    }
    return current; // make the change back up the recursive call
  }
  
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
   * node's key separated by a whitespace.
   * 
   * @return a string representing preOrder traversal
   */
  @Override
  public String preOrderTraversal() {
    return preOrderTraversal(root);
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
  private void printSideways(RBNode current, String indent) {
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
