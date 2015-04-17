package edu.brown.cs.brewer.handlers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

public class Server {
  private int portNum;

  public Server(int portNum) {
    this.portNum = portNum;
    new Stream(2567);
    runSparkServer();
  }

  // Server Handling
  public void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources");
    Spark.setPort(portNum);
    Spark.get("/brewer", new GetHandler(), new FreeMarkerEngine());
  }

  private static class GetHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(final Request req, final Response res) {
      return new ModelAndView(null, "main.ftl");
    }
  }
}
