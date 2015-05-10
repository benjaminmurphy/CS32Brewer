package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.BrewerRuntime.UndefinedVariableException;

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
   * @param runtimeArg The containing runtime.
   * @param argArg1 The first argument in division
   * @param argArg2 The second argument in division
   */
  public DivisionOperator(final BrewerRuntime runtimeArg, final Expression argArg1,
      final Expression argArg2) {
    super(runtimeArg);
    this.arg1 = argArg1;
    this.arg2 = argArg2;
  }

  @Override
  public final Double evaluate() throws ProgramKilledException, UndefinedVariableException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }
    return ((Double) arg1.evaluate() / (Double) arg2.evaluate());
  }


  @Override
  public final Class<?> getType() {
    return Double.class;
  }
}
