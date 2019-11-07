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
 * Represents a social network: create a graph and display the network of people and their friends
 * as recorded in the corresponding json file
 */
public class SocialNetwork {

  // graph is the social network graph
  private static Graph<String> graph;

  // filename is the name of the social network json file
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

    // parser is the JSON parser used for parsing JSON
    JSONParser parser = new JSONParser();
    
    // jsonfile is the JSON data from the corresponding file
    JSONObject jsonfile = (JSONObject) parser.parse(new FileReader(filename));
    
    // socialNetwork is the social network data from the json file
    JSONArray socialNetwork = (JSONArray) jsonfile.get("socialNetwork");
    
    // people is initialized with the size of the social network
    people = new Person[socialNetwork.size()];

    // for each person, parse and add that data to people
    for (int i = 0; i < people.length; i++) {
      
      // initialize the person in people
      people[i] = new Person();
      
      // personJSON is the gotten JSON object from socialNetwork
      JSONObject personJSON = (JSONObject) socialNetwork.get(i);
      
      // set the name of the person to the name from personJSON
      people[i].setName((String) personJSON.get("name"));
      
      // get the friends json array
      JSONArray friendsJSON = (JSONArray) personJSON.get("friends");
      
      // initialize a friends array to add to the person object and fill it
      String[] friendsArray = new String[friendsJSON.size()];
      for (int j = 0; j < friendsArray.length; j++) {
        friendsArray[j] = (String) friendsJSON.get(j);
      }
      people[i].setFriends(friendsArray);
    }

    // return the people array
    return people;
  }

  /**
   * Construct an undirected graph from array of Person objects.
   * 
   * @param people an array of People objects generated from a json file
   */
  private static void constructGraph(Person[] people) {
    
    // for each person in the people array, add to the graph
    for (Person person : people) {
      
      // add the name as the vertex
      graph.addVertex(person.getName());
      
      // for each friend, add the vertex if they don't exist, and add an edge between them
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

    // initialize graph
    graph = new Graph<String>();
    try {
      
      // parse the JSON file
      Person[] persons = parseJSON();
      
      // construct the graph using persons
      constructGraph(persons);
      
      // get the vertices of the graph
      List<String> people = graph.getAllVertices();
      
      // sort the collection and print the people and their friend lists
      Collections.sort(people);
      System.out.println("Network: " + people);
      for (String person : people) {
        List<String> friends = graph.getAdjacentVerticesOf(person);
        Collections.sort(friends);
        System.out.println(person + "'s friends: " + friends);
      }
    } catch (Exception e) {
      // throw any exceptions
      System.out.println("Error: An unexpected exception occurred");
    }
  }
}
