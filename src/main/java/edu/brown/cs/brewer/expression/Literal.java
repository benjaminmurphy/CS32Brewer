package edu.brown.cs.brewer.expression;

/**
 * This is an expression that wraps a literal value. This can be used to send literals, such as numbers or strings, to
 * @author raphaelkargon
 *
 * @param <T> The type of the literal
 */
public class Literal<T> implements Expression<T> {

  private T value;

  public Literal(final T val){
    this.value = val;
  }

  @Override
  public T evaluate() {
    return value;
  }

}
