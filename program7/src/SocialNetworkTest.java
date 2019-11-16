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

	@Rule
	public Timeout globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);

	@Before
	public void setUp() throws Exception {
		// you may use any or all of the provided json files for JUnit testing
		this.network = new SocialNetwork("social-network-md.json");
	}

	@After
	public void tearDown() throws Exception {
		this.network = null;
	}

	@Test
	public final void test00_socialButterflyValid() {
		try {
			assertEquals("test00: failed - expected: \"Addison\" returned: \"" + network.socialButterfly() +  "\"",
					true, network.socialButterfly().equals("Addison"));
		} catch (Exception e) {
			fail("test00: failed - unexpected exception occurred");
		}
	}

	@Test
	public final void test01_socialButterflyEmptyNetwork() {
		try {
			this.network = new SocialNetwork("");
			assertEquals("test00: failed - expected: \"\" returned: \"" + network.socialButterfly() +  "\"",
					true, network.socialButterfly().equals(""));
		} catch (Exception e) {
			fail("test00: failed - unexpected exception occurred");
		}
	}

	// TODO: write your tests here - don't forget edge cases!
}
