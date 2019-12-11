package tree;

import java.util.ArrayList;

public interface BTreeADT<K extends Comparable<K>, V> {

  public void insert(K key, V value) throws DuplicateKeyException, NullKeyException;
  
  public void remove(K key) throws KeyNotFoundException, NullKeyException;
  
  public V getValue(K key) throws KeyNotFoundException, NullKeyException;
  
  public boolean contains(K key) throws NullKeyException;
  
  public int height();
  
  public int size();
  
  public ArrayList<K> getAllKeys();
}
