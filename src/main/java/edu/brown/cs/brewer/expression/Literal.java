package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * This is an expression that represents a literal value.
 *
 * @author raphaelkargon
 *
 */
public class Literal extends Expression {

  /**
   * The value of this literal.
   */
  private Object value;
  /**
   * The type of this literal.
   */
  private Class<?> valtype;

  /**
   * Constructs a new Literal object.
   *
   * @param runtimeArg The containing runtime.
   * @param val The literal's value
   * @param type The literal's type.
   */
  public Literal(BrewerRuntime runtimeArg, final Object val, final Class<?> type) {
    super(runtimeArg);
    if (!type.isInstance(val)) {
      throw new IllegalArgumentException("Value of type \"" + val.getClass()
          + "\" cannot be placed in literal of type \"" + type + "\".");
    }
    this.value = val;
    this.valtype = type;
  }

  @Override
  public final Object evaluate() throws ProgramKilledException {
    if (!runtime().isRunning()) {
      throw new ProgramKilledException();
    }
    return value;
  }


  @Override
  public final Class<?> getType() {
    return valtype;
  }
}
