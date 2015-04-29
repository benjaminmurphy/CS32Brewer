package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.Variable;

public class PrintExpression extends Expression {

  private String varname;
  private Literal literal;

  public PrintExpression(final BrewerRuntime _runtime, final String _varname) {
    super(_runtime);
    this.varname = _varname;
    this.literal = null;
  }

  public PrintExpression(final BrewerRuntime _runtime, final Literal _literal) {
    super(_runtime);
    this.literal = _literal;
    this.varname = null;
  }


  @Override
  public Void evaluate() throws ProgramKilledException {
    if(!runtime.isRunning()) {
      throw new ProgramKilledException();
    }

    if (varname != null) {
      String msg = varname + ": ";
      Variable v = runtime.getVariables().get(varname);
      if (v == null) {
        msg += "null";
      } else {
        msg += v.getValue();
      }
      runtime.addLog(msg, false);
      return null;
    } else {

      String msg = this.literal.evaluate().toString();
      runtime.addLog(msg, false);
      return null;
    }
  }

  @Override
  public Class<?> getType() {
    return Void.class;
  }
}
