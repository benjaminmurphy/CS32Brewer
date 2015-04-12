package edu.brown.cs.brewer.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.Command;
import edu.brown.cs.brewer.expression.Expression;

public class Server {

  private static final Gson GSON = new Gson();
  // A Print interface represents the types "log" and "error"
  // TODO instantiate runtime
  private static BrewerRuntime runtime;

  private static int portNum;

  public Server(int portNum) {
    this.portNum = portNum;
  }

  // Server Handling
  public void runSparkServer() throws IOException {
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.setPort(portNum);
    Spark.get("/brewer", new GetHandler(), new FreeMarkerEngine());
    Spark.get("/evaluate", new ProgramHandler());
  }

  private static class GetHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(final Request req, final Response res) {
      Map<String, Object> variables = ImmutableMap.of();
      return new ModelAndView(variables, "index.ftl");
    }
  }

  private class ProgramHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {

      String requests = req.params("exps");

      Command[] comms = GSON.fromJson(requests, Command[].class);

      List<Expression<?>> exprs = Parser.parse(comms);
      runtime = new BrewerRuntime();
      runtime.setProgram(exprs);

      //TODO in a separate thread
      runtime.run();

      return GSON.toJson(runtime.getLogs());
    }

  }
}
