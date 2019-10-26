//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Hash Table
// Files: Airport.java, AirportDataReader.java, HashTable.java, HashTaleADT.java,
// DuplicateKeyException.java, KeyNotFoundException.java, NullKeyException.java
// Course: Computer Science 400
//
// Author: Sam Govier
// Email: sgovier@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
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
