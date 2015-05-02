package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

public class ComparisonOperator extends Expression {

  private Expression arg1, arg2;
  private int cmp;

  public ComparisonOperator(final BrewerRuntime _runtime,
      final Expression _arg1, final Expression _arg2, final int _cmp) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
    this.cmp = _cmp;
  }

  @Override
  public Object evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }
    if (cmp == 0) {
      return arg1.evaluate().equals(arg2.evaluate());
    } else {
      int cmpResult =
          ((Comparable<Object>) arg1.evaluate()).compareTo(arg2.evaluate());
      return Math.signum(cmpResult) == Math.signum(cmp);
    }
  }

  @Override
  public Class<?> getType() {
    return Boolean.class;
  }

}
