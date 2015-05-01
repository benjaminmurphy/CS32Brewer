package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * An expression that divides two numerical arguments.
 *
 * @author raphaelkargon
 *
 */
public class DivisionOperator extends Expression {

  /**
   * The two arguments (in order) for the division operation.
   */
  private Expression arg1, arg2;

  /**
   * Constructs a division operation.
   *
   * @param _runtime The containing runtime.
   * @param _arg1 The first argument in division
   * @param _arg2 The second argument in division
   */
  public DivisionOperator(final BrewerRuntime _runtime, final Expression _arg1,
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
    return ((Double) arg1.evaluate() / (Double) arg2.evaluate());
  }


  @Override
  public Class<?> getType() {
    return Double.class;
  }
}
