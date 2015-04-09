package edu.brown.cs.brewer.handlers;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.Command;
import edu.brown.cs.brewer.expression.Expression;
import edu.brown.cs.brewer.expression.Literal;

public class Parser {

  // A Print interface represents the types "log" and "error"
  private static BrewerRuntime runtime;

  public static List<Expression<?>> parse(Command[] comms) {
    List<Expression<?>> exprs = new ArrayList<Expression<?>>(comms.length);

    for (Command comm : comms) {
      exprs.add(evaluateR(comm));
    }

    return exprs;
  }

  /**
   * Recursively parses an expression into a command / commands and returns
   * the
   * commands
   *
   * Further inner parsing is already done
   *
   * Approach 2
   *
   * @param comm
   *          the expression
   */
  public static Expression<?> evaluateR(Command comm) {
    // TODO This should be transported to an evaluation class so that it can
    // be
    // used in inner command parsing as well
    String type = comm.type;
    Expression<?> expr = null;

    // TODO First evaluate for literal value, null is currently a placeholder
    // for type checking
    if (type == null) {
      // TODO add typing
      return new Literal<>(runtime, comm.value);
    }
    Class<?> valueType = comm.value.getClass();
    switch (type) {

    // TODO Add this as default constructor

    // case "set":
    // expr = new SetCommand<valueType>(runtime, comm.name, comm.value,
    // valueType);
    // case "get":
    // expr = new GetCommand<valueType>(runtime, comm.name,
    // comm.value.getClass());
    // case "while":
    // expr = new WhileCommand(runtime, evaluateR(comm.condition),
    // parse(comm.commands));
    // case "if":
    // expr = new IfElseCommand(runtime, evaluateR(comm.condition),
    // parse(comm.commands));
    //
    // // TODO Create subinterfaces for numOp and BinOp and apply the same
    // // default constructor methods
    // case "numeric_operator":
    // expr = new NumOp(runtime, comm.name, evaluateR(comm.arg1),
    // evaluateR(comm.arg2));
    // case "binary_operator":
    // expr = new BinOp(runtime, comm.name, evaluateR(comm.arg1),
    // evaluateR(comm.arg2));

    }
    return expr;
  }
}
