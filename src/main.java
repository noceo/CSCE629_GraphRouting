package src;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Main {
  public static void main(String[] args) {
    int vertexCount = 5000;
    int graphPairsCount = 5;
    int randomSTCount = 5;
    int averageDegree = 6;
    float averagePercentage = 0.2f;
    long startTime;
    long estimatedTime;
    
    // Generate random graph pairs
    List<List<List<List<Vertex>>>> graphPairs = new ArrayList<>();
    for (int i = 0; i < graphPairsCount; i++) {
      List<List<Vertex>> averageDegreeGraph = RGG.averageDegree(vertexCount, averageDegree, 1, 100);
      List<List<Vertex>> averagePercentageGraph = RGG.averagePercentage(vertexCount, averagePercentage, 1, 100);
      List<List<List<Vertex>>> graphPair = new ArrayList<>();
      graphPair.add(averageDegreeGraph);
      graphPair.add(averagePercentageGraph);
      graphPairs.add(graphPair);
    }

    System.out.println("Graph generation finished.");
    System.out.println(graphPairsCount + " graph pairs generated.");

    // Run routing algorithms on the random graph pairs
    // (each graph five times with randomly selected start and end vertices)
    int[] randSTPair;
    List<Long> dijkstraNaiveTimeSparse = new ArrayList<Long>();
    List<Long> dijkstraHeapTimeSparse = new ArrayList<Long>();
    List<Long> kruskalTimeSparse = new ArrayList<Long>();

    List<Long> dijkstraNaiveTimeDense = new ArrayList<Long>();
    List<Long> dijkstraHeapTimeDense = new ArrayList<Long>();
    List<Long> kruskalTimeDense = new ArrayList<Long>();

    for (int i = 0; i < graphPairs.size(); i++) {
      List<List<List<Vertex>>> pair = graphPairs.get(i);
      List<List<Vertex>> averageDegreeGraph = pair.get(0);
      List<List<Vertex>> averagePercentageGraph = pair.get(1);
      System.out.println(String.format("---------------- Graph Pair %d ----------------", i+1));

      for (int j = 0; j < randomSTCount; j++) {
        System.out.println(String.format("------- ST Pair %d -------", j+1));
        randSTPair= Utils.randomPair(0, vertexCount);
        
        startTime  = System.nanoTime();
        Routing.dijkstra_naive(averageDegreeGraph, randSTPair[0], randSTPair[1]);
        estimatedTime = System.nanoTime() - startTime;
        estimatedTime = TimeUnit.MICROSECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS);
        dijkstraNaiveTimeSparse.add(estimatedTime);
        System.out.println("Microseconds elsapsed for DIJKSTRA_NAIVE on average degree graph: " + estimatedTime);
        

        startTime  = System.nanoTime();
        Routing.dijkstra_heap(averageDegreeGraph, randSTPair[0], randSTPair[1]);
        estimatedTime = System.nanoTime() - startTime;
        estimatedTime = TimeUnit.MICROSECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS);
        dijkstraHeapTimeSparse.add(estimatedTime);
        System.out.println("Microseconds elsapsed for DIJKSTRA_HEAP on average degree graph: " + estimatedTime);
    
        startTime  = System.nanoTime();
        Routing.kruskal(averageDegreeGraph, randSTPair[0], randSTPair[1]);
        estimatedTime = System.nanoTime() - startTime;
        estimatedTime = TimeUnit.MICROSECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS);
        kruskalTimeSparse.add(estimatedTime);
        System.out.println("Microseconds elsapsed for KRUSKAL on average degree graph: " + estimatedTime);
        System.out.println(String.format("------- End ST Pair %d -------", j+1));
      }

      // Percentage based graphs
      for (int j = 0; j < randomSTCount; j++) {
        System.out.println(String.format("------- ST Pair %d -------", j+1));
        randSTPair= Utils.randomPair(0, vertexCount);
        
        startTime  = System.nanoTime();
        Routing.dijkstra_naive(averagePercentageGraph, randSTPair[0], randSTPair[1]);
        estimatedTime = System.nanoTime() - startTime;
        estimatedTime = TimeUnit.MICROSECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS);
        dijkstraNaiveTimeDense.add(estimatedTime);
        System.out.println("Microseconds elsapsed for DIJKSTRA_NAIVE on average percentage graph: " + estimatedTime);
    
        startTime  = System.nanoTime();
        Routing.dijkstra_heap(averagePercentageGraph, randSTPair[0], randSTPair[1]);
        estimatedTime = System.nanoTime() - startTime;
        estimatedTime = TimeUnit.MICROSECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS);
        dijkstraHeapTimeDense.add(estimatedTime);
        System.out.println("Microseconds elsapsed for DIJKSTRA_HEAP on average percentage graph: " + estimatedTime);
    
        startTime  = System.nanoTime();
        Routing.kruskal(averagePercentageGraph, randSTPair[0], randSTPair[1]);
        estimatedTime = System.nanoTime() - startTime;
        estimatedTime = TimeUnit.MICROSECONDS.convert(estimatedTime, TimeUnit.NANOSECONDS);
        kruskalTimeDense.add(estimatedTime);
        System.out.println("Microseconds elsapsed for KRUSKAL on average percentage graph: " + estimatedTime);
        System.out.println(String.format("------- End ST Pair %d -------", j+1));
      }
      System.out.println(String.format("------------------ Graph Pair %d End ----------------\n\n", i+1));
    }

    String res1 = "DijkstraNaive on all G1: " + dijkstraNaiveTimeSparse;
    String res2 = "DijkstraHeap on all G1: " + dijkstraHeapTimeSparse;
    String res3 = "Kruskal on all G1: " + kruskalTimeSparse;
    String res4 = "DijkstraNaive on all G2: " + dijkstraNaiveTimeDense;
    String res5 = "DijkstraHeap on all G2: " + dijkstraHeapTimeDense;
    String res6 = "Kruskal on all G2: " + kruskalTimeDense;

    String[] results = {res1, res2, res3, res4, res5, res6};

    try {
      File file = new File("results.txt");
      file.createNewFile();
      FileWriter writer = new FileWriter(file); 
      for (int i = 0; i < results.length; i++) {
        if (i == results.length - 1) {
          writer.write(results[i]);
        } else {
          writer.write(results[i] + "\n");
        }
        writer.flush();
      }
      writer.close();
    } catch (IOException e) {
      System.err.println(e);
    }
  }
}