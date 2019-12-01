package tree;
/**
 * Checked exception thrown when a key can't be found
 */
public class KeyNotFoundException extends Exception {

  /**
   * Constructor for a KeyNotFoundException with an input message
   * 
   * @param message is the error message
   */
  public KeyNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructor for a KeyNotFoundException with default message
   */
  public KeyNotFoundException() {
    super("Key not found");
  }
}