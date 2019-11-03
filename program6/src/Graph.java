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
import java.util.ArrayList;
import java.util.List;

/**
 * A generic graph interface. Edges are undirected and unweighted. You may choose how to implement
 * the graph data structure (adjacency matrix, adjacency list) and you may use any of these built-in
 * Java data structures in your implementation: ArrayList, HashMap, LinkedList, List, Map, Set.
 * 
 * @param <T> vertex type
 */
public class Graph<T> implements GraphADT<T> {

  /**
   * VNode is the inner class
   */
  private class VNode {

    // value is the value at this vertex
    private T value;

    // successors is an arrayList of all connected vertices
    private ArrayList<VNode> successors;

    private VNode(T value) {
      this.value = value;
      this.successors = new ArrayList<VNode>();
    }
  }

  private ArrayList<VNode> graphCollection;
  private ArrayList<T> allVertices;
  private int size;
  private int order;

  public Graph() {
    this.graphCollection = new ArrayList<VNode>();
    this.allVertices = new ArrayList<T>();
    this.size = 0;
    this.order = 0;
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

    if (allVertices.contains(vertex)) {
      return;
    }

    graphCollection.add(new VNode(vertex));
    allVertices.add(vertex);
    order++;
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

    if (!allVertices.contains(vertex)) {
      return;
    }

    allVertices.remove(vertex);

    for (VNode vertexNode : graphCollection) {
      if (vertexNode.value.equals(vertex)) {
        for (VNode suc : vertexNode.successors) {
          suc.successors.remove(vertexNode);
          size--;
        }
        graphCollection.remove(vertexNode);
        order--;
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

    for (VNode vertexNode : graphCollection) {
      if (vertexNode.value.equals(vertex1)) {
        vertexNode1 = vertexNode;
      } else if (vertexNode.value.equals(vertex2)) {
        vertexNode2 = vertexNode;
      }

      if (vertexNode1 != null && vertexNode2 != null) {
        break;
      }
    }

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

    for (VNode vertexNode : graphCollection) {
      if (vertexNode.value.equals(vertex1)) {
        vertexNode1 = vertexNode;
      } else if (vertexNode.value.equals(vertex2)) {
        vertexNode2 = vertexNode;
      }

      if (vertexNode1 != null && vertexNode2 != null) {
        break;
      }
    }

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

    if (!allVertices.contains(vertex)) {
      return new ArrayList<T>();
    }

    for (VNode vertexNode : graphCollection) {
      if (vertexNode.value.equals(vertex)) {
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
    return order;
  }


}
