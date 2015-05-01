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
   * @param _runtime The containing runtime.
   * @param _arg1 The first operand
   * @param _arg2 The second operand
   */
  public AndOperator(final BrewerRuntime _runtime, final Expression _arg1,
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
    return (Boolean) arg1.evaluate() && (Boolean) arg2.evaluate();
  }

  @Override
  public Class<?> getType() {
    return Boolean.class;
  }

}
