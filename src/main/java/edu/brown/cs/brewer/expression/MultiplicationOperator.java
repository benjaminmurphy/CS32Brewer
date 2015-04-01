package edu.brown.cs.brewer.expression;

public class MultiplicationOperator implements Expression<Number> {

  private Expression<Number> arg1, arg2;

  public MultiplicationOperator(Expression<Number> _arg1, Expression<Number> _arg2) {
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Number evaluate() {
    return (arg1.evaluate().doubleValue() * arg2.evaluate().doubleValue());
  }

}
