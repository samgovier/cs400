//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Final Project
// Files: BTree.java, BtreeADT.java, BTreeTests.java,
// DuplicateKeyException.java, KeyNotFoundException.java, NullKeyException.java,
// Main.java, MainHelper.java, hrtoon.java
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
package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * BTree is the class for building a 2-3 B-Tree. It includes options for inserting, removing,
 * getting the value and other data on the tree.
 *
 * @param <K> is the key value that items are sorted according to
 * @param <V> is the value stored with a corresponding key
 */
public class BTree<K extends Comparable<K>, V> implements BTreeADT<K, V> {

  /**
   * BNode is an internal class for tracking nodes in the b-tree
   *
   */
  private class BNode {

    // keyList is the list of keys in this node
    private ArrayList<K> keyList;

    // valueMap is the map of keys to values
    private HashMap<K, V> valueMap;

    // childrenList is the list of children in this node
    private ArrayList<BNode> childrenList;

    // this constructor initializes the internal values
    private BNode() {
      this.keyList = new ArrayList<K>();
      this.valueMap = new HashMap<K, V>();
      this.childrenList = new ArrayList<BNode>();
    }

    /**
     * this recursive method finds the height from this node as the root
     * 
     * @return the height of the tree as an integer. If the node has no children, return 1
     */
    private int getHeight() {
      // if there are no children, return 1
      if (this.childrenList.size() == 0) {
        return 1;
      }

      // maxHeight marks the maximum height from this node
      int maxHeight = 0;

      // for each child, get the height of that child, and set maxHeight if maxHeight is less
      for (BNode child : this.childrenList) {
        maxHeight = Math.max(maxHeight, child.getHeight());
      }

      // return the largest child plus 1
      return 1 + maxHeight;
    }

    /**
     * this recursive method finds all the keys from this node as the root
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList<K> getAllKeys() {
      // if there are no children, simply return the keylist
      if (this.childrenList.size() == 0) {
        return keyList;
      }

      // copy the keys here into the child keys
      ArrayList<K> childKeys = (ArrayList<K>) keyList.clone();

      // call this method recursively to get all children keys
      for (BNode child : this.childrenList) {
        childKeys.addAll(child.getAllKeys());
      }

      // return the full list
      return childKeys;
    }
  }

  // root is the root of the tree
  private BNode root;

  // size is the amount of keys in the tree
  private int size;

  /**
   * This constructor simply initializes the root as null and size to zero
   */
  public BTree() {
    root = null;
    size = 0;
  }

  /**
   * insert the key/value pair into the BTree, following 2-3 B-Tree rules.
   * 
   * @param key   is the key for inserting and searching the B-Tree
   * @param value is the value to match with the key
   * @throws DuplicateKeyException if the key already exists
   * @throws NullKeyException      if the key is null
   */
  @Override
  public void insert(K key, V value) throws DuplicateKeyException, NullKeyException {

    // if the key is null, throw nullKeyException
    if (key == null) {
      throw new NullKeyException("The passed key is null.");
    }

    // if the root is null, create the first BNode and add key and value
    if (null == root) {
      root = new BNode();
      root.keyList.add(key);
      root.valueMap.put(key, value);
      size++;
    }

    // else, start recursing to find the location to insert, then rebalance
    else {
      root = insert(key, value, root);
      root = reBalanceInsert(root);
    }

    // final rebalance step: if the root has too many keys, create new root and split
    if (root.keyList.size() > 2) {
      // get keys and values to move
      K keyToMove = root.keyList.remove(1);
      V valToMove = root.valueMap.remove(keyToMove);

      BNode newRoot = new BNode();
      // add them to parent
      newRoot.keyList.add(keyToMove);
      newRoot.valueMap.put(keyToMove, valToMove);

      // split keys and values
      K keyToSplit = root.keyList.remove(1);
      V valToSplit = root.valueMap.remove(keyToSplit);
      BNode newChild = new BNode();
      newChild.keyList.add(keyToSplit);
      newChild.valueMap.put(keyToSplit, valToSplit);

      // if there are children, include them in split: move to new child
      if (root.childrenList.size() > 0) {
        newChild.childrenList.add(root.childrenList.remove(3));
        newChild.childrenList.add(0, root.childrenList.remove(2));
      }

      // add the children to the new root and set it
      newRoot.childrenList.add(root);
      newRoot.childrenList.add(newChild);
      root = newRoot;
    }
  }

  /**
   * This method is a recursive method for inserting within the children of the tree, following
   * B-Tree methodology
   * 
   * @param key     is the key to insert
   * @param value   is the value to match with corresponding key
   * @param current is the current node of the recursion
   * @return the current node modified
   * @throws DuplicateKeyException if the key already exists
   */
  private BNode insert(K key, V value, BNode current) throws DuplicateKeyException {

    // if the key exists, throw DuplicateKeyException
    if (current.keyList.contains(key)) {
      throw new DuplicateKeyException("Key already exists in Tree.");
    }

    // if there are 2 children, find the right child to insert to
    if (current.childrenList.size() == 2) {
      K singleKey = current.keyList.get(0);

      // go right and rebalance
      if (key.compareTo(singleKey) > 0) {
        current.childrenList.set(1, insert(key, value, current.childrenList.get(1)));
        current = reBalanceInsert(current);
      }

      // go left and rebalance
      else {
        current.childrenList.set(0, insert(key, value, current.childrenList.get(0)));
        current = reBalanceInsert(current);
      }
    }

    // if there are 3 children, find the right child to insert to
    else if (current.childrenList.size() == 3) {

      // go left and rebalance
      if (key.compareTo(current.keyList.get(0)) < 0) {
        current.childrenList.set(0, insert(key, value, current.childrenList.get(0)));
        current = reBalanceInsert(current);
      }

      // go right and rebalance
      else if (key.compareTo(current.keyList.get(1)) > 0) {
        current.childrenList.set(2, insert(key, value, current.childrenList.get(2)));
        current = reBalanceInsert(current);
      }

      // go center and rebalance
      else {
        current.childrenList.set(1, insert(key, value, current.childrenList.get(1)));
        current = reBalanceInsert(current);
      }
    }

    // if we've reached this point, we're in a leaf. Insert
    else {
      current.keyList.add(key);
      Collections.sort(current.keyList);
      current.valueMap.put(key, value);
      size++;
    }

    return current;
  }

  /**
   * This is a helper rebalance method for after insertion is complete
   * 
   * @param current is the current node that needs to be rebalanced
   * @return the rebalanced node
   */
  private BNode reBalanceInsert(BNode current) {

    // for each child, if the child is too large, split
    for (int i = current.childrenList.size() - 1; i >= 0; i--) {
      BNode child = current.childrenList.get(i);
      if (child.keyList.size() > 2) {
        split(current, child);
      }
    }

    return current;
  }

  /**
   * This is a helper split method for when a child is too big
   * 
   * @param current is the current parent node that will have new children
   * @param child   is the child that is too big
   */
  private void split(BNode current, BNode child) {
    // get keys and values to move
    K keyToMove = child.keyList.remove(1);
    V valToMove = child.valueMap.remove(keyToMove);

    // add them to parent
    current.keyList.add(keyToMove);
    current.valueMap.put(keyToMove, valToMove);
    Collections.sort(current.keyList);

    // split keys and values
    K keyToSplit = child.keyList.remove(1);
    V valToSplit = child.valueMap.remove(keyToSplit);
    BNode newChild = new BNode();
    newChild.keyList.add(keyToSplit);
    newChild.valueMap.put(keyToSplit, valToSplit);

    // if there are children, include them in split: move to new child
    if (child.childrenList.size() > 0) {
      newChild.childrenList.add(child.childrenList.remove(3));
      newChild.childrenList.add(0, child.childrenList.remove(2));
    }

    // find the index of the child and add the new child next to it
    int childIndex = current.childrenList.indexOf(child);
    current.childrenList.add(childIndex + 1, newChild);
  }

  /**
   * remove the key/value pair from the BTree, following 2-3 B-Tree rules.
   * 
   * @param key is the key of the object to remove
   * @throws KeyNotFoundException if the key can't be found
   * @throws NullKeyException     if the key is null
   */
  @Override
  public void remove(K key) throws KeyNotFoundException, NullKeyException {

    // if the key is null, return NullKeyException
    if (key == null) {
      throw new NullKeyException("The passed key is null.");
    }

    // if the tree is empty, there's nothing, return keyNotFound
    if (null == root) {
      throw new KeyNotFoundException("Key not found.");
    }

    // start searching for the key to remove, then rebalance
    root = remove(key, root);
    root = reBalanceRemove(root);

    // final rebalance: if the root is empty and removed from, set the child to root
    if ((root.keyList.size() == 0) && (root.childrenList.size() == 1)) {
      root = root.childrenList.get(0);
    }
  }

  /**
   * helper recursive function for the remove. Find the key to remove and remove, maintaining 2-3
   * B-Tree rules.
   * 
   * @param key     is the key to remove
   * @param current is the current node in the recursion
   * @return the modified node after removal
   * @throws KeyNotFoundException if the key wasn't found
   */
  private BNode remove(K key, BNode current) throws KeyNotFoundException {

    // if we successfully remove, the key is here. Finish remove and start reBalance
    if (current.keyList.remove(key)) {
      current.valueMap.remove(key);
      size--;

      // swap with inOrderPredecessor (given there are any children)
      current = swapWithInOrderPred(key, current, current);
      current = reBalanceRemove(current);
    }

    // if there are 2 children, find the right child to recurse
    else if (current.childrenList.size() == 2) {
      K singleKey = current.keyList.get(0);

      // go right and rebalance
      if (key.compareTo(singleKey) > 0) {
        current.childrenList.set(1, remove(key, current.childrenList.get(1)));
        current = reBalanceRemove(current);
      }

      // go left and rebalance
      else {
        current.childrenList.set(0, remove(key, current.childrenList.get(0)));
        current = reBalanceRemove(current);
      }
    }

    // if there are 3 children, find the right child to recurse
    else if (current.childrenList.size() == 3) {

      // go left and rebalance
      if (key.compareTo(current.keyList.get(0)) < 0) {
        current.childrenList.set(0, remove(key, current.childrenList.get(0)));
        current = reBalanceRemove(current);
      }

      // go right and rebalance
      else if (key.compareTo(current.keyList.get(1)) > 0) {
        current.childrenList.set(2, remove(key, current.childrenList.get(2)));
        current = reBalanceRemove(current);
      }

      // go center and rebalance
      else {
        current.childrenList.set(1, remove(key, current.childrenList.get(1)));
        current = reBalanceRemove(current);
      }
    }

    // if we've reached this point, we're in a leaf. Key not found
    else {
      throw new KeyNotFoundException("Key not found.");
    }

    return current;
  }

  /**
   * helper function to find and swap the lacking node with the InOrder Predecessor and rebalance.
   * 
   * @param key     is the key that was removed
   * @param current is the current node in the recursion
   * @param toSwap  is the node that needs the InOrder Predecessor
   * @return the modified current node
   */
  private BNode swapWithInOrderPred(K key, BNode current, BNode toSwap) {

    // if there are two children, find the InOrderPredecessor
    if (current.childrenList.size() == 2) {

      // if current and toSwap are the same, go left. Otherwise, go right.
      if (current == toSwap) {

        // nextNode is the next node to check for InOrder predecessor
        BNode nextNode = current.childrenList.get(0);

        // if there are children in the next node, recurse. Otherwise swap
        if (nextNode.childrenList.size() > 0) {
          current.childrenList.set(0, swapWithInOrderPred(key, nextNode, toSwap));
        } else {
          K keyToMove = nextNode.keyList.remove(nextNode.keyList.size() - 1);
          V valToMove = nextNode.valueMap.remove(keyToMove);
          toSwap.keyList.add(keyToMove);
          toSwap.valueMap.put(keyToMove, valToMove);
          Collections.sort(toSwap.keyList);
        }
      } else {

        // nextNode is the next node to check for InOrder predecessor
        BNode nextNode = current.childrenList.get(1);

        // if there are children in the next node, recurse. Otherwise swap
        if (nextNode.childrenList.size() > 0) {
          current.childrenList.set(1, swapWithInOrderPred(key, nextNode, toSwap));
        } else {
          K keyToMove = nextNode.keyList.remove(nextNode.keyList.size() - 1);
          V valToMove = nextNode.valueMap.remove(keyToMove);
          toSwap.keyList.add(keyToMove);
          toSwap.valueMap.put(keyToMove, valToMove);
          Collections.sort(toSwap.keyList);
        }
      }

      // finally, rebalance
      current = reBalanceRemove(current);

    }

    // if there are 3 children, find the InOrderPredecessor
    else if (current.childrenList.size() == 3) {

      // if current and toSwap are the same, go left or center. Otherwise, go right.
      if (current == toSwap) {

        // nextNode is the next node to check for InOrder predecessor
        BNode nextNode;
        if (key.compareTo(current.keyList.get(0)) > 0) {
          nextNode = current.childrenList.get(1);
        } else {
          nextNode = current.childrenList.get(0);
        }

        // if there are children in the next node, recurse. Otherwise swap
        if (nextNode.childrenList.size() > 0) {
          current.childrenList.set(current.childrenList.indexOf(nextNode),
              swapWithInOrderPred(key, nextNode, toSwap));
        } else {
          K keyToMove = nextNode.keyList.remove(nextNode.keyList.size() - 1);
          V valToMove = nextNode.valueMap.remove(keyToMove);
          toSwap.keyList.add(keyToMove);
          toSwap.valueMap.put(keyToMove, valToMove);
          Collections.sort(toSwap.keyList);
        }

      } else {

        // nextNode is the next node to check for InOrder predecessor
        BNode nextNode = current.childrenList.get(2);

        // if there are children in the next node, recurse. Otherwise swap
        if (nextNode.childrenList.size() > 0) {
          current.childrenList.set(2, swapWithInOrderPred(key, nextNode, toSwap));
        } else {
          K keyToMove = nextNode.keyList.remove(nextNode.keyList.size() - 1);
          V valToMove = nextNode.valueMap.remove(keyToMove);
          toSwap.keyList.add(keyToMove);
          toSwap.valueMap.put(keyToMove, valToMove);
          Collections.sort(toSwap.keyList);
        }
      }

      // finally, rebalance
      current = reBalanceRemove(current);

    }

    return current;
  }

  /**
   * This is a helper balancing function that handles every possible case of an unbalanced 2-3
   * B-Tree to bring it back to proper balance.
   * 
   * @param current is the current node to be rebalanced
   * @return the current node, rebalanced
   */
  private BNode reBalanceRemove(BNode current) {

    // if there are 3 children, check for nodes to remove and rebalance
    if (current.childrenList.size() == 3) {

      // check the current child list and remove any empty children
      for (int i = current.childrenList.size() - 1; i >= 0; i--) {
        BNode toCheck = current.childrenList.get(i);
        if (toCheck.keyList.size() == 0 && toCheck.childrenList.size() == 0) {
          current.childrenList.remove(i);
        }
      }

      // if we successfully removed children, rebalance
      if (current.childrenList.size() != 3) {

        // get keys of current and all children to compare and see what keys live in the parent
        ArrayList<K> curKeys = (ArrayList<K>) current.keyList.clone();
        for (BNode child : current.childrenList) {
          curKeys.addAll(child.keyList);
        }
        Collections.sort(curKeys);

        // if the highest key is in the parent, remove it, add to highest child
        if (current.keyList.contains(curKeys.get(curKeys.size() - 1))) {
          BNode toAddNode = current.childrenList.get(1);
          K keyToAdd = current.keyList.remove(1);
          toAddNode.keyList.add(keyToAdd);
          Collections.sort(toAddNode.keyList);
          toAddNode.valueMap.put(keyToAdd, current.valueMap.remove(keyToAdd));

          // if the highest child is now too big, split
          if (toAddNode.keyList.size() > 2) {
            split(current, toAddNode);
          }
        }

        // otherwise, remove the lowest key in the parent, add to lowest child
        else {
          BNode toAddNode = current.childrenList.get(0);
          K keyToAdd = current.keyList.remove(0);
          toAddNode.keyList.add(keyToAdd);
          Collections.sort(toAddNode.keyList);
          toAddNode.valueMap.put(keyToAdd, current.valueMap.remove(keyToAdd));

          // if the lowest child is now too big, split
          if (toAddNode.keyList.size() > 2) {
            split(current, toAddNode);
          }
        }
      }
    }

    // if there are 2 children, check for nodes to remove and rebalance
    else if (current.childrenList.size() == 2) {

      // check the current child list and remove any empty children
      for (int i = current.childrenList.size() - 1; i >= 0; i--) {
        BNode toCheck = current.childrenList.get(i);
        if (toCheck.keyList.size() == 0 && toCheck.childrenList.size() == 0) {
          current.childrenList.remove(i);
        }
      }

      // if we successfully removed children, rebalance
      if (current.childrenList.size() != 2) {

        // move the parent key and value into the child
        BNode currentChild = current.childrenList.get(0);
        K keyToMove = current.keyList.remove(0);
        V valToMove = current.valueMap.remove(keyToMove);
        currentChild.keyList.add(keyToMove);
        currentChild.valueMap.put(keyToMove, valToMove);
        Collections.sort(currentChild.keyList);

        // if the child is too big, split for proper balance
        if (currentChild.keyList.size() > 2) {
          split(current, currentChild);
        }
        // else leave it alone:
        // we'll take care of it on the next stop up recursion in the below for loop
      }
    }

    // recurse through the children to see if there is a child to be fixed
    // a child needs to be fixed if it is empty and has only one child left
    for (int i = current.childrenList.size() - 1; i >= 0; i--) {

      BNode fixerChild = current.childrenList.get(i);

      // if this child actually needs fixing, begin rebalancing
      if ((fixerChild.keyList.size() == 0) && (fixerChild.childrenList.size() == 1)) {

        // set keyToMove and valToMove to be used in the below sets
        K keyToMove;
        V valToMove;

        // if the index is 0
        if (i == 0) {

          // get the right sibling to share with
          BNode sibling = current.childrenList.get(1);

          // if the sibling has enough keys to share, move sibling key to the parent and
          // re-distribute
          if (sibling.keyList.size() > 1) {
            keyToMove = sibling.keyList.remove(0);
            valToMove = sibling.valueMap.remove(keyToMove);

            current.keyList.add(keyToMove);
            current.valueMap.put(keyToMove, valToMove);
            Collections.sort(current.keyList);

            keyToMove = current.keyList.remove(0);
            valToMove = current.valueMap.remove(keyToMove);

            fixerChild.keyList.add(keyToMove);
            fixerChild.valueMap.put(keyToMove, valToMove);
            Collections.sort(fixerChild.keyList);

            // move child over to maintain 2-3 structure
            fixerChild.childrenList.add(sibling.childrenList.remove(0));
          }

          // if the sibling only has one key, move parent key into sibling and add fixerChildChild
          // to sibling
          else {
            keyToMove = current.keyList.remove(0);
            valToMove = current.valueMap.remove(keyToMove);

            sibling.keyList.add(keyToMove);
            sibling.valueMap.put(keyToMove, valToMove);
            Collections.sort(sibling.keyList);

            sibling.childrenList.add(0, fixerChild.childrenList.remove(0));
            current.childrenList.remove(0);
          }
        }

        // if the index is 1
        else if (i == 1) {

          // get the left sibling to share with
          BNode sibling = current.childrenList.get(0);

          // if the sibling has enough keys to share, move sibling key to the parent and
          // re-distribute
          if (sibling.keyList.size() > 1) {
            keyToMove = sibling.keyList.remove(1);
            valToMove = sibling.valueMap.remove(keyToMove);

            current.keyList.add(keyToMove);
            current.valueMap.put(keyToMove, valToMove);
            Collections.sort(current.keyList);

            keyToMove = current.keyList.remove(1);
            valToMove = current.valueMap.remove(keyToMove);

            fixerChild.keyList.add(keyToMove);
            fixerChild.valueMap.put(keyToMove, valToMove);
            Collections.sort(fixerChild.keyList);

            // move child over to maintain 2-3 structure
            fixerChild.childrenList.add(0,
                sibling.childrenList.remove(sibling.childrenList.size() - 1));
          }

          // if the sibling only has one key, move parent key into sibling and add fixerChildChild
          // to sibling
          else {
            keyToMove = current.keyList.remove(0);
            valToMove = current.valueMap.remove(keyToMove);

            sibling.keyList.add(keyToMove);
            sibling.valueMap.put(keyToMove, valToMove);
            Collections.sort(sibling.keyList);

            sibling.childrenList.add(fixerChild.childrenList.remove(0));
            current.childrenList.remove(1);
          }
        }

        // if the index is 2
        else {

          // get the left sibling to share with
          BNode sibling = current.childrenList.get(1);

          // if the sibling has enough keys to share, move sibling key to the parent and
          // re-distribute
          if (sibling.keyList.size() > 1) {
            keyToMove = sibling.keyList.remove(1);
            valToMove = sibling.valueMap.remove(keyToMove);

            current.keyList.add(keyToMove);
            current.valueMap.put(keyToMove, valToMove);
            Collections.sort(current.keyList);

            keyToMove = current.keyList.remove(2);
            valToMove = current.valueMap.remove(keyToMove);

            fixerChild.keyList.add(keyToMove);
            fixerChild.valueMap.put(keyToMove, valToMove);
            Collections.sort(fixerChild.keyList);

            // move child over to maintain 2-3 structure
            fixerChild.childrenList.add(0,
                sibling.childrenList.remove(sibling.childrenList.size() - 1));
          }

          // if the sibling only has one key, move parent key into sibling and add fixerChildChild
          // to sibling
          else {
            keyToMove = current.keyList.remove(1);
            valToMove = current.valueMap.remove(keyToMove);

            sibling.keyList.add(keyToMove);
            sibling.valueMap.put(keyToMove, valToMove);
            Collections.sort(sibling.keyList);

            sibling.childrenList.add(fixerChild.childrenList.remove(0));
            current.childrenList.remove(2);
          }
        }
      }
    }
    return current;
  }

  /**
   * get the value of the corresponding key from the B-Tree
   * 
   * @param key is the key of the object to get
   * @return the value associated with the passed key
   * @throws KeyNotFoundException if the key isn't found
   * @throws NullKeyException     if the key is null
   */
  @Override
  public V getValue(K key) throws KeyNotFoundException, NullKeyException {

    // if the key iis null,l throw NullKeyException
    if (key == null) {
      throw new NullKeyException("The passed key is null.");
    }

    // otherwise begin getValue recursion
    return getValue(key, root);
  }

  /**
   * helper function to find the needed value
   * 
   * @param key     is the key of the object to get
   * @param current is the current node of the recursion
   * @return the value associated with the passed key
   * @throws KeyNotFoundException if the key isn't found
   */
  private V getValue(K key, BNode current) throws KeyNotFoundException {

    // if the key is here, return it
    if (current.keyList.contains(key)) {
      return current.valueMap.get(key);
    }

    // if there are 2 children, find the right child to recurse
    if (current.childrenList.size() == 2) {
      K singleKey = current.keyList.get(0);

      // go right
      if (key.compareTo(singleKey) > 0) {
        return getValue(key, current.childrenList.get(1));
      }

      // go left
      return getValue(key, current.childrenList.get(0));
    }

    // if there are 3 children, find the right child to recurse
    if (current.childrenList.size() == 3) {

      // go left
      if (key.compareTo(current.keyList.get(0)) < 0) {
        return getValue(key, current.childrenList.get(0));
      }

      // go right
      if (key.compareTo(current.keyList.get(1)) > 0) {
        return getValue(key, current.childrenList.get(2));
      }

      // go center
      return getValue(key, current.childrenList.get(1));
    }

    // if we get here, we're in a leaf and the key wasn't found: throw KeyNotFound
    throw new KeyNotFoundException("Key wasn't found in tree.");
  }

  /**
   * check to see if the passed key exists in the tree
   * 
   * @param key is the key to check for
   * @return boolean of whether the key exists or not
   * @throws NullKeyException if the key is null
   */
  @Override
  public boolean contains(K key) throws NullKeyException {

    if (key == null) {
      throw new NullKeyException("The passed key is null.");
    }

    return contains(key, root);
  }

  /**
   * Helper recursive function to see if the key exists
   * 
   * @param key     is the key to check for
   * @param current is the current node of the recursion
   * @return boolean of whether the key exists or not
   */
  private boolean contains(K key, BNode current) {

    // if the key is here, return true
    if (current.keyList.contains(key)) {
      return true;
    }

    // if there are 2 children, find the right child to recurse
    if (current.childrenList.size() == 2) {
      K singleKey = current.keyList.get(0);

      // go right
      if (key.compareTo(singleKey) > 0) {
        return contains(key, current.childrenList.get(1));
      }

      // go left
      return contains(key, current.childrenList.get(0));
    }

    // if there are 3 children, find the right child to recurse
    if (current.childrenList.size() == 3) {

      // go left
      if (key.compareTo(current.keyList.get(0)) < 0) {
        return contains(key, current.childrenList.get(0));
      }

      // go right
      if (key.compareTo(current.keyList.get(1)) > 0) {
        return contains(key, current.childrenList.get(2));
      }

      // go center
      return contains(key, current.childrenList.get(1));
    }

    // if we get here, we're in a leaf and the key wasn't found: return false
    return false;
  }

  /**
   * Get the height of the tree, 0 if there are no keys, 1 if there is only one node, otherwise
   * return the number of tree levels.
   * 
   * @return the height of the tree from root to leaf
   */
  @Override
  public int height() {

    // if root is null or empty, return 0. otherwise call getHeight
    if (null == root || root.keyList.size() == 0) {
      return 0;
    }

    return root.getHeight();
  }

  /**
   * Get the amount of keys in the tree
   * 
   * @return the size of the tree, which is the amount of keys
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Get all keys in the tree. If the tree is empty, return an empty list
   * 
   * @return an arraylist of all keys in the tree
   */
  @Override
  public ArrayList<K> getAllKeys() {

    // if the root is null or empty, return an empty list. Otherwise call getAllKeys
    if (null == root || root.keyList.size() == 0) {
      return new ArrayList<K>();
    }

    return root.getAllKeys();
  }

}
