package edu.brown.cs.brewer;

/**
 * Represents a Variable and its type.
 *
 * @author raphaelkargon
 *
 */
public class Variable {
  /**
   * The value stored by this variable.
   */
  private Object value;

  /**
   * The value's type.
   */
  private Class<?> type;

  /**
   * Creates a new Variable object.
   *
   * @param o The variable value
   * @param t The variabe type
   */
  public Variable(final Object o, final Class<?> t) {
    if (!t.isAssignableFrom(o.getClass())) {
      throw new IllegalArgumentException("Variable data is of type \""
          + o.getClass() + "\" while the given type is \"" + t + "\".");
    }
    this.value = o;
    this.type = t;
  }

  /**
   * Returns the value.
   *
   * @return The variable value.
   */
  public Object getValue() {
    return value;
  }

  /**
   * Sets the value of this variable.
   *
   * @param newval The new variable value.
   */
  public void setValue(final Object newval) {
    this.value = type.cast(newval);
  }

  /**
   * The variable's type.
   *
   * @return The Class object representing the variable's type.
   */
  public Class<?> getType() {
    return type;
  }

  @Override
  public boolean equals(final Object obj) {
    return value.equals(obj);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
