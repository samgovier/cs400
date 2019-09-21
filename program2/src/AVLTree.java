// remember that you are not allowed to add any public methods or fields
// but you can add any private methods or fields

public class AVLTree<K extends Comparable<K>, V> implements TreeADT<K, V> {
	public TreeNode<K, V> root = null;

	/** 
	 * Inner class representing a node of the AVL tree. 
	 * @param <K>
	 * @param <V>
	 */
	private class TreeNode<K, V> {
		private K key;
		private V value;
		private int height;
		private TreeNode<K, V> left, right;

		// TODO: constructor
	}

   // TODO:  IMPLEMENT ALL INTERFACE METHODS
   
	/**
	 * AVLTree rotate left.
	 * @param root an imbalance node
	 * @return TreeNode<K, V> the node for which balance has been modified 
	 */
	private TreeNode<K, V> rotateLeft(TreeNode<K, V> root) {
		// TODO: implement
		return null;
	}

	/**
	 * AVLTree rotate right.
	 * @param root an imbalance node
	 * @return the node for which balance has been modified 
	 */
	private TreeNode<K, V> rotateRight(TreeNode<K, V> root) {
		// TODO: implement
		return root;
	}


	/**
	 * Print a tree sideways to show structure. This code is completed for you.
	 */
	public void printSideways() {
		System.out.println("------------------------------------------");
		recursivePrintSideways(root, "");
		System.out.println("------------------------------------------");
	}

	/**
	 * Print nodes in a tree. This code is completed for you. 
	 * You are allowed to modify this code to include balance factors or heights
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