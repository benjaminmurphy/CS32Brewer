package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * An expression that adds two numbers. Currently, each number is added as a
 * double value.
 *
 * @author raphaelkargon
 *
 */
public class AdditionOperator extends Expression {

  /**
   * These are the two expressions whose values are added together to produce a
   * sum.
   */
  private Expression arg1, arg2;

  /**
   *
   * @param runtimeArg The runtime containing this expression.
   * @param argArg1 The first addend
   * @param argArg2 The second addend
   */
  public AdditionOperator(final BrewerRuntime runtimeArg, final Expression argArg1,
      final Expression argArg2) {
    super(runtimeArg);
    this.arg1 = argArg1;
    this.arg2 = argArg2;
  }

  @Override
  public final Double evaluate() throws ProgramKilledException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }
    return ((Double) arg1.evaluate() + (Double) arg2.evaluate());
  }

  @Override
  public final Class<?> getType() {
    return Double.class;
  }

}
