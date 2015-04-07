package edu.brown.cs.brewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import com.google.gson.Gson;

import edu.brown.cs.brewer.expression.Expression;
import edu.brown.cs.brewer.expression.GetCommand;
import edu.brown.cs.brewer.expression.IfElseCommand;
import edu.brown.cs.brewer.expression.Literal;
import edu.brown.cs.brewer.expression.SetCommand;
import edu.brown.cs.brewer.expression.WhileCommand;

public class Main {

  private static final Gson GSON = new Gson();

  // TODO current placeholder for variables
  private static Map<String, Object> variables = new HashMap<String, Object>();

  // A Print interface represents the types "log" and "error"
  private static List<Log> toPrint;

  public static void main(String[] args) throws IOException {
    runSparkServer();
  }

  // Server Handling
  private static void runSparkServer() throws IOException {
    Spark.get("/evaluate", new ProgramHandler());
  }

  private static class ProgramHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {

      // TODO update the params name
      String requests = req.params("exps");

      Command[] exps = GSON.fromJson(requests, Command[].class);

      toPrint = new ArrayList<Log>();

      List<Expression<?>> comms = parse(exps);

      for (Expression<?> comm : comms) {
        comm.evaluate();
      }

      return GSON.toJson(toPrint);
    }

    /**
     * Parses an array of command objects
     *
     * @param exps
     * @return
     */
    private List<Expression<?>> parse(Command[] exps) {
      List<Expression<?>> commands = new ArrayList<Expression<?>>(
        exps.length);

      for (Command exp : exps) {
        commands.add(evaluateR(exp));
      }

      return commands;
    }

  }

  /**
   * Recursively parses an expression into a command / commands and returns the
   * commands
   *
   * Further inner parsing is already done
   *
   * Approach 2
   *
   * @param exp
   *          the expression
   */
  public static Expression<?> evaluateR(Command exp) {
    // TODO This should be transported to an evaluation class so that it can be
    // used in inner command parsing as well
    String type = exp.type;
    Expression<?> comm = null;

    // TODO First evaluate for literal value, null is currently a placeholder
    // for type checking
    if (exp.type == null) {
      return new Literal<>(exp);
    }

    switch (type) {

    // TODO Research if there's a more dynamic way to access the type and
    // automatically run the constructor for that type assuming the fields for
    // the given type exist.
    // If so, we could just add this as a default method to the Command
    // interface

    case "set":
      comm = new SetCommand(variables, exp.name, exp.value);
    case "get":
      comm = new GetCommand(variables, exp.name);
    case "while":
      comm = new WhileCommand(variables, evaluateR(exp.condition),
        parse(exp.commands));
    case "if":
      comm = new IfElseCommand(variables, evaluateR(exp.condition),
        parse(exp.commands));
      // case "numeric_operator":
      // comm = new NumOp(variables, exp.name, evaluateR(exp.arg1),
      // evaluateR(exp.arg2));
      // case "binary_operator":
      // comm = new BinOp(variables, exp.name, evaluateR(exp.arg1),
      // evaluateR(exp.arg2));

    default:
      break;
    // TODO More commands to add
    }

    // TODO Either output the individual "log" to be added to a print
    String output;
    // TODO or update a global log in the main

    if (comm != null) {
      comm.evaluate();
      // output = comm.log();
    } else {
      System.err.println("ERROR: Invalid command type");
    }

  }

  /**
   * Parses a single given expression into a command and executes it, further
   * inner parsing is done inside the command classes themselves
   *
   * Approach1
   *
   * @param exp
   *          the expression
   */
  public static void evaluate(Command exp) {
    // TODO This should be transported to an evaluation class so that it can be
    // used in inner command parsing as well
    String type = exp.type;
    Expression<?> comm = null;
    switch (type) {

    // TODO Research if there's a more dynamic way to access the type and
    // automatically run the constructor for that type assuming the fields for
    // the given type exist.
    // If so, we could just add this as a default method to the Command
    // interface

    case "set":
      comm = new SetCommand(variables, exp.name, exp.value);
    case "get":
      comm = new GetCommand(variables, exp.name);
      // case "while":
      // comm = new WhileCommand(variables, exp.condition, exp.commands);
      // case "if":
      // comm = new IfElseCommand(variables, exp.condition, exp.commands);
    default:
      break;
    // TODO More commands to add
    }

    // TODO Either output the individual "log" to be added to a print
    String output;
    // TODO or update a global log in the main

    if (comm != null) {
      comm.evaluate();
      // output = comm.log();
    } else {
      System.err.println("ERROR: Invalid command type");
    }

  }
}
