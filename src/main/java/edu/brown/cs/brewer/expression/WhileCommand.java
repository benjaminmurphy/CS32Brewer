package edu.brown.cs.brewer.expression;

import java.util.List;

import edu.brown.cs.brewer.BrewerRuntime;

public class WhileCommand extends Expression<Void> {

  private Expression<Boolean> condition;
  private List<Expression<?>> commands;

  public WhileCommand(BrewerRuntime _runtime, Expression<Boolean> _condition,
      List<Expression<?>> _commands) {
    super(_runtime);
    this.condition = _condition;
    this.commands = _commands;
  }

  @Override
  public Void evaluate() {
    while (condition.evaluate()) {
      for (Expression<?> c : commands) {
        c.evaluate();
      }
    }
    return null;
  }

  @Override
  public Class<?> getType() {
    return Void.class;
  }
}
