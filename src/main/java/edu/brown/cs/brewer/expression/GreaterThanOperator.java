package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * An expression that determined whether the value of one subexpression is
 * greater than the other.
 *
 * @author raphaelkargon
 *
 */
public class GreaterThanOperator extends Expression {
  /**
   * The expressions whose values are being compared.
   */
  private Expression arg1, arg2;

  /**
   * Creates a greater than operator.
   *
   * @param _runtime The containing runtime
   * @param _arg1 The first argument
   * @param _arg2 The second argument
   */
  public GreaterThanOperator(final BrewerRuntime _runtime,
      final Expression _arg1, final Expression _arg2) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Boolean evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }
    return ((Comparable<Object>) arg1.evaluate()).compareTo(arg2.evaluate()) > 0;
  }

  @Override
  public Class<?> getType() {
    return Boolean.class;
  }
}
