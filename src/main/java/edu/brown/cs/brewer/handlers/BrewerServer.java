package edu.brown.cs.brewer.handlers;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.Log;
import edu.brown.cs.brewer.handlers.Parser.BrewerParseException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

public class BrewerServer {
  private static int portNum;
  private static BrewerRuntime runtime;
  private static Gson gson = new Gson();

  public static void runServer(int port) {
    portNum = port;
    runSparkServer();
  }

  private static void runSparkServer() {
    Spark.setPort(portNum);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.get("/", new IndexHandler(), new FreeMarkerEngine());
    Spark.post("/run", new RunHandler());
    Spark.post("/logs", new LogHandler());
    Spark.post("/kill", new KillHandler());
  }

  private static final class IndexHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response resp) {
      return new ModelAndView(null, "main.ftl");
    }
  }

  private static final class RunHandler implements Route {

    @Override
    public Object handle(Request req, Response resp) {

      ImmutableMap.Builder<String, Object> variables =
          new ImmutableMap.Builder<String, Object>();
      try {
        runtime = Parser.parseJSONProgram(req.body());
        runtime.run();
        variables.put("status", "success");
      } catch (BrewerParseException | ParseException e) {
        List<Log> logs = new ArrayList<>();
        logs.add(new Log(e.getMessage(), true));
        variables.put("error", logs);
        variables.put("status", "failure");
      }
      return gson.toJson(variables.build());

    }
  }

  private static final class LogHandler implements Route {
    @Override
    public Object handle(Request req, Response resp) {

      ImmutableMap.Builder<String, Object> variables =
          new ImmutableMap.Builder<String, Object>();

      if (runtime != null) {
        List<Log> logs = runtime.getLogs();
        runtime.clearLogs();

        if (runtime.isRunning()) {
          variables.put("running", true);
        } else {
          variables.put("running", false);
        }

        // List<String> messages = new ArrayList<String>();
        // for (Log l : logs) {
        // messages.add(l.getMsg());
        // }

        variables.put("status", "success");
        variables.put("messages", logs);
      }

      else {
        variables.put("status", "failure");
      }

      return gson.toJson(variables.build());

    }
  }

  private static final class KillHandler implements Route {
    @Override
    public Object handle(Request req, Response resp) {

      if (runtime != null) {
        runtime.kill();

        ImmutableMap.Builder<String, Object> variables =
            new ImmutableMap.Builder<String, Object>();

        variables.put("status", "success");

        return gson.toJson(variables.build());
      }

      ImmutableMap.Builder<String, Object> variables =
          new ImmutableMap.Builder<String, Object>();

      variables.put("status", "failure");

      return gson.toJson(variables.build());


    }
  }
}
