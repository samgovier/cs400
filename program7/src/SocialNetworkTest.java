//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Friend Graph Algorithms
// Files: Graph.java, GraphADT.java, Person.java,
// SocialNetwork.java, SocialNetworkADT.java, SocialNetworkTest.java
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
import java.util.HashSet;
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
public class SocialNetworkTest {
  private SocialNetwork network;

//  @Rule
//  public Timeout globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
    this.network = null;
  }

  @Test
  public final void test00_socialButterflyValid() {
    try {
      this.network = new SocialNetwork("social-network-sm.json");
      assertEquals(
          "test00: failed - expected: \"Lilly\" returned: \"" + network.socialButterfly() + "\"",
          true, network.socialButterfly().equals("Lilly"));
      this.network = new SocialNetwork("social-network-md.json");
      assertEquals(
          "test00: failed - expected: \"Addison\" returned: \"" + network.socialButterfly() + "\"",
          true, network.socialButterfly().equals("Addison"));
      this.network = new SocialNetwork("social-network-lg.json");
      assertEquals(
          "test00: failed - expected: \"Alisha\" returned: \"" + network.socialButterfly() + "\"",
          true, network.socialButterfly().equals("Alisha"));
    } catch (Exception e) {
      fail("test00: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test01_socialButterflyEmptyNetwork() {
    try {
      this.network = new SocialNetwork("social-network-empty.json");
      assertEquals(
          "test01: failed - expected: \"\" returned: \"" + network.socialButterfly() + "\"", true,
          network.socialButterfly().equals(""));
    } catch (Exception e) {
      fail("test01: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test02_avgFriendsValid() {
    try {
      this.network = new SocialNetwork("social-network-sm.json");
      assertEquals("test02: failed - expected: \"2\" returned: \""
          + network.averageFriendsPerPerson() + "\"", 2, network.averageFriendsPerPerson());
      this.network = new SocialNetwork("social-network-md.json");
      assertEquals("test02: failed - expected: \"3\" returned: \""
          + network.averageFriendsPerPerson() + "\"", 3, network.averageFriendsPerPerson());
      this.network = new SocialNetwork("social-network-lg.json");
      assertEquals("test02: failed - expected: \"Addison\" returned: \""
          + network.averageFriendsPerPerson() + "\"", 3, network.averageFriendsPerPerson());
    } catch (Exception e) {
      fail("test02: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test03_avgFriendsEmptyNetwork() {
    try {
      this.network = new SocialNetwork("social-network-empty.json");
      assertEquals(
          "test03: failed - expected: \"\" returned: \"" + network.socialButterfly() + "\"", 0,
          network.averageFriendsPerPerson());
    } catch (Exception e) {
      fail("test03: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test04_mutFriendsValid() {
    try {
      this.network = new SocialNetwork("social-network-sm.json");
      assertEquals(
          "test04: failed - expected: \"0\" returned: \""
              + network.mutualFriends("Lilly", "Scott").size() + "\"",
          0, network.mutualFriends("Lilly", "Scott").size());
      this.network = new SocialNetwork("social-network-md.json");
      assertEquals(
          "test04: failed - expected: \"2\" returned: \""
              + network.mutualFriends("Addison", "Bailey").size() + "\"",
          2, network.mutualFriends("Addison", "Bailey").size());
      assertEquals(
          "test04: failed - expected: \"Alex\" returned: \""
              + network.mutualFriends("Jess", "Bailey").toArray()[0] + "\"",
          "Alex", (String) network.mutualFriends("Jess", "Bailey").toArray()[0]);
      this.network = new SocialNetwork("social-network-lg.json");
      assertEquals(
          "test04: failed - expected: \"2\" returned: \""
              + network.mutualFriends("Cory", "Drew").size() + "\"",
          3, network.mutualFriends("Cory", "Drew").size());
    } catch (Exception e) {
      fail("test04: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test05_mutFriendsEmpty() {
    try {
      this.network = new SocialNetwork("social-network-empty.json");
      assertEquals(
          "test05: failed - expected \"0\" returned: \""
              + network.mutualFriends("test", "test").size() + "\"",
          0, network.mutualFriends("test", "test").size());
    } catch (Exception e) {
      fail("test05: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test06_influencer() {
    try {
      this.network = new SocialNetwork("social-network-sm.json");
      assertEquals(
          "test06: failed - expected: \"Lilly\" returned: \"" + network.influencer() + "\"",
          "Lilly", network.influencer());
      this.network = new SocialNetwork("social-network-md.json");
      assertEquals("test06: failed - expected: \"Alex\" returned: \"" + network.influencer() + "\"",
          "Alex", network.influencer());
    } catch (Exception e) {
      fail("test06: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test07_influencerempty() {
    try {
      this.network = new SocialNetwork("social-network-empty.json");
      assertEquals("test07: failed - expected: \"\" returned: \"" + network.influencer() + "\"",
          true, network.influencer().equals(""));
    } catch (Exception e) {
      fail("test07: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test08_haveSeenMeme() {
    try {
      this.network = new SocialNetwork("social-network-sm.json");
      assertEquals("test08: failed - expected: \"1\" returned: \""
          + network.haveSeenMeme("Scott", 1).size() + "\"", 1,
          network.haveSeenMeme("Scott", 1).size());
      assertEquals("test08: failed - expected: \"3\" returned: \""
          + network.haveSeenMeme("Scott", 2).size() + "\"", 3,
          network.haveSeenMeme("Scott", 2).size());
      assertEquals("test08: failed - expected: \"4\" returned: \""
          + network.haveSeenMeme("Scott", 3).size() + "\"", 4,
          network.haveSeenMeme("Scott", 3).size());
      this.network = new SocialNetwork("social-network-md.json");
      assertEquals("test08: failed - expected: \"3\" returned: \""
          + network.haveSeenMeme("Daniel", 3).size() + "\"", 3,
          network.haveSeenMeme("Daniel", 3).size());
      assertEquals("test08: failed - expected: \"7\" returned: \""
          + network.haveSeenMeme("Alex", 3).size() + "\"", 7,
          network.haveSeenMeme("Alex", 3).size());
      assertEquals("test08: failed - expected: \"5\" returned: \""
          + network.haveSeenMeme("Mel", 3).size() + "\"", 5, network.haveSeenMeme("Mel", 3).size());
    } catch (Exception e) {
      fail("test06: failed - unexpected exception occurred");
    }
  }

  @Test
  public final void test09_haveSeenMemeEmpty() {
    try {
      this.network = new SocialNetwork("social-network-empty.json");
      assertEquals("test09: failed - expected: \"\" returned: \""
          + network.haveSeenMeme("test", 5).isEmpty() + "\"", true,
          network.haveSeenMeme("test", 5).isEmpty());
    } catch (Exception e) {
      fail("test09: failed - unexpected exception occurred");
    }
  }
  
  @Test
  public final void test10_youMayKnow() {
    try {
      this.network = new SocialNetwork("social-network-sm.json");
      assertEquals(
          "test10: failed - expected: \"Lilly\" returned: \""
              + network.youMayKnow("Malika").toArray()[0] + "\"",
          "Lilly", network.youMayKnow("Malika").toArray()[0]);
      this.network = new SocialNetwork("social-network-md.json");
      assertEquals(
          "test10: failed - expected: \"2\" returned: \""
              + network.youMayKnow("Edward").size() + "\"",
          2, network.youMayKnow("Edward").size());
      this.network = new SocialNetwork("social-network-lg.json");
      assertEquals(
          "test10: failed - expected: \"2\" returned: \""
              + network.youMayKnow("Stanley").size() + "\"",
          2, network.youMayKnow("Stanley").size());
    } catch (Exception e) {
      fail("test10: failed - unexpected exception occurred");
    }
  }
  
  @Test
  public final void test11_isFriendGroup() {
    try {
      this.network = new SocialNetwork("social-network-sm.json");
      HashSet<String> friendGroup = new HashSet<String>();
      friendGroup.add("Malika");
      friendGroup.add("Scott");
      assertEquals(
          "test11: failed - expected: \"true\" returned: \""
              + network.isFriendGroup(friendGroup)+ "\"",
          true, network.isFriendGroup(friendGroup));
      friendGroup.add("Lilly");
      assertEquals(
          "test11: failed - expected: \"false\" returned: \""
              + network.isFriendGroup(friendGroup)+ "\"",
          false, network.isFriendGroup(friendGroup));
      this.network = new SocialNetwork("social-network-md.json");
      friendGroup = new HashSet<String>();
      friendGroup.add("Mel");
      friendGroup.add("Daniel");
      friendGroup.add("Riley");
      assertEquals(
          "test11: failed - expected: \"false\" returned: \""
              + network.isFriendGroup(friendGroup)+ "\"",
          false, network.isFriendGroup(friendGroup));
      friendGroup = new HashSet<String>();
      friendGroup.add("Addison");
      friendGroup.add("Edward");
      friendGroup.add("Bailey");
      friendGroup.add("Alex");
      assertEquals(
          "test11: failed - expected: \"true\" returned: \""
              + network.isFriendGroup(friendGroup)+ "\"",
          true, network.isFriendGroup(friendGroup));
    } catch (Exception e) {
      fail("test11: failed - unexpected exception occurred");
    }
  }
  
  @Test
  public final void test12_sixDegreesOfSeparation() {
    try {
      this.network = new SocialNetwork("social-network-sm.json");
      assertEquals(
          "test12: failed - expected: \"true\" returned: \""
              + network.sixDegreesOfSeparation()+ "\"",
          true, network.sixDegreesOfSeparation());
      this.network = new SocialNetwork("social-network-md.json");
      assertEquals(
          "test12: failed - expected: \"true\" returned: \""
              + network.sixDegreesOfSeparation()+ "\"",
          true, network.sixDegreesOfSeparation());
      this.network = new SocialNetwork("social-network-lg.json");
      assertEquals(
          "test12: failed - expected: \"false\" returned: \""
              + network.sixDegreesOfSeparation()+ "\"",
          false, network.sixDegreesOfSeparation());
    } catch (Exception e) {
      fail("test12: failed - unexpected exception occurred");
    }
  }
  
  @Test
  public final void test13_socialLadder() {
    try {
      this.network = new SocialNetwork("social-network-sm.json");
      assertEquals(
          "test13: failed - expected: \"1\" returned: \""
              + network.socialLadder("Lilly","Lilly").size() + "\"",
          1, network.socialLadder("Lilly","Lilly").size());
      assertEquals(
          "test13: failed - expected: \"2\" returned: \""
              + network.socialLadder("Lilly","Scott").size() + "\"",
          2, network.socialLadder("Lilly","Scott").size());
      assertEquals(
          "test13: failed - expected: \"4\" returned: \""
              + network.socialLadder("Aaron","Malika").size() + "\"",
          4, network.socialLadder("Aaron","Malika").size());
      this.network = new SocialNetwork("social-network-md.json");
      assertEquals(
          "test13: failed - expected: \"3\" returned: \""
              + network.socialLadder("Edward","Jess").size() + "\"",
          3, network.socialLadder("Edward","Jess").size());
      assertEquals(
          "test13: failed - expected: \"6\" returned: \""
              + network.socialLadder("Mel","Daniel").size() + "\"",
          6, network.socialLadder("Mel","Daniel").size());
    } catch (Exception e) {
      fail("test13: failed - unexpected exception occurred");
    }
  }
  
  @Test
  public final void test14_glue() {
    try {
      this.network = new SocialNetwork("social-network-sm.json");
      HashSet<String> friendGroup = new HashSet<String>();
      friendGroup.add("Malika");
      friendGroup.add("Scott");
      assertEquals(
          "test14: failed - expected: \"\" returned: \""
              + network.glue(friendGroup)+ "\"",
          "", network.glue(friendGroup));
      friendGroup.add("Lilly");
      assertEquals(
          "test14: failed - expected: \"Scott\" returned: \""
              + network.glue(friendGroup)+ "\"",
          "Scott", network.glue(friendGroup));
      this.network = new SocialNetwork("social-network-md.json");
      friendGroup = new HashSet<String>();
      friendGroup.add("Mel");
      friendGroup.add("Daniel");
      friendGroup.add("Riley");
      assertEquals(
          "test14: failed - expected: \"Daniel\" returned: \""
              + network.glue(friendGroup)+ "\"",
          "Daniel", network.glue(friendGroup));
      friendGroup = new HashSet<String>();
      friendGroup.add("Addison");
      friendGroup.add("Edward");
      friendGroup.add("Bailey");
      friendGroup.add("Alex");
      assertEquals(
          "test14: failed - expected: \"\" returned: \""
              + network.glue(friendGroup)+ "\"",
          "", network.glue(friendGroup));
    } catch (Exception e) {
      fail("test11: failed - unexpected exception occurred: " + e.getMessage());
    }
  }


  @Test
  public final void test15_finalEmptyTest() {
    try {
      this.network = new SocialNetwork("social-network-empty.json");
      assertEquals(
          "test15: failed - expected: \"true\" returned: \""
              + network.sixDegreesOfSeparation()+ "\"",
          true, network.sixDegreesOfSeparation());
      assertEquals(
          "test15: failed - expected: \"0\" returned: \""
              + network.socialLadder("Lilly","Lilly").size() + "\"",
          0, network.socialLadder("Lilly","Lilly").size());
      HashSet<String> friendGroup = new HashSet<String>();
      friendGroup.add("Malika");
      friendGroup.add("Scott");
      assertEquals(
          "test15: failed - expected: \"\" returned: \""
              + network.glue(friendGroup)+ "\"",
          "", network.glue(friendGroup));
    } catch (Exception e) {
      fail("test15: failed - unexpected exception occurred");
    }
  }

}
