import java.util.ArrayList;
import java.util.Arrays;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: String Heap
// Files: StringHeap.java, HeapRunner.java
// Course: Computer Science 400
//
// Author: Sam Govier
// Email: sgovier@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 * StringHeap contains the object definition for a String Heap. The heap is initialized as empty,
 * and each node is inserted.
 *
 */
public class StringHeap {

  // heap is the backing array for the string heap
  private String[] heap;

  // size is the size of the heap
  private int size;

  // initialize un-modifiable vowels array for use in prioritization
  private static final char[] VOWELS_ARRAY = new char[] {'a', 'e', 'i', 'o', 'u'};

  /**
   * this constructor creates the empty heap with size 0
   * 
   * @param capacity is the initial heap capacity
   */
  public StringHeap(int capacity) {
    heap = new String[capacity];
    size = 0;
  }

  private int parent(int index) {
    return index / 2;
  }

  private int leftChild(int index) {
    return index * 2;
  }

  private int rightChild(int index) {
    return (index * 2) + 1;
  }

  private boolean hasParent(int index) {
    // TODO this is... not enough
    return index != 1;
  }

  private boolean hasleftChild(int index) {
    return leftChild(index) <= size;
  }

  private boolean hasRightChild(int index) {
    return rightChild(index) <= size;
  }

  private void swap(String[] modifyingArray, int index1, int index2) {
    String temp = modifyingArray[index1];
    modifyingArray[index1] = modifyingArray[index2];
    modifyingArray[index2] = temp;
  }

  /**
   * determines which String has higher priority, using rules in this order: 0) convert each String
   * to lower case first, since case does not matter 1) if lengths are different, the largest length
   * String has priority 2) else if numVowels (a,e,i,o,u) are different, the String with the most
   * vowels has priority 3) else, alphabetical order (since already in lower case, use .compareTo()
   * in the String class)
   * 
   * @param first  the first String
   * @param second the second String
   * @return a positive integer if first has higher priority; a negative integer if second has
   *         higher priority; 0 if the priorities are equal
   */
  public static int prioritize(String first, String second) {


    // convert strings to lower case since case doesn't matter
    first = first.toLowerCase();
    second = second.toLowerCase();

    // if the strings are the same, skip all the logic: return 0
    if (first.equals(second)) {
      return 0;
    }

    // if lengths are different, return the difference between the two
    if (first.length() != second.length()) {
      return first.length() - second.length();
    }

    // initialize counters for first and second's vowel count
    int firstVowels = 0;
    int secondVowels = 0;

    // for each character in first, if it's a vowel, increment firstVowels counter
    for (char pos : first.toCharArray()) {
      if (Arrays.binarySearch(VOWELS_ARRAY, pos) >= 0) {
        firstVowels++;
      }
    }

    // for each character in second, if it's a vowel, increment secondVowels counter
    for (char pos : second.toCharArray()) {
      if (Arrays.binarySearch(VOWELS_ARRAY, pos) >= 0) {
        secondVowels++;
      }
    }

    // if they don't have the same amount of vowels, return the difference
    if (firstVowels != secondVowels) {
      return firstVowels - secondVowels;
    }

    // at this point, just use compareTo() for alphabetical comparison
    return first.compareTo(second);
  }

  /**
   * adds a String to the heap and prioritizes using the 'prioritize' method
   * 
   * @param value the string to be added (duplicate values should be added again)
   * @throws IllegalArgumentException if the value is null or an empty String
   */
  public void add(String value) {
    // TODO check if the heap is full, if so make a new one, shadow array,
    // https://stackoverflow.com/questions/12300854/what-is-a-shadow-array

    size++;
    heap[size] = value;

    // mark current as the size
    int current = size;

    // while the current has a parent, and the current is greater in priority than the parent,
    // swap the values and check the next parent
    while (hasParent(current)
        && (StringHeap.prioritize(heap[current], heap[parent(current)]) > 0)) {
      this.swap(heap, parent(current), current);
      current = parent(current);
    }
  }

  /**
   * removes the String with the highest priority from the queue and adjusts the heap to maintain
   * priority rules
   * 
   * @return the String with the highest priority; null if the heap is empty
   */
  public String remove() {
    // TODO implement size
    return null;
  }

  /**
   * @return true if the String has no elements, false otherwise
   */
  public boolean isEmpty() {
    return heap[1] == null;
  }

  /**
   * @return the number of Strings stored in the heap
   */
  public int getSize() {
    return size;
  }

  /**
   * @return the element with the highest priority but do not remove it
   */
  public String peek() {
    // TODO
    return null;
  }

  /**
   * calculates the height (the number of levels past the root); an empty heap has a height of 0 a
   * heap with just a root has a height of 1
   * 
   * @return the height of the heap
   */
  public int getHeight() {
    // TODO
    return 0;
  }

  /**
   * a new ArrayList containing all Strings at this level - may not contain null values - an empty
   * heap will result in returning an empty ArrayList - throws an IndexOutOfBoundsException if level
   * is not appropriate for this heap
   * 
   * @return a new String ArrayList that contains only the Strings at this level
   */
  public ArrayList<String> getLevel(int level) {
    // TODO
    return null;
  }

  /**
   * returns a deep copy of the heap array, but not the actual heap object so that the original heap
   * remains unchangeable outside this class - will be used by the autograder - an empty heap will
   * return null
   * 
   * @return a new array of length heap.length with copied Strings
   * 
   */
  public String[] getHeap() {
    if (this.isEmpty()) {
      return null;
    }
    return Arrays.copyOf(heap, heap.length);
  }

  /**
   * prints out heap size, then prints out the heap elements - one per line following the indexing
   * of the heap array
   */
  public void printHeap() {
    System.out.println(this.getSize());
    for (int i = 1; i <= size; i++) {
      System.out.println(heap[i]);
    }
  }

  /**
   * prints out level-order traversal of the heap - one level per line with nodes delimited by a
   * single whitespace
   */
  public void printLevelOrderTraversal() {
    String result = "";
    for (int i = 1; i <= size; i++) {
      result +
    }
    
    System.out.println(result.trim());
  }
}
