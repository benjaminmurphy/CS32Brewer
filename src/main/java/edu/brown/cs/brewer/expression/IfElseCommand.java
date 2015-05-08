package edu.brown.cs.brewer.expression;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.BrewerRuntime.ProgramKilledException;
import edu.brown.cs.brewer.BrewerRuntime.UndefinedVariableException;

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
   * @param runtimeArg The containing runtime
   * @param conditionArg The conditional for the if block
   * @param commandsIfTrueArg Commands to be run if conditional is true
   * @param commandsIfFalseArg Commands to be run if conditional is false (can be
   *        null)
   */
  public IfElseCommand(final BrewerRuntime runtimeArg,
      final Expression conditionArg, final List<Expression> commandsIfTrueArg,
      final List<Expression> commandsIfFalseArg) {
    super(runtimeArg);
    this.condition = conditionArg;

    if (commandsIfTrueArg == null) {
      this.commandsIfTrue = null;
    } else {
      this.commandsIfTrue = new ArrayList<Expression>(commandsIfTrueArg);
    }

    if (commandsIfFalseArg == null) {
      this.commandsIfFalse = null;
    } else {
      this.commandsIfFalse = new ArrayList<Expression>(commandsIfFalseArg);
    }
  }

  @Override
  public final Void evaluate() throws ProgramKilledException, UndefinedVariableException {
    if (!runtime().isRunning()) {
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
  public final Class<?> getType() {
    return Void.class;
  }
}
