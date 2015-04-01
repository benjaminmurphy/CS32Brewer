package edu.brown.cs.brewer.expression;

public class LessThanOperator<T extends Comparable<T>> implements Expression<Boolean> {
  private Expression<T> arg1, arg2;

  public LessThanOperator(final Expression<T> _arg1, final Expression<T> _arg2){
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Boolean evaluate() {
    return arg1.evaluate().compareTo(arg2.evaluate()) < 0;
  }
}
