package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * An expression that takes the modulo of one number by another.
 *
 */
public class ModuloOperator extends Expression {

  private Expression arg1, arg2;

  public ModuloOperator(BrewerRuntime _runtime, Expression _arg1,
    Expression _arg2) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Double evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }
    return ((Double) arg1.evaluate() % (Double) arg2.evaluate());
  }

  @Override
  public Class<?> getType() {
    return Double.class;
  }

}