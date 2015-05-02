package edu.brown.cs.brewer;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import edu.brown.cs.brewer.handlers.BrewerServer;

/**
 * Runs a Brewer Server that accepts requests from clients and runs
 * user-generated programs.
 *
 * @author raphaelkargon
 *
 */
public final class Main {
  /**
   * The default port number.
   */
  private static final int DEFAULT_PORT = 4567;

  /**
   * Private constructor; not used.
   */
  private Main() {
    ;
  }

  /**
   * This runs the server.
   *
   * @param args Command line arguments. Currently just "port", the port number.
   */
  public static void main(final String[] args) {
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
