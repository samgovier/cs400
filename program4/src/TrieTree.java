import java.util.ArrayList;

public class TrieTree {

  private class TNode {
    private char letter;
    private boolean isEndOfWord;
    private int endWordCount;
    private ArrayList<TNode> childList;

    private TNode(char letter) {
      this.letter = letter;
      this.isEndOfWord = false;
      this.childList = new ArrayList<TNode>();
    }
  }

  private TNode root;

  public TrieTree() {
    root = new TNode('@');
  }

  public void insert(String word) {
    if (word == null || word.length() == 0) {
      throw new IllegalArgumentException();
    }

    insert(root, word);
  }

  private void insert(TNode current, String word) {
    // TODO handle alphabetical order, and no calling sort methods!
    char firstLetter = word.charAt(0);
    TNode node = null;
    for (TNode child : current.childList) {
      if (child.letter == firstLetter) {
        node = child;
        break;
      }
    }
    
    if (null == node) {
      node = new TNode(firstLetter);
      current.childList.add(node);
    }
    
    if (word.length() == 1) {
      node.isEndOfWord = true;
      node.endWordCount++;
    }
    else {
      insert(node, word.substring(1));
    }
  }
  
  public static void main(String[] args) {
    TrieTree tree = new TrieTree();
    tree.insert("hello");
    tree.insert("help");
    tree.insert("hel");
    tree.printTrie();
  }
  
  public void printTrie() {
    printTrieRecursive(root, "");
}

private void printTrieRecursive(TNode current, String indent) {
    if (current != null) {
        if (current.isEndOfWord) {
            System.out.println(indent + current.letter + ":" + current.endWordCount);
        } else {
            System.out.println(indent + current.letter);
        }
        for (TNode child : current.childList) {
            printTrieRecursive(child, indent + "  ");
        }
    }
}

}
