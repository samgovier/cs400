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

/**
 * AVLTree class contains the object definition for an AVL Binary Search Tree. The tree is
 * initialized empty, and each node must be inserted. PreOrderTraversal and PrintSidways return a
 * string representation of the tree. Nodes can be removed and looked up.
 *
 * @param <K> is the key element type of the tree
 * @param <V> is the value element type of the tree
 */
public class AVLTree<K extends Comparable<K>, V> implements TreeADT<K, V> {

  /**
   * Inner class representing a node of the AVL tree. getHeight returns the height from this node as
   * the root: getBalance returns the balance based on the two subtrees
   * 
   * @param <K> is the key data type
   * @param <V> is the value data type
   */
  private class TreeNode<K, V> {

    // key is the key value of the node
    private K key;

    // value is the contained value of the node
    private V value;

    // left and right are the left and right children of the node
    private TreeNode<K, V> left, right;

    // this constructor sets the key, value, and left and right to null
    private TreeNode(K key, V value) {
      this.key = key;
      this.value = value;
      this.left = null;
      this.right = null;
    }

    /**
     * this recursive method which finds the height from this node as the root
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

    /**
     * this method finds the balance factor of the node. negative means left is bigger, positive
     * means right is bigger. 0 is perfect balance.
     * 
     * @return an integer representing the balance of this node
     */
    private int getBalance() {
      // if there are no children, perfect balance, return 0
      if (this.left == null && this.right == null) {
        return 0;
      }

      // if left is null, return 0 - right height
      else if (this.left == null) {
        return -(right.getHeight());
      }

      // if right is null, return left height - 0
      else if (this.right == null) {
        return left.getHeight();
      }

      // if we're here, both exist, so return left - right
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
   * @return the node for which balance has been modified
   */
  private TreeNode<K, V> rotateLeft(TreeNode<K, V> root) {

    // if we have a value, store it temporarily. If there is nothing this will simply be null
    TreeNode<K, V> temp = root.right.left;

    // move the root to the left child of the right child
    root.right.left = root;

    // set the new root as the right child
    root = root.right;

    // if there is any data, store it in the now empty root.left.right
    root.left.right = temp;

    // return the modified root
    return root;

  }

  /**
   * AVLTree rotate right.
   * 
   * @param root an imbalance node
   * @return the node for which balance has been modified
   */
  private TreeNode<K, V> rotateRight(TreeNode<K, V> root) {

    // if we have a value, store it temporarily. If there is nothing this will simply be null
    TreeNode<K, V> temp = root.left.right;

    // move the root to the right child of the left child
    root.left.right = root;

    // set the new root as the right child
    root = root.left;

    // if there is any data, store it in the now empty root.right.left
    root.right.left = temp;

    // return the modified root
    return root;
  }

  /**
   * based on the balance factors, rebalance the subtree of the passed root
   * 
   * @param root is the root of the subtree to be rebalanced
   * @return the rebalanced root
   */
  private TreeNode<K, V> rebalance(TreeNode<K, V> root) {

    // if the root is left-child heavy, balance
    if (root.getBalance() >= 2) {

      // if the mis-balance runs all the way left, simply rotate right
      if (root.left.getBalance() == 1) {
        root = rotateRight(root);
      }

      // if the mis-balance goes back right, rotate the left child then rotate right
      else if (root.left.getBalance() == -1) {
        root.left = rotateLeft(root.left);
        root = rotateRight(root);
      }
    }

    // else if the root is right-child heavy, balance
    else if (root.getBalance() <= -2) {

      // if the mis-balance runs all the way right, simply rotate left
      if (root.right.getBalance() == -1) {
        root = rotateLeft(root);
      }

      // if the mis-balance goes back left, rotate the right child then rotate left
      else if (root.right.getBalance() == 1) {
        root.right = rotateRight(root.right);
        root = rotateLeft(root);
      }
    }

    // the root is re-balanced: return
    return root;
  }

  /**
   * calls a private recursive helper method and stores the result of that method as the new root
   * 
   * @param key   is the lookup key to be inserted
   * @param value is the element to be inserted
   * @throws DuplicateKeyException if the key has already been inserted into the tree
   * @throws IllegalKeyException   if the key is null
   */
  @Override
  public void insert(K key, V value) throws DuplicateKeyException, IllegalKeyException {

    // if the key is null, throw illegal key exception
    if (null == key) {
      throw new IllegalKeyException("key is null");
    }

    // else start recursion
    root = insert(root, key, value);
  }

  /**
   * Private recursive helper method adds a node maintaining BST and AVL rules
   * 
   * @param current the root of this subtree
   * @param key     the lookup key to be added
   * @param value   the element to be added
   * @return the updated reference to current
   * @throws DuplicateKeyException if the key has already been inserted into the tree
   */
  private TreeNode<K, V> insert(TreeNode<K, V> current, K key, V value)
      throws DuplicateKeyException {
    if (current == null) {
      // set the current null location to the inserted element
      current = new TreeNode<K, V>(key, value);
    }

    // if the keys match, throw DuplicateKeyException
    else if (key.compareTo(current.key) == 0) {
      throw new DuplicateKeyException("key has already been inserted into the tree");
    }
    // if the element is smaller than current, go left
    else if (key.compareTo(current.key) < 0) {
      current.left = insert(current.left, key, value);
    }

    // if the element is greater than current, go right
    else if (key.compareTo(current.key) > 0) {
      current.right = insert(current.right, key, value);
    }

    // before returning: if the height is greater than 2, make sure the tree is balanced
    if (current.getHeight() > 2) {
      current = rebalance(current);
    }

    // return the current node when it's set
    return current;


  }


  /**
   * Calls a private recursive method to remove a key from the AVL tree. Returns nothing if the key
   * is not found.
   * 
   * @param key the key value to be removed
   * @throws IllegalKeyException if attempt to delete null
   */
  @Override
  public void delete(K key) throws IllegalKeyException {

    // if the key is null, throw illegal key exception
    if (null == key) {
      throw new IllegalKeyException("key is null");
    }

    root = delete(root, key);
  }

  /**
   * private recursive helper method removes a node, maintaining BST and AVL rules
   * 
   * @param current the root of this subtree
   * @param key     the key value to be removed. If the element doesn't exist, return nothing
   * @return the updated reference to current
   */
  private TreeNode<K, V> delete(TreeNode<K, V> current, K key) {

    // if we're at a null node, the element doesn't exist, return nothing
    if (current == null) {
      return null;
    }
    // if the element is smaller than current, go left
    if (key.compareTo(current.key) < 0) {
      current.left = delete(current.left, key);

      // before returning: if the height is greater than 2, make sure the tree is balanced
      if (current.getHeight() > 2) {
        current = rebalance(current);
      }
      return current;
    }
    // if the element is greater than current, go right
    if (key.compareTo(current.key) > 0) {
      current.right = delete(current.right, key);

      // before returning: if the height is greater than 2, make sure the tree is balanced
      if (current.getHeight() > 2) {
        current = rebalance(current);
      }
      return current;
    }

    // if we're here, we've found the right element. Begin removal

    // if there are no children, set this node to null. No need to balance since height is 0
    if (current.left == null && current.right == null) {
      return null;
    }

    // if there is only one child, decrement size and bring child up
    if (current.left == null) {

      // before returning: if the height is greater than 2, make sure the tree is balanced
      if (current.right.getHeight() > 2) {
        current.right = rebalance(current.right);
      }

      return current.right;
    }
    if (current.right == null) {

      // before returning: if the height is greater than 2, make sure the tree is balanced
      if (current.left.getHeight() > 2) {
        current.left = rebalance(current.left);
      }

      return current.left;
    }

    // in the case where the immediate left node is the predecessor, set the new node
    if (current.left.right == null) {
      // point the predecessor at the current right node
      current.left.right = current.right;

      // set the current node to the predecessor
      current = current.left;

      // before returning: if the height is greater than 2, make sure the tree is balanced
      if (current.getHeight() > 2) {
        current = rebalance(current);
      }

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

    // before returning: if the height is greater than 2, make sure the tree is balanced
    if (current.getHeight() > 2) {
      current = rebalance(current);
    }

    return current;
  }

  /**
   * second private recursive helper method to find the parent of the removing root's predecessor,
   * when it's not the immediate left node
   * 
   * @param current the root of this subtree
   * 
   * @return the parent of the predecessor
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
   * Calls a private recursive method to search the tree for that element
   * 
   * @param key the key value to be got
   * @return value the object associated with this key
   * @throws IllegalArgumentException if the key is null
   */
  @Override
  public V get(K key) throws IllegalKeyException {

    // if the key is null, throw illegal key exception
    if (null == key) {
      throw new IllegalKeyException("key is null");
    }

    return get(root, key);
  }

  /**
   * Get a value in the tree given the key. If the value isn't in the tree, return null
   * 
   * @param current the root of this subtree
   * @param key     the key value to be got
   * @return value the object associated with this key
   */
  private V get(TreeNode<K, V> current, K key) {

    // if the current element is null, the tree has ended, return null
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
  private String preOrderTraversal(TreeNode<K, V> current) {
    // if the current node is null, return an empty string
    if (current == null) {
      return "";
    }

    // otherwise, return this data along with the data of the children
    return current.key.toString() + " " + preOrderTraversal(current.left)
        + preOrderTraversal(current.right);
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
