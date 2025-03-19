import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    Set<Vertex<String>> visited = new HashSet<>();
    printShortWords(vertex, k, visited);
  }
  
  private static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> visited) {
    if (vertex == null || visited.contains(vertex)) return;
    if (k <= 0) return;

    visited.add(vertex);

    if (vertex.data.length() < k) System.out.println(vertex.data);
    
    for (Vertex<String> neighbor : vertex.neighbors) {
      printShortWords(neighbor, k, visited);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    Set<Vertex<String>> visited = new HashSet<>();
    return longestWord(vertex, visited);
  }
  
  private static String longestWord(Vertex<String> vertex, Set<Vertex<String>> visited) {
    if (vertex == null || visited.contains(vertex)) return "";

    visited.add(vertex);

    String longest = vertex.data;

    for (Vertex<String> neighbor : vertex.neighbors) {
      String current = longestWord(neighbor, visited);
      if (current.length() > longest.length()) longest = current;
    }

    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    Set<Vertex<T>> visited = new HashSet<>();
    printSelfLoopers(vertex, visited);
  }
  
  public static <T> void printSelfLoopers(Vertex<T> vertex, Set<Vertex<T>> visited) {
    if (vertex == null || visited.contains(vertex)) return;

    visited.add(vertex);

    for (Vertex<T> neighbor : vertex.neighbors) {
      if (neighbor.neighbors.contains(vertex)) System.out.println(vertex.data);
      printSelfLoopers(neighbor, visited);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    Set<Airport> visited = new HashSet<>();
    return canReach(start, destination, visited);
  }
  
  public static boolean canReach(Airport start, Airport destination, Set<Airport> visited) {
    // Target base case: stop when we find destination
    if (start == destination) return true;

    // Edge cases/ Base cases
    if (start == null || destination == null) return false;
    if (visited.contains(start)) return false;

    // add unique airport classes to hashset
    visited.add(start);

    // recurse through each neighboring outbound flights of each airport
    for (Airport neighborOutbound : start.getOutboundFlights()) {
      // if we are able to recurse until we hit the target base case, return true
      // in other words: if(path exists/successful) return true;
      if(canReach(neighborOutbound, destination, visited)) return true;
    }
    
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    Set<T> visited = new HashSet<>();
    Set<T> unvisited = new HashSet<>();

    unreachable(graph, starting, visited);

    unvisited.addAll(graph.keySet());
    unvisited.removeAll(visited);

    return unvisited;
  }
  
  public static <T> void unreachable(Map<T, List<T>> graph, T starting, Set<T> visited) {
    if (graph == null) return;
    if (starting == null) return;
    if (visited.contains(starting)) return;
    if (!graph.containsKey(starting)) return;

    visited.add(starting);

    for (T neighbor : graph.get(starting)) {
      unreachable(graph, neighbor, visited);
    }
  }
}