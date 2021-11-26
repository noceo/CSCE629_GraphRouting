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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Edge other = (Edge) obj;
    if ((v == other.v && w == other.w && value == other.value) || (v == other.w && w == other.v && value == other.value)) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (Integer.toString(v).hashCode() + Integer.toString(w).hashCode());
    result = prime * result + Integer.toString(value).hashCode();
    return result;
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
