
public class Rectangle {

  private double length;
  private double width;
  
  public Rectangle(double length, double width) {
    if (length < 0 || width < 0) {
      throw new IllegalArgumentException();
    }
    this.length = length;
    this.width = width;
  }
  
  public double getArea() {
    return length * width;
  }
  
  public boolean isEmpty() {
    if (length == 0 && width == 0) {
      return true;
    }
    
    return false;
  }
}
