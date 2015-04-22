package edu.brown.cs.brewer;


public class Main {
  private static final int DEFAULT_PORT = 4567;

  public static void main(String[] args) {

    // BrewerServer.runServer(DEFAULT_PORT);


    // Server server = new Server(DEFAULT_PORT);

    // testing json parser
    /*
     * String factorial =
     * "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"value\":{\"type\":\"literal\",\"value\":6,\"class\":\"number\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"},\"value\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"}},{\"type\":\"while\",\"condition\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"greater\"},\"commands\":[{type : \"print\", name : \"b\"}, {\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"}},\"arg2\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"name\":\"mul\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"sub\"}}]}, {type : \"print\", name : \"b\"}]}"
     * ; BrewerRuntime br; try { br = Parser.parseJSONProgram(factorial, null);
     * br.setRunner(new Runner(null, 0)); br.run();
     * System.out.println(Arrays.toString(br.getLogs().toArray())); } catch
     * (BrewerParseException e) { // TODO Auto-generated catch block
     * e.printStackTrace(); }
     */

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
