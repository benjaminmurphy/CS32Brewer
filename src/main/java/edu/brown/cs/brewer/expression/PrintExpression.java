package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.Variable;

public class PrintExpression extends Expression {

  private Expression exp;

  public PrintExpression(final BrewerRuntime _runtime, final Expression _exp) {
    super(_runtime);
    this.exp = _exp;
  }


  @Override
  public Void evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }

    if (Void.class.isAssignableFrom(exp.getType())) {
      runtime.addLog("Cannot print value of void expression.", true);
    } else {
      runtime.addLog(exp.evaluate().toString(), false);
    }
    return null;
  }

  @Override
  public Class<?> getType() {
    return Void.class;
  }
}
