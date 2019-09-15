/*
 * implements a min heap of Integer assumes that the root is at index 1
 */
public class IntegerHeap {
  private Integer[] heap;
  private int size;

  public IntegerHeap(int capacity) {
    heap = new Integer[capacity];
    size = 0;
  }

  // returns the index of the parent, left child, right child of the element at index

  /*
   * finds the index of the parent of the element at this index assumes that the root is at index 1
   * 
   * @param index the index of the current element
   * 
   * @return the index of the parent
   */
  private int parent(int index) {
    return index / 2;
  }

  private int leftChild(int index) {
    return index * 2;
  }

  private int rightChild(int index) {
    return (index * 2) + 1;
  }


  // returns true if the element at index has a parent, leftchild, rightchild
  private boolean hasParent(int index) {
    return index != 1;
  }

  private boolean hasLeftChild(int index) {
    return leftChild(index) <= size;
  }

  private boolean hasRightChild(int index) {
    return rightChild(index) <= size;
  }

  // swaps the elements at two indices in the array
  private void swap(Integer[] a, int index1, int index2) {
    Integer temp = a[index1];
    a[index1] = a[index2];
    a[index2] = temp;
  }

  /*
   * inserts element into the heap, maintaining Heap rules assumes that the root is at index 1
   */
  public void insert(Integer element) {
    // check if the heap is full haha
    // if you run out of room, create a new array and copy elements
    // from old array into new array
    // TODO implement a shadow array

    heap[size + 1] = element;
    size++;

    // bubble up
    int current = size;
    while (hasParent(size) && heap[parent(current)] > heap[current]) {
      swap(heap, parent(current), current);
      current = parent(current);
    }
  }

  public String levelOrderTraversal() {
    String result = "";
    for (int i = 1; i <= size; i++) {
      result += heap[i].toString();
    }
    return result.trim();
  }

  public static void main(String[] args) {
    IntegerHeap myHeap = new IntegerHeap(10);
    System.out.println(myHeap.parent(1));

    myHeap.insert(34);
    System.out.println(myHeap.levelOrderTraversal());
  }

}
