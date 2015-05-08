package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.BrewerRuntime.UndefinedVariableException;

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
  private BrewerRuntime runtime;

  /**
   * Creates an expression, within the given runtime. (The program still needs
   * to be added to the runtime for it to be run from the BrewerRuntime object.)
   *
   * @param runtimeArg The containing runtime.
   */
  public Expression(final BrewerRuntime runtimeArg) {
    this.runtime = runtimeArg;
  }

  /**
   * Evaluate the expression, and return a value of the appropriate type.
   *
   * @return The value produced when this expression is evaluated.
   * @throws ProgramKilledException When the program is killed and this method
   *         is still called.
   * @throws UndefinedVariableException When the Expression is an undefined variable.
   */
  public abstract Object evaluate() throws ProgramKilledException,
    UndefinedVariableException;

  /**
   * Returns the type of value returned by evaluate().
   *
   * @return A Class object representign the type returned by evaluate()
   */
  public abstract Class<?> getType();

  /**
   * Returns this expression's corresponding runtime.
   *
   * @return the runtime
   */
  protected final BrewerRuntime runtime() {
    return runtime;
  }
}
