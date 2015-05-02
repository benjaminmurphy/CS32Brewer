package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * An expression that takes the modulo of one number by another.
 *
 */
public class ModuloOperator extends Expression {

  /**
   * The two subexpressions. This expression as a whole evaluates to arg1 %
   * arg2.
   */
  private Expression arg1, arg2;

  /**
   * Creates a new modulo block.
   *
   * @param _runtime The containing runtime
   * @param _arg1 The first argument
   * @param _arg2 The second argument
   */
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
