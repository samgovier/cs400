
public class Program {

  public static void main(String[] args) {
    System.out.println("Beesh");
    Rectangle testRect = new Rectangle(3,8);
    Rectangle test2 = new Rectangle(3,8);
    Rectangle test3 = new Rectangle(3, 5);
    System.out.println(testRect.hashCode());
    System.out.println(test2.hashCode());
    System.out.println(testRect.equals(test2));
  }

}
