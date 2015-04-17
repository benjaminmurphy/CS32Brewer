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

  public static BrewerRuntime parseJSONProgram(String json, Stream.Runner runner) {
    Gson GSON = new Gson();// TODO make this static
    JsonParser parser = new JsonParser();
    
    System.out.println(json);
    
    JsonObject mainprog = parser.parse(json).getAsJsonObject();
    
    System.out.println(mainprog);
    
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

  public static Expression<?> parseJSONExpression(JsonObject obj,
      BrewerRuntime runtime) {
    String exprType = obj.getAsJsonPrimitive("type").getAsString();
    Expression<?> expr = null;

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
        }
        break;
      }
      case "comparison": {
        String opname = obj.getAsJsonArray("name").getAsString();
        Expression<?> arg1 =
            parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
        Expression<?> arg2 =
            parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
        switch (opname) {
          case "eq":
            expr = new EqualityOperator(runtime, arg1, arg2);
            break;
          case "less": {
            Class<?> type1 = arg1.getType();
            Class<?> type2 = arg2.getType();
            if (!type1.isAssignableFrom(type2)
                || !type2.isAssignableFrom(type1)) {
              ;// TODO
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
              // TODO
            }
            break;
          }
          case "greater": {
            Class<?> type1 = arg1.getType();
            Class<?> type2 = arg2.getType();
            if (!type1.isAssignableFrom(type2)
                || !type2.isAssignableFrom(type1)) {
              ;// TODO
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
              // TODO
            }
            break;
          }
          default:
            // TODO
        }
        break;
      }
      case "logic_operator": {
        String opname = obj.getAsJsonArray("name").getAsString();
        Expression<?> arg1 =
            parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
        Expression<?> arg2 =
            parseJSONExpression(obj.getAsJsonObject("arg2"), runtime);
        if (!Boolean.class.isAssignableFrom(arg1.getType())
            || !Boolean.class.isAssignableFrom(arg2.getType())) {
          ;// TODO
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
            // TODO
        }
      }
      case "numeric_operator": {
        String opname = obj.getAsJsonArray("name").getAsString();
        Expression<?> arg1 =
            parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
        Expression<?> arg2 =
            parseJSONExpression(obj.getAsJsonObject("arg2"), runtime);
        if (!Double.class.isAssignableFrom(arg1.getType())
            || !Double.class.isAssignableFrom(arg2.getType())) {
          ;// TODO
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
            ;// TODO
        }
        break;
      }
      case "unary_operator": {
        String opname = obj.getAsJsonArray("name").getAsString();
        Expression<?> arg1 =
            parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
        if (!Boolean.class.isAssignableFrom(arg1.getType())) {
          // TODO
        }
        switch (opname) {
          case "not":
            expr = new NotOperator(runtime, (Expression<Boolean>) arg1);
            break;
          default:
            ;// TODO
        }
        break;
      }
      case "while": {
        Expression<?> cond =
            parseJSONExpression(obj.getAsJsonObject("condition"), runtime);
        if (!Boolean.class.isAssignableFrom(cond.getType())) {
          ;// TODO
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
        if (!Boolean.class.isAssignableFrom(cond.getType())) {
          ;// TODO
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
        if (!Boolean.class.isAssignableFrom(cond.getType())) {
          ;// TODO
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
        ;// TODO
    }

    return expr;
  }
}
