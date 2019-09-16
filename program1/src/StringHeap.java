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
 * and each node is inserted. Priority is considered using first length, then vowel amount, then
 * alphabetical. The top node can be peeked or removed, and other details on height and size can
 * retrieved or printed.
 */
public class StringHeap {

  // heap is the backing array for the string heap
  private String[] heap;

  // size is the amount of elements in the heap
  private int size;

  // VOWELS_ARRAY is the un-modifiable vowels array for use in prioritization
  private static final char[] VOWELS_ARRAY = new char[] {'a', 'e', 'i', 'o', 'u'};

  /**
   * this constructor creates the empty heap with size 0
   * 
   * @param capacity is the initial heap array capacity
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

  /**
   * this method returns the parent of a given index
   * 
   * @param index is the index of the subject child
   * @return the index of that child's parent
   */
  private int parent(int index) {
    return index / 2;
  }

  /**
   * this method returns if the given index has a parent. As long as it's below the root, it has a
   * parent
   * 
   * @param index is the index of the subject child
   * @return whether that child has a parent
   */
  private boolean hasParent(int index) {
    return index > 1;
  }

  /**
   * this method swaps the values of two indexes of an array.
   * 
   * @param modifyingArray is the array to be modified
   * @param index1         is to be switched with index2
   * @param index2         is to be switched with index1
   */
  private void swap(String[] modifyingArray, int index1, int index2) {

    // assign index1 to a temp value, swap and then assign index2 to the temp
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
  public void add(String value) throws IllegalArgumentException {

    // if the value is null or empty, throw IllegalArgumentException
    if ((value == null) || value == "") {
      throw new IllegalArgumentException("Input string is not a valid element");
    }

    // if the heap is full, create a new heap that is 2x the size
    if (size >= heap.length - 1) {
      heap = Arrays.copyOf(heap, heap.length * 2);
    }

    // increment the size and place the new value
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
   * this method returns the left child of a given index
   * 
   * @param index is the index of the node
   * @return the index of that node's left child
   */
  private int leftChild(int index) {
    return index * 2;
  }

  /**
   * this method returns the right child of a given index
   * 
   * @param index is the index of the node
   * @return the index of that node's right child
   */
  private int rightChild(int index) {
    return (index * 2) + 1;
  }

  /**
   * this method returns if the given index has a left child. As long as the left child index is
   * lower than size, it has a left child.
   * 
   * @param index is the index of the node
   * @return whether that node has a left child
   */
  private boolean hasLeftChild(int index) {
    return leftChild(index) <= size;
  }

  /**
   * this method returns if the given index has a right child. As long as the right child index is
   * lower than size, it has a right child.
   * 
   * @param index is the index of the node
   * @return whether that node has a right child
   */
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

    // if the heap is empty, return null
    if (this.isEmpty()) {
      return null;
    }

    // set our current location to 1 in the heap
    int current = 1;

    // set the return string to the current value
    String returnString = heap[current];

    // swap with the child as the end and set the end to null, decrement size
    heap[current] = heap[size];
    heap[size] = null;
    size--;

    // while the current node has a child (if it has a child, it must be left, because complete)
    while (hasLeftChild(current)) {

      // if the node has no right child or the left child is higher or equal in priority than the
      // right, swap with the left child and move the current location to the left child
      if (!hasRightChild(current)
          || ((StringHeap.prioritize(heap[leftChild(current)], heap[rightChild(current)])) >= 0)) {
        this.swap(heap, current, leftChild(current));
        current = leftChild(current);
      }

      // if the right child has higher priority, swap with the right child and move location
      else if ((StringHeap.prioritize(heap[rightChild(current)], heap[leftChild(current)])) > 0) {
        this.swap(heap, current, rightChild(current));;
        current = rightChild(current);
      }
    }

    // bubbling is finished; return the return string
    return returnString;
  }

  /**
   * this method returns whether the heap is empty or not
   * 
   * @return true if the heap has no elements, false otherwise
   */
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * this method returns the size of the heap
   * 
   * @return the number of Strings stored in the heap
   */
  public int getSize() {
    return size;
  }

  /**
   * this method returns the greatest element without removing it
   * 
   * @return the element with the highest priority but do not remove it
   */
  public String peek() {
    return heap[1];
  }

  /**
   * calculates the height (the number of levels past the root); an empty heap has a height of 0 a
   * heap with just a root has a height of 1
   * 
   * Each new level can be represented by a power of 2, so taking the logarithm of the size will
   * give us the highest level
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
   * heap will result in returning an empty ArrayList
   * 
   * @param level is the level to collect elements from
   * @throws IndexOutOfBoundsException if level is not appropriate for this heap
   * @return a new String ArrayList that contains only the Strings at this level
   */
  public ArrayList<String> getLevel(int level) throws IndexOutOfBoundsException {

    // returnList is the ArrayList we'll return at the end of the method
    ArrayList<String> returnList = new ArrayList<String>();

    // if the heap is empty, return an empty ArrayList
    if (this.isEmpty()) {
      return returnList;
    }
    // if the level is less than 1 (the root), or greater than the height, throw IndexOutOfBounds
    if ((level < 1) || (level > this.getHeight())) {
      throw new IndexOutOfBoundsException("Level is not valid for this heap");
    }

    // firstIndex is the first index of the level: 2^(level-1)
    int firstIndex = (int) Math.pow(2, level - 1);

    // lastIndex is the last index, which is just the first index of the next level - 1
    int lastIndex = (int) Math.pow(2, level) - 1;

    // for each index of the level, add that value to returnList if it's not null
    for (int i = firstIndex; i <= Math.min(this.getSize(), lastIndex); i++) {
      if (heap[i] != null) {
        returnList.add(heap[i]);
      }
    }

    // return the full ArrayList
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

    // if the heap is empty, return null
    if (this.isEmpty()) {
      return null;
    }

    // otherwise return a copy of the heap via copyOf()
    return Arrays.copyOf(heap, heap.length);
  }

  /**
   * prints out heap size, then prints out the heap elements - one per line following the indexing
   * of the heap array
   */
  public void printHeap() {

    // print the heap size
    System.out.println("HEAP SIZE: " + this.getSize());

    // for each element of the heap array, print it on it's own line
    for (int i = 1; i <= size; i++) {
      System.out.println(heap[i].trim());
    }
  }

  /**
   * prints out level-order traversal of the heap - one level per line with nodes delimited by a
   * single whitespace
   */
  public void printLevelOrderTraversal() {

    // for each level to the height of the heap, construct and print the level
    for (int i = 1; i <= this.getHeight(); i++) {

      // currentLevel is an arrayList of the level
      ArrayList<String> currentLevel = this.getLevel(i);

      // levelRep is a string representing the level, space-delimited
      String levelRep = "";

      // for each string on this level, add it and a space to levelRep
      for (int j = 0; j < currentLevel.size(); j++) {
        levelRep += currentLevel.get(j).trim() + " ";
      }

      // print out the level
      System.out.println(levelRep);
    }
  }
}
