public class HashCodeString {

    public static void main(String[] args) {
                
        Integer myInt = -121;
        System.out.println("hash codes for integers are what you expect");
        System.out.println("the hash code for Integer -121 is " + myInt.hashCode() + "\n");
        
        
        String word = "Stevie";
        System.out.println("hash codes for Strings can be negative");
        System.out.println("the hash code for 'Stevie'  is " + word.hashCode() + "\n");

        
        String s = "CAT";
        System.out.print("the hash code for CAT is " + s.hashCode());
        int result = s.charAt(0)*31*31 + s.charAt(1)*31 + (int)(s.charAt(2));
        System.out.println(" which equals " +  result + "\n");
        
        
        System.out.println("A left bit-shift is equivalent to multiplying by 2");
        int x = 5;
        while (x < 999) {
            System.out.println(x + " in binary is " + Integer.toBinaryString(x));
            x = x << 1; // left bit shift of 1, equivalent to multiplying by 2
        }
        
        // take any string and generate its hash code
        String phrase = "Meet me at the union";
        int calculation = computeHash(phrase);
        System.out.println("my computeHash on " +  phrase + " is " + calculation);
        System.out.println("Javas hashcode on " + phrase + " is " + phrase.hashCode());
    
        
    }//main
    
    /*
     * does manually what the String class hashCode method does for us
     * this is just meant for demonstration purposes
     * your program would just call .hashCode() on your object
     * @param s the String we are trying to compute the hash on
     * @return the hashCode, consistent with the String class javadoc
     */
    private static int computeHash(String s) {
        int result = 0;
        int exponent;
        for (int i =0; i < s.length(); i++) {
            exponent = s.length() - 1 - i; 
            result += s.charAt(i) * powerOf31(exponent);
            System.out.println("\tchar at i\t" + s.charAt(i)+ "\tresult\t" + result);
        }
        return result;
    }
    
    /*
     * computes 31 to the power of the given exponent
     * using bit shifting
     * @param exponent 
     * @return 31 to the power of exponent
     */
    private static int powerOf31(int exponent) {
        int result = 1;
        for (int e = 0; e < exponent; e++) {  // loop exponent times
            result = (result << 5) - result;  // multiply result by 31
        }
        return result;
    }

}
