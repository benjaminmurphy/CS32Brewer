package edu.brown.cs.brewer;

import java.util.Arrays;

import edu.brown.cs.brewer.handlers.Parser;

public class Main {
  private static final int DEFAULT_PORT = 4567;

  public static void main(String[] args) {
    // Server server = new Server(DEFAULT_PORT);
    // String factorial =
    // {"main":[{"type":"set","name":{"type":"var","class":"number","name":"a"},"value":{"type":"literal","value":6,"class":"number"}},{"type":"set","name":{"type":"var","class":"number","name":"b"},"value":{"type":"literal","value":1,"class":"number"}},{"type":"while","condition":{"type":"comparison","arg1":{"type":"get","name":{"type":"var","class":"number","name":"a"}},"arg2":{"type":"literal","value":1,"class":"number"},"name":"greater"},"commands":[{"type":"set","name":{"type":"var","class":"number","name":"b"},"value":{"type":"numeric_operator","arg1":{"type":"get","name":{"type":"var","class":"number","name":"b"}},"arg2":{"type":"get","name":{"type":"var","class":"number","name":"a"}},"name":"mul"}},{"type":"set","name":{"type":"var","class":"number","name":"a"},"value":{"type":"numeric_operator","arg1":{"type":"get","name":{"type":"var","class":"number","name":"a"}},"arg2":{"type":"literal","value":1,"class":"number"},"name":"sub"}}]}]}`;
    String factorial =
        "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"value\":{\"type\":\"literal\",\"value\":6,\"class\":\"number\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"},\"value\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"}},{\"type\":\"while\",\"condition\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"greater\"},\"commands\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"}},\"arg2\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"name\":\"mul\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"sub\"}}]}]}";
    BrewerRuntime br = Parser.parseJSONProgram(factorial, null);
    br.run();
    System.out.println(Arrays.toString(br.getLogs().toArray()));
  }
}
