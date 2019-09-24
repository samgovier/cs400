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

  /**
   * Inner class representing a node of the AVL tree.
   * 
   * @param <K> is the key data type
   * @param <V> is the value data type
   */
  private class TreeNode<K, V> {
    private K key;
    private V value;
    private TreeNode<K, V> left, right;

    private TreeNode(K key, V value) {
      this.key = key;
      this.value = value;
      this.left = null;
      this.right = null;
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
     * @return an integer representing the balance of this node
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

  // root is the top node of the tree
  private TreeNode<K, V> root;

  /**
   * This constructor creates the tree with a null root
   */
  public AVLTree() {
    root = null;
  }

  /**
   * Checks for an empty tree.
   * 
   * @return true if root is empty
   */
  @Override
  public boolean isEmpty() {
    return null == root;
  }


  /**
   * AVLTree rotate left.
   * 
   * @param root an imbalance node
   * @return TreeNode<K, V> the node for which balance has been modified
   */
  private TreeNode<K, V> rotateLeft(TreeNode<K, V> root) {

    TreeNode<K, V> temp = root.right.left;
    root.right.left = root;
    root = root.right;
    root.left.right = temp;
    return root;

  }

  /**
   * AVLTree rotate right.
   * 
   * @param root an imbalance node
   * @return the node for which balance has been modified
   */
  private TreeNode<K, V> rotateRight(TreeNode<K, V> root) {

    TreeNode<K, V> temp = root.left.right;
    root.left.right = root;
    root = root.left;
    root.right.left = temp;
    return root;
  }

  /**
   * 
   * @param root
   * @return
   */
  private TreeNode<K, V> rebalance(TreeNode<K, V> root) {
    if (root.getBalance() >= 2) {
      if (root.left.getBalance() == 1) {
        root = rotateRight(root);
      }
      else if (root.left.getBalance() == -1) {
        root.left = rotateLeft(root.left);
        root = rotateRight(root);
      }
    }
    else if (root.getBalance() <= -2) {
      if (root.right.getBalance() == -1) {
        root = rotateLeft(root);
      }
      else if (root.right.getBalance() == 1) {
        root.right = rotateRight(root.right);
        root = rotateLeft(root);
      }
    }

    // the root is re-balanced: return
    return root;
  }

  /**
   * Adds a key, value pair to the tree.
   * 
   * @param key
   * @param value
   * @throws DuplicateKeyException if the key has already been inserted into the tree
   * @throws IllegalKeyException   if the key is null
   */
  @Override
  public void insert(K key, V value) throws DuplicateKeyException, IllegalKeyException {
    if (null == key) {
      throw new IllegalKeyException("key is null");
    }

    root = insert(root, key, value);
  }

  /**
   * 
   * @param current
   * @param key
   * @param value
   * @return
   * @throws DuplicateKeyException
   */
  private TreeNode<K, V> insert(TreeNode<K, V> current, K key, V value)
      throws DuplicateKeyException {
    if (current == null) {
      // set the current null location to the inserted element
      current = new TreeNode<K, V>(key, value);
    }

    else if (key.compareTo(current.key) == 0) {
      throw new DuplicateKeyException("The key has already been inserted into the tree");
    }
    // if the element is smaller than current, go left
    else if (key.compareTo(current.key) < 0) {
      current.left = insert(current.left, key, value);
    }

    // if the element is greater than current, go right
    else if (key.compareTo(current.key) > 0) {
      current.right = insert(current.right, key, value);
    }

    if (current.getHeight() > 2) {
      current = rebalance(current);
    }
    return current;


  }


  /**
   * Removes a key from the AVL tree. Returns nothing if the key is not found.
   * 
   * @param key
   * @throws IllegalKeyException if attempt to delete null
   */
  @Override
  public void delete(K key) throws IllegalKeyException {
    if (null == key) {
      throw new IllegalKeyException("key is null");
    }

    root = delete(root, key);
  }

  /**
   * 
   * @param current
   * @param key
   * @return
   */
  private TreeNode<K, V> delete(TreeNode<K, V> current, K key) {

    // if we're at a null node, the element doesn't exist, return nothing
    if (current == null) {
      return null;
    }
    // if the element is smaller than current, go left
    if (key.compareTo(current.key) < 0) {
      current.left = delete(current.left, key);
      if (current.getHeight() > 2) {
        current = rebalance(current);
      }
      return current;
    }
    // if the element is greater than current, go right
    if (key.compareTo(current.key) > 0) {
      current.right = delete(current.right, key);
      if (current.getHeight() > 2) {
        current = rebalance(current);
      }
      return current;
    }

    // if we're here, we've found the right element. Begin removal

    // if there are no children, set this node to null
    if (current.left == null && current.right == null) {
      return null;
    }

    // if there is only one child, decrement size and bring child up
    if (current.left == null) {
      return current.right;
    }
    if (current.right == null) {
      return current.left;
    }

    // in the case where the immediate left node is the predecessor, set the new node
    if (current.left.right == null) {
      // point the predecessor at the current right node
      current.left.right = current.right;

      // set the current node to the predecessor
      current = current.left;

      return current;
    }

    // find the inOrderPredecessor parent node
    // the right of the parent must be the predecessor
    TreeNode<K, V> inOrderPredecessorParent = findInOrderPredecessorParent(current.left);

    // set the subtree root to the data of the predecessor
    current.key = inOrderPredecessorParent.right.key;
    current.value = inOrderPredecessorParent.right.value;

    // point the parent at any remaining data from the predecessor
    inOrderPredecessorParent.right = inOrderPredecessorParent.right.left;

    return current;
  }

  /**
   * 
   * @param current
   * @return
   */
  private TreeNode<K, V> findInOrderPredecessorParent(TreeNode<K, V> current) {
    // if the right child of the right child isn't null, this right child isn't the predecessor
    if (current.right.right != null) {
      findInOrderPredecessorParent(current.right);
    }

    // if the right child of the right child IS null, the right child is predecessor, return parent
    return current;
  }

  /**
   * Get a value in the tree given the key. If the value isn't in the tree, return null.
   * 
   * @param key
   * @return value the object associated with this key
   * @throws IllegalArgumentException if the key is null
   */
  @Override
  public V get(K key) throws IllegalKeyException {
    if (null == key) {
      throw new IllegalKeyException("key is null");
    }

    return get(root, key);
  }

  private V get(TreeNode<K, V> current, K key) {

    // if the current element is null, the tree has ended, return false
    if (current == null) {
      return null;
    }

    // if the element is smaller than current, go left
    if (key.compareTo(current.key) < 0) {
      return get(current.left, key);
    }

    // if the element is greater than current, go right
    if (key.compareTo(current.key) > 0) {
      return get(current.right, key);
    }

    // if current is not null, more, or less than the passed key, it's the same.
    return current.value;
  }

  /**
   * Returns the AVL tree in pre-order traversal. The string that is returned will have each node's
   * key separated by a whitespace. Example: "MKE ATL MSN LAX".
   */
  @Override
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
   * Print a tree sideways to show structure. This code is completed for you.
   */
  @Override
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
