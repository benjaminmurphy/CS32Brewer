package storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class Database {

  private String dbPath;
  private Connection conn;

  // TODO reform constructor as needed
  public Database(String dbPath) throws SQLException {
    this.dbPath = dbPath;
    openConnection();
    createTable();
    close();
  }

  /**
   * Open connection to database.
   */
  public void openConnection() {
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
      stat.execute("DROP TABLE IF EXISTS programs");
      stat.close();

      // Create the test tables
      String schema = "CREATE TABLE programs (id text primary key, program text);";
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

    String query = "INSERT INTO programs VALUES (?,?);";
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

    String query = "INSERT INTO programs VALUES (?,?);";
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
