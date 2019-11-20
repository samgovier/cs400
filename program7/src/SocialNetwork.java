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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Interface for a Social Network class which represents a graph of people in a social network and
 * the connections between them. This class implements the following graph algorithm methods on the
 * social network. You may add private helper methods, but you may not add any public methods to any
 * file other than those specified in the ADT. You may use the Java Stream API.
 */
public class SocialNetwork implements SocialNetworkADT {
  private Graph<String> graph;
  private static String filename;
  private List<String> snPeople;

  /**
   * Constructs a social network from a json file.
   * 
   * @param filename json file
   */
  public SocialNetwork(String filename) {
    SocialNetwork.filename = filename;

    this.graph = new Graph<String>();
    try {
      Person[] persons = parseJSON();
      constructGraph(persons);
      this.snPeople = this.graph.getAllVertices();
      Collections.sort(snPeople);

      // feel free to use this code for testing purposes System.out.println("Network: " + people);
      // for (String person : people) { List<String> friends =
      // this.graph.getAdjacentVerticesOf(person); Collections.sort(friends);
      // System.out.println(person + "'s friends: " + friends); }

    } catch (FileNotFoundException fnfEx) {
      System.out.println("The specified file wasn't found: " + filename);
      System.out.println(fnfEx.getMessage());
    }
    catch (Exception e) {
      System.out.println("Error: An unexpected exception occurred");
      System.out.println(e.getMessage());
    }
  }

  /**
   * Parses the input JSON file.
   * 
   * @return array of Person objects which stores information about a single person
   * @throws Exception like FileNotFound, JsonParseException
   */
  private static Person[] parseJSON() throws FileNotFoundException, Exception {

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
  private void constructGraph(Person[] people) {

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

  /**
   * Return the average number of friends people in the social network have. Use the half round up
   * rounding technique (examples: 6.4 rounds to 6, 6.5 rounds to 7, 6.6 rounds to 7). If the
   * network is empty return 0.
   * 
   * @return average number of friends per person
   */
  @Override
  public int averageFriendsPerPerson() {
    int totalFriends = 0;
    for (String snPerson : snPeople) {
      List<String> friends = graph.getAdjacentVerticesOf(snPerson);
      totalFriends += friends.size();
    }
    return (int) Math.round((double) totalFriends / (double) snPeople.size());
  }

  /**
   * Given two people, return their mutual friends. If either person parameter is null or does not
   * exist in the network, return an empty Set.
   * 
   * @param person1 first person
   * @param person2 second person
   * @return mutual friends
   */
  @Override
  public Set<String> mutualFriends(String person1, String person2) {

    if (null == person1 || null == person2) {
      return new HashSet<String>();
    }

    List<String> person1Friends = graph.getAdjacentVerticesOf(person1);
    List<String> person2Friends = graph.getAdjacentVerticesOf(person2);

    if (person1Friends.size() <= 0 || person2Friends.size() <= 0) {
      return new HashSet<String>();
    }

    HashSet<String> mutuals = new HashSet<String>();

    for (String person1Friend : person1Friends) {
      if (!person1Friend.equals(person2)) {
        for (String person2Friend : person2Friends) {
          if (person1Friend.equals(person2Friend)) {
            mutuals.add(person1Friend);
          }
        }
      }
    }

    return mutuals;
  }

  /**
   * Return the person in the social network who has the most friends. If there is a tie return the
   * social butterfly who comes first in alphabetical order. If the network is empty return the
   * empty string "".
   * 
   * @return the social butterfly
   */
  @Override
  public String socialButterfly() {

    String socialButterfly = "";
    int sbFriends = 0;

    for (String snPerson : snPeople) {
      List<String> friends = graph.getAdjacentVerticesOf(snPerson);
      if ((socialButterfly.isBlank()) || (friends.size() > sbFriends)
          || ((friends.size() == sbFriends) && (socialButterfly.compareTo(snPerson) > 0))) {
        socialButterfly = snPerson;
        sbFriends = friends.size();
      }
    }

    return socialButterfly;
  }

  /**
   * Return the person who has the most friends of friends. Include friends in this count as well as
   * friends of friends, but be careful not to double-count if a friend is also a friend of a
   * friend. If there is a tie return the influencer who comes first in alphabetical order. If the
   * network is empty return the empty string "".
   * 
   * @return the influencer
   */
  @Override
  public String influencer() {
    String influencer = "";
    int infFoF = 0;

    for (String snPerson : snPeople) {
      List<String> friendsOfFriends = graph.getAdjacentVerticesOf(snPerson);

      for (int i = friendsOfFriends.size() - 1; i >= 0; i--) {
        List<String> curFriendFriends = graph.getAdjacentVerticesOf(friendsOfFriends.get(i));
        for (String curFriendFriend : curFriendFriends) {
          if (!friendsOfFriends.contains(curFriendFriend)) {
            friendsOfFriends.add(curFriendFriend);
          }
        }
      }


      if ((influencer.isBlank()) || (friendsOfFriends.size() > infFoF)
          || ((friendsOfFriends.size() == infFoF) && (influencer.compareTo(snPerson) > 0))) {
        influencer = snPerson;
        infFoF = friendsOfFriends.size();
      }
    }

    return influencer;
  }

  /**
   * Given a person who posts a meme online and assuming that it spreads to all of their friends on
   * the second day, all of their friends' friends on the third day, etc., return the set of all
   * people who have seen the meme (including the meme's creator) after the provided number of days.
   * In other words, if Katie starts the meme and the days parameter is 3, then on Day 1 the set of
   * people who have seen the meme would be Katie. On Day 2, the set of people would be Katie and
   * Katie's friends. On Day 3, the set of people would be Katie, Katie's friends, and Katie's
   * friends' friends. If the person parameter is null or does not exist in the network, or the
   * number of days is less than 1, return an empty Set.
   * 
   * @param person person starting meme
   * @param days   days since meme was posted
   * @return all people who have seen the meme
   */
  @Override
  public Set<String> haveSeenMeme(String person, int days) {

    if (null == person || days < 1 || !snPeople.contains(person)) {
      return new HashSet<String>();
    }

    HashSet<String> haveSeenMeme = new HashSet<String>();
    haveSeenMeme.add(person);

    for (int i = 1; i < days; i++) {
      @SuppressWarnings("unchecked")
      HashSet<String> currentMemesters = (HashSet<String>) haveSeenMeme.clone();

      for (String memester : currentMemesters) {
        List<String> memeFriends = graph.getAdjacentVerticesOf(memester);
        haveSeenMeme.addAll(memeFriends);
      }
    }

    return haveSeenMeme;
  }

  /**
   * Given a person, return a set of all of the people they might know: these are their friends'
   * friends who are not already their friends. If the person parameter is null or does not exist in
   * the network, return an empty Set.
   * 
   * @param person person in question
   * @return set of friends' friends who are not already a friend
   */
  @Override
  public Set<String> youMayKnow(String person) {

    if (null == person) {
      return new HashSet<String>();
    }

    List<String> friends = graph.getAdjacentVerticesOf(person);

    if (friends.size() <= 0) {
      return new HashSet<String>();
    }

    HashSet<String> youMayKnow = new HashSet<String>();

    for (String firstDegree : friends) {
      List<String> firstDegreeFriends = graph.getAdjacentVerticesOf(firstDegree);
      for (String secondDegree : firstDegreeFriends) {
        if (!friends.contains(secondDegree) && !secondDegree.equals(person)) {
          youMayKnow.add(secondDegree);
        }
      }
    }

    return youMayKnow;

  }

  /**
   * Given a set of people, find out if they are a friend group, i.e., each person in the group is a
   * friend of every other person in the group. If the people parameter has a size less than 2,
   * return false.
   * 
   * @param people
   * @return
   */
  @Override
  public boolean isFriendGroup(Set<String> people) {

    if (people == null || people.size() < 2) {
      return false;
    }

    for (String person : people) {

      List<String> pFriends = graph.getAdjacentVerticesOf(person);

      for (String pFriend : people) {
        if (!pFriend.equals(person) && !pFriends.contains(pFriend)) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Six degrees of separation is the idea that all people are six, or fewer, social connections
   * away from each other. In other words, a chain of "a friend of a friend" statements can be made
   * to connect any two people in a maximum of six steps. If the network is empty return true.
   * 
   * @return true if the theory holds for this network, otherwise false
   */
  @Override
  public boolean sixDegreesOfSeparation() {
    if (snPeople.size() <= 0) {
      return true;
    }

    for (String person1 : snPeople) {
      for (String person2 : snPeople) {
        if (!person1.equals(person2)) {
          if (graph.getAdjacentVerticesOf(person1).contains(person2)) {
            continue;
          }
          List<String> degreesOfSeparation = socialLadder(person1, person2);
          if (degreesOfSeparation.size() > 6 || degreesOfSeparation.size() <= 0) {
            return false;
          }
        }
      }
    }

    return true;
  }

  /**
   * Given two people, the first one wanting to be friends with the second, return a list, in order,
   * of the fewest amount of people the first person can befriend to get to the second person. If
   * there is a tie between two paths choose the path which has the starting person who comes first
   * alphabetically (ex: choose Alex->Jess over Bailey->Jess) If either person parameter is null or
   * does not exist in the network, or if there is no path from person1 to person2, return an empty
   * List.
   * 
   * @param person1 the person who is seeking to be friends with person2
   * @param person2 the person with whom person1 is seeking to be friends with
   * @return the ordered list of people in the social ladder
   */
  @Override
  public List<String> socialLadder(String person1, String person2) {

    if ((person1 == null) || (person2 == null) || !snPeople.contains(person1)
        || !snPeople.contains(person2)) {
      return new ArrayList<String>();
    }

    ArrayList<String> socialLadder = new ArrayList<String>();
    
    if (person1.equals(person2)) {
      socialLadder.add(person1);
      return socialLadder;
    }
    
    List<String> friendList = graph.getAdjacentVerticesOf(person1);

    if (friendList.contains(person2)) {
      socialLadder.add(person1);
      socialLadder.add(person2);
      return socialLadder;
    }

    ArrayList<ArrayList<String>> possiblePaths = new ArrayList<ArrayList<String>>();
    SocialLadder(possiblePaths, friendList, socialLadder, person2);

    socialLadder = new ArrayList<String>();
    int ladderSize = Integer.MAX_VALUE;

    for (ArrayList<String> possibleLadder : possiblePaths) {
      if (possibleLadder.size() < ladderSize || ((possibleLadder.size() == ladderSize)
          && (socialLadder.get(0).compareTo(possibleLadder.get(0)) > 0))) {
        socialLadder = possibleLadder;
        ladderSize = socialLadder.size();
      }
    }

    socialLadder.add(0, person1);
    socialLadder.add(person2);
    return socialLadder;
  }

  @SuppressWarnings("unchecked")
  private void SocialLadder(ArrayList<ArrayList<String>> possiblePaths,
      List<String> currentFriendList, ArrayList<String> socialLadder, String person2) {

    for (int i = 0; i < currentFriendList.size(); i++) {
      String tryFriend = currentFriendList.get(i);
      socialLadder.add(tryFriend);
      List<String> tryFriendList = graph.getAdjacentVerticesOf(tryFriend);
      if (tryFriendList.contains(person2)) {
        possiblePaths.add((ArrayList<String>) socialLadder.clone());
      }
      socialLadder.remove(tryFriend);
    }

    int shortestPath = Integer.MAX_VALUE;

    for (ArrayList<String> possibleLadder : possiblePaths) {
      if (possibleLadder.size() < shortestPath) {
        shortestPath = possibleLadder.size();
      }
    }

    if (socialLadder.size() < shortestPath) {
      for (int i = 0; i < currentFriendList.size(); i++) {
        String nextFriend = currentFriendList.get(i);
        socialLadder.add(nextFriend);
        List<String> nextFriendList = graph.getAdjacentVerticesOf(nextFriend);
        
        // remove any existing socialLadder people to prevent infinite loops
        nextFriendList.removeAll(socialLadder);
        SocialLadder(possiblePaths, nextFriendList, socialLadder, person2);
        socialLadder.remove(nextFriend);
      }
    }

  }

  /**
   * Given a set of people, find out if there is one friend which, without that person, the
   * sub-network would fall apart (in other words, there would be two separate groups of people with
   * no connection between them). If there are multiple people who could be the "glue", return the
   * one that comes first alphabetically. If there is no single person that is the "glue" of this
   * set of people, return the empty string "". If the set of people has a size less than 3 or is
   * already disconnected, return the empty string "".
   * 
   * @param people
   * @return glue of the network, without whom, the network would fall apart
   */
  @SuppressWarnings("unchecked")
  @Override
  public String glue(Set<String> people) {
    String glue = "";

    if (people.size() < 3) {
      return glue;
    }

    HashSet<String> peopleToHash = (HashSet<String>) people;

    for (String glueTest : peopleToHash) {
      HashSet<String> connectionTest = (HashSet<String>) peopleToHash.clone();
      connectionTest.remove(glueTest);

      String testPerson = (String) connectionTest.toArray()[0];

      HashSet<String> peopleToFind = (HashSet<String>) connectionTest.clone();
      peopleToFind.remove(testPerson);
      List<String> testPersonFriends = graph.getAdjacentVerticesOf(testPerson);
      findAllFriends(testPersonFriends, peopleToFind);

      if (!peopleToFind.isEmpty() && (glue == "" || glue.compareTo(glueTest) > 0)) {
        glue = glueTest;
      }
    }

    return glue;
  }

  private void findAllFriends(List<String> testPersonFriends, HashSet<String> peopleToFind) {
    HashSet<String> furtherChecks = new HashSet<String>();

    for (String personToFind : peopleToFind) {
      if (testPersonFriends.contains(personToFind)) {
        furtherChecks.add(personToFind);
      }
    }
    
    // to prevent null in for-each loop
    peopleToFind.removeAll(furtherChecks);

    if (!peopleToFind.isEmpty() && !furtherChecks.isEmpty()) {
      for (String furtherCheck : furtherChecks) {
        List<String> furtherCheckFriends = graph.getAdjacentVerticesOf(furtherCheck);
        findAllFriends(furtherCheckFriends, peopleToFind);
      }
    }

  }
}
