package edu.brown.cs.brewer;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import edu.brown.cs.brewer.handlers.BrewerServer;


public class Main {
  private static final int DEFAULT_PORT = 4567;

  public static void main(String[] args) {
    OptionParser parser = new OptionParser();
    OptionSpec<Integer> portSpec =
        parser.accepts("port").withRequiredArg().ofType(Integer.class);
    OptionSet parsedArgs = parser.parse(args);

    int port = DEFAULT_PORT;
    if (parsedArgs.has("port")) {
      port = portSpec.value(parsedArgs);
    }

    BrewerServer.runServer(port);
  }

}
