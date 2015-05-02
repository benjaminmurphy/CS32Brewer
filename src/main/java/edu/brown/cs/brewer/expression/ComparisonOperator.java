package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * Implements a comparison of two values. It follows the contract of Comparable,
 * and compares the output of compareTo to a user-specified value.
 *
 * @author raphaelkargon
 *
 */
public class ComparisonOperator extends Expression {

  /**
   * The two expressions whose values are compared.
   */
  private Expression arg1, arg2;

  /**
   * The comparison value. According to the contract of Comparable.compareTo():
   * 0 : a1 == a2 <br>
   * \<0 : a1 \< a2 <br>
   * \>0 : a1 \> a2
   */
  private int cmp;

  /**
   * Constructs a new comparison operator.
   *
   * @param _runtime The containing runtime
   * @param _arg1 The first argument
   * @param _arg2 The second argument
   * @param _cmp The comparison value (expected sign of compareTo(arg1, arg2) if
   *        this block returns true).
   */
  public ComparisonOperator(final BrewerRuntime _runtime,
      final Expression _arg1, final Expression _arg2, final int _cmp) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
    this.cmp = _cmp;
  }

  @Override
  public Object evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }
    if (cmp == 0) {
      return arg1.evaluate().equals(arg2.evaluate());
    } else {
      int cmpResult =
          ((Comparable<Object>) arg1.evaluate()).compareTo(arg2.evaluate());
      return Math.signum(cmpResult) == Math.signum(cmp);
    }
  }

  @Override
  public Class<?> getType() {
    return Boolean.class;
  }

}
