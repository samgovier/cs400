import java.util.ArrayList;

public class StringHeap {
    // instance variables; you may add additional private fields
    private String[] heap;
    private int size;

    /**
     * constructor (this code is done for you)
     * 
     * @param the initial heap capacity
     */
    public StringHeap(int capacity) {
        heap = new String[capacity];
        size = 0;
    }

    /**
     * determines which String has higher priority, using rules in this order:
     *  0) convert each String to lower case first, since case does not matter
     *  1) if lengths are different, the largest length String has priority
     *  2) else if numVowels (a,e,i,o,u) are different, the String with the most vowels has priority
     *  3) else, alphabetical order (since already in lower case, use .compareTo() in the String class)
     * 
     * @param first the first String
     * @param second the second String
     * @return a positive integer if first has higher priority; a negative integer if
     * second has higher priority; 0 if the priorities are equal
     */
    public static int prioritize(String first, String second) {
        // TODO
        return 0;
    }

    /**
     * adds a String to the heap and prioritizes using the 'prioritize' method
     * 
     * @param s the string to be added (duplicate values should be added again)
     * @throws IllegalArgumentException if the value is null or an empty String
     */
    public void add(String value) {
        // TODO
    }

    /**
     * removes the String with the highest priority from the queue and adjusts the heap
     * to maintain priority rules
     * 
     * @return the String with the highest priority; null if the heap is empty
     */
    public String remove() {
        // TODO
        return null;
    }

    /**
     * @return true if the String has no elements, false otherwise
     */
    public boolean isEmpty() {
        // TODO
        return false;
    }

    /**
     * @return the number of Strings stored in the heap
     */
    public int getSize() {
        // TODO
        return 0;
    }

    /**
     * @return the element with the highest priority but do not remove it
     */
    public String peek() {
        // TODO
        return null;
    }

    /**
     * calculates the height (the number of levels past the root); an empty heap has a
     * height of 0 a heap with just a root has a height of 1
     * 
     * @return the height of the heap
     */
    public int getHeight() {
        // TODO
        return 0;
    }

    /**
     * a new ArrayList containing all Strings at this level
     *  - may not contain null values
     *  - an empty heap will result in returning an empty ArrayList
     *  - throws an IndexOutOfBoundsException if level is not appropriate for this heap
     * 
     * @return a new String ArrayList that contains only the Strings at this level
     */
    public ArrayList<String> getLevel(int level) {
        // TODO
        return null;
    }

    /**
     * returns a deep copy of the heap array, but not the actual heap object so that
     * the original heap remains unchangeable outside this class
     *  - will be used by the autograder
     *  - an empty heap will return null
     * 
     * @return a new array of length heap.length with copied Strings
     * 
     */
    public String[] getHeap() {
        // TODO
        return null;
    }

    /**
     * prints out heap size, then prints out the heap elements
     *  - one per line following the indexing of the heap array
     */
    public void printHeap() {
        // TODO
    }

    /**
     * prints out level-order traversal of the heap
     *  - one level per line with nodes delimited by a single whitespace
     */
    public void printLevelOrderTraversal() {
        // TODO
    }

    // you are welcome to add private methods
}