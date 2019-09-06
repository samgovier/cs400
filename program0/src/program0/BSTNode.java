package program0;

public class BSTNode<T extends Comparable<T>> {

	private T data;
	private BSTNode<T> left;		// a reference to the left child
	private BSTNode<T> right; 		// a reference to the right child
	
	public BSTNode(T data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public BSTNode<T> getLeft() {
		return left;
	}

	public void setLeft(BSTNode<T> left) {
		this.left = left;
	}

	public BSTNode<T> getRight() {
		return right;
	}

	public void setRight(BSTNode<T> right) {
		this.right = right;
	}

	// returns the height of the tree with this node as the root
	// the height is defined as the number of edges between the root and its furthese leaf
	// if this node has no children, return 1
	public int getHeight()
	{
		if (this.getLeft() == null && this.getRight() == null)
		{
			return 1;
		}
		if (this.getLeft() == null)
		{
			return 1 + this.getRight().getHeight();
		}
		if (this.getRight() == null)
		{
			return 1 + this.getLeft().getHeight();
		}
		return 1 + Math.max(this.getLeft().getHeight(), this.getRight().getHeight());
	}
	
	public static void main(String[] args)
	{
		BSTNode<String> node1 = new BSTNode<String>("M");
		BSTNode<String> node2 = new BSTNode<String>("Q");
		BSTNode<String> node3 = new BSTNode<String>("D");
		
		node1.setLeft(node2);
		node1.setRight(node3);
		System.out.println("     " + node1.getData());
		System.out.println(node1.getLeft().getData() + "\t" + node1.getRight().getData());
		System.out.println("Heights");
		System.out.println(node1.getHeight());
		System.out.println(node2.getHeight());
		System.out.println(node3.getHeight());
	}
}