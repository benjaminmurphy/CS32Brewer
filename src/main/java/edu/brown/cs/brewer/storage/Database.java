package edu.brown.cs.brewer.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Database class for saving and reloading Brewer programs
 * @author Shi
 *
 */
public class Database {

  private String dbPath;
  private Connection conn;

  // TODO reform constructor as needed
  /**
   * Constructor for the Database class.
   * @param dbPath Path for the database
   * @throws SQLException If the schema is incorrect
   */
  public Database(String dbPath) throws SQLException {
    this.dbPath = dbPath;
    openConnection();
    createTable();
    close();
  }

  /**
   * Open connection to database.
   */
  private void openConnection() {
    // Set up a connection
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + dbPath;
      // Store the connection in a field
      this.conn = DriverManager.getConnection(urlToDB);
    } catch (ClassNotFoundException | SQLException e) {
      System.err.println("ERROR: Database initialization failed.");
      e.printStackTrace();
    }
  }

  // table values
  private void createTable() throws SQLException {
    try {
      // Delete the tables if they already exist
      // TODO remove this to prevent refreshing of database
      Statement stat = conn.createStatement();
      stat.execute("DROP TABLE IF EXISTS program");
      stat.close();

      // Create the test tables
      String schema = "CREATE TABLE program (id text primary key, program text);";
      PreparedStatement prep = conn.prepareStatement(schema);
      prep.executeUpdate();
      prep.close();

    } catch (SQLException e) {
      System.err.println("ERROR: Table initialization failed.");
      e.printStackTrace();
    }
  }

  /**
   * Takes an id and a program and stores it in the database.
   * @param id Program id
   * @param program Program JSON
   */
  public void addProgram(String id, String program) throws SQLException {
    openConnection();

    String query = "INSERT INTO program VALUES (?,?);";
    PreparedStatement ps;

    ps = conn.prepareStatement(query);
    ps.setString(1, id);
    ps.setString(2, program);
    ps.addBatch();
    ps.executeBatch();
    ps.close();

    close();
  }

  /**
   * Takes a collection of an array of Strings that are programs and stores them in the database.
   * @param programs A collection of String arrays of the form {id, program}.
   */
  public void addPrograms(Collection<String[]> programs)
    throws SQLException {
    openConnection();

    String query = "INSERT INTO program VALUES (?,?);";
    PreparedStatement ps;
    try {
      ps = conn.prepareStatement(query);

      for (String[] program : programs) {
        ps.setString(1, program[0]);
        ps.setString(2, program[1]);
        ps.addBatch();
        ps.executeBatch();
      }
    } catch (SQLException e) {
      System.err.println("ERROR: Adding program batch failed.");
      e.printStackTrace();
    }

    close();
  }

  /**
   * Takes an id and returns the program associated with that id.
   * @param id Program id
   * 
   * returns null if program id has no corresponding program.
   */
  public String getProgram(String id) throws SQLException {
    openConnection();

    String query = "SELECT p.program FROM program AS p WHERE p.id = ?;";

    PreparedStatement ps = conn.prepareStatement(query);
    ps.setString(1, id);
    ResultSet rs = ps.executeQuery();

    String program = null;
    if (rs.next()) {
      program = rs.getString(1);
    }

    rs.close();
    ps.close();
    close();

    return program;
  }

  /**
   * Returns the current programs associated with the database.
   */
  public Collection<String> getPrograms() throws SQLException {
    // TODO add some sort of ordering (maybe sort by timestamp)
    openConnection();

    // Select the program ids
    String query = "SELECT p.id FROM program;";

    PreparedStatement ps = conn.prepareStatement(query);
    ResultSet rs = ps.executeQuery();

    List<String> programIds = new ArrayList<String>();
    while (rs.next()) {
      programIds.add(rs.getString(1));
    }

    rs.close();
    ps.close();
    close();

    return programIds;
  }

  /**
   * Gets the size of the database
   * @return Number of entries.
   * @throws SQLException If schema incorrect.
   */
  public int getSize() throws SQLException {
    openConnection();

    String query = "SELECT COUNT(id) FROM program;";

    PreparedStatement ps = conn.prepareStatement(query);
    ResultSet rs = ps.executeQuery();

    Integer size = null;
    if (rs.next()) {
      size = rs.getInt(1);
    }
    close();

    return size;
  }

  /**
   * Closes database connection.
   * @throws SQLException 
   */
  public void close() {
    try {
      conn.close();
    } catch (SQLException e) {
      System.err
        .println("ERROR: Database connection could not be closed.");
      e.printStackTrace();
    }
  }
}
