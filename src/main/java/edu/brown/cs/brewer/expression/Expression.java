package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * Represents a single expression in a Brewer program, the basic unit of
 * computation.
 *
 * @author raphaelkargon
 *
 */
public abstract class Expression {

  /**
   * The runtime encapsulating this expression.
   */
  protected BrewerRuntime runtime;

  /**
   * Creates an expression, within the given runtime. (The program still needs
   * to be added to the runtime for it to be run from the BrewerRuntime object.)
   *
   * @param _runtime The containing runtime.
   */
  public Expression(final BrewerRuntime _runtime) {
    this.runtime = _runtime;
  }

  /**
   * Evaluate the expression, and return a value of the appropriate type.
   *
   * @return The value produced when this expression is evaluated.
   */
  public abstract Object evaluate() throws ProgramKilledException;

  /**
   * Returns the type of value returned by evaluate().
   *
   * @return A Class object representign the type returned by evaluate()
   */
  public abstract Class<?> getType();
}
