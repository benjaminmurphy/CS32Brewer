package edu.brown.cs.brewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.brewer.expression.Expression;

/**
 * This class represents a Brewer program, as well its environment, ie the
 * variables and logs associated with it.
 *
 * @author raphaelkargon
 *
 */
public class BrewerRuntime implements Runnable {
  /**
   * Keeps track of the program's variables.
   */
  private Map<String, Variable> variables;

  /**
   * Keeps track of messages logged by the program. (Includign runtime errors)
   */
  private List<Log> logs;

  /**
   * The list of expressions that represent the program.
   */
  private List<Expression> program = null;

  /**
   * Whether or not the program is running.
   */
  private boolean isRunning = false;

  /**
   * Constructs a runtime with an empty variable list and an empty log list.
   */
  public BrewerRuntime() {
    this.variables = new HashMap<String, Variable>();
    this.logs = new ArrayList<Log>();
  }

  /**
   * Sets the program for this runtime.
   *
   * @param newprog The new program.
   */
  public void setProgram(final List<Expression> newprog) {
    if (!isRunning && program == null) {
      this.program = newprog;
    }
  }

  @Override
  public void run() {
    if (!isRunning) {
      isRunning = true;

      try {
        for (Expression e : program) {
          e.evaluate();
        }
      } catch (ProgramKilledException e) {
        addLog("Program Killed.", true);
      }
    }
    isRunning = false;
  }

  /**
   * Returns the set of variables for this runtime.
   *
   * @return The program's variables, in a map, stored by name
   */
  public Map<String, Variable> getVariables() {
    return variables;
  }

  /**
   * Returns the program's logs
   *
   * @return The program's logs
   */
  public List<Log> getLogs() {
    return logs;
  }

  /**
   * Adds a log to the log list.
   *
   * @param msg The log message
   * @param isError Whether the log is an error
   */
  public void addLog(final String msg, final boolean isError) {
    Log l = new Log(msg, isError);
    logs.add(l);
  }

  /**
   * Stops the program from running.
   */
  public void kill() {
    this.isRunning = false;
  }

  /**
   * Clears the program logs.
   */
  public void clearLogs() {
    this.logs = new ArrayList<Log>();
  }

  /**
   * Checks whether the program is currently running.
   *
   * @return whether or not the program is running
   */
  public boolean isRunning() {
    return this.isRunning;
  }

  /**
   * An exception thrown when the program is killed an an expression is
   * evaluated. Used to stop program execution.
   *
   * @author raphaelkargon
   *
   */
  public static class ProgramKilledException extends Exception {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -8717793914226114346L;

  }
}
