package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;

/**
 * An expression that divides two numerical arguments
 *
 * @author raphaelkargon
 *
 */
public class DivisionOperator extends Expression<Double> {

  private Expression<Double> arg1, arg2;

  public DivisionOperator(BrewerRuntime _runtime, Expression<Double> _arg1,
      Expression<Double> _arg2) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Double evaluate() {
    return (arg1.evaluate() / arg2.evaluate());
  }


  @Override
  public Class<?> getType() {
    return Double.class;
  }
}
