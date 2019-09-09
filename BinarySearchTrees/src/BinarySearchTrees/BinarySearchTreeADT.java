package BinarySearchTrees;

interface BinarySearchTreeADT<T extends Comparable<T>> {

  // adds element to the tree maintaining BST rules...ignore duplicates
  void insert(T element);

  // if element exists, remove from tree, maintaining BST rules
  void remove(T element);

  // returns true if element is in the tree, false otherwise
  boolean contains(T element);

  // returns a String representing the pre-order traversal of this tree
  String preOrderTraversal();

  // returns the height of this tree (empty = 0, root only = 1, etc)
  int getHeight();

  // returns the number of elements in this tree
  int getSize();
}
