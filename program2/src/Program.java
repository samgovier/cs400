
public class Program {
  
  public static void main(String[] args) {
    System.out.println("supppup");
    
    AVLTree<Integer, String> treetest = new AVLTree<Integer, String>();
    
    System.out.println(treetest.isEmpty());
    
    try {
      treetest.insert(99, "ONE");
      treetest.insert(88, "TWO");
      treetest.insert(77, "THREE");
      System.out.println(treetest.get(1));
      System.out.println(treetest.get(99));
      System.out.println(treetest.isEmpty());
      System.out.println(treetest.preOrderTraversal());
      treetest.printSideways();
      treetest.insert(99, "ERROR");
    } catch (IllegalKeyException e) {
      System.out.println(e.getMessage());
    } catch (DuplicateKeyException e2) {
      System.out.println(e2.getMessage());
    }
  }
}
