
public class Program {

  public static void main(String[] args) {
    System.out.println("Hump mercredi");
    Integer test = null;
    System.out.println(test);
    RedBlackTree<Integer, String> months = new RedBlackTree<Integer, String>();
    System.out.println(months.isEmpty());

    try {
      months.insert(7, "MAY");
      System.out.println(months.isEmpty());
      months.insert(14, "AUGUST");
      months.insert(18, "OCTOBER");
      months.insert(23, "Feb");
      months.insert(1, "FROGTEMBER");
      months.insert(11, "frogtober");
      months.insert(20, "SUPP");
      months.insert(29, "INFINITUARY");
      months.insert(25, "UGH");
      months.insert(27, "NO MORE");
      months.insert(7, "MAY");
      System.out.println(months.getSize());
      months.printSideways();
      System.out.println(months.preOrderTraversal());
    } catch (IllegalKeyException e1) {
      System.out.println("OOPSIE DAISIE");
    }

  }

}
