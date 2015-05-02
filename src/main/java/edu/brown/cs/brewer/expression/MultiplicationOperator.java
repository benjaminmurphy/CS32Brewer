package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * An operator that multiplies its two arguments together.
 *
 * @author raphaelkargon
 *
 */
public class MultiplicationOperator extends Expression {

  /**
   * The two factors in the multiplication.
   */
  private Expression arg1, arg2;

  /**
   * Creates a new multiplication block.
   *
   * @param runtimeArg The containing runtime
   * @param argArg1 The first argument
   * @param argArg2 The second argument
   */
  public MultiplicationOperator(BrewerRuntime runtimeArg, Expression argArg1,
      Expression argArg2) {
    super(runtimeArg);
    this.arg1 = argArg1;
    this.arg2 = argArg2;
  }

  @Override
  public final Double evaluate() throws ProgramKilledException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }
    return ((Double) arg1.evaluate() * (Double) arg2.evaluate());
  }

  @Override
  public final Class<?> getType() {
    return Double.class;
  }
}
