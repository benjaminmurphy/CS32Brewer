package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;

/**
 * This is an expression that wraps a literal value. This can be used to send
 * literals, such as numbers or strings, to
 *
 * @author raphaelkargon
 *
 * @param <T> The type of the literal
 */
public class Literal extends Expression {

  private Object value;
  private Class<?> valtype;

  public Literal(BrewerRuntime _runtime, final Object val, final Class<?> type) {
    super(_runtime);
    if (!type.isInstance(val)) {
      throw new IllegalArgumentException("Value of type \"" + val.getClass()
          + "\" cannot be placed in literal of type \"" + type + "\".");
    }
    this.value = val;
    this.valtype = type;
  }

  @Override
  public Object evaluate() {
    return value;
  }


  @Override
  public Class<?> getType() {
    return valtype;
  }
}
