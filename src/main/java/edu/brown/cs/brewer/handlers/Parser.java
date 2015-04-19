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

    List<Expression> programCommands = new ArrayList<Expression>();
    for (JsonElement e : mainprogArr) {
      JsonObject expressionObj = e.getAsJsonObject();
      programCommands.add(parseJSONExpression(expressionObj, runtime));
    }
    runtime.setProgram(programCommands);
    runtime.setRunner(runner);
    return runtime;
  }

  private static Expression parseJSONExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    String exprType = obj.getAsJsonPrimitive("type").getAsString();

    switch (exprType) {
      case "set": {
        return parseSetExpression(obj, runtime);
      }
      case "get": {
        return parseGetExpression(obj, runtime);
      }
      case "print": {
        return parsePrintExpression(obj, runtime);
      }
      case "literal": {
        return parseLiteralExpression(obj, runtime);
      }
      case "comparison": {
        return parseComparisonExpression(obj, runtime);
      }
      case "logic_operator": {
        return parseLogicalExpression(obj, runtime);
      }
      case "numeric_operator": {
        return parseNumericalExpression(obj, runtime);
      }
      case "unary_operator": {
        return parseUnaryExpression(obj, runtime);
      }
      case "while": {
        return parseWhileExpression(obj, runtime);
      }
      case "if": {
        return parseIfElseExpression(obj, runtime);
      }
      case "ifelse": {
        return parseIfElseExpression(obj, runtime);
      }
      default:
        throw new SyntaxErrorException("Expression type \"" + exprType
            + "\" is not recognized.");
    }
  }

  private static SetCommand parseSetExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    JsonObject variableObj = obj.getAsJsonObject("name");
    String varname = variableObj.getAsJsonPrimitive("name").getAsString();
    Class<?> vartype =
        parseTypeFromString(variableObj.getAsJsonPrimitive("class")
            .getAsString());
    Expression value =
        parseJSONExpression(obj.getAsJsonObject("value"), runtime);
    Class<?> valtype = value.getType();
    if (!vartype.isAssignableFrom(valtype)) {
      throw new TypeErrorException("Cannot assign expression of type \""
          + valtype + "\" to variable of type \"" + vartype + "\".");
    }
    return new SetCommand(runtime, varname, value, vartype);
  }

  private static GetCommand parseGetExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    JsonObject variableObj = obj.getAsJsonObject("name");
    String varname = variableObj.getAsJsonPrimitive("name").getAsString();
    String vartypename = variableObj.getAsJsonPrimitive("class").getAsString();
    Class<?> vartype = parseTypeFromString(vartypename);
    return new GetCommand(runtime, varname, vartype);
  }

  private static PrintExpression parsePrintExpression(JsonObject obj,
      BrewerRuntime runtime) {
    String varname = obj.getAsJsonPrimitive("name").getAsString();
    return new PrintExpression(runtime, varname);
  }

  private static Literal parseLiteralExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    String vartypename = obj.getAsJsonPrimitive("class").getAsString();
    JsonPrimitive valuePrim = obj.getAsJsonPrimitive("value");

    switch (vartypename) {
      case "string":
        return new Literal(runtime, valuePrim.getAsString(), String.class);
      case "number":
        return new Literal(runtime, valuePrim.getAsDouble(), Double.class);
      case "boolean":
        return new Literal(runtime, valuePrim.getAsBoolean(), Boolean.class);
      default:
        throw new TypeErrorException("Literals of the type \"" + vartypename
            + "\" are unsupported.");
    }
  }

  private static Expression parseComparisonExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    String opname = obj.getAsJsonPrimitive("name").getAsString();
    Expression arg1 = parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
    Expression arg2 = parseJSONExpression(obj.getAsJsonObject("arg2"), runtime);
    Class<?> type1 = arg1.getType();
    Class<?> type2 = arg2.getType();
    if (!type1.isAssignableFrom(type2) || !type2.isAssignableFrom(type1)) {
      throw new TypeErrorException("The types \"(" + type1 + ", " + type2
          + "\" cannot be compared.");
    }
    switch (opname) {
      case "eq":
        return new EqualityOperator(runtime, arg1, arg2);
      case "less":
        return new LessThanOperator(runtime, arg1, arg2);
      case "greater":
        return new GreaterThanOperator(runtime, arg1, arg2);
      default:
        throw new SyntaxErrorException("Unrecognized comparison of type \""
            + opname + "\"");
    }
  }

  private static Expression parseLogicalExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    String opname = obj.getAsJsonPrimitive("name").getAsString();
    Expression arg1 = parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
    Class<?> arg1type = arg1.getType();
    Expression arg2 = parseJSONExpression(obj.getAsJsonObject("arg2"), runtime);
    Class<?> arg2type = arg2.getType();
    if (!Boolean.class.isAssignableFrom(arg1type)
        || !Boolean.class.isAssignableFrom(arg2type)) {
      throw new TypeErrorException("Either argument 1 of type \"" + arg1type
          + "\" or argument 2 of type \"" + arg2type
          + "\" of logic_operator \"" + opname + "\"is not of type Boolean.");
    }
    switch (opname) {
      case "and":
        return new AndOperator(runtime, arg1, arg2);
      case "or":
        return new OrOperator(runtime, arg1, arg2);
      default:
        throw new SyntaxErrorException("Unrecognized logic_operator");
    }
  }

  private static Expression parseNumericalExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    String opname = obj.getAsJsonPrimitive("name").getAsString();
    Expression arg1 = parseJSONExpression(obj.getAsJsonObject("arg1"), runtime);
    Class<?> arg1type = arg1.getType();
    Expression arg2 = parseJSONExpression(obj.getAsJsonObject("arg2"), runtime);
    Class<?> arg2type = arg2.getType();
    if (!Double.class.isAssignableFrom(arg1type)
        || !Double.class.isAssignableFrom(arg2type)) {
      throw new TypeErrorException("Either argument 1 of type \"" + arg1type
          + "\" or argument 2 of type \"" + arg2type
          + "\" of numeric_operator of type \"" + opname
          + "\" is not of type Double.");
    }

    switch (opname) {
      case "add":
        return new AdditionOperator(runtime, arg1, arg2);
      case "sub":
        return new SubtractionOperator(runtime, arg1, arg2);
      case "mul":
        return new MultiplicationOperator(runtime, arg1, arg2);
      case "div":
        return new DivisionOperator(runtime, arg1, arg2);
      default:
        throw new SyntaxErrorException("Unrecognized numeric_operator \""
            + opname + "\".");
    }
  }

  private static Expression parseUnaryExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    String opname = obj.getAsJsonPrimitive("name").getAsString();
    Expression arg1 =
        parseJSONExpression(obj.getAsJsonObject("condition"), runtime);
    Class<?> argtype = arg1.getType();
    if (!Boolean.class.isAssignableFrom(argtype)) {
      throw new TypeErrorException("Argument for " + opname
          + " operator is not Boolean, its type is " + argtype);
    }
    switch (opname) {
      case "not":
        return new NotOperator(runtime, arg1);
      default:
        throw new SyntaxErrorException("Unrecognized unary_operator \""
            + opname + "\".");
    }
  }

  private static WhileCommand parseWhileExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    Expression cond =
        parseJSONExpression(obj.getAsJsonObject("condition"), runtime);
    Class<?> condtype = cond.getType();
    if (!Boolean.class.isAssignableFrom(condtype)) {
      throw new TypeErrorException(
          "Condition for while statement is not Boolean, its type is "
              + condtype);
    }
    List<Expression> commands = new ArrayList<Expression>();
    for (JsonElement e : obj.getAsJsonArray("commands")) {
      commands.add(parseJSONExpression(e.getAsJsonObject(), runtime));
    }
    return new WhileCommand(runtime, cond, commands);
  }

  private static IfElseCommand parseIfElseExpression(JsonObject obj,
      BrewerRuntime runtime) throws BrewerParseException {
    Expression cond =
        parseJSONExpression(obj.getAsJsonObject("condition"), runtime);
    Class<?> condtype = cond.getType();
    if (!Boolean.class.isAssignableFrom(condtype)) {
      throw new TypeErrorException(
          "Condition for ifelse statement is not Boolean, its type is "
              + condtype);
    }
    List<Expression> commands = new ArrayList<Expression>();
    List<Expression> commandsElse = new ArrayList<Expression>();
    for (JsonElement e : obj.getAsJsonArray("commands")) {
      commands.add(parseJSONExpression(e.getAsJsonObject(), runtime));
    }
    if (obj.has("else")) {
      for (JsonElement e : obj.getAsJsonArray("else")) {
        commandsElse.add(parseJSONExpression(e.getAsJsonObject(), runtime));
      }
    }
    return new IfElseCommand(runtime, cond, commands, commandsElse);
  }

  /**
   * Given a string type name, returns the corresponding type. This function is
   * tied to the Brewer specification, so types not mentioned by the specs will
   * throw an error, and "number" returns the Double type.
   *
   * @param typename The string name of the type
   * @return The Class object representing the corresponding type
   * @throws TypeErrorException When the string does not represent a type
   *         supported by Brewer
   */
  private static Class<?> parseTypeFromString(String typename)
      throws TypeErrorException {
    switch (typename) {
      case "string":
        return String.class;
      case "number":
        return Double.class;
      case "boolean":
        return Boolean.class;
      default:
        try {
          return Class.forName(typename);
        } catch (ClassNotFoundException e) {
          throw new TypeErrorException("The type \"" + typename
              + "\" is unsupported.");
        }
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
