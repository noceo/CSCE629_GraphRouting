package Tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import src.Edge;
import src.RGG;
import src.Routing;
import src.Vertex;

public class KruskalTest {
  @Test
  public void testSortEdges() {
    int vertexCount = 5000;
    int minWeight = 1;
    int maxWeight = 100;
    List<List<Vertex>> graph1 = RGG.averageDegree(vertexCount, 6, minWeight, maxWeight);
    List<Edge> sortedEdges = Routing.sortEdges(graph1);

    for (int i = 0; i < sortedEdges.size(); i++) {
      Edge e = sortedEdges.get(i);
      for (int j = 0; j < sortedEdges.size(); j++) {
        Edge other = sortedEdges.get(j);
        assertTrue(e.getV() != other.getW() || e.getW() != other.getV());
      }
    }
  }
}
