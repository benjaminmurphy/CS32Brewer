package edu.brown.cs.brewer.handlers;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
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
    Spark.setPort(portNum);

    Spark.get("/evaluate", new ProgramHandler());
  }

  private class ProgramHandler implements Route {
    @Override
    public Object handle(final Request req, final Response res) {

      String requests = req.params("exps");

      Command[] comms = GSON.fromJson(requests, Command[].class);

      List<Expression<?>> exprs = Parser.parse(comms);

      for (Expression<?> expr : exprs) {
        expr.evaluate();
      }

      return GSON.toJson(runtime.getLogs());
    }

  }
}
