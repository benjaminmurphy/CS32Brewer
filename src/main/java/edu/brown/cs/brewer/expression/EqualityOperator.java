package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * An operator that evalutes two expressions and checks if their values are
 * equal.
 *
 * @author raphaelkargon
 *
 */
public class EqualityOperator extends Expression {
  /**
   * Arguments to compare for equality. (Commutative)
   */
  private Expression arg1, arg2;

  /**
   * Creates an equality comparison.
   *
   * @param _runtime The containing runtime.
   * @param _arg1 The first expression being compared for equality.
   * @param _arg2 The second expression being compared for equality.
   */
  public EqualityOperator(final BrewerRuntime _runtime, final Expression _arg1,
      final Expression _arg2) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Boolean evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }
    return arg1.evaluate().equals(arg2.evaluate());
  }

  @Override
  public Class<?> getType() {
    return Boolean.class;
  }
}
