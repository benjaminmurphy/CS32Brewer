package edu.brown.cs.brewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.brewer.expression.Expression;
import edu.brown.cs.brewer.handlers.Stream.Runner;

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
  private Runner thread;

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

  public void setRunner(Runner runner) {
    this.thread = runner;
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
      if (thread != null) {
        thread.close();
      }
    }
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
    if (thread != null) {
      thread.message(msg, isError);
    }
  }

  public void kill() {
    this.isRunning = false;
  }
}
