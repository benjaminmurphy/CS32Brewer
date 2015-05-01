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
   * @param _runtime The runtime containing this expression.
   * @param _arg1 The first addend
   * @param _arg2 The second addend
   */
  public AdditionOperator(final BrewerRuntime _runtime, final Expression _arg1,
      final Expression _arg2) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Double evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }
    return ((Double) arg1.evaluate() + (Double) arg2.evaluate());
  }

  @Override
  public Class<?> getType() {
    return Double.class;
  }

}
