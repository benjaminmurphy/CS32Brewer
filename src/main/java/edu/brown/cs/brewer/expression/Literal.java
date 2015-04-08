package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;

/**
 * This is an expression that wraps a literal value. This can be used to send literals, such as numbers or strings, to
 * @author raphaelkargon
 *
 * @param <T> The type of the literal
 */
public class Literal<T> extends Expression<T> {

  private T value;

  public Literal(BrewerRuntime _runtime, final T val){
    super(_runtime);
    this.value = val;
  }

  @Override
  public T evaluate() {
    return value;
  }

}
