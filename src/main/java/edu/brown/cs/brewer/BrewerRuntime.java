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
   * Keeps track of messages logged by the program. (Including runtime errors)
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
  public final void setProgram(final List<Expression> newprog) {
    if (!isRunning && program == null) {
      this.program = newprog;
    }
  }

  @Override
  public final void run() {
    if (!isRunning) {
      isRunning = true;

      try {
        for (Expression e : program) {
          e.evaluate();
        }
      } catch (ProgramKilledException e) {
        addLog("Program Killed.", true);
      } catch (UndefinedVariableException e1) {
        // Error Log added at evaluation to specify variable
      }
    }
    isRunning = false;
  }

  /**
   * Returns the set of variables for this runtime.
   *
   * @return The program's variables, in a map, stored by name
   */
  public final Map<String, Variable> getVariables() {
    return variables;
  }

  /**
   * Returns the program's logs.
   *
   * @return The program's logs
   */
  public final List<Log> getLogs() {
    return logs;
  }

  /**
   * Adds a log to the log list.
   *
   * @param msg The log message
   * @param isError Whether the log is an error
   */
  public final void addLog(final String msg, final boolean isError) {
    Log l = new Log(msg, isError);
    logs.add(l);
  }

  /**
   * Stops the program from running.
   */
  public final void kill() {
    this.isRunning = false;
  }

  /**
   * Clears the program logs.
   */
  public final void clearLogs() {
    this.logs = new ArrayList<Log>();
  }

  /**
   * Checks whether the program is currently running.
   *
   * @return whether or not the program is running
   */
  public final boolean isRunning() {
    return this.isRunning;
  }

  /**
   * An exception thrown when the program is killed and an expression is
   * evaluated. Used to stop program execution.
   *
   * @author raphaelkargon
   *
   */
  public static class ProgramKilledException extends Exception {

    /**
     * Generated UID.
     */
    private static final long serialVersionUID = -8717793914226114346L;

  }

  /**
   * An exception thrown when a variable is undefined.
   */
  public static class UndefinedVariableException extends Exception {

    /**
     * Generated UID.
     */
    private static final long serialVersionUID = -7567976769693274464L;

  }
}
