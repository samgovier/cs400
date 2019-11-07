import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

public class SetPractice {

  public static void main(String[] args) {
    TreeSet<Integer> tSet = new TreeSet<Integer>();
    HashSet<Integer> hSet = new HashSet<Integer>();

    Random rndGen = new Random();

    for (int i = 0; i < 11; i++) {
      int r = rndGen.nextInt(50);
      if (!tSet.add(r)) {
        System.out.println("failed to add " + r);
      }
      if (!hSet.add(r)) {
        System.out.println("didn't add " + r);
      }
    }
    // what happens when a set is not adding an element?

    System.out.println("tSet contents: " + tSet);
    System.out.println("hSet contents: " + hSet);

    // what order is hSet using when it prints out ?
    // how can we find out ?

  }

}
