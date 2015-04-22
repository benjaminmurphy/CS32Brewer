package edu.brown.cs.brewer.junit.Parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Runner;

import com.google.gson.Gson;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.Variable;
import edu.brown.cs.brewer.handlers.Parser;
import edu.brown.cs.brewer.handlers.Parser.BrewerParseException;

/**
 * The Junit test for the Parser.java class.
 *
 *
 * @author Shi
 *
 */
public class ParserTests {
  Gson GSON = new Gson();
  BrewerRuntime br;

  @Before
  public void init() {}

  @Test
  public void test1() {
    String setGet =
        "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"x\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":5,\"class\":\"number\"}},{\"type\":\"get\",\"name\":{\"type\":\"var\",\"name\":\"x\",\"class\":\"number\"}}]}";
    try {
      br = Parser.parseJSONProgram(setGet);
      br.run();

      Map<String, Variable> vars = br.getVariables();
      Object value = vars.get("x").getValue();
      assertTrue((double) value == 5);

    } catch (BrewerParseException | ParseException e) {
      fail("test1 failed");
      e.printStackTrace();
    }
  }

  public void test2() {
    String factorial =
        "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"value\":{\"type\":\"literal\",\"value\":6,\"class\":\"number\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"},\"value\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"}},{\"type\":\"while\",\"condition\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"greater\"},\"commands\":[{type : \"print\", name : \"b\"}, {\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"b\"}},\"arg2\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"name\":\"mul\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"get\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"}},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"sub\"}}]}, {type : \"print\", name : \"b\"}]}";
    try {
      br = Parser.parseJSONProgram(factorial);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog =
          "[Log: MESSAGE: b: 1.0, Log: MESSAGE: b: 6.0, Log: MESSAGE: b: 30.0, Log: MESSAGE: b: 120.0, Log: MESSAGE: b: 360.0, Log: MESSAGE: b: 720.0]";
      assertTrue(expectedLog.equals(outputLog));

      Map<String, Variable> vars = br.getVariables();
      Object value = vars.get("b").getValue();
      assertTrue((double) value == 720.0);

    } catch (BrewerParseException | ParseException e) {
      fail("test2 failed");
      e.printStackTrace();
    }
  }

  public void test3() {}
}
