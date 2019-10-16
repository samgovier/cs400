
public class Program {
public static void main(String[] args) {
  System.out.println("We here");
  String x = "hel";
  System.out.println(x + "lo");
  System.out.println('a' < 'c');
  TrieTree tree = new TrieTree();
  tree.insert("h");
  tree.insert("hello");
  tree.insert("help");
  tree.insert("hel");
  tree.insert("hela");
  tree.insert("hellblade");
  tree.insert("senua");
  tree.insert("hello");
  tree.insert("hell");
  tree.insert("hell");
  tree.insert("hell");
  tree.insert("hell");
  tree.insert("z");
  tree.insert("a");
  tree.insert("dillion");
  System.out.println(tree.getSize());
  System.out.println(tree.getFrequency("FUCK"));
  System.out.println(tree.getWordsWithPrefix(""));
  System.out.println(tree.getWordsWithPrefix("z"));
  System.out.println(tree.getWordsWithPrefix("hello"));
  System.out.println(tree.getWordsWithPrefix("hellblade"));
  System.out.println(tree.getWordsWithPrefix("hel"));
  System.out.println(tree.getWordsWithPrefix("dillion"));
  tree.printTrie();
}
}
