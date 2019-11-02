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

  // TODO: add additional JUnit tests here, 5 OR MORE
}
