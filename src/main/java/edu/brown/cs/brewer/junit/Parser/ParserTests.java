package edu.brown.cs.brewer.junit.Parser;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.Variable;
import edu.brown.cs.brewer.handlers.Parser;
import edu.brown.cs.brewer.handlers.Parser.BrewerParseException;
import edu.brown.cs.brewer.handlers.Stream;

/**
 * The Junit test for the Parser.java class.
 *
 *
 * @author Shi
 *
 */
public class ParserTests {

  String t1 =
      "{\"main\":[{\"type\":\"set\",\"name\":\"x\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":\"5\",\"class\":\"number\"}},{\"type\":\"get\",\"name\":\"x\"}]}";

  Gson GSON = new Gson();

  Stream.Runner runner;

  @Before
  public void init() {}

  @Test
  public void test() {
    BrewerRuntime runtime;
    try {
      runtime = Parser.parseJSONProgram(t1, null);
      Map<String, Variable<?>> vars = runtime.getVariables();
      Object value = vars.get("\"x\"").getValue();
      assertTrue((int) value == 5);
    } catch (BrewerParseException e) {
      // TODO auto-generated
      e.printStackTrace();
    }
  }
}
