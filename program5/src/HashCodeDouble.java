// Demonstrates how the Double class generates its hash code
public class HashCodeDouble {

    public static void main(String[] args) {

        
        Double d = 0.123456;
        System.out.println("the hash code for the double 0.123456 is " + d.hashCode());
        

        long bits = Double.doubleToLongBits(d);
        System.out.println(d+ " as bits equals " + bits);

        int lower = (int) bits;         // extract lower 32 bits
        int upper = (int) (bits>>>32);      // extract upper 32 bits
        

        System.out.printf("%64s\n", Long.toBinaryString(bits));     // display bits as a string
        
        System.out.printf("%32s", Integer.toBinaryString(upper));   // extract upper
        System.out.printf("%32s\n", Integer.toBinaryString(lower)); // extract lower
        
        System.out.print("|----------- upper  -----------|");
        System.out.println("|----------- lower  -----------|");
        
        
        System.out.println();
        System.out.printf("%32s%s", Integer.toBinaryString(upper), "\tupper\n");
        System.out.printf("%32s%s", Integer.toBinaryString(lower), "\tlower\n");

        System.out.println("--------------------------------  bitwise XOR");
        
        int exclusiveOr = (int)(upper ^ lower);
        System.out.printf("%32s\n", Integer.toBinaryString(exclusiveOr));
        System.out.println("\nSo my hashcode, as an integer, is  " + exclusiveOr);
        
        
        int hash = d.hashCode();
        System.out.println("The String.hashCode method returns " + hash + " which in binary is:");
        System.out.printf("%32s\n", Integer.toBinaryString(hash));

        

        

    }

}

