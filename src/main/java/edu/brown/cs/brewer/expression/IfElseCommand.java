package edu.brown.cs.brewer.expression;

import java.util.Arrays;


public class IfElseCommand implements Expression<Void> {

  private Expression<Boolean> condition;
  private Expression<Void> commandsIfTrue[], commandsIfFalse[];

  public IfElseCommand(Expression<Boolean> _condition, Expression<Void> _commandsIfTrue[], Expression<Void> _commandsIfFalse[]){
    this.condition = _condition;

    if(_commandsIfTrue == null){
      this.commandsIfTrue = null;
    }
    else{
      this.commandsIfTrue = Arrays.copyOf(_commandsIfTrue, _commandsIfTrue.length);
    }

    if(_commandsIfFalse == null){
      this.commandsIfFalse = null;
    }
    else{
      this.commandsIfFalse = Arrays.copyOf(_commandsIfFalse, _commandsIfFalse.length);
    }
  }

  @Override
  public Void evaluate() {
    if(condition.evaluate()){
      if(commandsIfTrue != null){
        for(Expression<Void> c : commandsIfTrue){
          c.evaluate();
        }
      }
    }
    else{
      if(commandsIfFalse != null){
        for(Expression<Void> c : commandsIfFalse){
          c.evaluate();
        }
      }
    }

    return null;
  }

}
