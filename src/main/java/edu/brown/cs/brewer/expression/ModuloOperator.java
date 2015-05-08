package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.BrewerRuntime.UndefinedVariableException;

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
   * @param runtimeArg The containing runtime
   * @param argArg1 The first argument
   * @param argArg2 The second argument
   */
  public ModuloOperator(BrewerRuntime runtimeArg, Expression argArg1,
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
    return ((Double) arg1.evaluate() % (Double) arg2.evaluate());
  }

  @Override
  public final Class<?> getType() {
    return Double.class;
  }

}
