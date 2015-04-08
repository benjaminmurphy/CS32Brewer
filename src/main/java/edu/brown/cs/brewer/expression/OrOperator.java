package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;

/**
 * Implements the Or operator, takes two boolean expressions and returns their
 * logical disjunction.
 *
 * @author raphaelkargon
 *
 */
public class OrOperator extends Expression<Boolean> {

  private Expression<Boolean> arg1, arg2;

  public OrOperator(final BrewerRuntime _runtime, final Expression<Boolean> _arg1,
      final Expression<Boolean> _arg2) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Boolean evaluate() {
    return arg1.evaluate() || arg2.evaluate();
  }

}
