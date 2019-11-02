import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class RectangleTest {

  private static Rectangle rectangle;
  private static int points;
  
  @Rule
  public Timeout globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // code here will run once before the start of all tests
    points = 0;
    
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    // code here will run after all tests have finished
    System.out.println("You got: " + points + " points");
  }

  @Before
  public void setUp() throws Exception {
    // code here will run before each test
    rectangle = new Rectangle(0, 0);
  }

  @After
  public void tearDown() throws Exception {
    // code here will run after each test
    rectangle = null;
  }

  @Test
  public void test01validArea() {
    rectangle = new Rectangle(2, 4);
    assertEquals(rectangle.getArea(), 8, .001);
    points += 2;
  }

  @Test
  public void test02constructNegativeRectangle() {
    try {
      rectangle = new Rectangle(-1,-5);
      fail("Should have caught exception");
    } catch (IllegalArgumentException e1) {
      points += 2;
    } catch (Exception e2) {
      fail("Unexpected exception caught");
    }
  }
  
  @Test
  public void test03isEmpty() {
    assertTrue("empty rectangle is empty", rectangle.isEmpty());
    points += 2;
  }
  
  @Test
  public void test04runsForever() {
    while (true) {
      // infinitiiiiii
    }
  }
}
