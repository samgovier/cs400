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

public class BTree<K extends Comparable<K>, V> implements BTreeADT<K, V> {

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
     * this recursive method which finds the height from this node as the root
     * 
     * @return the height of the tree as an integer. If the node has no children, return 1
     */
    private int getHeight() {
      // if there are no children, return 1
      if (this.childrenList.size() == 0) {
        return 1;
      }

      int maxHeight = 0;

      for (BNode child : this.childrenList) {
        maxHeight = Math.max(maxHeight, child.getHeight());
      }

      return 1 + maxHeight;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<K> getAllKeys() {
      if (this.childrenList.size() == 0) {
        return keyList;
      }

      ArrayList<K> childKeys = (ArrayList<K>) keyList.clone();
      for (BNode child : this.childrenList) {
        childKeys.addAll(child.getAllKeys());
      }

      return childKeys;
    }
  }

  private BNode root;
  private int size;

  public BTree() {
    root = null;
    size = 0;
  }

  @Override
  public void insert(K key, V value) throws DuplicateKeyException, NullKeyException {

    if (key == null) {
      throw new NullKeyException("The passed key is null.");
    }

    if (null == root) {
      root = new BNode();
      root.keyList.add(key);
      root.valueMap.put(key, value);
      size++;
    } else {
      root = insert(key, value, root);
      root = reBalanceInsert(root);
    }


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

      newRoot.childrenList.add(root);
      newRoot.childrenList.add(newChild);
      root = newRoot;
    }
  }

  private BNode insert(K key, V value, BNode current) throws DuplicateKeyException {

    if (current.keyList.contains(key)) {
      throw new DuplicateKeyException("Key already exists in Tree.");
    }

    if (current.childrenList.size() == 2) {
      K singleKey = current.keyList.get(0);
      if (key.compareTo(singleKey) > 0) {
        current.childrenList.set(1, insert(key, value, current.childrenList.get(1)));
        current = reBalanceInsert(current);
      } else {
        current.childrenList.set(0, insert(key, value, current.childrenList.get(0)));
        current = reBalanceInsert(current);
      }
    }

    else if (current.childrenList.size() == 3) {
      if (key.compareTo(current.keyList.get(0)) < 0) {
        current.childrenList.set(0, insert(key, value, current.childrenList.get(0)));
        current = reBalanceInsert(current);
      } else if (key.compareTo(current.keyList.get(1)) > 0) {
        current.childrenList.set(2, insert(key, value, current.childrenList.get(2)));
        current = reBalanceInsert(current);
      } else {
        current.childrenList.set(1, insert(key, value, current.childrenList.get(1)));
        current = reBalanceInsert(current);
      }
    } else {
      // if we've reached this point, we're in a leaf. Insert
      current.keyList.add(key);
      Collections.sort(current.keyList);
      current.valueMap.put(key, value);
      size++;
    }

    return current;
  }

  private BNode reBalanceInsert(BNode current) {
    for (int i = current.childrenList.size() - 1; i >= 0; i--) {
      BNode child = current.childrenList.get(i);
      if (child.keyList.size() > 2) {
        split(current, child);
      }
    }

    return current;
  }

  @Override
  public void remove(K key) throws KeyNotFoundException, NullKeyException {

    if (key == null) {
      throw new NullKeyException("The passed key is null.");
    }

    if (null == root) {
      throw new KeyNotFoundException("Key not found.");
    }

    root = remove(key, root);
    root = reBalanceRemove(root);

    if ((root.keyList.size() == 0) && (root.childrenList.size() == 1)) {
      root = root.childrenList.get(0);
    }
  }

  private BNode remove(K key, BNode current) throws KeyNotFoundException {

    // if we successfully remove, the key is here. Finish remove and start reBalance
    if (current.keyList.remove(key)) {
      current.valueMap.remove(key);
      size--;
      current = swapWithInOrderPred(key, current, current);
      current = reBalanceRemove(current);
    }

    else if (current.childrenList.size() == 2) {
      K singleKey = current.keyList.get(0);
      if (key.compareTo(singleKey) > 0) {
        current.childrenList.set(1, remove(key, current.childrenList.get(1)));
        current = reBalanceRemove(current);
      } else {
        current.childrenList.set(0, remove(key, current.childrenList.get(0)));
        current = reBalanceRemove(current);
      }
    }

    else if (current.childrenList.size() == 3) {
      if (key.compareTo(current.keyList.get(0)) < 0) {
        current.childrenList.set(0, remove(key, current.childrenList.get(0)));
        current = reBalanceRemove(current);
      } else if (key.compareTo(current.keyList.get(1)) > 0) {
        current.childrenList.set(2, remove(key, current.childrenList.get(2)));
        current = reBalanceRemove(current);
      } else {
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

  private BNode swapWithInOrderPred(K key, BNode current, BNode toSwap) {
    if (current.childrenList.size() == 2) {
      if (current == toSwap) {
        BNode nextNode = current.childrenList.get(0);
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
        BNode nextNode = current.childrenList.get(1);
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

      current = reBalanceRemove(current);

    } else if (current.childrenList.size() == 3) {
      if (current == toSwap) {
        BNode nextNode;
        if (key.compareTo(current.keyList.get(0)) > 0) {
          nextNode = current.childrenList.get(1);
        } else {
          nextNode = current.childrenList.get(0);
        }

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
        BNode nextNode = current.childrenList.get(2);
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

      current = reBalanceRemove(current);

    }

    return current;
  }

  private BNode reBalanceRemove(BNode current) {
    if (current.childrenList.size() == 3) {
      for (int i = current.childrenList.size() - 1; i >= 0; i--) {
        BNode toCheck = current.childrenList.get(i);
        if (toCheck.keyList.size() == 0 && toCheck.childrenList.size() == 0) {
          current.childrenList.remove(i);
        }
      }
      if (current.childrenList.size() != 3) {
        ArrayList<K> curKeys = (ArrayList<K>) current.keyList.clone();
        for (BNode child : current.childrenList) {
          curKeys.addAll(child.keyList);
        }
        Collections.sort(curKeys);

        if (current.keyList.contains(curKeys.get(curKeys.size() - 1))) {
          BNode toAddNode = current.childrenList.get(1);
          K keyToAdd = current.keyList.remove(1);
          toAddNode.keyList.add(keyToAdd);
          Collections.sort(toAddNode.keyList);
          toAddNode.valueMap.put(keyToAdd, current.valueMap.remove(keyToAdd));
          if (toAddNode.keyList.size() > 2) {
            split(current, toAddNode);
          }
        }

        else {
          BNode toAddNode = current.childrenList.get(0);
          K keyToAdd = current.keyList.remove(0);
          toAddNode.keyList.add(keyToAdd);
          Collections.sort(toAddNode.keyList);
          toAddNode.valueMap.put(keyToAdd, current.valueMap.remove(keyToAdd));
          if (toAddNode.keyList.size() > 2) {
            split(current, toAddNode);
          }
        }
      }
    } else if (current.childrenList.size() == 2) {
      for (int i = current.childrenList.size() - 1; i >= 0; i--) {
        BNode toCheck = current.childrenList.get(i);
        if (toCheck.keyList.size() == 0 && toCheck.childrenList.size() == 0) {
          current.childrenList.remove(i);
        }
      }

      if (current.childrenList.size() != 2) {
        BNode currentChild = current.childrenList.get(0);
        K keyToMove = current.keyList.remove(0);
        V valToMove = current.valueMap.remove(keyToMove);
        currentChild.keyList.add(keyToMove);
        currentChild.valueMap.put(keyToMove, valToMove);
        Collections.sort(currentChild.keyList);
        if (currentChild.keyList.size() > 2) {
          split(current, currentChild);
        }
        // else leave it alone, we'll take care of it on the next stop up recursion
      }
    }

    for (int i = current.childrenList.size() - 1; i >= 0; i--) {

      BNode fixerChild = current.childrenList.get(i);

      if ((fixerChild.keyList.size() == 0) && (fixerChild.childrenList.size() == 1)) {
        K keyToMove;
        V valToMove;

        if (i == 0) {
          BNode sibling = current.childrenList.get(1);
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

            fixerChild.childrenList.add(sibling.childrenList.remove(0));
          } else {
            keyToMove = current.keyList.remove(0);
            valToMove = current.valueMap.remove(keyToMove);

            sibling.keyList.add(keyToMove);
            sibling.valueMap.put(keyToMove, valToMove);
            Collections.sort(sibling.keyList);

            sibling.childrenList.add(0, fixerChild.childrenList.remove(0));
            current.childrenList.remove(0);
          }
        } else if (i == 1) {
          BNode sibling = current.childrenList.get(0);
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

            fixerChild.childrenList.add(0,
                sibling.childrenList.remove(sibling.childrenList.size() - 1));
          } else {
            keyToMove = current.keyList.remove(0);
            valToMove = current.valueMap.remove(keyToMove);

            sibling.keyList.add(keyToMove);
            sibling.valueMap.put(keyToMove, valToMove);
            Collections.sort(sibling.keyList);

            sibling.childrenList.add(fixerChild.childrenList.remove(0));
            current.childrenList.remove(1);
          }
        } else {
          BNode sibling = current.childrenList.get(1);
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

            fixerChild.childrenList.add(0,
                sibling.childrenList.remove(sibling.childrenList.size() - 1));
          } else {
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

    int childIndex = current.childrenList.indexOf(child);
    current.childrenList.add(childIndex + 1, newChild);
  }

  @Override
  public V getValue(K key) throws KeyNotFoundException, NullKeyException {

    if (key == null) {
      throw new NullKeyException("The passed key is null.");
    }

    return getValue(key, root);
  }

  private V getValue(K key, BNode current) throws KeyNotFoundException {

    if (current.keyList.contains(key)) {
      return current.valueMap.get(key);
    }

    if (current.childrenList.size() == 2) {
      K singleKey = current.keyList.get(0);
      if (key.compareTo(singleKey) > 0) {
        return getValue(key, current.childrenList.get(1));
      }
      return getValue(key, current.childrenList.get(0));
    }

    if (current.childrenList.size() == 3) {
      if (key.compareTo(current.keyList.get(0)) < 0) {
        return getValue(key, current.childrenList.get(0));
      }
      if (key.compareTo(current.keyList.get(1)) > 0) {
        return getValue(key, current.childrenList.get(2));
      }

      return getValue(key, current.childrenList.get(1));
    }

    throw new KeyNotFoundException("Key wasn't found in tree.");
  }

  @Override
  public boolean contains(K key) throws NullKeyException {

    if (key == null) {
      throw new NullKeyException("The passed key is null.");
    }

    return contains(key, root);
  }

  private boolean contains(K key, BNode current) {

    if (current.keyList.contains(key)) {
      return true;
    }

    if (current.childrenList.size() == 2) {
      K singleKey = current.keyList.get(0);
      if (key.compareTo(singleKey) > 0) {
        return contains(key, current.childrenList.get(1));
      }
      return contains(key, current.childrenList.get(0));
    }

    if (current.childrenList.size() == 3) {
      if (key.compareTo(current.keyList.get(0)) < 0) {
        return contains(key, current.childrenList.get(0));
      }
      if (key.compareTo(current.keyList.get(1)) > 0) {
        return contains(key, current.childrenList.get(2));
      }

      return contains(key, current.childrenList.get(1));
    }

    return false;
  }

  @Override
  public int height() {

    if (null == root || root.keyList.size() == 0) {
      return 0;
    }

    return root.getHeight();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public ArrayList<K> getAllKeys() {

    if (null == root || root.keyList.size() == 0) {
      return new ArrayList<K>();
    }

    return root.getAllKeys();
  }

}
