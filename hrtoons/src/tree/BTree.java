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
  public void insert(K key, V value) throws DuplicateKeyException {

    if (null == root) {
      root = new BNode();
      root.keyList.add(key);
      root.valueMap.put(key, value);
      size++;
    }

    root = insert(key, value, root);
    root = reBalanceInsert(root);

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
        newChild.childrenList.add(root.childrenList.remove(2));
        newChild.childrenList.add(root.childrenList.remove(3));
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
        current = insert(key, value, current.childrenList.get(1));
        current = reBalanceInsert(current);
      } else {
        current = insert(key, value, current.childrenList.get(0));
        current = reBalanceInsert(current);
      }
    }

    else if (current.childrenList.size() == 3) {
      if (key.compareTo(current.keyList.get(0)) < 0) {
        current = insert(key, value, current.childrenList.get(0));
        current = reBalanceInsert(current);
      } else if (key.compareTo(current.keyList.get(1)) > 0) {
        current = insert(key, value, current.childrenList.get(2));
        current = reBalanceInsert(current);
      } else {
        current = insert(key, value, current.childrenList.get(1));
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
    for (BNode child : current.childrenList) {
      if (child.keyList.size() > 2) {
        split(current, child);
      }
    }

    return current;
  }

  @Override
  public void remove(K key) throws KeyNotFoundException {

    if (null == root) {
      throw new KeyNotFoundException("Key not found.");
    }

    root = remove(key, root);
    root = reBalanceRemove(root);
  }

  private BNode remove(K key, BNode current) throws KeyNotFoundException {

    // if we successfully remove, the key is here. Finish remove and start reBalance
    if (current.keyList.remove(key)) {
      current.valueMap.remove(key);
      current = reBalanceRemove(current);
    }

    else if (current.childrenList.size() == 2) {
      K singleKey = current.keyList.get(0);
      if (key.compareTo(singleKey) > 0) {
        current = remove(key, current.childrenList.get(1));
        current = reBalanceRemove(current);
      } else {
        current = remove(key, current.childrenList.get(0));
        current = reBalanceRemove(current);
      }
    }

    else if (current.childrenList.size() == 3) {
      if (key.compareTo(current.keyList.get(0)) < 0) {
        current = remove(key, current.childrenList.get(0));
        current = reBalanceRemove(current);
      } else if (key.compareTo(current.keyList.get(1)) > 0) {
        current = remove(key, current.childrenList.get(2));
        current = reBalanceRemove(current);
      } else {
        current = remove(key, current.childrenList.get(1));
        current = reBalanceRemove(current);
      }
    }

    // if we've reached this point, we're in a leaf. Key not found
    else {
      throw new KeyNotFoundException("Key not found.");
    }

    return current;
  }

  private BNode reBalanceRemove(BNode current) {
    // TODO https://pages.cs.wisc.edu/~deppeler/cs400/readings/23Trees/#delete
    // TODO https://www.cs.usfca.edu/~galles/visualization/BTree.html
    // TODO https://opendsa-server.cs.vt.edu/ODSA/Books/CS3/html/TwoThreeTree.html
    // TOOD https://www.geeksforgeeks.org/delete-operation-in-b-tree/
    if (current.childrenList.size() == 3) {
      for (int i = current.childrenList.size() - 1; i >= 0; i--) {
        if (current.childrenList.get(i).keyList.size() == 0) {
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
        if (current.childrenList.get(i).keyList.size() == 0) {
          current.childrenList.remove(i);
        }
      }
      
      if (current.childrenList.size() != 2) {
        BNode currentChild = current.childrenList.get(0);
        if (currentChild.keyList.size() == 2) {
          K keyToMove = current.keyList.remove(0);
          V valToMove = current.valueMap.remove(keyToMove);
          currentChild.keyList.add(keyToMove);
          currentChild.valueMap.put(keyToMove, valToMove);
          Collections.sort(currentChild.keyList);
          split(current, currentChild);
        }
        // else leave it alone, we'll take care of it on the next stop up recursion
      }
    } else {
      for (BNode fixerChild : current.childrenList) {

        if ((fixerChild.keyList.size() == 1) && (fixerChild.childrenList.size() == 1)) {
          K keyToMove = fixerChild.keyList.remove(0);
          V valToMove = fixerChild.valueMap.remove(keyToMove);
          BNode fixerChildChild = fixerChild.childrenList.get(0);
          fixerChildChild.keyList.add(keyToMove);
          fixerChildChild.valueMap.put(keyToMove, valToMove);
          Collections.sort(fixerChildChild.keyList);
          
          for (BNode theftChild : current.childrenList) {
            if (theftChild.keyList.size() > 1) {
              theftChild
              break;
            }
          }
          
        }
      }
    }
    return current;
  }

  private void split(BNode current, BNode toAddNode) {
    // get keys and values to move
    K keyToMove = toAddNode.keyList.remove(1);
    V valToMove = toAddNode.valueMap.remove(keyToMove);

    // add them to parent
    current.keyList.add(keyToMove);
    current.valueMap.put(keyToMove, valToMove);
    Collections.sort(current.keyList);

    // split keys and values
    K keyToSplit = toAddNode.keyList.remove(1);
    V valToSplit = toAddNode.valueMap.remove(keyToSplit);
    BNode newChild = new BNode();
    newChild.keyList.add(keyToSplit);
    newChild.valueMap.put(keyToSplit, valToSplit);

    // if there are children, include them in split: move to new child
    if (toAddNode.childrenList.size() > 0) {
      newChild.childrenList.add(toAddNode.childrenList.remove(2));
      newChild.childrenList.add(toAddNode.childrenList.remove(3));
    }

    int childIndex = current.childrenList.indexOf(toAddNode);
    current.childrenList.add(childIndex + 1, newChild);
  }

  @Override
  public V getValue(K key) throws KeyNotFoundException {

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
  public boolean contains(K key) {

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

    if (null == root) {
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
    return root.getAllKeys();
  }

}
