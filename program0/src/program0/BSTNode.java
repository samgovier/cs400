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
*	BSTNode class contains the object definition for a Binary Search Tree node.
*	Each node is instantiated with no children and linked via setters.
*	The getHeight method is used to determine the height from the defined node.
*/
public class BSTNode<T extends Comparable<T>> {

	// data is the element content of the node
	private T data;

	// left is a reference to the left child of the node
	private BSTNode<T> left;
	
	// right is a reference to the right child of the node
	private BSTNode<T> right;
	
	/*
	*	This constructor creates the node with passed data and null children
	*	@param data is the node content
	*/
	public BSTNode(T data)
	{
		this.data = data;
		this.left = null;
		this.right = null;
	}

	/*
	*	This method returns the content of the node
	*	@return the content of the node
	*/
	public T getData()
	{
		return data;
	}

	/*
	*	This method sets the content of the node
	*	@param data is the new node content
	*/
	public void setData(T data)
	{
		this.data = data;
	}

	/*
	*	This method returns the left child of the node
	*	@return the left child of the node
	*/
	public BSTNode<T> getLeft()
	{
		return left;
	}

	/*
	*	This method sets the left child of the node
	*	@param left is the new left child
	*/
	public void setLeft(BSTNode<T> left)
	{
		this.left = left;
	}

	/*
	*	This method returns the right child of the node
	*	@return the right child of the node
	*/
	public BSTNode<T> getRight()
	{
		return right;
	}

	/*
	*	This method sets the right child of the node
	*	@param right is the new right child
	*/
	public void setRight(BSTNode<T> right)
	{
		this.right = right;
	}

	/*
	*	this recursive method which finds the height from this node as the root
	*	@return the height of the tree as an integer
	*	If the node has no children, return 1
	*/
	public int getHeight()
	{
		// if there are no children, return 1, as defined
		if (this.getLeft() == null && this.getRight() == null)
		{
			return 1;
		}

		// otherwise, if there is no left child, return 1 + the right height
		if (this.getLeft() == null)
		{
			return 1 + this.getRight().getHeight();
		}

		// otherwise, if there is no right child, return 1 + the left height
		if (this.getRight() == null)
		{
			return 1 + this.getLeft().getHeight();
		}

		// if there are 2 children, return the max height between the two
		return 1 +
			Math.max(this.getLeft().getHeight(), this.getRight().getHeight());
	}
}