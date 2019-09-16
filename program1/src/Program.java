
public class Program {

  public static void main(String[] args) {
    System.out.println("ALlo mate");
    
    StringHeap testHeap = new StringHeap(5);
    
    String test1 = "test";
    String test2 = "torsty";
    String test3 = "teatay";
    String test4 = "testat";
    String test5 = "testat";
    
    System.out.println(StringHeap.prioritize(test1, test2));
    System.out.println(StringHeap.prioritize(test2, test3));
    System.out.println(StringHeap.prioritize(test3, test4));
    System.out.println(StringHeap.prioritize(test4, test5));
    
//    testHeap.add(test1);
//    testHeap.add(test2);
//    testHeap.add(test3);
//    testHeap.add(test2);
//    testHeap.add(test4);
//    testHeap.add(test5);
    
    testHeap.add("a");
    testHeap.add("b");
    testHeap.add("c");
    testHeap.add("d");
    testHeap.add("e");
    testHeap.add("f");
    testHeap.add("g");
    testHeap.add("h");
    testHeap.add("i");
    testHeap.add("j");
    testHeap.add("k");

    String[] copiedHeap = testHeap.getHeap();
    
    testHeap.printHeap();
    testHeap.printLevelOrderTraversal();
    
    System.out.println(testHeap.remove());
    
    testHeap.printHeap();
    testHeap.printLevelOrderTraversal();
    
    copiedHeap = testHeap.getHeap();
    
    System.out.println("Hello again");
    
//    try {
//      testHeap.add("\t");
//    }
//    catch (Exception e) {
//      System.out.println(e.getMessage());
//    }
//    System.out.println(testHeap.getLevel(3));
    
    
  }

}
