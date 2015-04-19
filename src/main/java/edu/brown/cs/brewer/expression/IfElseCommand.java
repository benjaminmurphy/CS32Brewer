package edu.brown.cs.brewer.expression;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.brewer.BrewerRuntime;

public class IfElseCommand extends Expression {

  private Expression condition;
  private List<Expression> commandsIfTrue, commandsIfFalse;

  public IfElseCommand(BrewerRuntime _runtime, Expression _condition,
      List<Expression> _commandsIfTrue, List<Expression> _commandsIfFalse) {
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
  public Void evaluate() {
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
