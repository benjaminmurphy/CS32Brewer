package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;

public class GreaterThanOperator extends Expression {
  private Expression arg1, arg2;

  public GreaterThanOperator(final BrewerRuntime _runtime,
      final Expression _arg1, final Expression _arg2) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Boolean evaluate() {
    return ((Comparable<Object>) arg1.evaluate()).compareTo(arg2.evaluate()) > 0;
  }

  @Override
  public Class<?> getType() {
    return Boolean.class;
  }
}
