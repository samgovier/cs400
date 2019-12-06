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
      
      ArrayList<K> childKeys = (ArrayList<K>)keyList.clone();
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

    root = insert(key, value, root);
  }

  private BNode insert(K key, V value, BNode current) throws DuplicateKeyException {
    
    if (current.keyList.contains(key)) {
      throw new DuplicateKeyException("Key already exists in Tree.");
    }

    if (current.childrenList.size() == 2) {
      K singleKey = current.keyList.get(0);
      if (key.compareTo(singleKey) > 0) {
        current = insert(key, value, current.childrenList.get(1));
      }
      else {
        current = insert(key, value, current.childrenList.get(0));
      }
    }

    else if (current.childrenList.size() == 3) {
      if (key.compareTo(current.keyList.get(0)) < 0) {
        current = insert(key, value, current.childrenList.get(0));
      }
      else if (key.compareTo(current.keyList.get(1)) > 0) {
        current = insert(key, value, current.childrenList.get(2));
      }
      else {
        current = insert(key, value, current.childrenList.get(1));
      }
    }
    else {
      // if we've reached this point, we're in a leaf. Insert
      current.keyList.add(key);
      Collections.sort(current.keyList);
      current.valueMap.put(key, value);
      size++;
    }
    
    return current;
  }
  
  private BNode ReBalanceAdd(BNode current) {
    for (BNode child : current.childrenList) {
      if (child.keyList.size() > 2) {
        K keyToMove = child.keyList.get(1);
        V valToMove = child.valueMap.get(keyToMove);
        child.keyList.remove(1);
        child.valueMap.remove(keyToMove);
        current.keyList.add(keyToMove);
        current.valueMap.put(keyToMove, valToMove);
        Collections.sort(current.keyList);
      }
    }
  }

  @Override
  public boolean remove(K key) {
    // TODO Auto-generated method stub

    // MAKE SURE TO SORT WHEN ADDING/SUBTRACTING
    // DECREMENT SIZE
    return false;
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

  @Override
  public void printTreeSideways() {
    // TODO Auto-generated method stub
    
  }

}
