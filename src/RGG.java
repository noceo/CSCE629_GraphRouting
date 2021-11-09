package src;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RGG {
  public static List<List<Vertex>> averageDegree(int vertexCount, int degree, int minWeight, int maxWeight) {
    int totalEdges = vertexCount * degree / 2;
    return generateGraph(vertexCount, totalEdges, minWeight, maxWeight);
  }

  public static List<List<Vertex>> averagePercentage(int vertexCount, float percentage, int minWeight, int maxWeight) {
    int totalEdges = vertexCount * (int)(vertexCount * percentage) / 2;
    return generateGraph(vertexCount, totalEdges, minWeight, maxWeight);
  }

  private static List<List<Vertex>>generateGraph(int vertexCount, int totalEdges, int minWeight, int maxWeight) {
    int randInt;
    int randV;
    int randW;
    List<List<Vertex>> adjList = new ArrayList<>(vertexCount);
    List<Vertex> vertices = new ArrayList<>(vertexCount);
    // init all vertices and store them in a list and
    // init the neighbor lists for every vertex
    for (int i = 0; i < vertexCount; i++) {
      randInt = random(minWeight, maxWeight + 1);
      vertices.add(new Vertex(i, randInt));
      adjList.add(new LinkedList<Vertex>());
    }

    // temp array of sets to check if vertex is contained in the given list of the random vector in constant time
    List<Set<Integer>> tempAdjSets = new ArrayList<Set<Integer>>();
    for (int i = 0; i < vertexCount; i++) {
      tempAdjSets.add(new HashSet<Integer>());
    }

    // make initial cycle to ensure that graph is connected
    Vertex v;
    Vertex w;
    for (int i = 0; i < vertexCount; i++) {
      v = vertices.get(i);
      if (i != vertexCount - 1) {
        w = vertices.get(i+1);
        adjList.get(i).add(w);
        adjList.get(i+1).add(v);
        tempAdjSets.get(i).add(w.getName());
        tempAdjSets.get(i+1).add(v.getName());
      } else {
        w = vertices.get(0);
        adjList.get(i).add(w);
        adjList.get(0).add(v);
        tempAdjSets.get(i).add(w.getName());
        tempAdjSets.get(0).add(v.getName());
      }
    }

    // add edges randomly
    // vertexCount - 1 edges must be subtracted because a cycle has already been built
    for (int i = 0; i < totalEdges - vertexCount; i++) {
      randV = random(0, vertexCount);
      randW = random(0, vertexCount);
      while (randV == randW || tempAdjSets.get(randV).contains(randW)) {
        randV = random(0, vertexCount);
        randW = random(0, vertexCount);
      }
      adjList.get(randV).add(vertices.get(randW));
      adjList.get(randW).add(vertices.get(randV));
      tempAdjSets.get(randV).add(randW);
      tempAdjSets.get(randW).add(randV);
    }
    print(adjList);
    return adjList;
  }

  private static int random(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max);
  }

  private static void print(List<List<Vertex>> adjList) {
    for (int i = 0; i < adjList.size(); i++) {
      System.out.println(String.format("%s -> %s", i, adjList.get(i).toString()));
    }
  }
}
