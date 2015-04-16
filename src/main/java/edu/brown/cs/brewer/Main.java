package edu.brown.cs.brewer;

import java.io.IOException;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.brewer.handlers.Server;

public class Main {

  private static final int DEFAULT_PORT = 4567;

  // Number of threads, change to null or 1 for single threading
  private static final int NUM_THREADS = 4;

  // public static void main(String[] args) {
  // Map<String, Variable<?>> vars = new HashMap<>();
  // List<Log> logs = new ArrayList<Log>();
  // BrewerRuntime r = new BrewerRuntime(vars, logs);
  //
  // List<Expression<?>> factorial = new ArrayList<Expression<?>>();
  // factorial.add(new SetCommand<Double>(r, "x", new Literal<Double>(r,
  // 1.0), Double.class));
  // factorial.add(new SetCommand<Double>(r, "i", new Literal<Double>(r,
  // 5.0), Double.class));
  //
  // Expression<Boolean> loopCond = new GreaterThanOperator<Double>(r,
  // new GetCommand<Double>(r, "i", Double.class), new Literal<Double>(r,
  // 1.0));
  // List<Expression<?>> whileLoop = new ArrayList<>();
  // Expression<Double> mult = new MultiplicationOperator(r,
  // new GetCommand<Double>(r, "i", Double.class),
  // new GetCommand<Double>(r, "x", Double.class));
  // whileLoop.add(new SetCommand<Double>(r, "x", mult, Double.class));
  // whileLoop.add(new SetCommand<Double>(r, "i", new SubtractionOperator(
  // r, new GetCommand<Double>(r, "i", Double.class),
  // new Literal<Double>(r, 1.0)), Double.class));
  // factorial.add(new WhileCommand(r, loopCond, whileLoop));
  // factorial.add(new PrintExpression(r, "x"));
  //
  // r.setProgram(factorial);
  //
  // System.out.println("Starting program...");
  // r.run();
  // System.out.println(r.getVariables().get("x").getValue());
  // }

  public static void main(String[] args) throws IOException {
    Server server = new Server(DEFAULT_PORT, NUM_THREADS);
    server.runSparkServer();
  }

}
