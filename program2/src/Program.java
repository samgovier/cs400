
public class Program {

  public static void main(String[] args) {
    System.out.println("supppup");

    AVLTree<Integer, String> treetest = new AVLTree<Integer, String>();

    System.out.println(treetest.isEmpty());

    try {
      treetest.insert(20, "TWO");
      treetest.insert(10, "TEN");
      treetest.insert(30, "ONE");
      
      treetest.insert(40, "HELLO:");
      
//      treetest.insert(5, "HUH");
//      treetest.insert(15, "THREE");
//      treetest.insert(13, "PSYCH");
//      treetest.insert(18, "hey");
      treetest.delete(20);
      System.out.println(treetest.get(25));
      System.out.println(treetest.get(99));
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
