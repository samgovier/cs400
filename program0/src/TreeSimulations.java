//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Binary Search Trees
// Files: BSTNode.java, BSTTree.java, TreeSimulations.java
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

import java.util.Random;
import java.util.Scanner;

/**
 * TreeSimulations contains the static test method for creating many trees and computing statistics
 * on minimum, maximum, and average height. It also contains a main method for collecting parameters
 * to run the test method, then runs it.
 */
public class TreeSimulations {

  /**
   * this method generates numTrees amount of trees to test minimum, maximum, and average height
   * 
   * @param randSeed  is the seed of the randomness for the inserted integers
   * 
   * @param treeSize  is the maximum tree size allowed
   * 
   * @param randRange is the range of numbers to pull from
   * 
   * @param numTrees  is the amount of trees to generate
   */
  public static void testManyTrees(int randSeed, int treeSize, int randRange, int numTrees) {

    // tree is the value we'll use to store each random tree
    BSTTree<Integer> tree;

    // rnd is the random value seeded for insert generation
    Random rnd = new Random(randSeed);

    // minHeight is the minimum height of all random trees
    int minHeight = Integer.MAX_VALUE;

    // maxHeight is the maximum height of all random trees
    int maxHeight = 0;

    // avgHeight is the average height of all random trees
    double avgHeight = 0;

    // generate numTrees trees to get statistics from
    for (int i = 0; i < numTrees; i++) {
      tree = new BSTTree<Integer>();

      // insert values until the tree is the correct size
      while (tree.getSize() < treeSize) {
        // insert a random integer into the tree
        tree.insert(rnd.nextInt(randRange));
      }

      // after filling, if the tree's height equals its size, print out the tree
      int height = tree.getHeight();
      if (height == tree.getSize()) {
        tree.printSideways();
      }

      // change minHeight and maxHeight if the new value is a new minimum or maximum
      minHeight = Math.min(height, minHeight);
      maxHeight = Math.max(height, maxHeight);

      // do the math to add in the new value and re-compute the average
      avgHeight = ((avgHeight * (double) i) + (double) height) / ((double) i + 1D);
    }

    // round avgHeight so it displays well
    avgHeight = (double) Math.round(avgHeight * 10000D) / 10000D;

    // after all simulated trees made, output statistics
    System.out.println("min height was : " + minHeight);
    System.out.println("max height was : " + maxHeight);
    System.out.println("avg height was : " + avgHeight);
  }

  /**
   * This is the main method for receiving parameters about testManyTrees and then running it.
   * 
   * @param args is a string of command-line arguments
   */
  public static void main(String[] args) {
    System.out.println("Welcome to the BST Simulator.");

    // create input scanner
    Scanner scnr = new Scanner(System.in);

    // get all input values
    System.out.println("Enter the Random seed number: ");
    int randSeed = scnr.nextInt();
    System.out.println("Enter the number of Integers to be placed in each tree: ");
    int treeSize = scnr.nextInt();
    System.out.println("Enter the maximum random integer to be generated: ");
    int randRange = scnr.nextInt();
    System.out.println("Enter the number of trees to be simulated: ");
    int numTrees = scnr.nextInt();

    // call testManyTrees with the input values
    testManyTrees(randSeed, treeSize, randRange, numTrees);

    // close the input scanner
    scnr.close();
  }
}
