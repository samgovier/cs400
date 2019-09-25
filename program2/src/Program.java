
public class Program {

  public static void main(String[] args) {
    System.out.println("supppup");

    AVLTree<Integer, String> treetest = new AVLTree<Integer, String>();

    System.out.println(treetest.isEmpty());

    try {
      treetest.insert(5, "TWO");
      treetest.insert(9, "TEN");
      treetest.insert(6, "ONE");
      
      treetest.insert(4, "HELLO:");
      
      treetest.insert(8, "HUH");
      treetest.insert(3, "THREE");
      treetest.insert(2, "PSYCH");
      treetest.insert(7, "hey");
      treetest.insert(1, "one more");
      treetest.delete(20);
      System.out.println(treetest.get(5));
      System.out.println(treetest.get(2));
      System.out.println(treetest.isEmpty());
      System.out.println(treetest.preOrderTraversal());
      treetest.printSideways();
      treetest.insert(10, "ERROR");
    } catch (IllegalKeyException e) {
      System.out.println(e.getMessage());
    } catch (DuplicateKeyException e2) {
      System.out.println(e2.getMessage());
    }
  }
}
