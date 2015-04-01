package edu.brown.cs.brewer.commands;

/**
 * Implements the Not operator. This class Takes a boolean expression as an
 * argument and, when evaluated, returns the opposite of that expression's
 * result.
 *
 * @author raphaelkargon
 *
 */
public class NotOperator implements Expression<Boolean> {

  /**
   * The expression passed to Not
   */
  private Expression<Boolean> arg1;

  public NotOperator(final Expression<Boolean> _arg1) {
    this.arg1 = _arg1;
  }

  @Override
  public Boolean evaluate() {
    return !arg1.evaluate();
  }

}
