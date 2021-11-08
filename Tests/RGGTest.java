package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import src.RGG;
import src.Vertex;

public class RGGTest {
  @Test
  public void testAdjSize() {
    int vertexCount = 10;
    List<List<Vertex>> graph1 = RGG.averageDegree(vertexCount, 6, 1, 10);
    List<List<Vertex>> graph2 = RGG.averagePercentage(vertexCount, 0.2f, 1, 10);

    assertEquals(vertexCount, graph1.size());
    assertEquals(vertexCount, graph2.size());
  }
  
  @Test
  public void testAverageDegree() {
    int degree = 6;
    List<List<Vertex>> graph = RGG.averageDegree(10, degree, 1, 10);
    int sum = 0;
    for (int i = 0; i < graph.size(); i++) {
      sum += graph.get(i).size();
    }
    int averageDegree = sum / graph.size();
    assertEquals(degree, averageDegree);
  }

  @Test
  public void testPercentageAdjacency() {
    float percentage = 0.2f;
    int vertexCount = 5000;
    int spreadRange = (int)(vertexCount * 0.2);
    List<List<Vertex>> graph = RGG.averagePercentage(vertexCount, percentage, 1, 10);
    int degree = (int)(vertexCount * percentage);
    
    int sum = 0;
    for (int i = 0; i < graph.size(); i++) {
      sum += graph.get(i).size();
    }
    int averageDegree = sum / graph.size();
    System.out.println(averageDegree);
    assertTrue(averageDegree >= degree - spreadRange && averageDegree <= degree + spreadRange);
  }
}
