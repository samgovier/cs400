package tree;

import java.util.ArrayList;

public interface BTreeADT<K extends Comparable<K>, V> {

  public void insert(K key, V value);
  
  public V getValue(K key);
  
  public boolean contains(K key);
  
  public int height();
  
  public int size();
  
  public ArrayList<V> getAllKeys();
}
