package tree;

import java.util.ArrayList;
import java.util.HashMap;

public class BTree<K extends Comparable<K>, V> implements BTreeADT<K,V> {
  
  private class BNode {
    
    // keyList is the list of keys in this node
    private ArrayList<K> keyList;
    
    // valueMap is the map of keys to values
    private HashMap<K,V> valueMap;
    
    // childrenList is the list of children in this node
    private ArrayList<BNode> childrenList;
    
    // this constructor initializes the internal values
    private BNode() {
      this.keyList = new ArrayList<K>();
      this.valueMap = new HashMap<K,V>();
      this.childrenList = new ArrayList<BNode>();
    }
  }

  private BNode root;
  private int size;
  
  public BTree() {
    root = null;
    size = 0;
  }
  @Override
  public void insert(K key, V value) {
    // TODO Auto-generated method stub

    // MAKE SURE TO SORT WHEN ADDING/SUBTRACTING
    
  }

  @Override
  public boolean remove(K key) {
    // TODO Auto-generated method stub

    // MAKE SURE TO SORT WHEN ADDING/SUBTRACTING
    return false;
  }

  @Override
  public V getValue(K key) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean contains(K key) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int height() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ArrayList<V> getAllKeys() {
    // TODO Auto-generated method stub
    return null;
  }
  
}
