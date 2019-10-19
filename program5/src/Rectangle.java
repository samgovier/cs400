public class Rectangle {
  private int length;
  private int width;

  public Rectangle(int length, int width) {
    this.length = length;
    this.width = width;
  }
  
  @Override
  public int hashCode() {
    return length + width;
  }
  
  @Override
  public boolean equals(Object rect) {
    return this.hashCode() == rect.hashCode();
  }
}
