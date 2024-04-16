package src;

import java.util.Arrays;

public class MaxHeap {
  private int[] H;
  private int[] D;
  private int[] P;
  private int size;
  private int maxSize;

  /**
   * Constructs a heap with a given size
   * @param maxSize
   */
  public MaxHeap(int maxSize) {
    this.maxSize = maxSize;
    this.size = 0;
    this.H = new int[maxSize];
    this.D = new int[maxSize];
    this.P = new int[maxSize];
    Arrays.fill(this.H, -1);
    Arrays.fill(this.D, -1);
    Arrays.fill(this.P, -1);
  }

  /**
   * Prints the heap
   */
  public void print() {
    for (int i = 0; i < this.size / 2; i++) {
      System.out.print(
        " PARENT : " + getValueAt(i)
        + " LEFT CHILD : " + getValueAt(i * 2 + 1)
        + " RIGHT CHILD :" + getValueAt(i * 2 + 2));
      System.out.println();
    }
  }

  /**
   * Returns the maximum (first) element stored in the heap 
   * @return
   */
  public Vertex max() {
    int name = H[0];
    int value = D[name];
    return new Vertex(name, value);
  }

  /**
   * Inserts an element into the heap
   * @param name name of the element
   * @param value value of the element
   */
  public void insert(int name, int value) {
    ins(name, value);
  }

  /**
   * Inserts an element into the heap
   * @param v Vertex
   */
  public void insert(Vertex v) {
    int name = v.getName();
    int value = v.getValue();
    ins(name, value);
  }

  /**
   * Removes an element from the heap
   * @param name name of the element
   */
  public void delete(int name) {
    del(name);
  }

  /**
   * Removes an element from the heap
   * @param v Vertex
   */
  public void delete(Vertex v) {
    del(v.getName());
  }

  /**
   * Removes an element from the heap
   * @param name
   */
  private void del(int name) {
    int pos = P[name];
    P[name] = -1;
    D[H[pos]] = -1;

    // Swap last element to deleted slot
    H[pos] = H[size-1];
    P[H[pos]] = pos;

    H[size-1] = -1;
    size--;

    if (getValueAt(pos) < getValueAt(parent(pos)) || pos == 0) {
      fixHeap(pos);
    }
    else {
      while (pos > 1 && getValueAt(pos) > getValueAt(parent(pos))) {
        swap(pos, parent(pos));
        pos = parent(pos);
      }
    }
  }

  /**
   * Inserts an element into the heap
   * @param name
   * @param value
   */
  private void ins(int name, int value) {
    int pos = size;
    H[pos] = name;
    D[name] = value;
    P[name] = pos;
    while (getValueAt(pos) > getValueAt(parent(pos))) {
        swap(pos, parent(pos));
        pos = parent(pos);
    }
    size++;
  }

  /**
   * Gets the parent of a specific position in the heap based on the position of that element
   * @param pos
   * @return
   */
  private int parent(int pos) { return (pos - 1) / 2; }

  /**
   * Gets the left child of a specific element in the heap based on the position of that element
   * @param pos
   * @return
   */
  private int leftChild(int pos) { return ((pos * 2) + 1); }

  /**
   * Gets the right child of a specific element in the heap based on the position of that element
   * @param pos
   * @return
   */
  private int rightChild(int pos) { return ((pos + 1) * 2); }

  /**
   * Checks if the element at a given position is a leaf
   * @param pos
   * @return
   */
  private boolean isLeaf(int pos) {
    if (pos > ((size - 1) / 2) && pos <= (size - 1)) {
        return true;
    }
    return false;
  }

  /**
   * Swaps two elements in the heap given their positions
   * @param leftPos
   * @param rightPos
   */
  private void swap(int leftPos, int rightPos) {
    int tmpH;
    int tmpP;
    tmpH = H[leftPos];
    tmpP = P[H[leftPos]];

    P[H[leftPos]] = P[H[rightPos]];
    H[leftPos] = H[rightPos];
    P[H[rightPos]] = tmpP;
    H[rightPos] = tmpH;
  }

  /**
   * Fixes the heap property downwards from a given position
   * @param pos
   */
  private void fixHeap(int pos) {
    if (isLeaf(pos))
        return;

    if (getValueAt(pos) < getValueAt(leftChild(pos)) || getValueAt(pos) < getValueAt(rightChild(pos))) {
      if (getValueAt(leftChild(pos)) > getValueAt(rightChild(pos))) {
          swap(pos, leftChild(pos));
          fixHeap(leftChild(pos));
      }
      else {
        swap(pos, rightChild(pos));
        fixHeap(rightChild(pos));
      }
    }
  }

  public int getMaxSize() {
    return this.maxSize;
  }

  public int getSize() {
    return this.size;
  }

  public int[] getH() {
    return Arrays.copyOfRange(this.H, 0, this.size);
  }

  public int[] getD() {
    return this.D;
  }

  public int[] getP() {
    return this.P;
  }

  public int getValueAt(int i) {
    return i > H.length - 1 || H[i] == -1 ? -1 : D[H[i]];
  }
}
