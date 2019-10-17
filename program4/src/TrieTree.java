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

/**
 * TrieTree class contains the object definition for a Trie Tree. The tree is initialized empty, and
 * each word must be inserted. getSize returns the number of words, getFreqency will return the
 * amount a word is used, and getWordsWithPrefix will get the words with a given prefix. printTrie
 * will print the trie tree to console.
 */
public class TrieTree implements PrefixTreeADT {

  /**
   * Inner class representing a Trie node, with the letter char element and other details.
   *
   */
  private class TNode {

    // letter is the letter value of the node
    private char letter;

    // isEndOfWord marks if this node is the end of a word
    private boolean isEndOfWord;

    // endWordCount marks how many times this word is used
    private int endWordCount;

    // child list is the array of children to this node
    private ArrayList<TNode> childList;

    // this constructor sets the letter value, defaults isEndOfWord, and initializes the child list
    private TNode(char letter) {
      this.letter = letter;
      this.isEndOfWord = false;
      this.childList = new ArrayList<TNode>();
    }
  }

  // ROOT is the standard root char
  private final char ROOT = '@';

  // ROOT is the root node of the Trie tree
  private TNode root;

  // size is the amount of unique words
  private int size;

  /**
   * Trie constructor, creates the root node and sets size to zero
   */
  public TrieTree() {
    this.root = new TNode(ROOT); // stands for root node
    this.size = 0;
  }

  /**
   * Inserts a string into the trie and increases the count of the word. New nodes are inserted in
   * alphabetical order.
   * 
   * @param word to be stored. can be upper or lower case, but must be stored as lower case
   * @throws IllegalArgumentException if input string is null or length is 0
   */
  @Override
  public void insert(String word) throws IllegalArgumentException {

    // if word is badly formatted, throw IllegalArgumentException
    if (word == null || word.length() == 0) {
      throw new IllegalArgumentException("Input string must not be null or length 0");
    }

    // start recursion. pass the word as lower case to store it as such
    insert(root, word.toLowerCase());
  }

  /**
   * Adds each letter of a string into the trie per Trie Tree rules
   * 
   * @param current is the current node of this tree
   * @param word    is the current word being added under the current node
   */
  private void insert(TNode current, String word) {

    // firstLetter is the first letter of the word
    char firstLetter = word.charAt(0);

    // set node to null
    TNode node = null;

    // search through the child list for the current letter
    for (TNode child : current.childList) {
      if (child.letter == firstLetter) {
        node = child;
        break;
      }
    }

    // if we're still null after going through the child list, add the new letter
    if (null == node) {

      // create the new node
      node = new TNode(firstLetter);

      // if the childList isn't empty, find the alphabetically correct placement
      if (!current.childList.isEmpty()) {

        // loop through the child list to add the element
        for (int i = 0; i <= current.childList.size(); i++) {

          // if we're at the end of the list or the adding letter is less than the current node,
          // add the node at this index and break out of the loop
          if ((i == current.childList.size()) || (node.letter < current.childList.get(i).letter)) {
            current.childList.add(i, node);
            break;
          }
        }
      }

      // if the child list is empty, just add the node directly
      else {
        current.childList.add(node);
      }
    }

    // if we're at the end of the word, mark the node as endOfWord and skip recursion
    if (word.length() == 1) {
      if (node.isEndOfWord == false) {
        node.isEndOfWord = true;
        size++;
      }
      node.endWordCount++;
    }

    // else, continue recursion
    else {
      insert(node, word.substring(1));
    }
  }


  /**
   * this method returns the size of the tree
   * 
   * @return the number of unique words stored in the tree
   */
  @Override
  public int getSize() {
    return size;
  }


  /**
   * Returns the number of times the word appears in the trie.
   * 
   * @param word can be upper or lower case, but is stored as lower case
   * @return the number of occurrences of word (returns 0 if word not present)
   * @throws IllegalArgumentException if input string is null or length is 0
   */
  @Override
  public int getFrequency(String word) throws IllegalArgumentException {

    // if word is badly formatted, throw IllegalArgumentException
    if (word == null || word.length() == 0) {
      throw new IllegalArgumentException("Input string must not be null or length 0");
    }

    // start recursion. pass the word as lower case to search it as such
    return getFrequency(root, word.toLowerCase());
  }

  /**
   * recursive helper function to get the times the word appears in the trie.
   * 
   * @param current is the current node being checked
   * @param word    is the word to search the trie for
   * @return the frequency of the word
   */
  private int getFrequency(TNode current, String word) {

    // firstLetter is the first letter of the word
    char firstLetter = word.charAt(0);

    // set node to null
    TNode node = null;

    // search through the childList for the current letter
    for (TNode child : current.childList) {
      if (child.letter == firstLetter) {
        node = child;
        break;
      }
    }

    // if the node is null, the word isn't present. Return zero
    if (null == node) {
      return 0;
    }

    // if this is the last letter, return endWordCount here
    if (word.length() == 1) {
      return node.endWordCount;
    }

    // else continue recursion
    return getFrequency(node, word.substring(1));
  }

  /**
   * Returns an ArrayList<String> of all words in the trie that have the given prefix. If no words
   * match the prefix, return an empty ArrayList<String>. If an empty string is input, returns all
   * words. Must have Order(tree height) efficiency. In other words, you must traverse your trie :)
   * NOTE: if your trie is made correctly, your traversal will produce a sorted list so you should
   * not need to perform a sorting algorithm on this list
   * 
   * @param prefix (if an empty string is entered, returns all words)
   * @return an ArrayList<String>
   * @throws IllegalArgumentException if the prefix is null
   */
  @Override
  public ArrayList<String> getWordsWithPrefix(String prefix) throws IllegalArgumentException {

    // if word is null, throw IllegalArgumentException
    if (prefix == null) {
      throw new IllegalArgumentException("Input string must not be null");
    }

    // if the prefix is empty, return all words in the Trie tree
    if (prefix.length() == 0) {

      // create the wordsAtNode ArrayList to pass and populate
      ArrayList<String> wordsAtNode = new ArrayList<String>();

      // call collectWordsAtNode for each child, and then return the arrayList
      for (TNode child : root.childList) {
        collectWordsAtNode(child, prefix, wordsAtNode);
      }
      return wordsAtNode;
    }

    // start recursion. set prefix to lower case to search as such
    return getWordsWithPrefix(root, prefix.toLowerCase(), prefix.toLowerCase());
  }

  /**
   * recursive helper function for getWordsWithPrefix to get to the end of the prefix and begin
   * collecting words
   * 
   * @param current      is the current node being searched
   * @param prefix       is the current prefix per the recursive search
   * @param originPrefix is the original prefix, used for appending the full words
   * @return a string Array with the words prefix. if there are no words with the prefix, return an
   *         empty arrayList
   */
  private ArrayList<String> getWordsWithPrefix(TNode current, String prefix, String originPrefix) {

    // firstLetter is the first letter of the word
    char firstLetter = prefix.charAt(0);

    // set node to null
    TNode node = null;

    // search through the childList for the current letter
    for (TNode child : current.childList) {
      if (child.letter == firstLetter) {
        node = child;
        break;
      }
    }

    // if the node is null, no words match this prefix, return an empty arrayList
    if (null == node) {
      return new ArrayList<String>();
    }

    // if this is the end of the prefix, start collecting words
    if (prefix.length() == 1) {

      // create the wordsAtNode ArrayList to pass and populate
      ArrayList<String> wordsAtNode = new ArrayList<String>();

      // if this node is the end of a word, add it to the list
      if (node.isEndOfWord) {
        wordsAtNode.add(originPrefix);
      }

      // call collectWordsAtNode for each child, and then return the arrayList
      for (TNode child : node.childList) {
        collectWordsAtNode(child, originPrefix, wordsAtNode);
      }
      return wordsAtNode;
    }

    // else continue recursion
    return getWordsWithPrefix(node, prefix.substring(1), originPrefix);
  }

  /**
   * This helper function collects all words at this node, checking each node if it's the end of a
   * word and adding it if so
   * 
   * @param current      is the current node being checked as an endOfWord
   * @param word         is the current constructed full word
   * @param wordsAtNode  is the list which is used for collecting all the words at the node
   * @param originPrefix is the original prefix to append at the beginning for the full word
   */
  private void collectWordsAtNode(TNode current, String word, ArrayList<String> wordsAtNode) {

    // if this node is the end of a word, add the constructed word to the wordsAtNode arraylist
    if (current.isEndOfWord) {
      wordsAtNode.add(word + current.letter);
    }

    // for any children of this node, call collectWordsAtNode recursively,
    // adding the current letter to the word
    for (TNode child : current.childList) {
      collectWordsAtNode(child, word + current.letter, wordsAtNode);
    }
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
