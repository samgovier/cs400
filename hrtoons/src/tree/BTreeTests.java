package tree;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;
import java.util.Random;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BTreeTests {
  private BTree<Integer, String> btree;
  private Random rnd;

  /**
   * setUp initializes a new btree for each test
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    this.btree = new BTree<Integer, String>();
    this.rnd = new Random(313);
  }

  /**
   * tearDown sets the current btree to null
   * 
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    this.btree = null;
    this.rnd = null;
  }
  
  @Test
  public void test01_NullTesting() {
    try {
      try {
        btree.insert(null, "null");
      } catch (NullKeyException e) {
        //pass
      }
      try {
      btree.remove(null);
      } catch (NullKeyException e) {
        //pass
      }
      try {
      btree.contains(null);
      } catch (NullKeyException e) {
        //pass
      }
      try {
      btree.getValue(null);
      } catch (NullKeyException e) {
        //pass
      }
    } catch (DuplicateKeyException e) {
      // TODO Auto-generated catch block
      fail("Duplicate Key Exception");
      e.printStackTrace();
    } catch (KeyNotFoundException e) {
      // TODO Auto-generated catch block
      fail("Key Not Found Exception");
      e.printStackTrace();
    }
  }
  
  @Test
  public void test02_InsertMany() {
    for (int i = 0; i < 600; i++) {
      Integer intSert = rnd.nextInt();
      try {
        btree.insert(intSert, intSert.toString());
        assertTrue("insert failed at test " + i,btree.contains(intSert));
      } catch (DuplicateKeyException e) {
        // TODO Auto-generated catch block
        fail("Duplicate Key Exception");
        e.printStackTrace();
      } catch (NullKeyException e) {
        // TODO Auto-generated catch block
        fail("Null Key Exception");
        e.printStackTrace();
      } catch (Exception e) {
        fail("Other Exception\n" + e.getMessage());
      }
    }
  }
  
  @Test
  public void test03_RemoveMany() {
    for (int i = 0; i < 600; i++) {
      Integer intSert = rnd.nextInt();
      try {
        btree.insert(intSert, intSert.toString());
        assertTrue(btree.contains(intSert));
        btree.remove(intSert);
        assertFalse(btree.contains(intSert));
      } catch (DuplicateKeyException e) {
        // TODO Auto-generated catch block
        fail("Duplicate Key Exception");
        e.printStackTrace();
      } catch (NullKeyException e) {
        // TODO Auto-generated catch block
        fail("Null Key Exception");
        e.printStackTrace();
      } catch (Exception e) {
        fail("Other Exception");
        e.printStackTrace();
      }
    }
  }
  
  @Test
  public void test04_getValueMany() {
    
  }
  
  @Test
  public void test05_oneAndTwoTesting() {
    
  }
  
  @Test
  public void test06_height() {
    
  }
  
  @Test
  public void test07_size() {
    
  }
  
  @Test
  public void test08_getAllKeys() {
    
  }
  
  @Test
  public void test09_containsMany() {
    for (int i = 0; i < 600; i++) {
      Integer intSert = rnd.nextInt();
      try {
        btree.insert(intSert, intSert.toString());
        assertTrue(btree.contains(intSert));
        btree.remove(intSert);
        assertFalse(btree.contains(intSert));
      } catch (DuplicateKeyException e) {
        // TODO Auto-generated catch block
        fail("Duplicate Key Exception");
        e.printStackTrace();
      } catch (NullKeyException e) {
        // TODO Auto-generated catch block
        fail("Null Key Exception");
        e.printStackTrace();
      } catch (Exception e) {
        fail("Other Exception");
        e.printStackTrace();
      }
    }
  }
  
  @Test
  public void test10_exceptionTesting() {
    
  }
}
