package Tests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import src.MaxHeap;
import src.Vertex;

import java.util.concurrent.ThreadLocalRandom;

public class MaxHeapTest {
  @Test
  public void testMax() {
    int max;
    MaxHeap heap = new MaxHeap(15);
 
    // Inserting nodes
    for (int i = 0; i < heap.getMaxSize(); i++) {
      heap.insert(new Vertex(i, ThreadLocalRandom.current().nextInt(0, 100)));
    }

    max = heap.max().getValue();
    System.out.println(max);

    // Check if extracted max is bigger than all other values in the heap
    for (int i = 0; i < heap.getSize(); i++) {
      if (heap.getValueAt(i) > max) {
        System.out.println(heap.getValueAt(i));
      }
      assertTrue(max >= heap.getValueAt(i));
    }
    assertTrue(testHeapProperty(heap));
  }

  @Test
  public void testInsert() {
    MaxHeap heap = new MaxHeap(15);
 
    // Inserting nodes
    for (int i = 0; i < heap.getMaxSize(); i++) {
      heap.insert(new Vertex(i, ThreadLocalRandom.current().nextInt(0, 100)));
      assertTrue(testHeapProperty(heap));
    }
  }

  @Test
  public void testDelete() {
    for (int j = 0; j < 1000; j++) {
      // System.out.println(j);
      MaxHeap heap = new MaxHeap(100);
      
      // Inserting nodes
      for (int i = 0; i < heap.getMaxSize(); i++) {
        heap.insert(new Vertex(i, ThreadLocalRandom.current().nextInt(0, 100)));
      }

      for (int i = 0; i < heap.getSize(); i++) {
        heap.delete(i);
        assertTrue(testHeapProperty(heap));
      }
    }
  }

  private boolean testHeapProperty(MaxHeap heap) {
    // Check if first entry is the max
    int max = heap.getValueAt(0);
    for (int i = 1; i < heap.getSize(); i++) {
      if (heap.getValueAt(i) > max) { return false; }
    }

    // Check the other vertices
    int parent;
    int leftChild;
    int rightChild;
    for (int i = 0; i < heap.getSize(); i++) {
      parent = heap.getValueAt(i);
      leftChild = heap.getValueAt(leftChild(i));
      rightChild = heap.getValueAt(rightChild(i));
      if (leftChild > parent || rightChild > parent) {
        return false;
      }
    }

    return true;
  }

  private int leftChild(int pos) { return ((pos * 2) + 1); }

  private int rightChild(int pos) { return ((pos + 1) * 2); }
}
