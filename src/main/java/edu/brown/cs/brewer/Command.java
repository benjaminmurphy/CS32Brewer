package edu.brown.cs.brewer;

import edu.brown.cs.brewer.expression.Expression;

public abstract class Command {

  public String type;
  public String name;
  public Expression<?> value;

  // Control statement fields
  public Command condition;
  public Command[] commands;
  // TODO change JSON from else to elseCommands
  public Command[] elseCommands;

  // Operator fields
  public Command arg1;
  public Command arg2;

}
