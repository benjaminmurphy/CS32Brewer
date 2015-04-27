package edu.brown.cs.brewer.expression;

import java.util.List;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

public class WhileCommand extends Expression {

  private Expression condition;
  private List<Expression> commands;

  public WhileCommand(BrewerRuntime _runtime, Expression _condition,
      List<Expression> _commands) {
    super(_runtime);
    this.condition = _condition;
    this.commands = _commands;
  }

  @Override
  public Void evaluate() throws ProgramKilledException {
	if(!runtime.isRunning()){
		throw new ProgramKilledException();
	}
    while ((Boolean) condition.evaluate()) {
      for (Expression c : commands) {
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
