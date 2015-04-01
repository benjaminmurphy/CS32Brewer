package edu.brown.cs.brewer.commands;

public interface Expression<R> {
  /**
   * Evaluate the expression, and return a value of the appropriate type
   * @return
   */
  public R evaluate();
}
