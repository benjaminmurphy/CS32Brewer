package edu.brown.cs.brewer.junit.Parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import edu.brown.cs.brewer.BrewerRuntime;
import edu.brown.cs.brewer.Variable;
import edu.brown.cs.brewer.handlers.Parser;
import edu.brown.cs.brewer.handlers.Parser.BrewerParseException;

/**
 * The Junit test for the Parser.java class.
 *
 * All JSON strings were obtained by creating the program in the frontend and
 * logging the JSON to the console.
 *
 * Below each test will be some pseudocode.
 * 
 * @author Shi
 *
 */
public class ParserTests {
  Gson GSON = new Gson();
  BrewerRuntime br;

  @Before
  public void init() {
  }

  // Basic functionality testing of blocks

  // Simple setGet test with number type
  /**
   * x = 5;
   * get(x) == 5;
   */
  @Test
  public void setGet() {
    String set = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"x\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":5,\"class\":\"number\"}}]}";
    try {
      br = Parser.parseJSONProgram(set);
      br.run();

      Map<String, Variable> vars = br.getVariables();
      Object value = vars.get("x").getValue();
      assertTrue((double) value == 5);

    } catch (BrewerParseException | ParseException e) {
      fail("setGet test failed");
      e.printStackTrace();
    }
  }

  // Simple setPrint test with String type
  /**
   * x = "foo";
   * print(x);
   */
  @Test
  public void setPrint() {
    String setPrint = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"x\",\"class\":\"string\"},\"value\":{\"type\":\"literal\",\"value\":\"foo\",\"class\":\"string\"}},{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"x\",\"class\":\"string\"}}]}";

    String setValue = "foo";
    try {
      br = Parser.parseJSONProgram(setPrint);
      br.run();

      Map<String, Variable> vars = br.getVariables();
      String value = (String) vars.get("x").getValue();

      assertTrue(value.equals(setValue));

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", setValue);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("setPrint test failed");
      e.printStackTrace();
    }
  }

  // Simple ifThen test, if clause pass
  /**
   * if true:
   *    print(success!);
   */
  @Test
  public void ifThenIfClause() {
    String ifThenIfClause = "{\"main\":[{\"type\":\"if\",\"commands\":[{\"type\":\"print\",\"name\":{\"type\":\"literal\",\"value\":\"success!\",\"class\":\"string\"}}],\"condition\":{\"type\":\"literal\",\"value\":true,\"class\":\"bool\"}}]}";
    String expected = "success!";
    try {
      br = Parser.parseJSONProgram(ifThenIfClause);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("ifThenIfClause test failed");
      e.printStackTrace();
    }
  }

  // Simple ifThen test, if clause fail
  /**
   * if false:
   *    print(failure!);
   */
  @Test
  public void ifThenClauseFail() {
    String ifThenClauseFail = "{\"main\":[{\"type\":\"if\",\"commands\":[{\"type\":\"print\",\"name\":{\"type\":\"literal\",\"value\":\"failure!\",\"class\":\"string\"}}],\"condition\":{\"type\":\"literal\",\"value\":false,\"class\":\"bool\"}}]}";
    try {
      br = Parser.parseJSONProgram(ifThenClauseFail);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String emptyLog = "[]";

      assertTrue(outputLog.equals(emptyLog));
    } catch (BrewerParseException | ParseException e) {
      fail("ifThenIfClause test failed");
      e.printStackTrace();
    }
  }

  // Simple ifElse test, if clause pass
  /**
   * if true:
   *    print("success!");
   * else:
   *    print("sadness :(");
   */
  @Test
  public void ifElseIfClause() {
    String ifElseIfClause = "{\"main\":[{\"type\":\"ifelse\",\"condition\":{\"type\":\"literal\",\"value\":true,\"class\":\"bool\"},\"commands\":[{\"type\":\"print\",\"name\":{\"type\":\"literal\",\"value\":\"success!\",\"class\":\"string\"}}],\"else\":[{\"type\":\"print\",\"name\":{\"type\":\"literal\",\"value\":\"sadness :(\",\"class\":\"string\"}}]}]}";
    String expected = "success!";
    try {
      br = Parser.parseJSONProgram(ifElseIfClause);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("ifElseIfClause test failed");
      e.printStackTrace();
    }
  }

  // Simple ifElse test, deferred to else clause
  /**
   * if false:
   *    print("sadness :(");
   * else:
   *    print("success!");
   */
  @Test
  public void ifElseElseClause() {
    String ifElseElseClause = "{\"main\":[{\"type\":\"ifelse\",\"condition\":{\"type\":\"literal\",\"value\":false,\"class\":\"bool\"},\"commands\":[{\"type\":\"print\",\"name\":{\"type\":\"literal\",\"value\":\"sadness :(\",\"class\":\"string\"}}],\"else\":[{\"type\":\"print\",\"name\":{\"type\":\"literal\",\"value\":\"success!\",\"class\":\"string\"}}]}]}";
    String expected = "success!";
    try {
      br = Parser.parseJSONProgram(ifElseElseClause);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("ifElseElseClause test failed");
      e.printStackTrace();
    }
  }

  // Simple Equality Testing
  // Equal
  /**
   * a = (0 == 0);
   * print a;
   */
  @Test
  public void equalityEqual() {
    String equalityEqual = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"},\"value\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"name\":\"eq\"}},{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"}}]}";
    boolean expected = true;
    try {
      br = Parser.parseJSONProgram(equalityEqual);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("equalityEqual test failed");
      e.printStackTrace();
    }
  }

  // Not Equal
  /**
   * a = (0 == 1);
   * print a;
   */
  @Test
  public void equalityNotEqual() {
    String equalityNotEqual = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"},\"value\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"eq\"}},{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"}}]}";
    boolean expected = false;
    try {
      br = Parser.parseJSONProgram(equalityNotEqual);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("equalityNotEqual test failed");
      e.printStackTrace();
    }
  }

  // Less Than
  /**
   * a = (0 < 1);
   * print a;
   */
  @Test
  public void equalityLessThan() {
    String equalityLessThan = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"},\"value\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"less\"}},{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"}}]}";
    boolean expected = true;
    try {
      br = Parser.parseJSONProgram(equalityLessThan);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("equalityLessThan test failed");
      e.printStackTrace();
    }
  }

  // Not Less Than
  /**
   * a = (0 < 0);
   * print a;
   */
  @Test
  public void equalityNotLessThan() {
    String equalityNotLessThan = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"},\"value\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"name\":\"less\"}},{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"}}]}";
    boolean expected = false;
    try {
      br = Parser.parseJSONProgram(equalityNotLessThan);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("equalityNotLessThan test failed");
      e.printStackTrace();
    }
  }

  // Greater Than
  /**
   * a = (1 > 0);
   * print a;
   */
  @Test
  public void equalityGreaterThan() {
    String equalityGreaterThan = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"},\"value\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"name\":\"greater\"}},{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"}}]}";
    boolean expected = true;
    try {
      br = Parser.parseJSONProgram(equalityGreaterThan);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("equalityGreaterThan test failed");
      e.printStackTrace();
    }
  }

  // Not Greater Than
  /**
   * a = (0 > 0);
   * print a;
   */
  @Test
  public void equalityNotGreaterThan() {
    String equalityNotGreaterThan = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"},\"value\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"name\":\"greater\"}},{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"bool\"}}]}";
    boolean expected = false;
    try {
      br = Parser.parseJSONProgram(equalityNotGreaterThan);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("equalityNotGreaterThan test failed");
      e.printStackTrace();
    }
  }

  // And Pass
  /**
   * And pass test.
   * 
   * print(true and true);
   */
  @Test
  public void andPass() {
    String andPass = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"logic_operator\",\"name\":\"and\",\"arg1\":{\"type\":\"literal\",\"value\":true,\"class\":\"bool\"},\"arg2\":{\"type\":\"literal\",\"value\":true,\"class\":\"bool\"}}}]}";
    boolean expected = true;
    try {
      br = Parser.parseJSONProgram(andPass);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("andPass test failed");
      e.printStackTrace();
    }
  }

  // And Fail
  /**
   * And fail test1.
   * 
   * print(false and true);
   */
  @Test
  public void andFail() {
    String andFail = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"logic_operator\",\"name\":\"and\",\"arg1\":{\"type\":\"literal\",\"value\":false,\"class\":\"bool\"},\"arg2\":{\"type\":\"literal\",\"value\":true,\"class\":\"bool\"}}}]}";
    boolean expected = false;
    try {
      br = Parser.parseJSONProgram(andFail);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("andFail test failed");
      e.printStackTrace();
    }
  }

  // And Fail2
  /**
   * And pass test2.
   * 
   * print(true and false);
   */
  @Test
  public void andFail2() {
    String andFail2 = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"logic_operator\",\"name\":\"and\",\"arg1\":{\"type\":\"literal\",\"value\":true,\"class\":\"bool\"},\"arg2\":{\"type\":\"literal\",\"value\":false,\"class\":\"bool\"}}}]}";
    boolean expected = false;
    try {
      br = Parser.parseJSONProgram(andFail2);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("andFail2 test failed");
      e.printStackTrace();
    }
  }

  // Or Pass
  /**
   * Or pass test1.
   * 
   * print(true or false);
   */
  @Test
  public void orPass() {
    String orPass = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"logic_operator\",\"name\":\"or\",\"arg1\":{\"type\":\"literal\",\"value\":true,\"class\":\"bool\"},\"arg2\":{\"type\":\"literal\",\"value\":true,\"class\":\"bool\"}}}]}";
    boolean expected = true;
    try {
      br = Parser.parseJSONProgram(orPass);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("orPass test failed");
      e.printStackTrace();
    }
  }

  // Or Pass2
  /**
   * Or pass test2.
   * 
   * print(false or true);
   */
  @Test
  public void orPass2() {
    String orPass2 = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"logic_operator\",\"name\":\"or\",\"arg1\":{\"type\":\"literal\",\"value\":false,\"class\":\"bool\"},\"arg2\":{\"type\":\"literal\",\"value\":true,\"class\":\"bool\"}}}]}";
    boolean expected = true;
    try {
      br = Parser.parseJSONProgram(orPass2);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("orPass2 test failed");
      e.printStackTrace();
    }
  }

  // Or Fail
  /**
   * Or fail test.
   * 
   * print(false or false);
   */
  @Test
  public void orFail() {
    String orFail = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"logic_operator\",\"name\":\"or\",\"arg1\":{\"type\":\"literal\",\"value\":false,\"class\":\"bool\"},\"arg2\":{\"type\":\"literal\",\"value\":false,\"class\":\"bool\"}}}]}";
    boolean expected = false;
    try {
      br = Parser.parseJSONProgram(orFail);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("orFail test failed");
      e.printStackTrace();
    }
  }

  // Not Pass
  /**
   * Not pass test.
   * 
   * print(not (true));
   */
  @Test
  public void notPass() {
    String notPass = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"unary_operator\",\"name\":\"not\",\"arg1\":{\"type\":\"literal\",\"value\":true,\"class\":\"bool\"}}}]}";
    boolean expected = false;
    try {
      br = Parser.parseJSONProgram(notPass);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("notPass test failed");
      e.printStackTrace();
    }
  }

  // Not Fail
  /**
   * Not fail test.
   * 
   * print(not (false));
   */
  @Test
  public void notFail() {
    String notFail = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"unary_operator\",\"name\":\"not\",\"arg1\":{\"type\":\"literal\",\"value\":false,\"class\":\"bool\"}}}]}";
    boolean expected = true;
    try {
      br = Parser.parseJSONProgram(notFail);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %s]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("notFail test failed");
      e.printStackTrace();
    }
  }

  @Test
  /**
   * Simple addition test
   * print(3.25 + 1.111)
   */
  public void add() {
    String add = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"literal\",\"value\":3.25,\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":1.111,\"class\":\"number\"},\"name\":\"add\"}}]}";
    double expected = 3.25 + 1.111;
    try {
      br = Parser.parseJSONProgram(add);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %.3f]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("add test failed");
      e.printStackTrace();
    }
  }

  @Test
  /**
   * Simple subtraction test
   * print(3.25 - 1.111)
   */
  public void subtract() {
    String sub = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"literal\",\"value\":3.25,\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":1.111,\"class\":\"number\"},\"name\":\"sub\"}}]}";
    double expected = 3.25 - 1.111;

    try {
      br = Parser.parseJSONProgram(sub);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String
        .format("[Log: MESSAGE: %.16f]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("subtract test failed");
      e.printStackTrace();
    }
  }

  @Test
  /**
   * Simple multiplication test
   * print(3.25 * 1.111)
   */
  public void multiply() {
    String mul = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"literal\",\"value\":3.25,\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":1.111,\"class\":\"number\"},\"name\":\"mul\"}}]}";
    double expected = 3.25 * 1.111;
    try {
      br = Parser.parseJSONProgram(mul);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String.format("[Log: MESSAGE: %.5f]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("multiply test failed");
      e.printStackTrace();
    }
  }

  @Test
  /**
   * Simple divide test
   * print(3.25 / 1.111)
   */
  public void divide() {
    String div = "{\"main\":[{\"type\":\"print\",\"name\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"literal\",\"value\":3.25,\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":1.111,\"class\":\"number\"},\"name\":\"div\"}}]}";
    double expected = 3.25 / 1.111;
    try {
      br = Parser.parseJSONProgram(div);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = String
        .format("[Log: MESSAGE: %.16f]", expected);

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      fail("divide test failed");
      e.printStackTrace();
    }
  }

  // The following tests test order of commands.
  /**
   * Printing in while loop, subtraction first.
   * 
   * a = 5;
   * while a > 0:
   *    a -= 1;
   *    print(a);
   */
  @Test
  public void whilePrintSubtractFirst() {
    String whilePrint = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":5,\"class\":\"number\"}},{\"type\":\"while\",\"condition\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"name\":\"greater\"},\"commands\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"sub\"}},{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"}}]}]}";
    try {
      br = Parser.parseJSONProgram(whilePrint);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = "[Log: MESSAGE: 4.0, Log: MESSAGE: 3.0, Log: MESSAGE: 2.0, Log: MESSAGE: 1.0, Log: MESSAGE: 0.0]";

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      e.printStackTrace();
      fail("whilePrintSubtractFirst test failed");
    }
  }

  /**
   * Printing in while loop, print first.
   * 
   * a = 5;
   * while a > 0:
   *    print(a);
   *    a -= 1;
   */
  @Test
  public void whilePrintPrintFirst() {
    String whilePrint = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":5,\"class\":\"number\"}},{\"type\":\"while\",\"condition\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"},\"name\":\"greater\"},\"commands\":[{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"sub\"}}]}]}";
    try {
      br = Parser.parseJSONProgram(whilePrint);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = "[Log: MESSAGE: 5.0, Log: MESSAGE: 4.0, Log: MESSAGE: 3.0, Log: MESSAGE: 2.0, Log: MESSAGE: 1.0]";

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      e.printStackTrace();
      fail("whilePrintPrintFirst test failed");
    }
  }

  // The following tests test nested commands.
  /**
   * Nesting some arithmetic.
   * a = 8;
   * a = (2 * ((a / 2) + 1));
   */
  @Test
  public void nestedArithmetic() {
    String nestedArithmetic = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"value\":{\"type\":\"literal\",\"value\":8,\"class\":\"number\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"literal\",\"value\":2,\"class\":\"number\"},\"arg2\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"var\",\"class\":\"number\",\"name\":\"a\"},\"arg2\":{\"type\":\"literal\",\"value\":2,\"class\":\"number\"},\"name\":\"div\"},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"add\"},\"name\":\"mul\"}}]}";
    try {
      br = Parser.parseJSONProgram(nestedArithmetic);
      br.run();

      Map<String, Variable> vars = br.getVariables();
      Object value = vars.get("a").getValue();
      assertTrue((double) value == 10.0);

    } catch (BrewerParseException | ParseException e) {
      e.printStackTrace();
      fail("nestedArithmetic test failed");
    }
  }

  // Testing precision and if/else
  /**
   * pi = 3.1415926545
   * a = 3.1415926544;
   * if (pi == a):
   *    a = 1;
   * else
   *    a = 0;
   * print(a);
   */
  @Test
  public void precision1() {
    String precisionTest = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"pi\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":3.1415926545,\"class\":\"number\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":3.1415926544,\"class\":\"number\"}},{\"type\":\"ifelse\",\"condition\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"var\",\"name\":\"pi\",\"class\":\"number\"},\"arg2\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"name\":\"eq\"},\"commands\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"}}],\"else\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":0,\"class\":\"number\"}}]},{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"}}]}";
    try {
      br = Parser.parseJSONProgram(precisionTest);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = "[Log: MESSAGE: 0.0]";

      assertTrue(outputLog.equals(expectedLog));
    } catch (BrewerParseException | ParseException e) {
      e.printStackTrace();
      fail("preicision1 test failed");
    }
  }

  /**
   * pi = 3.1415926545;
   * a = 3.1415926545;
   * if (pi == a):
   *    a = 0;
   * else
   *    a = 1;
   * print(a);
   */

  // More complicated factorial test.
  /**
   * a = 10;
   * b = 1;
   * 
   * while a > 1:
   *    print(b);
   *    b = a * b;
   *    a = a - 1;
   * print(b);
   */
  @Test
  public void factorial() {
    String factorial = "{\"main\":[{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":10,\"class\":\"number\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"b\",\"class\":\"number\"},\"value\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"}},{\"type\":\"while\",\"condition\":{\"type\":\"comparison\",\"arg1\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"greater\"},\"commands\":[{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"b\",\"class\":\"number\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"b\",\"class\":\"number\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"arg2\":{\"type\":\"var\",\"name\":\"b\",\"class\":\"number\"},\"name\":\"mul\"}},{\"type\":\"set\",\"name\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"value\":{\"type\":\"numeric_operator\",\"arg1\":{\"type\":\"var\",\"name\":\"a\",\"class\":\"number\"},\"arg2\":{\"type\":\"literal\",\"value\":1,\"class\":\"number\"},\"name\":\"sub\"}}]},{\"type\":\"print\",\"name\":{\"type\":\"var\",\"name\":\"b\",\"class\":\"number\"}}]}";
    try {
      br = Parser.parseJSONProgram(factorial);
      br.run();

      String outputLog = Arrays.toString(br.getLogs().toArray());
      String expectedLog = "[Log: MESSAGE: 1.0, Log: MESSAGE: 10.0, Log: MESSAGE: 90.0, Log: MESSAGE: 720.0, Log: MESSAGE: 5040.0, Log: MESSAGE: 30240.0, Log: MESSAGE: 151200.0, Log: MESSAGE: 604800.0, Log: MESSAGE: 1814400.0, Log: MESSAGE: 3628800.0]";
      assertTrue(outputLog.equals(expectedLog));

      Map<String, Variable> vars = br.getVariables();
      Object value = vars.get("b").getValue();
      assertTrue((double) value == 3628800.0);

    } catch (BrewerParseException | ParseException e) {
      e.printStackTrace();
      fail("factorial test failed");
    }
  }

}
