package BinarySearchTrees;

import java.util.Random;
import java.util.Scanner;

public class TreeSimulations {
  public static void testManyTrees(
    int randSeed, int treeSize, int randRange, int numTrees) {
    // initial setup
    BSTTree<Integer> tree;
    Random rnd = new Random(randSeed); // Random with seed
    int minHeight = Integer.MAX_VALUE;
    int maxHeight = 0;
    double avgHeight = 0;

    for(int i = 0; i < numTrees; i++)
    {
      tree = new BSTTree<Integer>();

      while(tree.getSize() < treeSize)
      {
        // insert a random integer into the tree
        tree.insert(rnd.nextInt(randRange));
      }

      // after filling, if the tree's height equals its size, print out the tree
      int height = tree.getHeight();
      if (height == tree.getSize()) {
        tree.printSideways();
      }

      minHeight = Math.min(height, minHeight);
      maxHeight = Math.max(height, maxHeight);
      avgHeight = ((avgHeight * (double)i) + (double)height) / ((double)i + 1d);
    }

    // round avgHeight so it displays well
    avgHeight = (double)Math.round(avgHeight * 10000d) / 10000d;

    ///// after all simulated trees made, output statistics
    System.out.println("min height was : " + minHeight);
    System.out.println("max height was : " + maxHeight);
    System.out.println("avg height was : " + avgHeight);
  } // testManyTrees

  public static void main(String[] args) {
    System.out.println("Welcome to the BST Simulator.");
    Scanner scnr = new Scanner(System.in);
    System.out.println("Enter the Random seed number: ");
    int randSeed = scnr.nextInt();
    System.out.println(
      "Enter the number of Integers to be placed in each tree: ");
    int treeSize = scnr.nextInt();
    System.out.println("enter the maximum random integer to be generated: ");
    int randRange = scnr.nextInt();
    System.out.println("enter the number of trees to be simulated: ");
    int numTrees = scnr.nextInt();
    testManyTrees(randSeed, treeSize, randRange, numTrees);
    scnr.close();
  } // main
} // class