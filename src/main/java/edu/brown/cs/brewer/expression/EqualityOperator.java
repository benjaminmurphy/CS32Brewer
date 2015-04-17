package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;

public class EqualityOperator<T> extends Expression<Boolean> {
  private Expression<T> arg1, arg2;

  public EqualityOperator(final BrewerRuntime _runtime,
      final Expression<T> _arg1, final Expression<T> _arg2) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Boolean evaluate() {
    return arg1.evaluate().equals(arg2.evaluate());
  }

  @Override
  public Class<?> getType() {
    return arg1.getType();
  }
}
