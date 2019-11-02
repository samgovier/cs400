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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Represents a social network.
 */
public class SocialNetwork {
  private static Graph<String> graph;
  private static String filename = "social-network.json";

  /**
   * Parses the input JSON file.
   * 
   * @return array of Person objects which stores information about a single person
   * @throws Exception like FileNotFound, JsonParseException
   */
  private static Person[] parseJSON() throws Exception {
    // array storing the Person objects created from the JSON file to be loaded later in the graph
    Person[] people = null;

    // TODO: create People objects by parsing JSON file and add them to array to be returned

    return people;
  }

  /**
   * Construct an undirected graph from array of Person objects.
   * 
   * @param people an array of People objects generated from a json file
   */
  private static void constructGraph(Person[] people) {
    for (Person person : people) {
      graph.addVertex(person.getName());
      for (String friend : person.getFriends()) {
        graph.addVertex(friend);
        graph.addEdge(person.getName(), friend);
      }
    }
  }

  public static void main(String[] args) {
    if (args.length > 0) {
      filename = args[0]; // allows alternate filename to be passed in through args
    }

    graph = new Graph<String>();
    try {
      Person[] persons = parseJSON();
      constructGraph(persons);
      List<String> people = graph.getAllVertices();
      Collections.sort(people);
      System.out.println("Network: " + people);
      for (String person : people) {
        List<String> friends = graph.getAdjacentVerticesOf(person);
        Collections.sort(friends);
        System.out.println(person + "'s friends: " + friends);
      }
    } catch (Exception e) {
      System.out.println("Error: An unexpected exception occurred");
    }
  }
}
