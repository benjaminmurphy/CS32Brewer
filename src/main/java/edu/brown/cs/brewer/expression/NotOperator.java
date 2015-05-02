package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * Implements the Not operator. This class takes a boolean expression as an
 * argument and, when evaluated, returns the opposite of that expression's
 * result.
 *
 * @author raphaelkargon
 *
 */
public class NotOperator extends Expression {

  /**
   * The expression passed to Not.
   */
  private Expression arg1;

  /**
   * Creates a new Not operator.
   *
   * @param runtimeArg The containing runtime
   * @param argArg1 The argument passed to Not.
   */
  public NotOperator(final BrewerRuntime runtimeArg, final Expression argArg1) {
    super(runtimeArg);
    this.arg1 = argArg1;
  }

  @Override
  public final Boolean evaluate() throws ProgramKilledException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }
    return !(Boolean) arg1.evaluate();
  }


  @Override
  public final Class<?> getType() {
    return Boolean.class;
  }
}
