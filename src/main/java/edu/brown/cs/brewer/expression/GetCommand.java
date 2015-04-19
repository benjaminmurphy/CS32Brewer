package edu.brown.cs.brewer.expression;

import java.util.Map;

import edu.brown.cs.brewer.BrewerRuntime;
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
  public Object evaluate() {
    Variable var = runtime.getVariables().get(varname);
    return vartype.cast(var.getValue());
  }

  @Override
  public Class<?> getType() {
    return vartype;
  }
}
