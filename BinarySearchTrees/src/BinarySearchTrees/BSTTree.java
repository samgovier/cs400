package BinarySearchTrees;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Binary Search Trees
// Files: BSTNode.java, BSTTree.java, TreeSimulations.java
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

/*
 * BSTTree class contains the object definition for a Binary Search Tree.
 * The tree is initialized empty, and each node must be inserted.
 * getHeight and getSize will return height and size,
 * and PreOrderTraversal will return a string representation of the tree.
 */
public class BSTTree<T extends Comparable<T>> implements BinarySearchTreeADT<T> {
  // root is the top node of the tree
  private BSTNode<T> root;

  // size is the amount of nodes in the tree
  private int size;

  /*
   * This constructor creates the tree with a null root and size 0
   */
  public BSTTree() {
    root = null;
    size = 0;
  }

  /*
   * calls a private recursive helper method
   * and stores the result of that method as the new root
   * 
   * @param element the element to be inserted
   */
  @Override
  public void insert(T element) {
    root = insert(root, element);
  }

  /*
   * private recursive helper method adds a node maintaining BST rules
   * 
   * @param current the root of this subtree
   * 
   * @param element the element to be added
   * if the element is a duplicate, do nothing
   * 
   * @return the updated reference to current
   */
  private BSTNode<T> insert(BSTNode<T> current, T element) {
    if (current == null) {
      // set the current null location to the inserted element
      current = new BSTNode<T>(element);

      // increase the size of the tree with the new element
      size++;
    }
    // if the element is smaller than current, go left
    else if (element.compareTo(current.getData()) < 0) {
      current.setLeft(insert(current.getLeft(), element));
    }
    // if the element is greater than current, go right
    else if (element.compareTo(current.getData()) > 0) {
      current.setRight(insert(current.getRight(), element));
    }

    // return the current node when it's set
    return current;
  }

  /*
   * calls a private recursive helper method
   * and stores the result of that method as the new root
   * 
   * @param element the element to be removed
   */
  @Override
  public void remove(T element) {
    root = remove(root, element);
  }

  /*
   * private recursive helper method removes a node maintaining BST rules
   * 
   * @param current the root of this subtree
   * 
   * @param element the element to be removed
   * if the element doesn't exist, do nothing
   * 
   * @return the updated reference to current
   */
  private BSTNode<T> remove(BSTNode<T> current, T element) {
    // if we're at a null node, the element doesn't exist, return nothing
    if (current == null) {
      return null;
    }
    // if the element is smaller than current, go left
    if (element.compareTo(current.getData()) < 0) {
      current.setLeft(remove(current.getLeft(), element));
      return current;
    }
    // if the element is greater than current, go right
    if (element.compareTo(current.getData()) > 0) {
      current.setRight(remove(current.getRight(), element));
      return current;
    }

    // if we're here, we've found the right element. Begin removal

    // if there are no children, decrement size and set this node to null
    if (current.getLeft() == null && current.getRight() == null) {
      size--;
      return null;
    }

    // if there is only one child, decrement size and bring child up
    if (current.getLeft() == null) {
      size--;
      return current.getRight();
    }
    if (current.getRight() == null) {
      size--;
      return current.getLeft();
    }

    // in the case where the immediate left node is the predecessor,
    // set the new node
    if (current.getLeft().getRight() == null) {
      // point the predecessor at the current right node
      current.getLeft().setRight(current.getRight());

      // set the current node to the predecessor
      current = current.getLeft();

      // decrement size and return the new node
      size--;
      return current;
    }

    // find the inOrderPredecessor parent node
    // the right of the parent must be the predecessor
    BSTNode<T> inOrderPredecessorParent =
      findInOrderPredecessorParent(current.getLeft());

    // set the subtree root to the data of the predecessor
    current.setData(inOrderPredecessorParent.getRight().getData());

    // point the parent at any remaining data from the predecessor
    inOrderPredecessorParent.setRight(
      inOrderPredecessorParent.getRight().getLeft());

    // decrement size and return the modified node
    size--;
    return current;

  }

  /*
   * second private recursive helper method
   * find the parent of the removing root's predecessor,
   * when it's not the immediate left node
   * 
   * @param current the root of this subtree
   * 
   * @return the parent of the predecessor
   */
  private BSTNode<T> findInOrderPredecessorParent(BSTNode<T> current) {
    // if the right child of the right child isn't null, this right child
    // isn't the predecessor, keep looking
    if (current.getRight().getRight() != null) {
      findInOrderPredecessorParent(current.getRight());
    }

    // if the right child of the right child IS null, the right child is
    // the predecessor, so return the parent
    return current;
  }

  /*
   * calls a private recursive helper method to search the tree for that element
   * 
   * @param element the element to be found
   */
  @Override
  public boolean contains(T element) {
    return contains(root, element);
  }

  /*
   * private recursive helper method finds whether an element exists in the tree
   * 
   * @param current the root of this subtree
   * 
   * @param element the element to be found
   * 
   * @return bool of whether the element exists
   */
  private boolean contains(BSTNode<T> current, T element) {
    // if the current element is null, the tree has ended, return false
    if (current == null) {
      return false;
    }
    // if the element is smaller than current, go left
    if (element.compareTo(current.getData()) < 0) {
      return contains(current.getLeft(), element);
    }
    // if the element is greater than current, go right
    if (element.compareTo(current.getData()) > 0) {
      return contains(current.getRight(), element);
    }

    // if current is not null, more, or less than the passed element,
    // it is the same. return true
    return true;

  }

  /*
   * uses recursive helper method to print out a preOrder traversal
   */
  @Override
  public String preOrderTraversal() {
    return preOrderTraversal(root);
  }

  /*
   * recursive helper method
   * 
   * @param the root of the current subtree
   */
  private String preOrderTraversal(BSTNode<T> current) {
    // if the current node is null, return an empty string
    if (current == null) {
      return "";
    }

    // otherwise, return this data along with the data of the children
    return current.getData().toString() + preOrderTraversal(current.getLeft())
        + preOrderTraversal(current.getRight());
  }

  /*
   * uses recursive helper method to print out an inOrder traversal
   */
  @Override
  public String inOrderTraversal() {
    return inOrderTraversal(root);
  }

  /*
   * recursive helper method
   * 
   * @param the root of the current subtree
   */
  private String inOrderTraversal(BSTNode<T> current) {
    // if the current node is null, return an empty string
    if (current == null) {
      return "";
    }

    // otherwise, return this data along with the data of the children
    return inOrderTraversal(current.getLeft()) + current.getData().toString()
        + inOrderTraversal(current.getRight());
  }

  /*
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

  /*
   * method to return the size of the tree
   * 
   * @return the number of elements in the tree
   */
  @Override
  public int getSize() {
    return size;
  }

  /*
   * prints a tree sideways on your screen
   * this is meant to help you debug your program source:
   * Building Java Programs, 4th Ed., by Reges and Stepp, Ch. 17
   * tinyurl.com/AK-02-20-19
   */
  public void printSideways() {
    System.out.println("------------------------------------------");
    printSideways(root, "");
    System.out.println("------------------------------------------");
  }

  /*
   * private recursive helper method for printSideways above
   */
  private void printSideways(BSTNode<T> current, String indent) {
    if (current != null) {
      printSideways(current.getRight(), indent + "    ");
      System.out.println(indent + current.getData());
      printSideways(current.getLeft(), indent + "    ");
    }
  }

  /*
   * method to print postOrderTraversal, created in class
   */
  public void postOrderTraversal() {
    postOrderTraversal(root);
  }

  /*
   * private helper recursive method for postOrderTraversal
   */
  private void postOrderTraversal(BSTNode<T> current) {
    if (current != null) {
      postOrderTraversal(current.getLeft());
      postOrderTraversal(current.getRight());
      System.out.println(current.getData());
    }
  }


}
