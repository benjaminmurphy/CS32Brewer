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

public class Main {

  private static final int DEFAULT_PORT = 4567;

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
    runSparkServer();
  }

  private static void runSparkServer() {
    // TODO Auto-generated method stub
    Spark.setPort(DEFAULT_PORT);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.get("/brewer", new GetHandler(), new FreeMarkerEngine());
  }

  /**
   * This class serves the GUI to the client via a webserver.
   *
   * @author raphaelkargon
   *
   */
  private static class GetHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(final Request req, final Response res) {
      Map<String, Object> variables = ImmutableMap.of();
      return new ModelAndView(variables, "index.ftl");
    }
  }

}