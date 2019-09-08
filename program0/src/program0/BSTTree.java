package program0;

// TODO Change program name to BinarySearchTree
public class BSTTree<T extends Comparable<T>> implements BinarySearchTreeADT<T>
{
    private BSTNode<T> root;
    private int size;

    public BSTTree()
    {
        root = null;
    }

	public void insert(T element)
	{		
		root = insert(root,element);
	}
	
	private BSTNode<T> insert(BSTNode<T> current, T element)
	{
		if (current == null)
		{
			current = new BSTNode<T>(element);
		}
		else if (element.compareTo(current.getData()) < 0)
		{
			current.setLeft(insert(current.getLeft(), element));
		}
		else if (element.compareTo(current.getData()) > 0)
		{
			current.setRight(insert(current.getRight(), element));
		}
		else
		{
			throw new IllegalArgumentException("No duplicates allowed");
		}
		
		return current;
	}

    public void remove(T element)
    {
        // TODO implement Remove method
    }

    public boolean contains(T element)
    {}

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
    
    public String preOrderTraversal()
    {}
    
    public int getHeight()
    {}

    public int getSize()
    {
        return size;
        // TODO assure size is implemented across methods
    }
	
	/* you can find this page at      tinyurl.com/AK-02-20-19
	*  prints a tree sideways on your screen
	*  this is meant to help you debug your program
	* source: Building Java Programs, 4th Ed., by Reges and Stepp, Ch. 17
	*/
	public void printSideways() {
		System.out.println("------------------------------------------");
		printSideways(root, "");
		System.out.println("------------------------------------------");
	}

	// private recursive helper method for printSideways above
	private void printSideways(BSTNode<T> current, String indent) {
	if (current != null) {
			printSideways(current.getRight(), indent + "    ");
			System.out.println(indent + current.getData()); 
			printSideways(current.getLeft(), indent + "    ");
		}
	}
}