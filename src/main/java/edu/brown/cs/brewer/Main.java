package edu.brown.cs.brewer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.brown.cs.brewer.expression.*;
import edu.brown.cs.brewer.handlers.Parser;

public class Main {
  private static final int DEFAULT_PORT = 4567;

  public static void main(String[] args) {
    // Server server = new Server(DEFAULT_PORT);

    // testing json parser
    String factorial =
        "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"value\":{\"type\":\"literal\",\"value\":6,\"class\":\"number\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"},\"value\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"}},{\"type\":\"while\",\"condition\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"greater\"},\"commands\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"}},\"arg2\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"name\":\"mul\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"sub\"}}]}]}";
    BrewerRuntime br = Parser.parseJSONProgram(factorial, null);
    br.run();


    // A simple example program:
    // BrewerRuntime simplebr = new BrewerRuntime();
    // List<Expression<?>> prog = new ArrayList<Expression<?>>();
    // prog.add(new SetCommand<Double>(simplebr, "x", new Literal<Double>(
    // simplebr, 5.0), Double.class));
    // prog.add(new SetCommand<Double>(simplebr, "x", new
    // MultiplicationOperator(
    // simplebr, new GetCommand<Double>(simplebr, "x", Double.class),
    // new Literal<Double>(simplebr, 2.0)), Double.class));
    // prog.add(new PrintExpression(simplebr, "x"));
    // simplebr.setProgram(prog);
    // simplebr.run();
    // System.out.println(Arrays.toString(simplebr.getLogs().toArray()));

  }
}
