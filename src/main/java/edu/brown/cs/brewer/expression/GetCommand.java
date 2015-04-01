package edu.brown.cs.brewer.expression;

import java.util.Map;

/**
 * A statement that, when executed, returns the value of a given variable.
 *
 * As the variable type is not known until evaluation, it returns an object.
 *
 * Use o.getClass().cast(o) to cast the object to its proper type.
 *
 * @author raphaelkargon
 *
 */
public class GetCommand implements Expression<Object> {

  private Map<String, Object> variables;
  private String varname;

  public GetCommand(Map<String, Object> vars, String name){
    this.variables = vars;
    this.varname = name;
  }

  @Override
  public Object evaluate() {
    return variables.get(varname);
  }

}
