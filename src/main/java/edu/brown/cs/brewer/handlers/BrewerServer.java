package edu.brown.cs.brewer.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import storage.Database;

/**
 * The server for the Brewer program. It accepts requests to run programs (given
 * as JSON) and returns the outputs of logs.
 *
 * @author raphaelkargon
 *
 */
public class BrewerServer {

  // TODO This could be passed in as an argument to the constructor so the save
  // folder could be changed easily.
  private static String savesPath = "databases/";

  /**
   * The map of saves for sessions.
   */
  private static Map<String, Database> saves;

  /**
   * The port number for the server.
   */
  private static int portNum;

  /**
   * The runtime object used to store and run programs.
   */
  private static BrewerRuntime runtime;

  /**
   * The Gson object used to parse requests.
   */
  private static Gson gson = new Gson();

  /**
   * This runs the server with a given port.
   *
   * @param port The port on which to host the server.
   */
  public static void runServer(int port) {
    portNum = port;
    runSparkServer();
  }

  /**
   * Sets up the spark server.
   */
  private static void runSparkServer() {
    Spark.setPort(portNum);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.get("/", new IndexHandler(), new FreeMarkerEngine());
    Spark.post("/run", new RunHandler());
    Spark.post("/logs", new LogHandler());
    Spark.post("/kill", new KillHandler());
    Spark.post("/save", new SaveHandler());
  }

  /**
   * Servres the GUI to the client.
   *
   * @author raphaelkargon
   *
   */
  private static final class IndexHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response resp) {
      return new ModelAndView(null, "main.ftl");
    }
  }

  /**
   * Receives programs and runs them. The logs produced by the program are
   * returned.
   *
   * @author raphaelkargon
   *
   */
  private static final class RunHandler implements Route {

    @Override
    public Object handle(Request req, Response resp) {

      ImmutableMap.Builder<String, Object> variables = new ImmutableMap.Builder<String, Object>();
      try {
        System.out.println(req.body());
        runtime = Parser.parseJSONProgram(req.body());
        runtime.run();
        variables.put("status", "success");
      } catch (BrewerParseException | ParseException e) {
        List<Log> logs = new ArrayList<>();
        logs.add(new Log(e.getMessage(), true));
        variables.put("messages", logs);
        variables.put("status", "failure");
      }
      return gson.toJson(variables.build());

    }
  }

  /**
   * Returns the logs for a given program, and clears them.
   *
   * @author raphaelkargon
   *
   */
  private static final class LogHandler implements Route {
    @Override
    public Object handle(Request req, Response resp) {

      ImmutableMap.Builder<String, Object> variables = new ImmutableMap.Builder<String, Object>();

      if (runtime != null) {
        List<Log> logs = runtime.getLogs();
        runtime.clearLogs();

        if (runtime.isRunning()) {
          variables.put("running", true);
        } else {
          variables.put("running", false);
        }

        variables.put("status", "success");
        variables.put("messages", logs);
      }

      else {
        variables.put("status", "failure");
      }

      return gson.toJson(variables.build());

    }
  }

  /**
   * Kills a program if it is already running.
   *
   * @author raphaelkargon
   *
   */
  private static final class KillHandler implements Route {
    @Override
    public Object handle(Request req, Response resp) {

      if (runtime != null) {
        runtime.kill();

        ImmutableMap.Builder<String, Object> variables = new ImmutableMap.Builder<String, Object>();

        variables.put("status", "success");

        return gson.toJson(variables.build());
      }

      ImmutableMap.Builder<String, Object> variables = new ImmutableMap.Builder<String, Object>();

      variables.put("status", "failure");

      return gson.toJson(variables.build());

    }
  }

  /**
   * Saves a program to the database.
   * @author Shi
   *
   */
  private static final class SaveHandler implements Route {
    @Override
    public Object handle(Request req, Response resp) {

      String dbId = gson.fromJson(req.queryParams("sessionId"),
        String.class);
      Database db = saves.get(dbId);

      ImmutableMap.Builder<String, Object> variables = new ImmutableMap.Builder<String, Object>();

      try {
        if (db == null) {
          String dbPath = savesPath + dbId;
          db = new Database(dbPath);
          saves.put(dbId, db);
        }

        String[] program = gson.fromJson(req.body(), String[].class);
        String programId = program[0];
        String programJSON = program[1];

        db.addProgram(programId, programJSON);
        
      } catch (SQLException e) {
        variables.put("status", "save failed");
        return gson.toJson(variables.build());
      }

      variables.put("status", "saved");
      return gson.toJson(variables.build());
    }
  }
}
