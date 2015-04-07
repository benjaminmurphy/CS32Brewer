package edu.brown.cs.brewer.expression;

public interface Expression<R> {
  /**
   * Evaluate the expression, and return a value of the appropriate type.
   *
   * @return
   */
  public R evaluate();

  /**
   * Returns a List of Log objects which represent the logs of the code.
   *
   * @return
   */
  // public List<Log> log();
}
