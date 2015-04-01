package edu.brown.cs.brewer.expression;

import java.util.Map;

/**
 * Updates a variable list by assigning the result of a certain expression to a certain variable name
 * @author raphaelkargon
 *
 */
public class SetCommand implements Expression<Void> {

  private Map<String, Object> variables;
  private String varname;
  private Expression<?> value;

  public SetCommand(Map<String, Object> vars, String name, Expression<?> val){
    this.variables = vars;
    this.varname = name;
    this.value = val;
  }

  @Override
  public Void evaluate() {
    Object oldval = variables.get(varname);
    Object newval = value.evaluate();

    //if variable doesn't exist yet, create it
    if(oldval == null){
      variables.put(varname, value.evaluate());
    }
    else{
      //check if the existing variable's type can accept the new value
      Class<? extends Object> vartype = oldval.getClass();
      Class<? extends Object> newtype = newval.getClass();
      if(vartype.isAssignableFrom(newtype)){
        //cast the new value to the old type, to maintain generality
        variables.put(varname, vartype.cast(newval));
      }
      else{
        throw new IllegalArgumentException("Type mismatch, attemping to assign "+newtype+" to variables of type "+vartype);
      }
    }

    return null;
  }

}
