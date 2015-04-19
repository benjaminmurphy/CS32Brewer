package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;

public abstract class Expression {

  protected BrewerRuntime runtime;

  public Expression(final BrewerRuntime _runtime) {
    this.runtime = _runtime;
  }

  /**
   * Evaluate the expression, and return a value of the appropriate type.
   *
   * @return
   */
  public abstract Object evaluate();

  public abstract Class<?> getType();
}
