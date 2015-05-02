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
   * @param _runtime The containing runtime
   * @param _exp The expression to be printed
   */
  public PrintExpression(final BrewerRuntime _runtime, final Expression _exp) {
    super(_runtime);
    this.exp = _exp;
  }


  @Override
  public Void evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }

    if (Void.class.isAssignableFrom(exp.getType())) {
      runtime.addLog("Cannot print value of void expression.", true);
    } else {
      runtime.addLog(exp.evaluate().toString(), false);
    }
    return null;
  }

  @Override
  public Class<?> getType() {
    return Void.class;
  }
}
