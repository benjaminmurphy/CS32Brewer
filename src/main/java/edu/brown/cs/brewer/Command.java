package edu.brown.cs.brewer;

import edu.brown.cs.brewer.expression.Expression;

public abstract class Command {

  public String type;
  public String name;
  public Expression<?> value;

  public Command condition;
  public Command[] commands;

}
