package src;
public class Edge {
  private int v;
  private int w;
  private int value;

  public Edge(int v, int w, int value) {
    this.v = v;
    this.w = w;
    this.value = value;
  }

  @Override
  public String toString() {
    return "[" + Integer.toString(this.v) + ", " + Integer.toString(this.w) + ", " + Integer.toString(this.value) + "]";
  }

  public int getV() {
    return this.v;
  }

  public void setV(int v) {
    this.v = v;
  }

  public int getW() {
    return this.w;
  }

  public void setW(int w) {
    this.w = w;
  }

  public int getValue() {
    return this.value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
