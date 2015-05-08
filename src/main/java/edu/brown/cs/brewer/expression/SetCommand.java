package edu.brown.cs.brewer.expression;

import java.util.Map;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.BrewerRuntime.UndefinedVariableException;
import edu.brown.cs.brewer.Variable;

/**
 * Updates a variable list by assigning the result of a certain expression to a
 * certain variable name.
 *
 * @author raphaelkargon
 *
 */
public class SetCommand extends Expression {

  /**
   * The name of the variable.
   */
  private String varname;
  /**
   * The value of the variable.
   */
  private Expression value;
  /**
   * The type of the variable.
   */
  private Class<?> vartype;

  /**
   * Creates a new set command, for the given variables set, variable name, and
   * new value.
   *
   * @param runtimeArg The containing runtime.
   * @param name The name of the specific variable to modify
   * @param val The new value of the variable
   * @param type The type of the variable
   */
  public SetCommand(final BrewerRuntime runtimeArg, final String name,
    final Expression val, final Class<?> type) {
    super(runtimeArg);
    this.varname = name;
    this.value = val;
    this.vartype = type;
  }

  @Override
  public final Void evaluate() throws ProgramKilledException,
    UndefinedVariableException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }
    Map<String, Variable> vars = runtime().getVariables();
    Variable oldval = vars.get(varname);
    Object eval = value.evaluate();
    if (oldval == null) {
      vars.put(varname, new Variable(eval, vartype));
    } else if (oldval.getType().isAssignableFrom(vartype)) {
      oldval.setValue(eval);
    } else {
      String msg = "ERROR: Could not assign value of type "
        + vartype.getName() + " to variable " + varname + " of type"
        + oldval.getType().getName();
      runtime().addLog(msg, true);
      runtime().kill();
    }

    return null;
  }

  @Override
  public final Class<?> getType() {
    return vartype;
  }
}
