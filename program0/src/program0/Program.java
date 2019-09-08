package program0;

public class Program
{
    public static void main(String[] args)
    {
        
        // START BSTNode.java TESTS

        // BSTNode<String> node1 = new BSTNode<String>("M");
		// BSTNode<String> node2 = new BSTNode<String>("Q");
		// BSTNode<String> node3 = new BSTNode<String>("D");
		// BSTNode<String> node4 = new BSTNode<String>("A");
		
		// node1.setLeft(node2);
		// node1.setRight(node3);
		// node3.setRight(node4);
		// System.out.println("     " + node1.getData());
		// System.out.println(node1.getLeft().getData() + "\t" + node1.getRight().getData());
		// System.out.println("\t\t" + node1.getRight().getRight().getData());
		// System.out.println("Heights");
		// System.out.println(node1.getHeight());
		// System.out.println(node2.getHeight());
		// System.out.println(node3.getHeight());

		// START BSTTree.java TESTS

		BSTTree<Integer> tree = new BSTTree<Integer>();
		tree.insert(33);
		tree.insert(22);
		tree.insert(44);
		tree.printSideways();
		System.out.println(tree.preOrderTraversal());;

		// TEST NULL TREE, ONE TREE, MULTIPLE MULTIPLE TREES
    }
}