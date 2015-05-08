package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.BrewerRuntime.UndefinedVariableException;
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
   * @param runtimeArg The containing runtime
   * @param name The name of the variable being accessed.
   * @param vartypeArg The type of the variable
   */
  public GetCommand(final BrewerRuntime runtimeArg, final String name,
    final Class<?> vartypeArg) {
    super(runtimeArg);
    this.varname = name;
    this.vartype = vartypeArg;
  }

  @Override
  public final Object evaluate() throws ProgramKilledException,
    UndefinedVariableException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }
    Variable var = runtime().getVariables().get(varname);
    if (var == null) {
      runtime()
        .addLog(String.format("variable \"%s\" is undefined", varname), true);
      throw new UndefinedVariableException();
    } else {
      return vartype.cast(var.getValue());
    }
  }

  @Override
  public final Class<?> getType() {
    return vartype;
  }
}
