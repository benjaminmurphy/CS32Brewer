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
  private String varname;
  private Class<?> vartype;

  public GetCommand(BrewerRuntime _runtime, String name, Class<?> _vartype) {
    super(_runtime);
    this.varname = name;
    this.vartype = _vartype;
  }

  @Override
  public Object evaluate() throws ProgramKilledException {
	if(!runtime.isRunning()){
		throw new ProgramKilledException();
	}
    Variable var = runtime.getVariables().get(varname);
    System.out.println(varname);
    return vartype.cast(var.getValue());
  }

  @Override
  public Class<?> getType() {
    return vartype;
  }
}
