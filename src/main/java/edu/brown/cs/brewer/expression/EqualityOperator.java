package edu.brown.cs.brewer.expression;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

public class EqualityOperator extends Expression {
  private Expression arg1, arg2;

  public EqualityOperator(final BrewerRuntime _runtime, final Expression _arg1,
      final Expression _arg2) {
    super(_runtime);
    this.arg1 = _arg1;
    this.arg2 = _arg2;
  }

  @Override
  public Boolean evaluate() throws ProgramKilledException {
    return arg1.evaluate().equals(arg2.evaluate());
  }

  @Override
  public Class<?> getType() {
    return Boolean.class;
  }
}
