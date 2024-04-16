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

  @Test
  public void testIntegrity() {
    int vertexCount = 5000;
    List<List<Vertex>> graph = RGG.averageDegree(vertexCount, 6, 1, 10);
    List<Set<Integer>> sets = new ArrayList<Set<Integer>>();

    for (int i = 0; i < graph.size(); i++) {
      sets.add(new HashSet<Integer>());
    }

    for (int i = 0; i < graph.size(); i++) {
      for (int j = 0; j < graph.get(i).size(); j++) {
        int name = graph.get(i).get(j).getName();
        // Check that a vertex has no edge with itself
        assertNotEquals(i, name);
        // if (!sets.get(i).add(name)) {
        //   System.out.println(name + " " + i); //+ " " + sets.get(i));
        //   System.out.println(graph.get(i));
        //   System.out.println(graph.get(j));
        // }
        // Check that every vertex has only one edge to another vertex
        assertTrue(sets.get(i).add(name));
      }
    }

    List<List<Vertex>> graph2 = RGG.averagePercentage(vertexCount, 0.2f, 1, 10);
    List<Set<Integer>> sets2 = new ArrayList<Set<Integer>>();
    
    for (int i = 0; i < graph2.size(); i++) {
      sets2.add(new HashSet<Integer>());
    }

    for (int i = 0; i < graph2.size(); i++) {
      for (int j = 0; j < graph2.get(i).size(); j++) {
        int name = graph2.get(i).get(j).getName();
        // Check that a vertex has no edge with itself
        assertNotEquals(i, name);

        // Check that every vertex has only one edge to another vertex
        assertTrue(sets2.get(i).add(name));
      }
    }
  }
}
