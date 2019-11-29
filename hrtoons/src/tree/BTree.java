package tree;

import java.util.ArrayList;
import java.util.HashMap;

public class BTree<K extends Comparable<K>, V> {
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
  
  // MAKE SURE TO SORT WHEN ADDING/SUBTRACTING
}
