package edu.brown.cs.brewer.expression;

import java.util.List;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * An expression that represents a while loop.
 *
 * @author raphaelkargon
 *
 */
public class WhileCommand extends Expression {

  /**
   * The conditional for the loop.
   */
  private Expression condition;
  /**
   * The commands to be executed in the loop.
   */
  private List<Expression> commands;

  /**
   * Creates a new while loop object.
   *
   * @param runtimeArg The containing runtime.
   * @param conditionArg The conditional.
   * @param commandsArg The list of commands to be run.
   */
  public WhileCommand(BrewerRuntime runtimeArg, Expression conditionArg,
      List<Expression> commandsArg) {
    super(runtimeArg);
    this.condition = conditionArg;
    this.commands = commandsArg;
  }

  @Override
  public final Void evaluate() throws ProgramKilledException {
    if (!runtime().isRunning()) {
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
  public final Class<?> getType() {
    return Void.class;
  }
}
