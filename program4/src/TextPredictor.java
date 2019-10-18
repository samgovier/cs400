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
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Builds a trie with words from a .txt file, then uses the tree to predict words given a prefix.
 * Prompts the user to look up a prefix and gives how often the words show up. It will keep
 * accepting user input until we get 'qqq'.
 */
public class TextPredictor {
  private static final String FILE_NAME = "harry_potter.txt";

  /**
   * Main method to do all scanning, inputting and printing of prefix lookup.
   * 
   * @param args is a string of command-line arguments
   */
  public static void main(String[] args) {

    // testTree is the Trie tree of words
    TrieTree testTree = new TrieTree();

    // print a short test and insert tests
    System.out
        .println("testing printTrie on a tree with these inserts: yip, yes, pi, pie, yes, ye");
    testTree.insert("yip");
    testTree.insert("yes");
    testTree.insert("pi");
    testTree.insert("pie");
    testTree.insert("yes");
    testTree.insert("ye");
    testTree.printTrie();
    System.out.println("\n\n");

    // create a new tree for the text predictor
    TrieTree tree = new TrieTree();

    // print a welcome prompt
    System.out.println("Welcome to the Text Predictor!");

    try {

      // scan words in file
      Scanner input = new Scanner(new File(FILE_NAME));

      // while thre is still data, add to the Trie tree
      while (input.hasNextLine()) {

        // loop through all lines
        String line = input.nextLine();

        // clean file
        // .replaceAll("[^a-z ]", "") removes all non-alphabetic characters (replaces them with the
        // empty string) and .split("\\s+") separates out the words using whitespace as a delimiter
        String[] words = line.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+");

        // for every word in the words array, if it's not empty, add it to the tree
        for (String word : words) {
          if (!word.equals("")) {
            tree.insert(word);
          }

        }
      }

      // close the scanner one we're out of input
      input.close();

      // print statisticts
      System.out.println(
          "A tree of size " + tree.getSize() + " was created from file \"" + FILE_NAME + "\".");

      // if the tree is small enough, print it
      if (tree.getSize() < 7) {
        tree.printTrie();
      }

      // create a scanner for user input
      input = new Scanner(System.in);

      // request is the filtered string of the user input
      String request = "";

      // while the input doesn't match 'qqq', contiue word lookup
      do {

        // prompt for a word to collect prefixes for
        System.out
            .println("\nEnter a word to auto-complete from the file \"" + FILE_NAME + "\" : ");

        // filter the user input
        request = input.next().toLowerCase().replaceAll("[^a-z ]", "");

        // if the request is 'qqq', exit
        if (request.equals("qqq")) {
          System.out.println("Exiting.");
          break;
        }

        // predictedWords is a collection of all the words with the input prefix
        ArrayList<String> predictedWords = tree.getWordsWithPrefix(request);

        // if there are no words, print no match
        if (predictedWords.isEmpty()) {
          System.out.println("There are no matches for the prefix \"" + request + "\"");
        }

        // else print the words and find the most predicted
        else {

          // maxFrequency is the maximum frequency of the word that shows the most
          int maxFrequency = 0;

          // maxWord is the word that shows the most
          String maxWord = "FIX_ME";

          // find the word with the given prefix that occurs the most in the text.
          for (String word : predictedWords) {
            int frequency = tree.getFrequency(word);

            // if the frequency is higher than the max, set a new max
            if (frequency > maxFrequency) {
              maxFrequency = frequency;
              maxWord = word;
            }

            // if the amount of words is small enough, print the word
            if (predictedWords.size() < 7) {
              System.out.println(word + ": " + frequency);
            }
          }
          System.out.println("The predicted word is: " + maxWord);
        }

      } while (!request.equals("qqq"));

      // close the scanner
      input.close();

    } catch (FileNotFoundException e) {
      // if the file isn't found print failure
      System.out.println("The input file \"" + FILE_NAME + "\" was not found.");
    }

    // exit message
    System.out.println("Thanks for using the Text Predictor!");

  }
}
