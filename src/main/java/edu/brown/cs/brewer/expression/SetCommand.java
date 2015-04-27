package edu.brown.cs.brewer.expression;

import java.util.Map;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.Variable;

/**
 * Updates a variable list by assigning the result of a certain expression to a
 * certain variable name
 *
 * @author raphaelkargon
 *
 */
public class SetCommand extends Expression {

  private String varname;
  private Expression value;
  private Class<?> vartype;

  /**
   * Creates a new set command, for the given variables set, variable name, and
   * new value
   *
   * @param vars The set of variables to update
   * @param name The name of the specific variable to modify
   * @param val The new value of the variable
   */
  public SetCommand(BrewerRuntime _runtime, String name, Expression val,
      Class<?> type) {
    super(_runtime);
    this.varname = name;
    this.value = val;
    this.vartype = type;
  }

  @Override
  public Void evaluate() throws ProgramKilledException {
	if(!runtime.isRunning()){
		throw new ProgramKilledException();
	}
    Map<String, Variable> vars = runtime.getVariables();
    Variable oldval = vars.get(varname);
    Object eval = value.evaluate();
    if (oldval == null) {
      vars.put(varname, new Variable(eval, vartype));
    } else if (oldval.getType().isAssignableFrom(vartype)) {
      oldval.setValue(eval);
    } else {
      String msg =
          "ERROR: Could not assign value of type " + vartype.getName()
              + " to variable " + varname + " of type"
              + oldval.getType().getName();
      runtime.addLog(msg, true);
      runtime.kill();
    }

    return null;
  }


  @Override
  public Class<?> getType() {
    return vartype;
  }
}
