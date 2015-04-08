package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;

/**
 * Implements the Not operator. This class Takes a boolean expression as an
 * argument and, when evaluated, returns the opposite of that expression's
 * result.
 *
 * @author raphaelkargon
 *
 */
public class NotOperator extends Expression<Boolean> {

  /**
   * The expression passed to Not
   */
  private Expression<Boolean> arg1;

  public NotOperator(final BrewerRuntime _runtime, final Expression<Boolean> _arg1) {
    super(_runtime);
    this.arg1 = _arg1;
  }

  @Override
  public Boolean evaluate() {
    return !arg1.evaluate();
  }

}
