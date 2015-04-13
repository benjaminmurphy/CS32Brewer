package edu.brown.cs.brewer.handlers;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.Command;
import edu.brown.cs.brewer.expression.AdditionOperator;
import edu.brown.cs.brewer.expression.AndOperator;
import edu.brown.cs.brewer.expression.DivisionOperator;
import edu.brown.cs.brewer.expression.EqualityOperator;
import edu.brown.cs.brewer.expression.Expression;
import edu.brown.cs.brewer.expression.GetCommand;
import edu.brown.cs.brewer.expression.GreaterThanOperator;
import edu.brown.cs.brewer.expression.IfElseCommand;
import edu.brown.cs.brewer.expression.LessThanOperator;
import edu.brown.cs.brewer.expression.Literal;
import edu.brown.cs.brewer.expression.MultiplicationOperator;
import edu.brown.cs.brewer.expression.NotOperator;
import edu.brown.cs.brewer.expression.OrOperator;
import edu.brown.cs.brewer.expression.PrintExpression;
import edu.brown.cs.brewer.expression.SetCommand;
import edu.brown.cs.brewer.expression.SubtractionOperator;
import edu.brown.cs.brewer.expression.WhileCommand;

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
  // TODO(1) fix typing on raw types
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static Expression<?> evaluateR(Command comm) {
    Expression<?> expr = null;

    String type = comm.type;
    Object value = comm.value;
    String name = comm.name;

    // TODO Reform the Command class to incorporate all possible values and
    // interpret null as no value. Then assign the values beforehand to condense
    // switch statements

    // TODO First evaluate for literal value, null is currently a placeholder
    // for type checking
    if (type == null) {
      // TODO add typing
      return new Literal<>(runtime, comm.value);
    }
    Class<?> valueType = comm.value.getClass();

    switch (type) {

    // TODO Add this as default factory constructor in some superclass using a
    // HashMap

    case "set":
      expr = new SetCommand(runtime, comm.name, comm.value, valueType);
    case "get":
      expr = new GetCommand(runtime, comm.name, comm.value.getClass());

    case "log":
      expr = new PrintExpression(runtime, name);

    case "binary_operator":
      switch (name) {

      case "eq":
        expr = new EqualityOperator(runtime, evaluateR(comm.arg1),
          evaluateR(comm.arg2));
      case "less":
        expr = new LessThanOperator(runtime, evaluateR(comm.arg1),
          evaluateR(comm.arg2));
      case "greater":
        expr = new GreaterThanOperator(runtime, evaluateR(comm.arg1),
          evaluateR(comm.arg2));
      }

    case "logic_operator":
      switch (name) {

      case "and":
        expr = new AndOperator(runtime,
          (Expression<Boolean>) evaluateR(comm.arg1),
          (Expression<Boolean>) evaluateR(comm.arg2));
      case "or":
        expr = new OrOperator(runtime,
          (Expression<Boolean>) evaluateR(comm.arg1),
          (Expression<Boolean>) evaluateR(comm.arg2));
      }

    case "numeric_operator":
      switch (name) {

      case "add":
        expr = new AdditionOperator(runtime,
          (Expression<Double>) evaluateR(comm.arg1),
          (Expression<Double>) evaluateR(comm.arg2));
      case "sub":
        expr = new SubtractionOperator(runtime,
          (Expression<Double>) evaluateR(comm.arg1),
          (Expression<Double>) evaluateR(comm.arg2));
      case "mul":
        expr = new MultiplicationOperator(runtime,
          (Expression<Double>) evaluateR(comm.arg1),
          (Expression<Double>) evaluateR(comm.arg2));
      case "div":
        expr = new DivisionOperator(runtime,
          (Expression<Double>) evaluateR(comm.arg1),
          (Expression<Double>) evaluateR(comm.arg2));
      }

    case "unary_operator":
      switch (name) {

      case "not":
        expr = new NotOperator(runtime,
          (Expression<Boolean>) evaluateR(comm.arg1));
      }

    case "if":
    case "ifelse":
      expr = new IfElseCommand(runtime,
        (Expression<Boolean>) evaluateR(comm.condition),
        parse(comm.commands), parse(comm.elseCommands));

    case "while":
      expr = new WhileCommand(runtime,
        (Expression<Boolean>) evaluateR(comm.condition),
        parse(comm.commands));
    }
    return expr;
  }
}
