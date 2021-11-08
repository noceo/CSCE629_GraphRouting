package Tests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import src.MaxHeap;
import java.util.concurrent.ThreadLocalRandom;

public class MaxHeapTest {
  @Test
  public void testMax() {
    int max;
    MaxHeap heap = new MaxHeap(15);
 
    // Inserting nodes
    for (int i = 0; i < heap.getMaxSize(); i++) {
      heap.insert(i, ThreadLocalRandom.current().nextInt(0, 100));
    }

    max = heap.max();
    System.out.println(max);

    // Check if extracted max is bigger than all other values in the heap
    for (int i = 0; i < heap.getSize(); i++) {
      if (heap.getValueAt(i) > max) {
        System.out.println(heap.getValueAt(i));
      }
      assertTrue(max >= heap.getValueAt(i));
    }

    assertTrue(testHeapIntegrity(heap));

  }

  @Test
  public void testInsert() {
    MaxHeap heap = new MaxHeap(15);
 
    // Inserting nodes
    for (int i = 0; i < heap.getMaxSize(); i++) {
      heap.insert(i, ThreadLocalRandom.current().nextInt(0, 100));
      assertTrue(testHeapIntegrity(heap));
    }
  }

  @Test
  public void testDelete() {
    for (int j = 0; j < 1000; j++) {
      // System.out.println(j);
      MaxHeap heap = new MaxHeap(17);
      
      // Inserting nodes
      for (int i = 0; i < heap.getMaxSize(); i++) {
        heap.insert(i, ThreadLocalRandom.current().nextInt(0, 100));
      }

      for (int i = 0; i < heap.getSize(); i++) {
        System.out.println(heap.getH()[i]);
      }
      heap.print();
      heap.delete(0);
      heap.print();
      assertTrue(testHeapIntegrity(heap));
    }

    // for (int i = heap.getMaxSize() - 1; i >= 1; i--) {
    //   heap.delete(i);
    //   System.out.println("Deletion " + i);
    //   heap.print();
    //   assertTrue(testHeapIntegrity(heap));
    // }
  }

  private boolean testHeapIntegrity(MaxHeap heap) {
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
