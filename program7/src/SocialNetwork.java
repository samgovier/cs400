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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


// as well as the Java Collections framework and the Stream API.

/**
 * Represents a social network.
 */
public class SocialNetwork implements SocialNetworkADT {
	private Graph<String> graph;
	private static String filename;

	/**
	 * Constructs a social network from a json file.
	 * @param filename json file
	 */
	public SocialNetwork(String filename) {
		SocialNetwork.filename = filename;

		this.graph = new Graph<String>();
		try {
			Person[] persons = parseJSON();
			constructGraph(persons);
			List<String> people = this.graph.getAllVertices();
			Collections.sort(people);
			/* feel free to use this code for testing purposes
			System.out.println("Network: " + people);
			for (String person : people) {
				List<String> friends = this.graph.getAdjacentVerticesOf(person);
				Collections.sort(friends);
				System.out.println(person + "'s friends: " + friends);
			}
			*/
		} catch (Exception e) {
			System.out.println("Error: An unexpected exception occurred");
		}
	}

	/**
	 * Parses the input JSON file.
	 * @return array of Person objects which stores information about a single person
	 * @throws Exception like FileNotFound, JsonParseException
	 */
	private static Person[] parseJSON() throws Exception {
		// TODO: use your code from P6
	}

	/**
	 * Construct an undirected graph from array of Person objects.
	 * @param people an array of People objects generated from a json file
	 */
	private void constructGraph(Person[] people) {
		// TODO: use your code from P6
	}

	// TODO: add graph algorithm methods from SocialNetworkADT
}
