package BinarySearchTrees;

interface BinarySearchTreeADT<T extends Comparable<T>> {
	// for p0, assume that no element values are null
	
  	// adds element to the tree maintaining BST rules...ignores duplicates
	void insert(T element);

	// if element exists, remove from tree, maintaining BST rules
	// otherwise do nothing
	void remove(T element);

	// returns true if element is in the tree, false otherwise
	boolean contains(T element);
	
	// returns the height of this tree (empty = 0, single node = 1, etc)
	int getHeight();

	// returns the number of elements in this tree
	int getSize();

	// returns a String representing the post-order traversal of this tree
	String preOrderTraversal();
	
	// returns a String representing the in-order traversal of this tree
	String inOrderTraversal();
	
	// prints out this tree sideways
	void printSideways();




}

