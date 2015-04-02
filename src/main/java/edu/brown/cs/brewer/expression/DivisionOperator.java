package edu.brown.cs.brewer.expression;

/**
 *An expression that divides two numerical arguments
 *
 * @author raphaelkargon
 *
 */
public class DivisionOperator implements Expression<Number> {

  private Expression<Number> arg1, arg2;

  public DivisionOperator(Expression<Number> _arg1, Expression<Number> _arg2) {
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Number evaluate() {
    return (arg1.evaluate().doubleValue() / arg2.evaluate().doubleValue());
  }

}
