package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Routing {
  public static List<Vertex> dijkstra_naive(List<List<Vertex>> adjList, int s, int t) {
    int vertexCount = adjList.size();
    Status[] status = new Status[vertexCount];
    int[] bw = new int[vertexCount];
    int[] dad = new int[vertexCount];
    int fringeCount = 0;
    List<Vertex> path = new ArrayList<Vertex>();
    
    for (int i = 0; i < vertexCount; i++) {
      status[i] = Status.UNSEEN;
    }

    status[s] = Status.IN_TREE;

    for (int i = 0; i < adjList.get(s).size(); i++) {
      Vertex w = adjList.get(s).get(i);
      status[w.getName()] = Status.FRINGE;
      bw[w.getName()] = w.getValue();
      dad[w.getName()] = s;
      fringeCount++;
    }

    while (fringeCount != 0) {
      int maxIndex = getMaxIndex(status, bw);
      status[maxIndex] = Status.IN_TREE;
      fringeCount--;
      for (int i = 0; i < adjList.get(maxIndex).size(); i++) {
        Vertex w = adjList.get(maxIndex).get(i);
        int wName = w.getName();
        int min = Math.min(bw[maxIndex], w.getValue());
        if (status[wName] == Status.UNSEEN) {
          status[wName] = Status.FRINGE;
          fringeCount++;
          bw[wName] = min;
          dad[wName] = maxIndex;
        }
        else if (status[wName] == Status.FRINGE && bw[wName] < min) {
          bw[wName] = min;
          dad[wName] = maxIndex;
        }
      }
    }

    if (status[t] == Status.IN_TREE) {
      int x = t;
      while (x != s) {
        path.add(new Vertex(x, bw[x]));
        x = dad[x];
      }
      path.add(new Vertex(s, bw[s]));
      Collections.reverse(path);
    }
    return path;
  }

  public static List<Vertex> dijkstra_heap(List<List<Vertex>> adjList, int s, int t) {
    int vertexCount = adjList.size();
    Status[] status = new Status[vertexCount];
    int[] bw = new int[vertexCount];
    int[] dad = new int[vertexCount];
    MaxHeap fringes = new MaxHeap(vertexCount);
    List<Vertex> path = new ArrayList<Vertex>();
    
    for (int i = 0; i < vertexCount; i++) {
      status[i] = Status.UNSEEN;
    }

    status[s] = Status.IN_TREE;

    for (int i = 0; i < adjList.get(s).size(); i++) {
      Vertex w = adjList.get(s).get(i);
      status[w.getName()] = Status.FRINGE;
      bw[w.getName()] = w.getValue();
      dad[w.getName()] = s;
      fringes.insert(w);
    }

    while (fringes.getSize() > 0) {
      Vertex currentSrc = fringes.max();
      int currentName = currentSrc.getName();
      status[currentSrc.getName()] = Status.IN_TREE;
      fringes.delete(currentName);
      for (int i = 0; i < adjList.get(currentName).size(); i++) {
        Vertex w = adjList.get(currentName).get(i);
        int wName = w.getName();
        int min = Math.min(bw[currentName], w.getValue());
        if (status[wName] == Status.UNSEEN) {
          status[wName] = Status.FRINGE;
          fringes.insert(w);
          bw[wName] = min;
          dad[wName] = currentName;
        }
        else if (status[wName] == Status.FRINGE && bw[wName] < min) {
          fringes.delete(w);
          bw[wName] = min;
          dad[wName] = currentName;
          fringes.insert(new Vertex(wName, bw[wName]));
        }
      }
    }

    if (status[t] == Status.IN_TREE) {
      int x = t;
      while (x != s) {
        path.add(new Vertex(x, bw[x]));
        x = dad[x];
      }
      path.add(new Vertex(s, bw[s]));
      Collections.reverse(path);
    }
    return path;
  }

  public static List<Vertex> kruskal(List<List<Vertex>> adjList, int s, int t) {
    int vertexCount = adjList.size();

    // init max spanning tree
    List<List<Vertex>> maxSpanningTree = new ArrayList<List<Vertex>>(vertexCount);
    for (int i = 0; i < vertexCount; i++) {
      maxSpanningTree.add(new LinkedList<Vertex>());
    }

    // construct a list from the edge set and sort
    List<Edge> edgeList = sortEdges(adjList);

    
    // init the disjoint sets and start processing every edge
    int edgeCount = 0;
    List<DisjointSet> disjointSets = initDisjointSets(vertexCount);
    for (int i = 0; i < edgeList.size(); i++) {
        Edge e = edgeList.get(i);
        if (detectCycle(disjointSets, e.getV(), e.getW())) {
            continue;
        }

        int vName = e.getV();
        int wName = e.getW();
        int weight = e.getValue();
        maxSpanningTree.get(vName).add(new Vertex(wName, weight));
        maxSpanningTree.get(wName).add(new Vertex(vName, weight));
        edgeCount++;
        if (edgeCount == vertexCount - 1) {
            break;
        }
    }

    // run dijkstra on the maximum spanning tree
    return dijkstra_heap(maxSpanningTree, s, t);
  }

  public static List<Edge> sortEdges(List<List<Vertex>> adjList) {
    int vertexCount = adjList.size();
    List<Edge> edges = new ArrayList<Edge>();
    List<Edge> sortedEdges = new ArrayList<Edge>();

    // maximum number of edges with duplicates is V^2 so set max size to V^2
    MaxHeap heap = new MaxHeap(vertexCount * vertexCount);
    
    int edgeCount = 0;
    // add edges to the set so that are no duplicates (v -> w || v <- w)
    for (int i = 0; i < adjList.size(); i++) {
      List<Vertex> vertices = adjList.get(i);
      for (int j = 0; j < vertices.size(); j++) {
        Vertex w = vertices.get(j);

        if (w.getName() > i) {
          edges.add(new Edge(i, w.getName(), w.getValue()));
          heap.insert(edgeCount, w.getValue());
          edgeCount++;
        }
      }
    }
    
    // do heap sort
    for (int i = 0; i < edges.size(); i++) {
      Vertex v = heap.max();
      Edge e = edges.get(v.getName());
      sortedEdges.add(e);
      heap.delete(v);
    }
    
    // edges.sort(Collections.reverseOrder(Comparator.comparing(e -> e.getValue())));
    return sortedEdges;
  }

  private static int getMaxIndex(Status[] status, int[] bw) {
    int index = 0;
    int max = 0;
    for (int i = 0; i < status.length; i++)  {
      if (status[i] == Status.FRINGE && bw[i] > max) {
        index = i;
        max = bw[i];
      }
    }
    return index;
  }

  private static List<DisjointSet> initDisjointSets(int vertexCount) {
    List<DisjointSet> disjointSets = new ArrayList<>(vertexCount);
    for (int i = 0; i < vertexCount; i++) {
      disjointSets.add(new DisjointSet(i));
    }
    return disjointSets;
  }

  private static boolean detectCycle(List<DisjointSet> disjointSets, Integer u, Integer v) {
    Integer rootU = pathCompressionFind(disjointSets, u);
    Integer rootV = pathCompressionFind(disjointSets, v);
    if (rootU.equals(rootV)) {
        return true;
    }
    unionByRank(disjointSets, rootU, rootV);
    return false;
  }

  private static Integer find(List<DisjointSet> disjointSets, Integer node) {
    Integer parent = disjointSets.get(node).getParent();
    if (parent.equals(node)) {
        return node;
    } else {
        return find(disjointSets, parent);
    }
  }

  private static Integer pathCompressionFind(List<DisjointSet> disjointSets, Integer node) {
    DisjointSet setInfo = disjointSets.get(node);
    Integer parent = setInfo.getParent();
    if (parent.equals(node)) {
        return node;
    } else {
        Integer parentNode = find(disjointSets, parent);
        setInfo.setParent(parentNode);
        return parentNode;
    }
  }

  private static void unionByRank(List<DisjointSet> disjointSets, int rootU, int rootV) {
    DisjointSet setInfoU = disjointSets.get(rootU);
    DisjointSet setInfoV = disjointSets.get(rootV);
    int rankU = setInfoU.getRank();
    int rankV = setInfoV.getRank();
    if (rankU < rankV) {
        setInfoU.setParent(rootV);
    } else {
        setInfoV.setParent(rootU);
        if (rankU == rankV) {
            setInfoU.setRank(rankU + 1);
        }
    }
  }
}
