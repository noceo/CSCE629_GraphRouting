package src;
public class Vertex {
  private int name;
  private int value;

  public Vertex(int name, int value) {
    this.name = name;
    this.value = value;
  }

  public int getName() {
    return this.name;
  }

  public int getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    return Integer.toString(this.name);
  }

  @Override
  public boolean equals(Object object) {
    return this.name == (int)object;
  }
}
