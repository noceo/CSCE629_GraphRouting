package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import src.RGG;
import src.Routing;
import src.Utils;
import src.Vertex;

public class RoutingTest {
  @Test
  public void testFirstLastVertexOfPath() {
    int vertexCount = 5000;
    int minWeight = 1;
    int maxWeight = 100;
    List<List<Vertex>> graph1 = RGG.averageDegree(vertexCount, 6, minWeight, maxWeight);
    List<List<Vertex>> graph2 = RGG.averagePercentage(vertexCount, 0.2f, minWeight, maxWeight);
    int[] randomPair = Utils.randomPair(0, vertexCount);

    // test average degree graph
    List<Vertex> path1 = Routing.dijkstra_naive(graph1, randomPair[0], randomPair[1]);
    List<Vertex> path2 = Routing.dijkstra_heap(graph1, randomPair[0], randomPair[1]);
    List<Vertex> path3 = Routing.kruskal(graph1, randomPair[0], randomPair[1]);
    
    int s1 = path1.get(0).getName();
    int t1 = path1.get(path1.size()-1).getName();

    int s2 = path2.get(0).getName();
    int t2 = path2.get(path2.size()-1).getName();

    int s3 = path3.get(0).getName();
    int t3 = path3.get(path3.size()-1).getName();
    
    assertTrue(s1 == s2);
    assertTrue(t1 == t2);
    assertTrue(s1 == s3);
    assertTrue(t1 == t3);

    // test average percentage graph
    path1 = Routing.dijkstra_naive(graph2, randomPair[0], randomPair[1]);
    path2 = Routing.dijkstra_heap(graph2, randomPair[0], randomPair[1]);
    path3 = Routing.kruskal(graph2, randomPair[0], randomPair[1]);
    
    s1 = path1.get(0).getName();
    t1 = path1.get(path1.size()-1).getName();

    s2 = path2.get(0).getName();
    t2 = path2.get(path2.size()-1).getName();

    s3 = path3.get(0).getName();
    t3 = path3.get(path3.size()-1).getName();
    
    assertTrue(s1 == s2);
    assertTrue(t1 == t2);
    assertTrue(s1 == s3);
    assertTrue(t1 == t3);
  }

  @Test
  public void testRoutingPath() {
    int vertexCount = 5000;
    int minWeight = 1;
    int maxWeight = 100;
    List<List<Vertex>> graph1 = RGG.averageDegree(vertexCount, 6, minWeight, maxWeight);
    List<List<Vertex>> graph2 = RGG.averagePercentage(vertexCount, 0.2f, minWeight, maxWeight);
    int[] randomPair = Utils.randomPair(0, vertexCount);
    List<Vertex> path1;
    List<Vertex> path2;
    List<Vertex> path3;
    int minBandwith1;
    int minBandwith2;
    int minBandwith3;
    
    path1 = Routing.dijkstra_naive(graph1, randomPair[0], randomPair[1]);
    path2 = Routing.dijkstra_heap(graph1, randomPair[0], randomPair[1]);
    path3 = Routing.kruskal(graph1, randomPair[0], randomPair[1]);
    minBandwith1 = path1.get(path1.size() - 1).getValue();
    minBandwith2 = path2.get(path2.size() - 1).getValue();
    minBandwith3 = path3.get(path3.size() - 1).getValue();
    assertTrue(minBandwith1 == minBandwith2);
    assertTrue(minBandwith1 == minBandwith3);

    path1 = Routing.dijkstra_naive(graph2, randomPair[0], randomPair[1]);
    path2 = Routing.dijkstra_heap(graph2, randomPair[0], randomPair[1]);
    path3 = Routing.kruskal(graph2, randomPair[0], randomPair[1]);
    minBandwith1 = path1.get(path1.size() - 1).getValue();
    minBandwith2 = path2.get(path2.size() - 1).getValue();
    minBandwith3 = path3.get(path3.size() - 1).getValue();
    assertTrue(minBandwith1 == minBandwith2);
    assertTrue(minBandwith1 == minBandwith3);
  }
}
