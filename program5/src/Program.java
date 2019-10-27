
public class Program {

  public static void main(String[] args) {

    HashTable<Integer, String> testTable = new HashTable<Integer, String>();
    testTable = new HashTable<Integer, String>(5, 1);
    try {
      testTable.insert(1, "for real this time");
      testTable.insert(2, "Worshipful Soul");
      testTable.insert(3, "Funky Sole");
      testTable.insert(4, "Wait a minute... it's a mix");

      testTable.insert(5, "Well that's... something");
      testTable.insert(6, "Panacea");

      testTable.insert(7, "Pangaea");
      testTable.insert(8, "Anaemia");
      testTable.insert(9, "9051 and a few days");
      testTable.insert(10, "A few Tens of Thousands More");
      testTable.insert(11, "Enlightened Heart");
      testTable.insert(12, "con·cen·trate");

      testTable.insert(13, "For You");
      testTable.insert(14, "Breathe");

      testTable.insert(15, "f19");
      testTable.printHashTable();

      System.out.println(testTable.get(1));
      System.out.println(testTable.numKeys());
      System.out.println(testTable.getLoadFactor());
      System.out.println(testTable.getCapacity());
      System.out.println(testTable.remove(8));
      System.out.println(testTable.remove(15));
      System.out.println(testTable.remove(14));


      testTable.printHashTable();

    } catch (Exception e1) {
      System.out.println(e1.getMessage());
    }


    // System.out.println((int)'a');
    // System.out.println(computeHash("aa"));
    // System.out.println("Beesh");
    // Rectangle testRect = new Rectangle(3, 8);
    // Rectangle test2 = new Rectangle(3, 8);
    // Rectangle test3 = new Rectangle(3, 5);
    // System.out.println(testRect.hashCode());
    // System.out.println(test2.hashCode());
    // System.out.println(testRect.equals(test2));
  }

  /*
   * computes 31 to the power of the given exponent using bit shifting
   * 
   * @param exponent
   * 
   * @return 31 to the power of exponent
   */
  private static int powerOf31(int exponent) {
    int result = 1;;
    for (int e = 0; e < exponent; e++) {
      result = (result << 5) - result;;
    }
    return result;
  }

  /*
   * does manually what the String class hashCode method does for us this is just meant for
   * demonstration purposes your program would just call .hashCode() on your object
   * 
   * @param s the String we are trying to compute the hash on
   * 
   * @return the hashCode, consistent with the String class javadoc
   */
  private static int computeHash(String s) {
    int result = 0;
    int exponent;
    for (int i = 0; i < s.length(); i++) {
      exponent = s.length() - 1 - i;
      result += s.charAt(i) * powerOf31(exponent);
    }
    return result;

  }
}
