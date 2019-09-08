package program0;

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
		// TODO ignore current hmm... can i get away with this?
		// else
		// {
		// 	throw new IllegalArgumentException("No duplicates allowed");
		// }
		
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
			return null;
		}
		if (current.getLeft() == null)
		{
			return current.getRight();
		}
		if (current.getRight() == null)
		{
			return current.getLeft();
		}
		
		BSTNode<T> inOrderPredecessor = null;
		BSTNode<T> predecessorParent = current;
		BSTNode<T> oldCurrent = current;
		boolean found = false;

		if (current.getLeft().getRight() == null)
		{
			inOrderPredecessor = current.getLeft();
			found = true;
		}
		else
		{
			predecessorParent = current.getLeft();
			current = current.getLeft().getRight();
		}
		
		while(!found)
		{
			if (current.getRight() == null)
			{
				inOrderPredecessor = current;
				found = true;
			}
			else
			{
				predecessorParent = current;
				current = current.getRight();
			}
		}

		predecessorParent.setRight(inOrderPredecessor.getLeft());
		inOrderPredecessor.setRight(oldCurrent.getRight());
		inOrderPredecessor.setLeft(oldCurrent.getLeft());
		return inOrderPredecessor;

		// if both children are not null … this part is harder than it seems, test carefully
			// find the inorder predecessor
			// copy the data from the inorder predecessor into current
			// remove the inorder predecessor from the tree
			// return current

	}

	public boolean contains(T element)
    {
		if (null == find(element))
		{
			return false;
		}

		return true;
	}

	private BSTNode<T> find(T element)
	{
		return find(root, element);
	}

	private BSTNode<T> find(BSTNode<T> current, T element)
	{
		if (current == null)
		{
			return null;
		}
		if (element.compareTo(current.getData()) < 0)
		{
			return find(current.getLeft(), element);
		}
		if (element.compareTo(current.getData()) > 0)
		{
			return find(current.getRight(), element);
		}

		// if current is not null, more, or less than the passed element,
		// it is the same. return the matching node
		return current;
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