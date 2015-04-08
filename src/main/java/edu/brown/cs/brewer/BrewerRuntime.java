package edu.brown.cs.brewer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.ConcurrentRuntimeException;

import edu.brown.cs.brewer.expression.Expression;

/**
 * This class represents a Brewer program, as well its environment, ie the
 * variables and logs associated with it.
 *
 * @author raphaelkargon
 *
 */
public class BrewerRuntime implements Runnable {
  private Map<String, Variable<?>> variables;
  private List<Log> logs;
  private List<Expression<?>> program = null;
  private boolean isRunning = false;

  public BrewerRuntime(final Map<String, Variable<?>> _variables,
      final List<Log> _logs) {
    this.variables = _variables;
    this.logs = _logs;
  }

  // TODO what if fails?
  public void setProgram(List<Expression<?>> newprog) {
    if (!isRunning && program == null) {
      this.program = newprog;
    }
  }

  @Override
  public void run() {
    if (!isRunning) {
      isRunning = true;
      for (Expression<?> e : program) {
        e.evaluate();
        if (!isRunning) {
          break;
        }
      }
    }
  }

  public Map<String, Variable<?>> getVariables() {
    return variables;
  }

  public List<Log> getLogs() {
    return logs;
  }

  public void addLog(String msg, boolean isError) {
    Log l = new Log(msg, isError);
    logs.add(l);
  }

  public void kill() {
    this.isRunning = false;
  }
}
