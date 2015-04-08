package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;

/**
 * Implements the And operator, takes two boolean expressions and returns their
 * logical conjunction.
 *
 * @author raphaelkargon
 *
 */
public class AndOperator extends Expression<Boolean> {

  private Expression<Boolean> arg1, arg2;

  public AndOperator(final BrewerRuntime _runtime, final Expression<Boolean> _arg1,
      final Expression<Boolean> _arg2) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Boolean evaluate() {
    return arg1.evaluate() && arg2.evaluate();
  }

}
