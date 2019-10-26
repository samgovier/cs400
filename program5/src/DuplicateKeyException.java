/**
 * Checked exception thrown when a user attempts to insert a duplicate key.
 */
public class DuplicateKeyException extends Exception {

  /**
   * Constructor for a DuplicateKeyException with an input message
   * 
   * @param message is the error message
   */
  public DuplicateKeyException(String message) {
    super(message);
  }
  
  /**
   * Constructor for a DuplicateKeyException with a default message
   */
  public DuplicateKeyException() {
    super("Key already exists");
  }
}
