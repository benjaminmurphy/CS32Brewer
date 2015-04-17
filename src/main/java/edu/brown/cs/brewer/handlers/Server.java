package edu.brown.cs.brewer.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

  private static ExecutorService pool;
  private static final int DEFAULT_NUM_THREADS = 1;

  public Server(int portNum, Integer numThreads) {
    Server.portNum = portNum;

    if (numThreads != null && numThreads >= 0) {
      Server.pool = Executors.newFixedThreadPool(numThreads);
    } else {
      Server.pool = Executors.newFixedThreadPool(DEFAULT_NUM_THREADS);
    }
  }

  // Server Handling
  public void runSparkServer() throws IOException {
    Spark.externalStaticFileLocation("src/main/resources");
    Spark.setPort(portNum);
    Spark.get("/brewer", new GetHandler(), new FreeMarkerEngine());
    Spark.get("/evaluate", new ProgramHandler());
  }

  private static class GetHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(final Request req, final Response res) {
      Map<String, Object> variables = ImmutableMap.of();
      return new ModelAndView(variables, "main.ftl");
    }
  }

  private class ProgramHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {

      String requests = req.params("exps");

      Command[] comms = GSON.fromJson(requests, Command[].class);

      BrewerRuntime runtime = Parser.parseJSONProgram(requests);
      // TODO make runtime fields instance variables so they can be used in a
      // multithreaded purpose
      Future<?> status = pool.submit(runtime);

      try {
        if (status.get() == null) {
          return GSON.toJson(runtime.getLogs());
        }
      } catch (InterruptedException | ExecutionException e) {
        System.err.println("ERROR: Runtime thread failed.");
      }
      return null;
    }

  }
}
