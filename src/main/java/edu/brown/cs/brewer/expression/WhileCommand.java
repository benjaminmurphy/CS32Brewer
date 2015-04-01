package edu.brown.cs.brewer.expression;

public class WhileCommand implements Expression<Void> {

  private Expression<Boolean> condition;
  private Expression<Void> commands[];

  @Override
  public Void evaluate() {
    while(condition.evaluate()){
      for(Expression<Void> c : commands){
        c.evaluate();
      }
    }
    return null;
  }

}
