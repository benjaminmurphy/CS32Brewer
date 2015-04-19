package edu.brown.cs.brewer.handlers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.expression.*;

public class Parser {

  public static BrewerRuntime parseJSONProgram(String json, Stream.Runner runner)
      throws BrewerParseException {
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
    runtime.setRunner(runner);
    return runtime;
  }

  @SuppressWarnings("unchecked")
  private static Expression<?> parseJSONExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    String exprType = obj.getAsJsonPrimitive("type").getAsString();
    Expression<?> expr = null;

    switch (exprType) {
      case "set": {
        expr = parseSetExpression(obj, runtime);
      }
      case "get": {
        expr = parseGetExpression(obj, runtime);
      }
      case "print": {
        String varname = obj.getAsJsonPrimitive("name").getAsString();
        expr = new PrintExpression(runtime, varname);
        break;
      }
      case "literal": {
        String vartype = obj.getAsJsonPrimitive("class").getAsString();
        JsonPrimitive valuePrim = obj.getAsJsonPrimitive("value");

        switch (vartype) {
          case "string":
            expr = new Literal<String>(runtime, valuePrim.getAsString());
            break;
          case "number":
            expr = new Literal<Double>(runtime, valuePrim.getAsDouble());
            break;
          case "boolean":
            expr = new Literal<Boolean>(runtime, valuePrim.getAsBoolean());
            break;
          default:
            new TypeErrorException("Literals of the type \"" + vartype + "\"");
        }
        break;
      }
      case "comparison": {
        String opname = obj.getAsJsonPrimitive("name").getAsString();
        Expression<?> arg1 =
            parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
        Expression<?> arg2 =
            parseJSONExpression(obj.getAsJsonObject("arg2"), runtime);
        switch (opname) {
          case "eq":
            expr = new EqualityOperator(runtime, arg1, arg2);
            break;
          case "less": {
            Class<?> type1 = arg1.getType();
            Class<?> type2 = arg2.getType();
            if (!type1.isAssignableFrom(type2)
                || !type2.isAssignableFrom(type1)) {
              throw new TypeErrorException("The types \"(" + type1 + ", "
                  + type2 + "\" cannot be compared.");
            }
            if (Double.class.isAssignableFrom(type1)) {
              expr =
                  new LessThanOperator<Double>(runtime,
                      (Expression<Double>) arg1, (Expression<Double>) arg2);
            } else if (String.class.isAssignableFrom(type1)) {
              expr =
                  new LessThanOperator<String>(runtime,
                      (Expression<String>) arg1, (Expression<String>) arg2);
            } else {
              throw new TypeErrorException("Comparisons of the type \"("
                  + type1 + ", " + type2 + "\" are not supported.");
            }
            break;
          }
          case "greater": {
            Class<?> type1 = arg1.getType();
            Class<?> type2 = arg2.getType();
            if (!type1.isAssignableFrom(type2)
                || !type2.isAssignableFrom(type1)) {
              throw new TypeErrorException("The types \"(" + type1 + ", "
                  + type2 + "\" cannot be compared.");
            }
            if (Double.class.isAssignableFrom(type1)) {
              expr =
                  new GreaterThanOperator<Double>(runtime,
                      (Expression<Double>) arg1, (Expression<Double>) arg2);
            } else if (String.class.isAssignableFrom(type1)) {
              expr =
                  new GreaterThanOperator<String>(runtime,
                      (Expression<String>) arg1, (Expression<String>) arg2);
            } else {
              throw new TypeErrorException("Comparisons of the type \"("
                  + type1 + ", " + type2 + "\" are not supported.");
            }
            break;
          }
          default:
            throw new SyntaxErrorException("Unrecognized comparison of type \""
                + opname + "\"");
        }
        break;
      }
      case "logic_operator": {
        String opname = obj.getAsJsonPrimitive("name").getAsString();
        Expression<?> arg1 =
            parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
        Class<?> arg1type = arg1.getType();
        Expression<?> arg2 =
            parseJSONExpression(obj.getAsJsonObject("arg2"), runtime);
        Class<?> arg2type = arg2.getType();
        if (!Boolean.class.isAssignableFrom(arg1type)
            || !Boolean.class.isAssignableFrom(arg2type)) {
          throw new TypeErrorException("Either argument 1 of type \""
              + arg1type + "\" or argument 2 of type \"" + arg2type
              + "\" of logic_operator \"" + opname
              + "\"is not of type Boolean.");
        }

        switch (opname) {
          case "and":
            expr =
                new AndOperator(runtime, (Expression<Boolean>) arg1,
                    (Expression<Boolean>) arg2);
            break;
          case "or":
            expr =
                new OrOperator(runtime, (Expression<Boolean>) arg1,
                    (Expression<Boolean>) arg2);
            break;
          default:
            throw new SyntaxErrorException("Unrecognized logic_operator");
        }
      }
      case "numeric_operator": {
        String opname = obj.getAsJsonPrimitive("name").getAsString();
        Expression<?> arg1 =
            parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
        Class<?> arg1type = arg1.getType();
        Expression<?> arg2 =
            parseJSONExpression(obj.getAsJsonObject("arg2"), runtime);
        Class<?> arg2type = arg2.getType();
        if (!Double.class.isAssignableFrom(arg1type)
            || !Double.class.isAssignableFrom(arg2type)) {
          throw new TypeErrorException("Either argument 1 of type \""
              + arg1type + "\" or argument 2 of type \"" + arg2type
              + "\" of numeric_operator of type \"" + opname
              + "\" is not of type Double.");
        }

        switch (opname) {
          case "add":
            expr =
                new AdditionOperator(runtime, (Expression<Double>) arg1,
                    (Expression<Double>) arg2);
            break;
          case "sub":
            expr =
                new SubtractionOperator(runtime, (Expression<Double>) arg1,
                    (Expression<Double>) arg2);
            break;
          case "mul":
            expr =
                new MultiplicationOperator(runtime, (Expression<Double>) arg1,
                    (Expression<Double>) arg2);
            break;
          case "div":
            expr =
                new DivisionOperator(runtime, (Expression<Double>) arg1,
                    (Expression<Double>) arg2);
            break;
          default:
            throw new SyntaxErrorException("Unrecognized numeric_operator \""
                + opname + "\".");
        }
        break;
      }
      case "unary_operator": {
        String opname = obj.getAsJsonPrimitive("name").getAsString();
        Expression<?> arg1 =
            parseJSONExpression(obj.getAsJsonObject("condition"), runtime);
        Class<?> argtype = arg1.getType();
        if (!Boolean.class.isAssignableFrom(argtype)) {
          throw new TypeErrorException("Condition for " + opname
              + " operator is not Boolean, its type is " + argtype);
        }
        switch (opname) {
          case "not":
            expr = new NotOperator(runtime, (Expression<Boolean>) arg1);
            break;
          default:
            throw new SyntaxErrorException("Unrecognized unary_operator \""
                + opname + "\".");
        }
        break;
      }
      case "while": {
        Expression<?> cond =
            parseJSONExpression(obj.getAsJsonObject("condition"), runtime);
        Class<?> condtype = cond.getType();
        if (!Boolean.class.isAssignableFrom(condtype)) {
          throw new TypeErrorException(
              "Condition for while statement is not Boolean, its type is "
                  + condtype);
        }
        List<Expression<?>> commands = new ArrayList<Expression<?>>();
        for (JsonElement e : obj.getAsJsonArray("commands")) {
          commands.add(parseJSONExpression(e.getAsJsonObject(), runtime));
        }
        expr = new WhileCommand(runtime, (Expression<Boolean>) cond, commands);
        break;
      }
      case "if": {
        Expression<?> cond =
            parseJSONExpression(obj.getAsJsonObject("condition"), runtime);
        Class<?> condtype = cond.getType();
        if (!Boolean.class.isAssignableFrom(condtype)) {
          throw new TypeErrorException(
              "Condition for if statement is not Boolean, its type is "
                  + condtype);
        }
        List<Expression<?>> commands = new ArrayList<Expression<?>>();
        for (JsonElement e : obj.getAsJsonArray("commands")) {
          commands.add(parseJSONExpression(e.getAsJsonObject(), runtime));
        }
        expr =
            new IfElseCommand(runtime, (Expression<Boolean>) cond, commands,
                null);
        break;
      }
      case "ifelse": {
        Expression<?> cond =
            parseJSONExpression(obj.getAsJsonObject("condition"), runtime);
        Class<?> condtype = cond.getType();
        if (!Boolean.class.isAssignableFrom(condtype)) {
          throw new TypeErrorException(
              "Condition for ifelse statement is not Boolean, its type is "
                  + condtype);
        }
        List<Expression<?>> commands = new ArrayList<Expression<?>>();
        List<Expression<?>> commandsElse = new ArrayList<Expression<?>>();
        for (JsonElement e : obj.getAsJsonArray("commands")) {
          commands.add(parseJSONExpression(e.getAsJsonObject(), runtime));
        }
        for (JsonElement e : obj.getAsJsonArray("else")) {
          commandsElse.add(parseJSONExpression(e.getAsJsonObject(), runtime));
        }
        expr =
            new IfElseCommand(runtime, (Expression<Boolean>) cond, commands,
                commandsElse);
        break;
      }
      default:
        throw new SyntaxErrorException("Expression type \"" + exprType
            + "\" is not recognized.");
    }

    return expr;
  }

  @SuppressWarnings("unchecked")
  private static SetCommand<?> parseSetExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    JsonObject variableObj = obj.getAsJsonObject("name");
    String varname = variableObj.getAsJsonPrimitive("name").getAsString();
    Expression<?> value =
        parseJSONExpression(obj.getAsJsonObject("value"), runtime);
    Class<?> valuetype = value.getType();
    if (Double.class.isAssignableFrom(valuetype)) {
      return new SetCommand<Double>(runtime, varname,
          (Expression<Double>) value, Double.class);
    } else if (String.class.isAssignableFrom(valuetype)) {
      return new SetCommand<String>(runtime, varname,
          (Expression<String>) value, String.class);
    } else if (Boolean.class.isAssignableFrom(valuetype)) {
      return new SetCommand<Boolean>(runtime, varname,
          (Expression<Boolean>) value, Boolean.class);
    } else {
      throw new TypeErrorException("Variables of this type are not supported.");
    }
  }

  private static GetCommand<?> parseGetExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    JsonObject variableObj = obj.getAsJsonObject("name");
    String varname = variableObj.getAsJsonPrimitive("name").getAsString();
    String vartype = variableObj.getAsJsonPrimitive("class").getAsString();

    switch (vartype) {
      case "string":
        return new GetCommand<String>(runtime, varname, String.class);
      case "number":
        return new GetCommand<Double>(runtime, varname, Double.class);
      case "boolean":
        return new GetCommand<Boolean>(runtime, varname, Boolean.class);
      default:
        throw new TypeErrorException("Variables of the type \"" + vartype
            + "\" are not supported.");
    }
  }

  public static class BrewerParseException extends Exception {
    public BrewerParseException(String msg) {
      // TODO add fields for command num, to better identify location of bug
      super("Parser error: " + msg);
    }
  }

  public static class SyntaxErrorException extends BrewerParseException {
    public SyntaxErrorException(String msg) {
      // TODO add fields for command num, missing token, etc...
      super("Syntax error: " + msg);
    }
  }

  public static class TypeErrorException extends BrewerParseException {
    public TypeErrorException(String msg) {
      // TODO add fields for error type, etc.
      super("Type error: " + msg);
    }
  }
}
