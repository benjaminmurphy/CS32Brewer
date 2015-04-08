package edu.brown.cs.brewer.expression;

import java.util.Arrays;

import edu.brown.cs.brewer.BrewerRuntime;


public class IfElseCommand extends Expression<Void> {

  private Expression<Boolean> condition;
  private Expression<Void> commandsIfTrue[], commandsIfFalse[];

  public IfElseCommand(BrewerRuntime _runtime, Expression<Boolean> _condition, Expression<Void> _commandsIfTrue[], Expression<Void> _commandsIfFalse[]){
    super(_runtime);
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
