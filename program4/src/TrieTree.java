//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Trie Tree
// Files: TrieTree.java, PrefixTreeADT.java, TextPredictor.java
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
import java.util.ArrayList;
// no other import statements allowed

public class TrieTree implements PrefixTreeADT {

  // provide the inner class header and instance variables to students
  /**
   * Private inner node class.
   */
  private class TNode {
    private char letter;
    private boolean isEndOfWord;
    private int endWordCount;
    private ArrayList<TNode> childList;

    private TNode(char letter) {
      this.letter = letter;
      this.isEndOfWord = false;
      this.childList = new ArrayList<TNode>();
    }
  } // inner class

  private final char ROOT = '@';
  private TNode root;
  private int size;

  /**
   * Trie constructor.
   */
  public TrieTree() {
    this.root = new TNode(ROOT); // stands for root node
    this.size = 0;
  }

  /**
   * Inserts a string into the trie and increases the count of the word.
   * New nodes must be inserted in alphabetical order.
   * @param word can be upper or lower case, but must be stored as lower case
   * @throws IllegalArgumentException if input string is null or length is 0
   */
  @Override
  public void insert(String word) {
    if (word == null || word.length() == 0) {
      throw new IllegalArgumentException();
    }

    insert(root, word);
  }

  private void insert(TNode current, String word) {
    // TODO handle alphabetical order, and no calling sort methods!
    char firstLetter = word.charAt(0);
    TNode node = null;
    for (TNode child : current.childList) {
      if (child.letter == firstLetter) {
        node = child;
        break;
      }
    }

    if (null == node) {
      node = new TNode(firstLetter);
      current.childList.add(node);
    }

    if (word.length() == 1) {
      node.isEndOfWord = true;
      node.endWordCount++;
    } else {
      insert(node, word.substring(1));
    }
  }


  /**
   * Gets the size ....An empty trie returns 0.
   * @return size (number of unique words) in the trie.
   */
  @Override
  public int getSize() {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Returns the number of times the word appears in the trie.
   * @param word can be upper or lower case, but is stored as lower case
   * @return the number of occurrences of word (returns 0 if word not present)
   * @throws IllegalArgumentException if input string is null or length is 0
   */
  @Override
  public int getFrequency(String word) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Returns an ArrayList<String> of all words in the trie that have the given prefix.
   * If no words match the prefix, return an empty ArrayList<String>.
   * If an empty string is input, returns all words
   * Must have Order(tree height) efficiency. In other words, you must traverse your trie :)
   * NOTE: if your trie is made correctly, your traversal will produce a sorted list
   *       so you should not need to perform a sorting algorithm on this list
   * @param prefix (if an empty string is entered, returns all words)
   * @return an ArrayList<String> 
   * @throws IllegalArgumentException if the prefix is null
   */
  @Override
  public ArrayList<String> getWordsWithPrefix(String prefix) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Prints the tree to the console.
   */
  public void printTrie() {
    printTrieRecursive(root, "");
  }

  /**
   * Helper method for printTrie().
   * 
   * @param current the current node in the recursive call
   * @param indent  the amount of indent to print this level
   */
  private void printTrieRecursive(TNode current, String indent) {
    if (current != null) {
      if (current.isEndOfWord) {
        System.out.println(indent + current.letter + ":" + current.endWordCount);
      } else {
        System.out.println(indent + current.letter);
      }
      for (TNode child : current.childList) {
        printTrieRecursive(child, indent + "  ");
      }
    }
  }
}