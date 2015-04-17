package edu.brown.cs.brewer;

import edu.brown.cs.brewer.handlers.Server;

public class Main {
  private static final int DEFAULT_PORT = 4567;
  
  public static void main(String[] args) {    
    Server server = new Server(DEFAULT_PORT);
  }
}
