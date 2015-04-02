package edu.brown.cs.brewer;

/**
 * Represents a log message, with a String message and a boolean indicating whether or not this message is an error
 * @author raphaelkargon
 *
 */
public class Log {
  private String msg;
  private boolean isError;

  /**
   * @return the msg
   */
  public String getMsg() {
    return msg;
  }

  /**
   * @return the isError
   */
  public boolean isError() {
    return isError;
  }

  /**
   * @param msg
   * @param isError
   */
  private Log(String _msg, boolean _isError) {
    super();
    this.msg = _msg;
    this.isError = _isError;
  }
}
