
public class Program {
public static void main(String[] args) {
  System.out.println("We here");
  
  TrieTree tree = new TrieTree();
  tree.insert("hello");
  tree.insert("help");
  tree.insert("hel");
  tree.printTrie();
}
}
