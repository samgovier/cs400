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

// TODO javadoc @param, @returns, @throws

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
    return second.compareTo(first);
  }

  private int parent(int index) {
    return index / 2;
  }

  private boolean hasParent(int index) {
    // TODO this is... not enough
    return index != 1;
  }

  private void swap(String[] modifyingArray, int index1, int index2) {
    String temp = modifyingArray[index1];
    modifyingArray[index1] = modifyingArray[index2];
    modifyingArray[index2] = temp;
  }

  /**
   * adds a String to the heap and prioritizes using the 'prioritize' method
   * 
   * @param value the string to be added (duplicate values should be added again)
   * @throws IllegalArgumentException if the value is null or an empty String
   */
  public void add(String value) {

    if ((value == null) || value.isBlank()) {
      throw new IllegalArgumentException("Input string is not a valid element");
    }

    if (size >= heap.length - 1) {
      heap = Arrays.copyOf(heap, heap.length * 2);
    }
    
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

  private int leftChild(int index) {
    return index * 2;
  }

  private int rightChild(int index) {
    return (index * 2) + 1;
  }

  private boolean hasLeftChild(int index) {
    return leftChild(index) <= size;
  }

  private boolean hasRightChild(int index) {
    return rightChild(index) <= size;
  }

  /**
   * removes the String with the highest priority from the queue and adjusts the heap to maintain
   * priority rules
   * 
   * @return the String with the highest priority; null if the heap is empty
   */
  public String remove() {

    if (this.isEmpty()) {
      return null;
    }

    int current = 1;

    String returnString = heap[current];
    heap[current] = heap[size];
    heap[size] = null;
    size--;
    
    // TODO double check the parent and child have correct priority?
    // while the current node has a child (if it has a child, it must be left)
    while (hasLeftChild(current)) {

      if (!hasRightChild(current)
          || ((StringHeap.prioritize(heap[leftChild(current)], heap[rightChild(current)])) > 0)) {
        this.swap(heap, current, leftChild(current));
        current = leftChild(current);
      }

      else if ((StringHeap.prioritize(heap[rightChild(current)], heap[leftChild(current)])) >= 0) {
        this.swap(heap, current, rightChild(current));;
        current = rightChild(current);
      }
    }

    return returnString;
  }

  /**
   * @return true if the String has no elements, false otherwise
   */
  public boolean isEmpty() {
    return (size == 0);
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
    return heap[1];
  }

  /**
   * calculates the height (the number of levels past the root); an empty heap has a height of 0 a
   * heap with just a root has a height of 1
   * 
   * Each new level can be represented by a power of 2, so taking the logarithm of 2 will give us
   * the highest level
   * 
   * @return the height of the heap
   */
  public int getHeight() {

    // if the heap is empty, return 0
    if (this.isEmpty()) {
      return 0;
    }

    // otherwise, return log base 2 of the size + 1, which will truncate to the proper level
    return (int) (Math.log(this.getSize()) / Math.log(2)) + 1;
  }

  /**
   * a new ArrayList containing all Strings at this level - may not contain null values - an empty
   * heap will result in returning an empty ArrayList - throws an IndexOutOfBoundsException if level
   * is not appropriate for this heap
   * 
   * @return a new String ArrayList that contains only the Strings at this level
   */
  public ArrayList<String> getLevel(int level) throws IndexOutOfBoundsException {

    // firstIndex is the first index of the level: 2^(level-1)
    int firstIndex = (int) Math.pow(2, level - 1);

    // lastIndex is the last index, which is just the first index of the next level - 1
    int lastIndex = (int) Math.pow(2, level) - 1;

    // if the level is less than 1 (the root), or greater than the height, throw IndexOutOfBounds
    if ((level < 1) || (level > this.getHeight())) {
      throw new IndexOutOfBoundsException("Level is not valid for this heap");
    }

    // returnList is the ArrayList we'll return at the end of the method
    ArrayList<String> returnList = new ArrayList<String>();

    // for each index of the level, add that value to returnList if it's not null
    for (int i = firstIndex; i <= Math.min(this.getSize(), lastIndex); i++) {
      if (heap[i] != null) {
        returnList.add(heap[i]);
      }
    }

    return returnList;
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
    System.out.println("HEAP SIZE: " + this.getSize());
    for (int i = 1; i <= size; i++) {
      System.out.println(heap[i].trim());
    }
  }

  /**
   * prints out level-order traversal of the heap - one level per line with nodes delimited by a
   * single whitespace
   */
  public void printLevelOrderTraversal() {

    for (int i = 1; i <= this.getHeight(); i++) {
      ArrayList<String> currentLevel = this.getLevel(i);
      String levelRep = "";
      for (int j = 0; j < currentLevel.size(); j++) {
        levelRep += currentLevel.get(j).trim() + " ";
      }
      System.out.println(levelRep.trim());
    }
  }
}
