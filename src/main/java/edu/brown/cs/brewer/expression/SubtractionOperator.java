package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.BrewerRuntime.UndefinedVariableException;

/**
 * An expression that subtracts two values.
 *
 * @author raphaelkargon
 *
 */
public class SubtractionOperator extends Expression {

  /**
   * The operands to be subtracted.
   */
  private Expression arg1, arg2;

  /**
   * Creates a new subtraction operator.
   *
   * @param runtimeArg The containing runtime.
   * @param argArg1 The first argument.
   * @param argArg2 The second argument.
   */
  public SubtractionOperator(BrewerRuntime runtimeArg, Expression argArg1,
      Expression argArg2) {
    super(runtimeArg);
    this.arg1 = argArg1;
    this.arg2 = argArg2;
  }

  @Override
  public final Double evaluate() throws ProgramKilledException, UndefinedVariableException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }
    return ((Double) arg1.evaluate() - (Double) arg2.evaluate());
  }

  @Override
  public final Class<?> getType() {
    return Double.class;
  }
}
