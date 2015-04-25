package edu.brown.cs.brewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import edu.brown.cs.brewer.expression.Expression;

/**
 * This class represents a Brewer program, as well its environment, ie the
 * variables and logs associated with it.
 *
 * @author raphaelkargon
 *
 */
public class BrewerRuntime implements Runnable {
  private Map<String, Variable> variables;
  private List<Log> logs;
  private List<Expression> program = null;
  private boolean isRunning = false;

  public BrewerRuntime() {
    this.variables = new HashMap<String, Variable>();
    this.logs = new ArrayList<Log>();
  }

  // TODO what if fails?
  public void setProgram(List<Expression> newprog) {
    if (!isRunning && program == null) {
      this.program = newprog;
    }
  }

  @Override
  public void run() {
    if (!isRunning) {
      isRunning = true;
      for (Expression e : program) {
        e.evaluate();
        if (!isRunning) {
          break;
        }
      }
    }
    isRunning = false;
  }

  public Map<String, Variable> getVariables() {
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

  public void clearLogs() {
    this.logs = new ArrayList<Log>();
  }

  public boolean isRunning() {
    return this.isRunning;
  }
}
