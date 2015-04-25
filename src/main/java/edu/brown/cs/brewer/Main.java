package edu.brown.cs.brewer;

import edu.brown.cs.brewer.handlers.BrewerServer;


public class Main {
  private static final int DEFAULT_PORT = 4567;

  public static void main(String[] args) {
    BrewerServer.runServer(DEFAULT_PORT);
  }

}
