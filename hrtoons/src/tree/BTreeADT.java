package tree;

import java.util.ArrayList;

public interface BTreeADT<K extends Comparable<K>, V> {

  public void insert(K key, V value) throws DuplicateKeyException;
  
  public boolean remove(K key);
  
  public V getValue(K key) throws KeyNotFoundException;
  
  public boolean contains(K key);
  
  public int height();
  
  public int size();
  
  public ArrayList<K> getAllKeys();
}
