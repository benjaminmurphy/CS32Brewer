package edu.brown.cs.brewer;

/**
 * Represents a log message, with a String message and a boolean indicating
 * whether or not this message is an error
 *
 * @author raphaelkargon
 *
 */
public class Log {
  /**
   * The message represented by this log element.
   */
  private String msg;
  /**
   * Whether or not this message is an error.
   */
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
   * @param msg The log message
   * @param isError Whether this message is an error
   */
  public Log(final String _msg, final boolean _isError) {
    super();
    this.msg = _msg;
    this.isError = _isError;
  }

  @Override
  public String toString() {
    String out = "Log: ";
    if (isError) {
      out += "ERROR: ";
    } else {
      out += "MESSAGE: ";
    }

    return out + this.msg;
  }
}
