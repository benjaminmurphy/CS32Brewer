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
   * @param _runtime The containing runtime.
   * @param _condition The conditional.
   * @param _commands The list of commands to be run.
   */
  public WhileCommand(BrewerRuntime _runtime, Expression _condition,
      List<Expression> _commands) {
    super(_runtime);
    this.condition = _condition;
    this.commands = _commands;
  }

  @Override
  public Void evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
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
