
public class Program {

  public static void main(String[] args) {
    System.out.println((int)'a');
    System.out.println(computeHash("aa"));
    System.out.println("Beesh");
    Rectangle testRect = new Rectangle(3, 8);
    Rectangle test2 = new Rectangle(3, 8);
    Rectangle test3 = new Rectangle(3, 5);
    System.out.println(testRect.hashCode());
    System.out.println(test2.hashCode());
    System.out.println(testRect.equals(test2));
  }

  /*
   * computes 31 to the power of the given exponent using bit shifting
   * 
   * @param exponent
   * 
   * @return 31 to the power of exponent
   */
  private static int powerOf31(int exponent) {
      int result = 1;        
   ; 
      for (int e = 0; e < exponent; e++) {  
          result = (result << 5) - result;    
   ;  
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
