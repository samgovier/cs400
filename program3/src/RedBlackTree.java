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

    // inner classes don't need getters and setters when used in encompassing class

  }

  private RBNode<K, V> root;

  // RBTree instance variables

  // RBTree constructor

  // RBTree methods

  public RedBlackTree() {
    root = null;
  }


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


  // makes this nodes right child into its parent
  private RBNode<K, V> leftRotate(RBNode<K, V> current) {

    RBNode<K, V> temp = current.right;
    current.right = temp.left;
    temp.left = current;
    return current;
  }


  private RBNode<K, V> rebalanceLeft(RBNode<K, V> current) {
    // TODO Auto-generated method stub
    return current;
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

  public static void main(String[] args) {
    RedBlackTree<Integer, String> months = new RedBlackTree<Integer, String>();

    months.insert(5, "MAY");
    months.insert(8, "AUGUST");
    months.insert(10, "OCTOBER");
    months.printSideways(); // remove when submitting to ZyBooks

  }

}
