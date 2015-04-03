package edu.brown.cs.brewer;

import java.util.Iterator;
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
  private Map<String, Object> variables;
  private List<Log> logs;
  private List<Expression<?>> program;
  private boolean isRunning;

  public BrewerRuntime(final Map<String, Object> _variables,
      final List<Log> _logs, final List<Expression<?>> _program) {
    this.variables = _variables;
    this.logs = _logs;
    this.program = _program;
  }

  @Override
  public void run() {
    if (!isRunning) {
      isRunning = true;
      for (Expression<?> e : program) {
        e.evaluate();
        if (isRunning) {
          break;
        }
      }
    }
  }

  public void kill() {
    this.isRunning = false;
  }
}
