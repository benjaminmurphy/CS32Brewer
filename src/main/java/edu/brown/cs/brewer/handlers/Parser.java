package edu.brown.cs.brewer.handlers;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.expression.AdditionOperator;
import edu.brown.cs.brewer.expression.AndOperator;
import edu.brown.cs.brewer.expression.ComparisonOperator;
import edu.brown.cs.brewer.expression.DivisionOperator;
import edu.brown.cs.brewer.expression.Expression;
import edu.brown.cs.brewer.expression.GetCommand;
import edu.brown.cs.brewer.expression.IfElseCommand;
import edu.brown.cs.brewer.expression.Literal;
import edu.brown.cs.brewer.expression.MultiplicationOperator;
import edu.brown.cs.brewer.expression.NotOperator;
import edu.brown.cs.brewer.expression.OrOperator;
import edu.brown.cs.brewer.expression.PrintExpression;
import edu.brown.cs.brewer.expression.SetCommand;
import edu.brown.cs.brewer.expression.SubtractionOperator;
import edu.brown.cs.brewer.expression.WhileCommand;

/**
 * A class that parses JSON strings into Brewer programs (returned as
 * BrewerRuntime objects containing the given program)
 *
 * @author raphaelkargon
 *
 */
public class Parser {

  /**
   * Parses a program, represented as a JSON string.
   *
   * @param json The program
   * @return A BrewerRuntime object that contains the given program
   * @throws BrewerParseException If there is a syntax error with the JSON (in
   *         terms of the Brewer specification)
   * @throws ParseException If the string is not valid JSON
   */
  public static BrewerRuntime parseJSONProgram(final String json)
      throws BrewerParseException, ParseException {
    JSONParser parser = new JSONParser();
    JSONObject mainprog = (JSONObject) parser.parse(json);
    JSONArray mainprogArr = (JSONArray) mainprog.get("main");
    BrewerRuntime runtime = new BrewerRuntime();

    List<Expression> programCommands = new ArrayList<Expression>();
    for (Object e : mainprogArr) {
      JSONObject element = (JSONObject) e;
      programCommands.add(parseJSONExpression(element, runtime));
    }
    runtime.setProgram(programCommands);
    return runtime;
  }

  /**
   * Parses a single JSON Expression. Subexpressions are also parsed
   * recursively.
   *
   * @param obj The JSON object representing a certain Brewer block
   * @param runtime The runtime containing the program
   * @return The Expression represented by the JSON object
   * @throws BrewerParseException If the json object is not proper Brewer Code
   */
  private static Expression parseJSONExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    if (obj == null) {
      throw new MissingElementException("An element in the JSON is missing.");
    }
    String exprType = (String) obj.get("type");

    switch (exprType) {
      case "set":
        return parseSetExpression(obj, runtime);

      case "var":
        return parseVarExpression(obj, runtime);

      case "print":
        return parsePrintExpression(obj, runtime);

      case "literal":
        return parseLiteralExpression(obj, runtime);

      case "comparison":
        return parseComparisonExpression(obj, runtime);

      case "logic_operator":
        return parseLogicalExpression(obj, runtime);

      case "numeric_operator":
        return parseNumericalExpression(obj, runtime);

      case "unary_operator":
        return parseUnaryExpression(obj, runtime);

      case "while":
        return parseWhileExpression(obj, runtime);

      case "if":
        return parseIfElseExpression(obj, runtime);

      case "ifelse":
        return parseIfElseExpression(obj, runtime);

      default:
        throw new SyntaxErrorException("Expression type \"" + exprType
            + "\" is not recognized.");
    }
  }

  /**
   * Parses a "set variable" expression.
   *
   * @param obj The JSON object representing the expression
   * @param runtime The runtime containing the program
   * @return A SetCommand represented by the JSON object
   * @throws BrewerParseException If the JSONObject is not a proper set
   *         expression
   */
  private static SetCommand parseSetExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    JSONObject variableObj = (JSONObject) obj.get("name");
    if (variableObj == null) {
      throw new MissingElementException("Set expression is missing a variable.");
    }
    String varname = (String) variableObj.get("name");
    Class<?> vartype = parseTypeFromString((String) variableObj.get("class"));
    Expression value =
        parseJSONExpression((JSONObject) obj.get("value"), runtime);
    Class<?> valtype = value.getType();
    if (!vartype.isAssignableFrom(valtype)) {
      throw new TypeErrorException("Cannot assign expression of type \""
          + valtype + "\" to variable of type \"" + vartype + "\".");
    }
    return new SetCommand(runtime, varname, value, vartype);
  }

  /**
   * Parses a variable expression, returning a GetCommand that returns the given
   * variable's value.
   *
   * @param obj The JSON object representing the expression
   * @param runtime The runtime containing the program
   * @return A GetCommand represented by the JSON object
   * @throws BrewerParseException If the JSONObject is not a proper variable
   *         expression
   */
  private static GetCommand parseVarExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    String var = (String) obj.get("name");
    if (var == null) {
      throw new MissingElementException("Var expression is missing a name.");
    }
    String vartypename = (String) obj.get("class");
    Class<?> vartype = parseTypeFromString(vartypename);
    return new GetCommand(runtime, var, vartype);
  }

  /**
   * Parses a print expression.
   *
   * @param obj The JSON object representing the expression
   * @param runtime The runtime containing the program
   * @return A PrintExpression represented by the JSON object
   * @throws BrewerParseException If the JSONObject is not a proper print
   *         expression
   */
  private static PrintExpression parsePrintExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    // Changed the parsing to get inner name.
    JSONObject innerObj = (JSONObject) obj.get("name");
    return new PrintExpression(runtime, parseJSONExpression(innerObj, runtime));
  }

  /**
   * Parses a literal expression.
   *
   * @param obj The JSON string representing the expression
   * @param runtime The expression's containing runtime
   * @return A Literal value, as represented by the JSON
   * @throws BrewerParseException If the JSON is invalid brewer code
   */
  private static Literal parseLiteralExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    String vartypename = (String) obj.get("class");

    switch (vartypename) {
      case "string":
        String stringPrim = obj.get("value").toString();
        return new Literal(runtime, stringPrim, String.class);
      case "number":
        Double doublePrim = getDouble(obj, "value");
        return new Literal(runtime, doublePrim, Double.class);
      case "bool":
        Boolean boolPrim = Boolean.parseBoolean(obj.get("value").toString());
        return new Literal(runtime, boolPrim, Boolean.class);
      default:
        throw new TypeErrorException("Literals of the type \"" + vartypename
            + "\" are unsupported.");
    }
  }

  /**
   * Parses a comparison expression.
   *
   * @param obj The JSON string representing the expression
   * @param runtime The expression's containing runtime
   * @return An Expression of the type Equality-, LessThan-, or
   *         GreaterThanOperator type, as represented by the JSON
   * @throws BrewerParseException If the JSON is invalid brewer code
   */
  private static Expression parseComparisonExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    String opname = (String) obj.get("name");
    Expression arg1 =
        parseJSONExpression((JSONObject) obj.get("arg1"), runtime);
    Expression arg2 =
        parseJSONExpression((JSONObject) obj.get("arg2"), runtime);
    Class<?> type1 = arg1.getType();
    Class<?> type2 = arg2.getType();
    if (!type1.isAssignableFrom(type2) || !type2.isAssignableFrom(type1)) {
      throw new TypeErrorException("The types \"(" + type1 + ", " + type2
          + "\" cannot be compared.");
    }
    switch (opname) {
      case "eq":
        return new ComparisonOperator(runtime, arg1, arg2, 0);
      case "less":
        return new ComparisonOperator(runtime, arg1, arg2, -1);
      case "greater":
        return new ComparisonOperator(runtime, arg1, arg2, 1);
      default:
        throw new SyntaxErrorException("Unrecognized comparison of type \""
            + opname + "\"");
    }
  }

  /**
   * Parses a binary boolean expression.
   *
   * @param obj The JSON string representing the expression
   * @param runtime The expression's containing runtime
   * @return An And or Or Operator, as represented by the JSON
   * @throws BrewerParseException If the JSON is invalid brewer code
   */
  private static Expression parseLogicalExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    String opname = (String) obj.get("name");
    Expression arg1 =
        parseJSONExpression((JSONObject) obj.get("arg1"), runtime);
    Class<?> arg1type = arg1.getType();
    Expression arg2 =
        parseJSONExpression((JSONObject) obj.get("arg2"), runtime);
    Class<?> arg2type = arg2.getType();
    if (!Boolean.class.isAssignableFrom(arg1type)
        || !Boolean.class.isAssignableFrom(arg2type)) {
      throw new TypeErrorException("Either argument 1 of type \"" + arg1type
          + "\" or argument 2 of type \"" + arg2type
          + "\" of logicoperatorArg \"" + opname + "\"is not of type Boolean.");
    }
    switch (opname) {
      case "and":
        return new AndOperator(runtime, arg1, arg2);
      case "or":
        return new OrOperator(runtime, arg1, arg2);
      default:
        throw new SyntaxErrorException("Unrecognized logicoperatorArg");
    }
  }

  /**
   * Parses an arithmetic expression.
   *
   * @param obj The JSON string representing the expression
   * @param runtime The encapsulating runtime
   * @return An Expression representing an arithmetic operation
   * @throws BrewerParseException If the JSON is invalid brewer code
   */
  private static Expression parseNumericalExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    String opname = (String) obj.get("name");
    Expression arg1 =
        parseJSONExpression((JSONObject) obj.get("arg1"), runtime);
    Class<?> arg1type = arg1.getType();
    Expression arg2 =
        parseJSONExpression((JSONObject) obj.get("arg2"), runtime);
    Class<?> arg2type = arg2.getType();
    if (!Double.class.isAssignableFrom(arg1type)
        || !Double.class.isAssignableFrom(arg2type)) {
      throw new TypeErrorException("Either argument 1 of type \"" + arg1type
          + "\" or argument 2 of type \"" + arg2type
          + "\" of numericoperatorArg of type \"" + opname
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
        throw new SyntaxErrorException("Unrecognized numericoperatorArg \""
            + opname + "\".");
    }
  }

  /**
   * Parses a unary expression.
   *
   * @param obj The JSON string representing the expression
   * @param runtime The encapsulating runtime
   * @return A unary expression (currently just "not")
   * @throws BrewerParseException if the JSON is invalid brewer code
   */
  private static Expression parseUnaryExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    String opname = (String) obj.get("name");
    Expression arg1 =
        parseJSONExpression((JSONObject) obj.get("arg1"), runtime);
    Class<?> argtype = arg1.getType();
    if (!Boolean.class.isAssignableFrom(argtype)) {
      throw new TypeErrorException("Argument for " + opname
          + " operator is not Boolean, its type is " + argtype);
    }
    switch (opname) {
      case "not":
        return new NotOperator(runtime, arg1);
      default:
        throw new SyntaxErrorException("Unrecognized unaryoperatorArg \""
            + opname + "\".");
    }
  }

  /**
   * Parses a While loop expression.
   *
   * @param obj The JSON string representing the expression
   * @param runtime The encapsulating runtime
   * @return A WhileCommand corresponding to the JSON
   * @throws BrewerParseException if the JSON is invalid brewer code
   */
  private static WhileCommand parseWhileExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    Expression cond =
        parseJSONExpression((JSONObject) obj.get("condition"), runtime);
    Class<?> condtype = cond.getType();
    if (!Boolean.class.isAssignableFrom(condtype)) {
      throw new TypeErrorException(
          "Condition for while statement is not Boolean, its type is "
              + condtype);
    }
    List<Expression> commands = new ArrayList<Expression>();
    for (Object e : (JSONArray) obj.get("commands")) {
      commands.add(parseJSONExpression((JSONObject) e, runtime));
    }
    return new WhileCommand(runtime, cond, commands);
  }

  /**
   * Parses an IfElse expression. (including just "if" expressions)
   *
   * @param obj The JSON string representing the expression
   * @param runtime The encapsulating runtime
   * @return An IfElseCommand expression
   * @throws BrewerParseException if the JSON is invalid brewer code
   */
  private static IfElseCommand parseIfElseExpression(final JSONObject obj,
      final BrewerRuntime runtime) throws BrewerParseException {
    Expression cond =
        parseJSONExpression((JSONObject) obj.get("condition"), runtime);
    Class<?> condtype = cond.getType();
    if (!Boolean.class.isAssignableFrom(condtype)) {
      throw new TypeErrorException(
          "Condition for ifelse statement is not Boolean, its type is "
              + condtype);
    }
    List<Expression> commands = new ArrayList<Expression>();
    List<Expression> commandsElse = new ArrayList<Expression>();
    for (Object e : (JSONArray) obj.get("commands")) {
      commands.add(parseJSONExpression((JSONObject) e, runtime));
    }
    if (obj.containsKey("else")) {
      for (Object e : (JSONArray) obj.get("else")) {
        commandsElse.add(parseJSONExpression((JSONObject) e, runtime));
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
      case "bool":
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

  /**
   * Represents an error in parsing JSON code. This is thrown if the JSON is
   * syntactically valid, but does not represent a valid Brewer program.
   *
   * @author raphaelkargon
   *
   */
  public static class BrewerParseException extends Exception {
    /**
     * Generated UID.
     */
    private static final long serialVersionUID = 5358774121052623116L;

    public BrewerParseException(String msg) {
      super("Parser error: " + msg);
    }
  }

  /**
   * A Brewer Parsing error that involves a syntax error, ie missing or misnamed
   * fields.
   *
   * @author raphaelkargon
   *
   */
  public static class SyntaxErrorException extends BrewerParseException {
    /**
     * Generated UID.
     */
    private static final long serialVersionUID = 6357956308056427694L;

    public SyntaxErrorException(String msg) {
      super("Syntax error: " + msg);
    }
  }

  /**
   * A Brewer parsing error that involves mismatched types, i.e trying to assign
   * a number to a string variable.
   *
   * @author raphaelkargon
   *
   */
  public static class TypeErrorException extends BrewerParseException {
    /**
     * Generated UID.
     */
    private static final long serialVersionUID = -4379552698914138573L;

    public TypeErrorException(String msg) {
      super("Type error: " + msg);
    }
  }

  /**
   * A Brewer parsing error that involves missing subelements in an expression.
   * An example would be a missing conditional in a While block.
   *
   * @author raphaelkargon
   *
   */
  public static class MissingElementException extends BrewerParseException {
    /**
     * Generated UID.
     */
    private static final long serialVersionUID = 2736828088809684077L;

    public MissingElementException(String msg) {
      super("Missing element: " + msg);
    }
  }

  /**
   * Returns a double from a JSONObject
   *
   * @param item The JSON object
   * @param key The key that stores the value
   * @return A double, or 0 if the JSON object does not properly represent a
   *         real number.
   */
  private static Double getDouble(JSONObject item, String key) {
    if (item.get(key).getClass() == Double.class) {
      return ((Double) item.get(key));
    } else if (item.get(key).getClass() == Long.class) {
      return ((Long) item.get(key)).doubleValue();
    }
    return new Double(0);
  }
}
