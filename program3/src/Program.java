
public class Program {

  public static void main(String[] args) {
    System.out.println("Hump mercredi");
    Integer test = null;
    System.out.println(test);
    RedBlackTree<Integer, String> months = new RedBlackTree<Integer, String>();

    try {
      months.insert(5, "MAY");
      months.insert(8, "AUGUST");
      months.insert(10, "OCTOBER");
      months.printSideways();
      // TEST REPLACE
    } catch (IllegalKeyException e1) {
      System.out.println("OOPSIE DAISIE");
    }

  }

}
