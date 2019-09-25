//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: AVL Trees
// Files: TreeADT.java, AVLTree.java, Airport.java, AirportDataReader.java,
// DuplicateKeyException.java, IllegalKeyException.java
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

/**
 * Represents an airport. This class is completed for you.
 */
public class Airport {
  private String locid;
  private String city;
  private String name;

  /**
   * Constructor.
   * 
   * @param locid
   * @param city
   * @param name
   */
  public Airport(String locid, String city, String airportName) {
    this.locid = locid;
    this.city = city;
    this.name = airportName;
  }

  /**
   * Gets airport id.
   * 
   * @return
   */
  public String getId() {
    return locid;
  }

  /**
   * Gets airport city.
   * 
   * @return
   */
  public String getCity() {
    return city;
  }

  /**
   * Gets airport name.
   * 
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Overrides the default toString() method so that an airport object may be printed. Usage: for an
   * Airport object named airport, System.out.print(airport) will print the following to standard
   * output: [AIRPORT ID]: [AIRPORT NAME], [AIRPORT CITY]
   */
  @Override
  public String toString() {
    return locid + ": " + name + ", " + city;
  }
}
