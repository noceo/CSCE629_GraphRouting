package src.Kruskal;

public class DisjointSet {
  private Integer parent;
  private int rank;

  public DisjointSet(Integer parent) {
    setParent(parent);
    setRank(0);
  }

  public Integer getParent() {
    return this.parent;
  }

  public void setParent(Integer parent) {
    this.parent = parent;
  }

  public int getRank() {
    return this.rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }
}
