package edu.brown.cs.brewer;

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

  public static void main(String[] args) {
    runSpankServer();
  }

  public static void runSpankServer() {
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
      return new ModelAndView(variables, "main.ftl");
    }
  }
}
