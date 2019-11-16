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
import java.util.ArrayList;
import java.util.List;

/**
 * The graph data structure. Graph is initialized with no vertices and edges: they can be added or
 * removed. All existing vertices, all connected vertices, size and order can be retrieved. The
 * backing data structure is an ArrayList of VertexNodes, which is an inner class
 * 
 * @param <T> vertex type
 */
public class Graph<T> implements GraphADT<T> {

  /**
   * VNode is the inner class used for vertex node data
   */
  private class VNode {

    // value is the value at this vertex
    private T value;

    // successors is an arrayList of all connected vertices
    private ArrayList<VNode> successors;

    // this constructor sets the value and initializes the successor list
    private VNode(T value) {
      this.value = value;
      this.successors = new ArrayList<VNode>();
    }
  }

  // graphCollection is an arraylist of all VNodes in the graph
  private ArrayList<VNode> graphCollection;

  // allVertices is a useful reference of all existing values in the graph
  private ArrayList<T> allVertices;

  // size is the amount of edges in the graph
  private int size;

  // this constructor initializes the backing arraylists and the size
  public Graph() {
    this.graphCollection = new ArrayList<VNode>();
    this.allVertices = new ArrayList<T>();
    this.size = 0;
  }

  /**
   * Adds a new vertex to the graph. If the vertex already exists in the graph, returns without
   * throwing an exception or adding a vertex.
   * 
   * @param vertex to be added
   * @throws IllegalArgumentException if the vertex is null
   */
  @Override
  public void addVertex(T vertex) throws IllegalArgumentException {

    // if the passed vertex is null, throw IllegalArgumentException
    if (null == vertex) {
      throw new IllegalArgumentException("The passed vertex is null.");
    }

    // if we already have the vertex, return
    if (allVertices.contains(vertex)) {
      return;
    }

    // add the new vertex node and the data to the arraylists
    graphCollection.add(new VNode(vertex));
    allVertices.add(vertex);
  }

  /**
   * Removes a vertex and all associated edges from the graph. If the vertex does not exist in the
   * graph, returns without throwing an exception or removing a vertex.
   * 
   * @param vertex to be removed
   * @throws IllegalArgumentException if the vertex is null
   */
  @Override
  public void removeVertex(T vertex) throws IllegalArgumentException {

    // if the passed vertex is null, throw IllegalArgumentException
    if (null == vertex) {
      throw new IllegalArgumentException("The passed vertex is null.");
    }

    // if the vertex doesn't exist in the graph, return
    if (!allVertices.contains(vertex)) {
      return;
    }

    // remove the vertex value from the values arraylist
    allVertices.remove(vertex);

    // find the vertex node in the node arraylist
    for (VNode vertexNode : graphCollection) {

      // if this is the corresponding vertex node, continue
      if (vertexNode.value.equals(vertex)) {

        // remove all edges, remove the vertex itself, and return
        for (VNode suc : vertexNode.successors) {
          suc.successors.remove(vertexNode);
          size--;
        }
        graphCollection.remove(vertexNode);
        return;
      }
    }
  }

  /**
   * Adds an undirected edge from vertex1 to vertex2 to the graph. If either vertex does not exist
   * in the graph or if the edge already exists in the graph, returns without throwing an exception
   * or adding an edge.
   * 
   * @param vertex1 first vertex
   * @param vertex2 second vertex
   * @throws IllegalArgumentException if either vertex is null
   */
  @Override
  public void addEdge(T vertex1, T vertex2) throws IllegalArgumentException {

    // vertexNode 1 and 2 are the VNodes corresponding to the vertices
    VNode vertexNode1 = null;
    VNode vertexNode2 = null;

    // if a passed vertex is null, throw IllegalArgumentException
    if (null == vertex1 || null == vertex2) {
      throw new IllegalArgumentException("One of the passed vertices is null.");
    }

    // if the vertices are equal, return: no self-pointing vertices are allowed
    if (vertex1.equals(vertex2)) {
      return;
    }
    
    // if one of the vertices does not exist, return without modifying
    if (!allVertices.contains(vertex1) || !allVertices.contains(vertex2)) {
      return;
    }

    // search for the two vertices
    for (VNode vertexNode : graphCollection) {

      // if this is vertex1, set the VNode
      if (vertexNode.value.equals(vertex1)) {
        vertexNode1 = vertexNode;
      }

      // if this is vertex2, set the VNode
      else if (vertexNode.value.equals(vertex2)) {
        vertexNode2 = vertexNode;
      }

      // if both VNodes are set, break the loop
      if (vertexNode1 != null && vertexNode2 != null) {
        break;
      }
    }

    // if VNode1 doesn't already reference VNode2 and vice versa,
    // add them to each other's lists and increment size
    if (!vertexNode1.successors.contains(vertexNode2)
        && !vertexNode2.successors.contains(vertexNode1)) {
      vertexNode1.successors.add(vertexNode2);
      vertexNode2.successors.add(vertexNode1);
      size++;
    }

  }

  /**
   * Removes the undirected edge from vertex1 to vertex2 from the graph. If either vertex does not
   * exist in the graph or if the edge does not exist in the graph, returns without throwing an
   * exception or removing an edge.
   * 
   * @param vertex1 first vertex
   * @param vertex2 second vertex
   * @throws IllegalArgumentException if either vertex is null
   */
  @Override
  public void removeEdge(T vertex1, T vertex2) throws IllegalArgumentException {

    // vertexNode 1 and 2 are the VNodes corresponding to the vertices
    VNode vertexNode1 = null;
    VNode vertexNode2 = null;

    // if a passed vertex is null, throw IllegalArgumentException
    if (null == vertex1 || null == vertex2) {
      throw new IllegalArgumentException("One of the passed vertices is null.");
    }

    // if one of the vertices does not exist, return without modifying
    if (!allVertices.contains(vertex1) || !allVertices.contains(vertex2)) {
      return;
    }

    // search for the two vertices
    for (VNode vertexNode : graphCollection) {

      // if this is vertex1, set the VNode
      if (vertexNode.value.equals(vertex1)) {
        vertexNode1 = vertexNode;
      }

      // if this is vertex2, set the VNode
      else if (vertexNode.value.equals(vertex2)) {
        vertexNode2 = vertexNode;
      }

      // if both VNodes are set, break the loop
      if (vertexNode1 != null && vertexNode2 != null) {
        break;
      }
    }

    // if we successfully remove both vertexNodes from referencing each other, decrement size
    if (vertexNode1.successors.remove(vertexNode2) && vertexNode2.successors.remove(vertexNode1)) {
      size--;
    }
  }

  /**
   * Returns a list containing all vertices in the graph.
   * 
   * @return List<T> where T is the vertex type
   */
  @Override
  public List<T> getAllVertices() {
    return allVertices;
  }

  /**
   * Get all neighboring (adjacent) vertices of a vertex. If the vertex does not exist in the graph,
   * returns an empty list.
   * 
   * @param vertex specified vertex
   * @return List<T> of all adjacent vertices for the specified vertex
   * @throws IllegalArgumentException if the vertex is null
   */
  @Override
  public List<T> getAdjacentVerticesOf(T vertex) throws IllegalArgumentException {

    // if the passed vertex is null, throw IllegalArgumentException
    if (null == vertex) {
      throw new IllegalArgumentException("The passed vertex is null.");
    }

    // if we don't have the associated vertex, return empty list
    if (!allVertices.contains(vertex)) {
      return new ArrayList<T>();
    }

    // go through the VNode array list
    for (VNode vertexNode : graphCollection) {

      // if we found the associated vertex, continue
      if (vertexNode.value.equals(vertex)) {

        // create an arraylist of all vertex values and return it
        ArrayList<T> adjacentVertices = new ArrayList<T>();
        for (VNode suc : vertexNode.successors) {
          adjacentVertices.add(suc.value);
        }
        return adjacentVertices;
      }
    }

    // if we make it here, somehow the vertex wasn't found: return an empty arrayList
    return new ArrayList<T>();
  }

  /**
   * Returns the number of edges in the graph.
   * 
   * @return number of edges in graph
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Returns the number of vertices in the graph.
   * 
   * @return number of vertices in graph
   */
  @Override
  public int order() {
    return allVertices.size();
  }


}
