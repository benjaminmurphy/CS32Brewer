package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * Implements the And operator, takes two boolean expressions and returns their
 * logical conjunction.
 *
 * @author raphaelkargon
 *
 */
public class AndOperator extends Expression {

  /**
   * The two operands for the And expression.
   */
  private Expression arg1, arg2;

  /**
   * Constructs an And operator. (Note: And is commutative, and operand order
   * does not matter.)
   *
   * @param runtimeArg The containing runtime.
   * @param argArg1 The first operand
   * @param argArg2 The second operand
   */
  public AndOperator(final BrewerRuntime runtimeArg, final Expression argArg1,
      final Expression argArg2) {
    super(runtimeArg);
    this.arg1 = argArg1;
    this.arg2 = argArg2;
  }

  @Override
  public final Boolean evaluate() throws ProgramKilledException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }
    return (Boolean) arg1.evaluate() && (Boolean) arg2.evaluate();
  }

  @Override
  public final Class<?> getType() {
    return Boolean.class;
  }

}
