package tree;
/*
 * Checked exception thrown when a user attempts to insert or get a null key.
 */
public class NullKeyException extends Exception {

  /**
   * Constructor for a NullKeyException with an input message
   * 
   * @param message is the error message
   */
  public NullKeyException(String message) {
    super(message);
  }

  /**
   * Constructor for a NullKeyException with a default message
   */
  public NullKeyException() {
    super("Key is null");
  }
}