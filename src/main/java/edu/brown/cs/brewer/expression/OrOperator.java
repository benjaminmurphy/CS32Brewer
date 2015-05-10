package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.BrewerRuntime.UndefinedVariableException;

/**
 * Implements the Or operator, takes two boolean expressions and returns their
 * logical disjunction.
 *
 * @author raphaelkargon
 *
 */
public class OrOperator extends Expression {

  /**
   * The first and second arguments passed to Or.
   */
  private Expression arg1, arg2;

  /**
   * Creates a new Or block.
   *
   * @param runtimeArg The containing runtime
   * @param argArg1 The first argument
   * @param argArg2 The second argument
   */
  public OrOperator(final BrewerRuntime runtimeArg, final Expression argArg1,
      final Expression argArg2) {
    super(runtimeArg);
    this.arg1 = argArg1;
    this.arg2 = argArg2;
  }

  @Override
  public final Boolean evaluate() throws ProgramKilledException, UndefinedVariableException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }
    return (Boolean) arg1.evaluate() || (Boolean) arg2.evaluate();
  }

  @Override
  public final Class<?> getType() {
    return Boolean.class;
  }

}
