package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.Variable;

/**
 * A statement that, when executed, returns the value of a given variable.
 *
 * @author raphaelkargon
 *
 */
public class GetCommand extends Expression {
  /**
   * The name of the variable being accessed.
   */
  private String varname;
  /**
   * The type of the variable.
   */
  private Class<?> vartype;

  /**
   * Creates a new GetCommand for the given variable.
   *
   * @param _runtime The containing runtime
   * @param name The name of the variable being accessed.
   * @param _vartype The type of the variable
   */
  public GetCommand(final BrewerRuntime _runtime, final String name,
      final Class<?> _vartype) {
    super(_runtime);
    this.varname = name;
    this.vartype = _vartype;
  }

  @Override
  public Object evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }
    Variable var = runtime.getVariables().get(varname);
    return vartype.cast(var.getValue());
  }

  @Override
  public Class<?> getType() {
    return vartype;
  }
}
