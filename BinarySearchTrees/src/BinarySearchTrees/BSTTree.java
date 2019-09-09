package BinarySearchTrees;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Binary Search Trees
// Files:           BSTNode.java    BSTTree.java    TreeSimulations.java
// Course:          Computer Science 400
//
// Author:          Sam Govier
// Email:           sgovier@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/*
*	BSTTree class contains the object definition for a Binary Search Tree.
*	The tree is initialized empty, and each node must be inserted.
*	getHeight and getSize will return height and size,
*	and PreOrderTraversal will return a string representation of the tree.
*/
public class BSTTree<T extends Comparable<T>> implements BinarySearchTreeADT<T>
{
	// root is the top node of the tree
	private BSTNode<T> root;

	// size is the amount of nodes in the tree
    private int size;

	/*
	*	This constructor creates the tree with a null root and size 0
	*/
    public BSTTree()
    {
		root = null;
		size = 0;
    }

	public void insert(T element)
	{		
		root = insert(root,element);
	}
	
	private BSTNode<T> insert(BSTNode<T> current, T element)
	{
		if (current == null)
		{
			// set the current null location to the inserted element
			current = new BSTNode<T>(element);

			// increase the size of the tree
			size++;
		}
		else if (element.compareTo(current.getData()) < 0)
		{
			current.setLeft(insert(current.getLeft(), element));
		}
		else if (element.compareTo(current.getData()) > 0)
		{
			current.setRight(insert(current.getRight(), element));
		}
		
		return current;
	}

	/*
	 * calls a private recursive helper method
	 * and stores the result of that method as the new root
	 * @param element the element to be removed
	 */
	@Override
	public void remove(T element) {
		root = remove(root, element);
	}


	private BSTNode<T> remove(BSTNode<T> current, T element) {
		if (current == null)
		{
			return null;
		}
		if  (element.compareTo(current.getData()) < 0)
		{
			current.setLeft(remove(current.getLeft(), element));
			return current;
		}
		if (element.compareTo(current.getData()) > 0)
		{
			current.setRight(remove(current.getRight(), element));
			return current;
		}
		if (current.getLeft() == null && current.getRight() == null)
		{
			size--;
			return null;
		}
		if (current.getLeft() == null)
		{
			size--;
			return current.getRight();
		}
		if (current.getRight() == null)
		{
			size--;
			return current.getLeft();
		}

		if (current.getLeft().getRight() == null)
		{
			current.getLeft().setRight(current.getRight());
			current = current.getLeft();
			size--;
			return current;
		}

		BSTNode<T> inOrderPredecessorParent = findInOrderPredecessorParent(current.getLeft());

		current.setData(inOrderPredecessorParent.getRight().getData());

		inOrderPredecessorParent.setRight(inOrderPredecessorParent.getRight().getLeft());

		size--;
		return current;

	}
	
	private BSTNode<T> findInOrderPredecessorParent(BSTNode<T> current)
	{
		if (current.getRight().getRight() != null)
		{
			findInOrderPredecessorParent(current.getRight());
		}
		
		return current;
	}

	public boolean contains(T element)
    {
		return contains(root, element);
	}

	private boolean contains(BSTNode<T> current, T element)
	{
		if (current == null)
		{
			return false;
		}
		if (element.compareTo(current.getData()) < 0)
		{
			return contains(current.getLeft(), element);
		}
		if (element.compareTo(current.getData()) > 0)
		{
			return contains(current.getRight(), element);
		}

		// if current is not null, more, or less than the passed element,
		// it is the same. return true
		return true;

	}

	public String preOrderTraversal()
    {
		return preOrderTraversal(root);
	}
 
	private String preOrderTraversal(BSTNode<T> current)
	{
		if (current == null)
		{
			return "";
		}
		
		return current.getData().toString() +
			preOrderTraversal(current.getLeft()) +
			preOrderTraversal(current.getRight());
	}

	public int getHeight()
    {
		if(root == null)
		{
			return 0;
		}

		return root.getHeight();
	}

    public int getSize()
    {
        return size;
    }
	
	/* you can find this page at      tinyurl.com/AK-02-20-19
	*  prints a tree sideways on your screen
	*  this is meant to help you debug your program
	* source: Building Java Programs, 4th Ed., by Reges and Stepp, Ch. 17
	*/
	public void printSideways()
	{
		System.out.println("------------------------------------------");
		printSideways(root, "");
		System.out.println("------------------------------------------");
	}

	// private recursive helper method for printSideways above
	private void printSideways(BSTNode<T> current, String indent)
	{
	if (current != null) {
			printSideways(current.getRight(), indent + "    ");
			System.out.println(indent + current.getData()); 
			printSideways(current.getLeft(), indent + "    ");
		}
	}

	public void postOrderTraversal()
	{
		postOrderTraversal(root);
	}

	private void postOrderTraversal(BSTNode<T> current)
	{
		if (current != null)
		{
			postOrderTraversal(current.getLeft());
			postOrderTraversal(current.getRight());
			System.out.println(current.getData());
		}
	}
    

}