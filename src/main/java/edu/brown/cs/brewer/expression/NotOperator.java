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
   * @param _runtime The containing runtime
   * @param _arg1 The argument passed to Not.
   */
  public NotOperator(final BrewerRuntime _runtime, final Expression _arg1) {
    super(_runtime);
    this.arg1 = _arg1;
  }

  @Override
  public Boolean evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }
    return !(Boolean) arg1.evaluate();
  }


  @Override
  public Class<?> getType() {
    return Boolean.class;
  }
}
