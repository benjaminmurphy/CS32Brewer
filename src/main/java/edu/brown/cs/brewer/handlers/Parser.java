package edu.brown.cs.brewer.handlers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.Command;
import edu.brown.cs.brewer.Variable;
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

  // public static List<Expression<?>> parse(Command[] comms) {
  // List<Expression<?>> exprs = new ArrayList<Expression<?>>(comms.length);
  //
  // for (Command comm : comms) {
  // exprs.add(evaluateR(comm));
  // }
  //
  // return exprs;
  // }

  /**
   * Recursively parses an expression into a command / commands and returns the
   * commands
   *
   * Further inner parsing is already done
   *
   * Approach 2
   *
   * @param comm the expression
   */
  // // TODO(1) fix typing on raw types
  // @SuppressWarnings({"unchecked", "rawtypes"})
  // public static Expression<?> evaluateR(Command comm) {
  // Expression<?> expr = null;
  //
  // String type = comm.type;
  // Object value = comm.value;
  // String name = comm.name;
  //
  // // TODO Reform the Command class to incorporate all possible values and
  // // interpret null as no value. Then assign the values beforehand to
  // condense
  // // switch statements
  //
  // // TODO First evaluate for literal value, null is currently a placeholder
  // // for type checking
  // if (type == null) {
  // // TODO add typing
  // return new Literal<>(runtime, comm.value);
  // }
  // Class<?> valueType = comm.value.getClass();
  //
  // switch (type) {
  //
  // // TODO Add this as default factory constructor in some superclass using a
  // // HashMap
  //
  // case "set":
  // expr = new SetCommand(runtime, comm.name, comm.value, valueType);
  // case "get":
  // expr = new GetCommand(runtime, comm.name, comm.value.getClass());
  //
  // case "log":
  // expr = new PrintExpression(runtime, name);
  //
  // case "binary_operator":
  // switch (name) {
  //
  // case "eq":
  // expr =
  // new EqualityOperator(runtime, evaluateR(comm.arg1),
  // evaluateR(comm.arg2));
  // case "less":
  // expr =
  // new LessThanOperator(runtime, evaluateR(comm.arg1),
  // evaluateR(comm.arg2));
  // case "greater":
  // expr =
  // new GreaterThanOperator(runtime, evaluateR(comm.arg1),
  // evaluateR(comm.arg2));
  // }
  //
  // case "logic_operator":
  // switch (name) {
  //
  // case "and":
  // expr =
  // new AndOperator(runtime,
  // (Expression<Boolean>) evaluateR(comm.arg1),
  // (Expression<Boolean>) evaluateR(comm.arg2));
  // case "or":
  // expr =
  // new OrOperator(runtime,
  // (Expression<Boolean>) evaluateR(comm.arg1),
  // (Expression<Boolean>) evaluateR(comm.arg2));
  // }
  //
  // case "numeric_operator":
  // switch (name) {
  //
  // case "add":
  // expr =
  // new AdditionOperator(runtime,
  // (Expression<Double>) evaluateR(comm.arg1),
  // (Expression<Double>) evaluateR(comm.arg2));
  // case "sub":
  // expr =
  // new SubtractionOperator(runtime,
  // (Expression<Double>) evaluateR(comm.arg1),
  // (Expression<Double>) evaluateR(comm.arg2));
  // case "mul":
  // expr =
  // new MultiplicationOperator(runtime,
  // (Expression<Double>) evaluateR(comm.arg1),
  // (Expression<Double>) evaluateR(comm.arg2));
  // case "div":
  // expr =
  // new DivisionOperator(runtime,
  // (Expression<Double>) evaluateR(comm.arg1),
  // (Expression<Double>) evaluateR(comm.arg2));
  // }
  //
  // case "unary_operator":
  // switch (name) {
  //
  // case "not":
  // expr =
  // new NotOperator(runtime,
  // (Expression<Boolean>) evaluateR(comm.arg1));
  // }
  //
  // case "if":
  // case "ifelse":
  // expr =
  // new IfElseCommand(runtime,
  // (Expression<Boolean>) evaluateR(comm.condition),
  // parse(comm.commands), parse(comm.elseCommands));
  //
  // case "while":
  // expr =
  // new WhileCommand(runtime,
  // (Expression<Boolean>) evaluateR(comm.condition),
  // parse(comm.commands));
  // }
  // return expr;
  // }

  public static BrewerRuntime parseJSONProgram(String json) {
    Gson GSON = new Gson();// TODO make this static
    JsonParser parser = new JsonParser();
    JsonObject mainprog = parser.parse(json).getAsJsonObject();
    JsonArray mainprogArr = mainprog.getAsJsonArray("main");

    BrewerRuntime runtime = new BrewerRuntime();

    List<Expression<?>> programCommands = new ArrayList<Expression<?>>();
    for (JsonElement e : mainprogArr) {
      JsonObject expressionObj = e.getAsJsonObject();
      programCommands.add(parseJSONExpression(expressionObj, runtime));
    }
    runtime.setProgram(programCommands);
    return runtime;
  }

  public static Expression<?> parseJSONExpression(JsonObject obj,
      BrewerRuntime runtime) {
    String exprType = obj.getAsJsonPrimitive("type").getAsString();
    Expression<?> expr;

    switch (exprType) {
      case "set": {
        String varname = obj.getAsJsonPrimitive("name").getAsString();
        Expression<?> value =
            parseJSONExpression(obj.getAsJsonObject("value"), runtime);
        Class<?> valuetype = value.getType();
        if (Double.class.isAssignableFrom(valuetype)) {
          expr =
              new SetCommand<Double>(runtime, varname,
                  (Expression<Double>) value, Double.class);
        } else if (String.class.isAssignableFrom(valuetype)) {
          expr =
              new SetCommand<String>(runtime, varname,
                  (Expression<String>) value, String.class);
        } else if (Boolean.class.isAssignableFrom(valuetype)) {
          expr =
              new SetCommand<Boolean>(runtime, varname,
                  (Expression<Boolean>) value, Boolean.class);
        } else {
          // TODO
        }
        break;
      }
      case "get": {
        String varname = obj.getAsJsonPrimitive("name").getAsString();
        String vartype = obj.getAsJsonPrimitive("class").getAsString();

        switch (vartype) {
          case "string":
            expr = new GetCommand<String>(runtime, varname, String.class);
            break;
          case "number":
            expr = new GetCommand<Double>(runtime, varname, Double.class);
            break;
          case "boolean":
            expr = new GetCommand<Boolean>(runtime, varname, Boolean.class);
            break;
        }
        break;
      }
    }

    return null;
  }
}
