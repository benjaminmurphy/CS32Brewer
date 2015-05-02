package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.Variable;

/**
 * This block prints the value of a certain expression to the log, and does not
 * return a value.
 *
 * @author raphaelkargon
 *
 */
public class PrintExpression extends Expression {

  /**
   * The expression to print to the log.
   */
  private Expression exp;

  /**
   * Creates a new print expression.
   *
   * @param runtimeArg The containing runtime
   * @param expArg The expression to be printed
   */
  public PrintExpression(final BrewerRuntime runtimeArg, final Expression expArg) {
    super(runtimeArg);
    this.exp = expArg;
  }


  @Override
  public final Void evaluate() throws ProgramKilledException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }

    if (Void.class.isAssignableFrom(exp.getType())) {
      runtime().addLog("Cannot print value of void expression.", true);
    } else {
      runtime().addLog(exp.evaluate().toString(), false);
    }
    return null;
  }

  @Override
  public final Class<?> getType() {
    return Void.class;
  }
}
