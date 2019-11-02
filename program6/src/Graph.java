import java.util.List;

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
    
    // TODO some sort of adjacency collection
    
    private VNode(T value) {
      this.value = value;
    }
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
    // TODO Auto-generated method stub
    
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
    // TODO Auto-generated method stub
    
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
    // TODO Auto-generated method stub
    
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
    // TODO Auto-generated method stub
    
  }

  /**
   * Returns a list containing all vertices in the graph.
   * 
   * @return List<T> where T is the vertex type
   */
  @Override
  public List<T> getAllVertices() {
    // TODO Auto-generated method stub
    return null;
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
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Returns the number of edges in the graph.
   * 
   * @return number of edges in graph
   */
  @Override
  public int size() {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Returns the number of vertices in the graph.
   * 
   * @return number of vertices in graph
   */
  @Override
  public int order() {
    // TODO Auto-generated method stub
    return 0;
  }
  
  
}
