//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Graph
// Files: Graph.java, GraphADT.java, GraphTest.java,
// Person.java, SocialNetwork.java
// Course: Computer Science 400
//
// Author: Sam Govier
// Email: sgovier@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

/**
 * JUnit test class to test class Graph that implements GraphADT interface.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GraphTest {
  private Graph<String> graph;

  @Rule
  public Timeout globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);

  @Before
  public void setUp() throws Exception {
    this.graph = new Graph<String>();
  }

  @After
  public void tearDown() throws Exception {
    this.graph = null;
  }

  @Test
  public final void test00_addNullVertex() {
    try {
      graph.addVertex(null);
      fail("test00: failed - should throw an IllegalArgumentException upon adding a null vertex");
    } catch (IllegalArgumentException e) {
      // test passed
    } catch (Exception e) {
      fail("test00: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test01_removeNonexistentVertex() {
    try {
      graph.removeVertex("a");
    } catch (Exception e) {
      fail("test01: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test02_addEdge() {
    try {
      graph.addVertex("a");
      assertEquals("test02: failed - vertex insertion error", true,
          graph.getAllVertices().contains("a"));
      graph.addVertex("b");
      assertEquals("test02: failed - vertex insertion error", true,
          graph.getAllVertices().contains("b"));
      graph.addEdge("a", "b");
      assertEquals("test02: failed - edge insertion error", true,
          graph.getAdjacentVerticesOf("a").contains("b"));
    } catch (Exception e) {
      fail("test02: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test03_removeEdge() {
    try {
      graph.addVertex("a");
      assertEquals("test02: failed - vertex insertion error", true,
          graph.getAllVertices().contains("a"));
      graph.addVertex("b");
      assertEquals("test02: failed - vertex insertion error", true,
          graph.getAllVertices().contains("b"));
      graph.addEdge("a", "b");
      assertEquals("test02: failed - edge insertion error", true,
          graph.getAdjacentVerticesOf("a").contains("b"));
      graph.removeEdge("a", "b");
      assertEquals("test03: failed - edge removal error", false,
          graph.getAdjacentVerticesOf("a").contains("b"));
    } catch (Exception e) {
      fail("test03: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test04_testOrderAdd() {
    try {
      graph.addVertex("s");
      graph.addVertex("a");
      graph.addVertex("m");
      graph.addVertex("r");
      graph.addVertex("o");
      graph.addVertex("c");
      graph.addVertex("k");
      assertEquals("test04: failed - order mismatch", 7, graph.order());
    } catch (Exception e) {
      fail("test04: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test05_testOrderRemove() {
    try {
      graph.addVertex("s");
      graph.addVertex("a");
      graph.addVertex("m");
      graph.addVertex("r");
      graph.addVertex("o");
      graph.addVertex("c");
      graph.addVertex("k");
      assertEquals("test04: failed - order mismatch", 7, graph.order());
      graph.removeVertex("a");
      graph.removeVertex("m");
      graph.removeVertex("r");
      assertEquals("test05: failed - remove order mismatch", 4, graph.order());
    } catch (Exception e) {
      fail("test05: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test06_testSizeAdd() {
    try {
      graph.addVertex("s");
      graph.addVertex("a");
      graph.addVertex("m");
      graph.addVertex("r");
      graph.addVertex("o");
      graph.addVertex("c");
      graph.addVertex("k");
      assertEquals("test04: failed - order mismatch", 7, graph.order());
      graph.addEdge("s", "a");
      graph.addEdge("a", "m");
      graph.addEdge("r", "o");
      graph.addEdge("r", "c");
      graph.addEdge("r", "k");
      assertEquals("test06: failed - size mismatch", 5, graph.size());
    } catch (Exception e) {
      fail("test06: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test07_testSizeRemove() {
    try {
      graph.addVertex("s");
      graph.addVertex("a");
      graph.addVertex("m");
      graph.addVertex("r");
      graph.addVertex("o");
      graph.addVertex("c");
      graph.addVertex("k");
      assertEquals("test04: failed - order mismatch", 7, graph.order());
      graph.addEdge("s", "a");
      graph.addEdge("a", "m");
      graph.addEdge("r", "o");
      graph.addEdge("r", "c");
      graph.addEdge("r", "k");
      graph.addEdge("s", "o");
      assertEquals("test06: failed - size mismatch", 6, graph.size());
      graph.removeVertex("a");
      graph.removeVertex("m");
      graph.removeVertex("r");
      assertEquals("test05: failed - remove order mismatch", 4, graph.order());
      assertEquals("test07: failed - remove size mismatch implicit", 1, graph.size());
      graph.removeEdge("s", "o");
      assertEquals("test07: failed - remove size mismatch explicit", 0, graph.size());
    } catch (Exception e) {
      fail("test07: failed - unexpected exception occurred");
    }

  }

  @Test
  public final void test08_testDuplicates() {
    try {
      graph.addVertex("s");
      graph.addVertex("a");
      graph.addVertex("m");
      graph.addVertex("s");
      graph.addVertex("r");
      graph.addVertex("o");
      graph.addVertex("c");
      graph.addVertex("k");
      graph.addVertex("s");
      assertEquals("test08: failed - duplicates order mismatch", 7, graph.order());
      graph.addEdge("s", "a");
      graph.addEdge("a", "m");
      graph.addEdge("r", "o");
      graph.addEdge("r", "c");
      graph.addEdge("r", "k");
      graph.addEdge("k", "r");
      graph.addEdge("a", "m");
      assertEquals("test08: failed - duplicates size mismatch", 5, graph.size());
    } catch (Exception e) {
      fail("test08: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test09_testNonexistent() {
    graph.addVertex("s");
    graph.addVertex("a");
    graph.addVertex("m");
    graph.addVertex("r");
    graph.addVertex("o");
    graph.addVertex("c");
    graph.addVertex("k");
    assertEquals("test04: failed - order mismatch", 7, graph.order());
    graph.addEdge("s", "a");
    graph.addEdge("a", "m");
    graph.addEdge("r", "o");
    graph.addEdge("r", "c");
    graph.addEdge("r", "k");
    assertEquals("test06: failed - size mismatch", 5, graph.size());
    graph.removeEdge("s", "m");
    graph.removeVertex("a");
    graph.removeVertex("m");
    graph.removeVertex("r");
    graph.removeVertex("p");
    graph.removeVertex("a");
    graph.removeEdge("r", "k");
    assertEquals("test09: failed - Nonexistent remove order mismatch", 4, graph.order());
    assertEquals("test09: failed - Nonexistent remove size mismatch", 0, graph.size());
  }

  @Test
  public final void test10_gettingVertices() {
    String[] fullArray = new String[] {"s", "a", "m", "r", "o", "c", "k"};
    String[] shortArray = new String[] {"s", "o", "c", "k"};
    try {
      graph.addVertex("s");
      graph.addVertex("a");
      graph.addVertex("m");
      graph.addVertex("r");
      graph.addVertex("o");
      graph.addVertex("c");
      graph.addVertex("k");
      for (String value : fullArray) {
        assertTrue("test10: failed - missing element", graph.getAllVertices().contains(value));
      }
      graph.addEdge("s", "a");
      graph.addEdge("a", "m");
      graph.addEdge("r", "o");
      graph.addEdge("r", "c");
      graph.addEdge("r", "k");
      graph.addEdge("s", "o");
      assertTrue("test10: failed - missing edge", graph.getAdjacentVerticesOf("m").contains("a"));
      for (int i = 1; i < shortArray.length; i++) {
        assertTrue("test10: failed - missing edge",
            graph.getAdjacentVerticesOf("r").contains(shortArray[i]));
      }
      graph.removeVertex("a");
      graph.removeVertex("m");
      graph.removeVertex("r");
      for (String value : shortArray) {
        assertTrue("test10: failed - missing element after remove",
            graph.getAllVertices().contains(value));
      }
      assertFalse("test10: failed - existing element", graph.getAllVertices().contains("r"));
      assertTrue("test10: failed - missing edge", graph.getAdjacentVerticesOf("s").contains("o"));
      graph.removeEdge("s", "o");
      assertFalse("test10: failed - existing edge", graph.getAdjacentVerticesOf("s").contains("o"));
    } catch (Exception e) {
      fail("test010: failed - unexpected exception occurred");
    }
  }
}
