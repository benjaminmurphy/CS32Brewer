package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.Variable;

public class PrintExpression extends Expression {

  private String varname;

  public PrintExpression(final BrewerRuntime _runtime, final String _varname) {
    super(_runtime);
    this.varname = _varname;
  }

  @Override
  public Void evaluate() {
    String msg = varname + ": ";
    Variable v = runtime.getVariables().get(varname);
    if (v == null) {
      msg += "null";
    } else {
      msg += v.getValue();
    }
    runtime.addLog(msg, false);
    return null;
  }

  @Override
  public Class<?> getType() {
    return Void.class;
  }
}
