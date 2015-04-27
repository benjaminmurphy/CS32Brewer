package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

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
  public abstract Object evaluate() throws ProgramKilledException;

  public abstract Class<?> getType();
}
