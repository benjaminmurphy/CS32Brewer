package edu.brown.cs.brewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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

  // private Stack<Expression> programStack;
  // private Stack<Object> returnValuesStack;

  public BrewerRuntime() {
    this.variables = new HashMap<String, Variable>();
    this.logs = new ArrayList<Log>();
    // this.programStack = new Stack<>();
    // this.returnValuesStack = new Stack<>();
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

      // // set up stacks
      // programStack.clear();
      // returnValuesStack.clear();
      // for (int i = program.size() - 1; i >= 0; i--) {
      // programStack.push(program.get(i));
      // }
      //
      // while (isRunning && !programStack.isEmpty()) {
      // programStack.pop().evaluate();
      // }

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

  // public void pushExpressionToStack(Expression e) {
  // this.programStack.push(e);
  // }
  //
  // public void pushValueToStack(Object o) {
  // this.returnValuesStack.push(o);
  // }
  //
  // public Expression popExpressionFromStack() {
  // return this.programStack.pop();
  // }
  //
  // public Object popValueFromStack() {
  // return this.returnValuesStack.pop();
  // }

  public class ProgramKilledException extends Exception {

  }
}
