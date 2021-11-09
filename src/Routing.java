package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Routing {
  public static List<Integer> dijkstra_naive(List<List<Vertex>> adjList, int s, int t) {
    int vertexCount = adjList.size();
    Status[] status = new Status[vertexCount];
    int[] bw = new int[vertexCount];
    int[] dad = new int[vertexCount];
    List<Vertex> fringes = new ArrayList<Vertex>();
    List<Integer> path = new ArrayList<Integer>();
    
    for (int i = 0; i < vertexCount; i++) {
      status[i] = Status.UNSEEN;
    }

    status[s] = Status.IN_TREE;

    for (int i = 0; i < adjList.get(s).size(); i++) {
      Vertex w = adjList.get(s).get(i);
      status[w.getName()] = Status.FRINGE;
      bw[w.getName()] = w.getValue();
      dad[w.getName()] = s;
      fringes.add(w);
    }

    while (fringes.size() != 0) {
      int maxIndex = getMaxIndex(fringes);
      Vertex currentSrc = fringes.get(maxIndex);
      int currentName = currentSrc.getName();
      status[currentSrc.getName()] = Status.IN_TREE;
      fringes.remove(maxIndex);
      for (int i = 0; i < adjList.get(currentName).size(); i++) {
        Vertex w = adjList.get(currentName).get(i);
        int wName = w.getName();
        int min = Math.min(bw[currentName], w.getValue());
        if (status[wName] == Status.UNSEEN) {
          status[wName] = Status.FRINGE;
          fringes.add(w);
          bw[wName] = min;
          dad[wName] = currentName;
        }
        else if (status[wName] == Status.FRINGE && bw[wName] < min) {
          bw[wName] = min;
          dad[wName] = currentName;
        }
      }
    }

    if (status[t] != Status.IN_TREE) {
      return path;
    }
    
    int x = t;
    while (x != s) {
      path.add(x);
      x = dad[x];
    }
    path.add(s);
    Collections.reverse(path);
    return path;
  }

  public static void dijkstra_heap(List<List<Vertex>> adjList, int s, int t) {

  }

  public static void kruskal(List<List<Vertex>> adjList, int s, int t) {

  }

  private static int getMaxIndex(List<Vertex> list) {
    int index = 0;
    int max = 0;
    for (int i = 0; i < list.size(); i++)  {
      if (list.get(i).getValue() > max) {
        index = i;
        max = list.get(i).getValue();
      }
    }
    return index;
  }
}
