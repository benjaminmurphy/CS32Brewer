package edu.brown.cs.brewer.expression;

import java.util.Map;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.Variable;

/**
 * Updates a variable list by assigning the result of a certain expression to a
 * certain variable name
 *
 * @author raphaelkargon
 *
 */
public class SetCommand<T> extends Expression<Void> {

  private String varname;
  private Expression<? extends T> value;
  private Class<T> vartype;

  /**
   * Creates a new set command, for the given variables set, variable name, and
   * new value
   *
   * @param vars The set of variables to update
   * @param name The name of the specific variable to modify
   * @param val The new value of the variable
   */
  public SetCommand(BrewerRuntime _runtime, String name,
      Expression<? extends T> val, Class<T> type) {
    super(_runtime);
    this.varname = name;
    this.value = val;
    this.vartype = type;
  }

  @Override
  public Void evaluate() {
    Map<String, Variable<?>> vars = runtime.getVariables();
    Variable<?> oldval = vars.get(varname);
    T eval = value.evaluate();
    if (oldval == null) {
      vars.put(varname, new Variable<T>(eval, vartype));
    } else if (oldval.getType().isAssignableFrom(vartype)) {
      oldval.setValue(eval);
    } else {
      System.out.println(oldval.getType().getName());
      System.out.println(vartype.getName());
      // TODO emit error
    }

    return null;
  }

}
