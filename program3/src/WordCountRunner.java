import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title: Red Black Tree
//Files: SearchTreeADT.java, RedBlackTree.java, IllegalKeyException.java, WordCountRunner.java
//Course: Computer Science 400
//
//Author: Sam Govier
//Email: sgovier@wisc.edu
//Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Students who get help from sources other than their partner must fully
//acknowledge and credit those sources of help here. Instructors and TAs do
//not need to be credited here, but tutors, friends, relatives, room mates,
//strangers, and others do. If you received no outside help from either type
//of source, then please explicitly indicate NONE.
//
//Persons: NONE
//Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


public class WordCountRunner {
    private static final String INPUT_FILE = "test.txt";
    
    public static void main(String[] args) {
        RedBlackTree<String, Integer> tree = new RedBlackTree<String, Integer>();
        Scanner scnr;
        scnr = new Scanner(new File(INPUT_FILE));
        
        // loop through all lines
           String line = scnr.nextLine();

                // clean a line
                // .replaceAll("[^a-zA-Z ]", "") removes all non-alphabetic characters (replaces them with the 
                // empty string) and .split("\\s+") separates out the words using whitespace as a delimiter
                
                // this code should work without any modification
                String[] words = line.trim().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
                
                
                // for each word in words that has a length > 0
                   // add word to tree OR if the word already exists increment the count
                
        tree.printSideways();
        // after all words are read in, print out this message
       System.out.println("The tree has a size of " + tree.getSize() + " and a height of " + tree.getHeight());
        
        // prompt user for words until they enter 'qqq'
        // below are lines of code you can use in your program
        
        Scanner input = new Scanner(System.in);
        String request = "";
        System.out.print("Enter a word to search for in \"" + INPUT_FILE + "\": ");
        request = input.next().replaceAll("[^a-zA-Z ]", "").toLowerCase();
        System.out.println("\"" + request + "\" was found in the file " + tree.getValue(request) + " times.");
        System.out.println("Exiting.");
        System.out.println("\"" + request + "\" was not found in the given file.");
    
    }
}