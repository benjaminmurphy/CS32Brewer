package edu.brown.cs.brewer.expression;

public class EqualityOperator<T> implements Expression<Boolean> {
  private Expression<T> arg1, arg2;

  public EqualityOperator(final Expression<T> _arg1, final Expression<T> _arg2){
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Boolean evaluate() {
    return arg1.evaluate().equals(arg2.evaluate());
  }
}
