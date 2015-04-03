package edu.brown.cs.brewer.expression;

import java.util.Map;

public class PrintExpression implements Expression<Void> {

  private Map<String, Object> variables;
  private String varname;
  //TODO private Log/Printable log;


  @Override
  public Void evaluate() {
    // TODO log.update(variables.get(varname))
    variables.get(varname);
    return null;
  }

}
