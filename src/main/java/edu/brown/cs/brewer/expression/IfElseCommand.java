package edu.brown.cs.brewer.expression;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;

/**
 * Represents an if/else statement.
 *
 * @author raphaelkargon
 *
 */
public class IfElseCommand extends Expression {

  /**
   * The condition which, if it evaluates to true, runs one set of commands,
   * otherwise the other.
   */
  private Expression condition;

  /**
   * The expressions run by the if/else statement. Which expression is evaluated
   * is determined by the 'condition'.
   */
  private List<Expression> commandsIfTrue, commandsIfFalse;

  /**
   * Creates a new if/else block.
   *
   * @param _runtime The containing runtime
   * @param _condition The conditional for the if block
   * @param _commandsIfTrue Commands to be run if conditional is true
   * @param _commandsIfFalse Commands to be run if conditional is false (can be
   *        null)
   */
  public IfElseCommand(final BrewerRuntime _runtime,
      final Expression _condition, final List<Expression> _commandsIfTrue,
      final List<Expression> _commandsIfFalse) {
    super(_runtime);
    this.condition = _condition;

    if (_commandsIfTrue == null) {
      this.commandsIfTrue = null;
    } else {
      this.commandsIfTrue = new ArrayList<Expression>(_commandsIfTrue);
    }

    if (_commandsIfFalse == null) {
      this.commandsIfFalse = null;
    } else {
      this.commandsIfFalse = new ArrayList<Expression>(_commandsIfFalse);
    }
  }

  @Override
  public Void evaluate() throws ProgramKilledException {
    if (!runtime.isRunning()) {
      throw new ProgramKilledException();
    }
    if ((boolean) condition.evaluate()) {
      if (commandsIfTrue != null) {
        for (Expression c : commandsIfTrue) {
          c.evaluate();
        }
      }
    } else {
      if (commandsIfFalse != null) {
        for (Expression c : commandsIfFalse) {
          c.evaluate();
        }
      }
    }
    return null;
  }


  @Override
  public Class<?> getType() {
    return Void.class;
  }
}
