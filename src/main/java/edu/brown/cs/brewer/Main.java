package edu.brown.cs.brewer;

import java.util.Scanner;

import org.json.simple.parser.ParseException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import edu.brown.cs.brewer.handlers.BrewerServer;
import edu.brown.cs.brewer.handlers.Parser;
import edu.brown.cs.brewer.handlers.Parser.BrewerParseException;

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
   * This runs the server.
   *
   * @param args Command line arguments. Currently just "port", the port number.
   */
  public static void main(final String[] args) {
    OptionParser parser = new OptionParser();
    OptionSpec<Integer> portSpec =
        parser.accepts("port").withRequiredArg().ofType(Integer.class);
    parser.accepts("gui");
    OptionSet parsedArgs = parser.parse(args);

    int port = DEFAULT_PORT;
    if (parsedArgs.has("port")) {
      port = portSpec.value(parsedArgs);
    }

    if (parsedArgs.has("gui")) {
      BrewerServer.runServer(port);
    } else {
      Scanner in = new Scanner(System.in);
      BrewerRuntime runtime;
      String query;
      System.out.println("Query: ");
      while ((query = in.nextLine()).trim().length() > 0) {
        try {
          runtime = Parser.parseJSONProgram(query);
          runtime.run();
          for (Log l : runtime.getLogs()) {
            if (l.isError()) {
              System.out.print("ERROR: ");
            } else {
              System.out.print("MESSAGE: ");
            }
            System.out.println(l.getMsg());
          }
        } catch (ParseException | BrewerParseException e) {
          System.out.println("ERROR: " + e);
        }
        System.out.println("Query: ");
      }
      in.close();
    }
  }
}
