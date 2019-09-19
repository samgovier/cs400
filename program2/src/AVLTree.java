
public class AVLTree<K extends Comparable<K>, V> {

  // private inner class that stores Key, Value pairs. It should go at the top of a class
  public static class Node<K extends Comparable<K>, V> {
    private K key;
    private V value;
    private int height;
    private Node<K, V> left;
    private Node<K, V> right;

    public Node(K key, V value) {
      this.key = key;
      this.value = value;
      this.height = 0;
      this.left = null;
      this.right = null;
    }

    /**
     * this recursive method which finds the height from this node as the root
     * 
     * @return the height of the tree as an integer If the node has no children, return 1
     */
    public int getHeight() {
      // if there are no children, return 1, as defined
      if (this.left == null && this.right == null) {
        return 1;
      }

      // otherwise, if there is no left child, return 1 + the right height
      if (this.left == null) {
        return 1 + this.right.getHeight();
      }

      // otherwise, if there is no right child, return 1 + the left height
      if (this.right == null) {
        return 1 + this.left.getHeight();
      }

      // if there are 2 children, return the max height between the two
      return 1 + Math.max(this.left.getHeight(), this.right.getHeight());
    }

    /**
     * this method finds the balance factor of the node. negative means left is bigger, positive
     * means right is bigger. 0 is perfect balance.
     * 
     * @return an integer representing the balance of this node per its children
     */
    public int getBalance() {
      if (this.left == null && this.right == null) {
        return 0;
      } else if (this.left == null) {
        return right.getHeight();
      } else if (this.right == null) {
        return left.getHeight();
      }

      return left.getHeight() - right.getHeight();
    }

    /**
     * calcualtes the balance factor of this node, @ return the difference of : height of left child
     * - height of right child
     */
    public int getBalanceClass() {
      int leftHeight = 0;
      int rightHeight = 0;
      if (this.left != null) {
        leftHeight = this.left.getHeight();
      }
      if (this.right != null) {
        rightHeight = this.right.getHeight();
      }
      return leftHeight - rightHeight;

    }
    // inner class doesn't need getters or setters when used in encompassing class
  }

  public static void main(String[] args) {
    Node<Integer, String> n1 = new Node<Integer, String>(313, "NO");
    Node<Integer, String> n2 = new Node<Integer, String>(5473, "SPORTS");
    n1.left = n2;
    Node<Integer, String> n3 = new Node<Integer, String>(1324, "OREGON");
    n1.right = n3;

    System.out.println(n1.value);
    System.out.println(n1.left.value);

    System.out.println(n3.getHeight());
    System.out.println(n3.getBalance());

    // inserting to the left of current

    current.left = insert(current.left, key, value);

    // current.updateHeight();

    if (current.getBalance() == 2) {
      if (current.left.getBalance() == 1) {
        current = rightRotate(current);
      } else {
        current.left = leftRotate(current.left);
        current = rightRotate(current);
      }
    }

  }
  
  public static void printSideways() {
    
  }
}
