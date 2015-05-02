package edu.brown.cs.brewer;

/**
 * Represents a log message, with a String message and a boolean indicating
 * whether or not this message is an error.
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
  public final String getMsg() {
    return msg;
  }

  /**
   * @return the isError
   */
  public final boolean isError() {
    return isError;
  }

  /**
   * @param msgArg The log message
   * @param isErrorArg Whether this message is an error
   */
  public Log(final String msgArg, final boolean isErrorArg) {
    super();
    this.msg = msgArg;
    this.isError = isErrorArg;
  }

  @Override
  public final String toString() {
    String out = "Log: ";
    if (isError) {
      out += "ERROR: ";
    } else {
      out += "MESSAGE: ";
    }

    return out + this.msg;
  }
}
